package com.example.HospitalSystem.service.impl;

import com.example.HospitalSystem.constant.PaymentStatus;
import com.example.HospitalSystem.constant.PaymentType;
import com.example.HospitalSystem.constant.PaymentVerificationStatus;
import com.example.HospitalSystem.dto.response.PaymentResultResponse;
import com.example.HospitalSystem.entity.paymentsAndInvoices.Invoices;
import com.example.HospitalSystem.entity.paymentsAndInvoices.Payments;
import com.example.HospitalSystem.repository.InvoiceRepository;
import com.example.HospitalSystem.repository.PaymentRepository;
import com.example.HospitalSystem.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final InvoiceRepository invoiceRepository;


    @Value("${vnpay.tmncode}")
    private String vnpTmnCode;

    @Value("${vnpay.hashSecret}")
    private String vnpHashSecret;

    @Value("${vnpay.returnUrl}")
    private String returnUrl;

    @Value("${vnpay.paymentUrl}")
    private String paymentUrl;

    /**
     * Tạo URL thanh toán cho một đối tượng Payments đã lưu sẵn
     */
    @Override
    public String createPayment(Payments payment, String clientIp) {
        Map<String, String> vnpParams = new HashMap<>();
        vnpParams.put("vnp_Version", "2.1.0");
        vnpParams.put("vnp_Command", "pay");
        vnpParams.put("vnp_TmnCode", vnpTmnCode);
        vnpParams.put("vnp_Amount", String.valueOf(payment.getAmount() * 100)); // VNPay yêu cầu x100
        vnpParams.put("vnp_CurrCode", "VND");
        vnpParams.put("vnp_TxnRef", payment.getVnpTxnRef());
        vnpParams.put("vnp_OrderInfo", payment.getOrderInfo());
        vnpParams.put("vnp_OrderType", "other");
        vnpParams.put("vnp_Locale", "vn");
        vnpParams.put("vnp_ReturnUrl", returnUrl);
        vnpParams.put("vnp_IpAddr", clientIp);
        vnpParams.put("vnp_CreateDate", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));

        Map<String, String> sortedParams = new TreeMap<>(vnpParams);
        String queryString = sortedParams.entrySet().stream()
                .map(e -> e.getKey() + "=" + URLEncoder.encode(e.getValue(), StandardCharsets.UTF_8))
                .collect(Collectors.joining("&"));

        // Generate HMAC SHA512 signature
        String secureHash = hmacSHA512(vnpHashSecret, queryString);

        return paymentUrl + "?" + queryString + "&vnp_SecureHash=" + secureHash;
    }

    /**
     * Xử lý phản hồi từ VNPay sau khi thanh toán
     */

    @Override
    @Transactional
    public PaymentResultResponse handleVNPayReturn(Map<String, String> params) {
        String secureHash = params.remove("vnp_SecureHash");
        params.remove("vnp_SecureHashType");

        String sortedParamString = params.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(e -> e.getKey() + "=" + e.getValue())
                .collect(Collectors.joining("&"));

        String calculatedHash = hmacSHA512(vnpHashSecret, sortedParamString);
        if (!calculatedHash.equalsIgnoreCase(secureHash)) {
            return new PaymentResultResponse(
                    PaymentStatus.FAILED,
                    PaymentVerificationStatus.INVALID_SIGNATURE,
                    0,
                    null,
                    null,
                    null
            );
        }

        String txnRef = params.get("vnp_TxnRef");
        String responseCode = params.get("vnp_ResponseCode");
        String transactionStatus = params.get("vnp_TransactionStatus");
        long paidAmount = Long.parseLong(params.get("vnp_Amount")) / 100;

        Payments payment = paymentRepository.findByVnpTxnRef(txnRef)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        Invoices invoice = payment.getInvoice();

        boolean isSuccess = "00".equals(responseCode) && "00".equals(transactionStatus);
        boolean validPayment = false;

        if (isSuccess) {
            if (payment.getPaymentType() == PaymentType.DEPOSIT) {
                validPayment = paidAmount == 100_000;
                if (validPayment && invoice.getPaymentStatus() != PaymentStatus.PAID) {
                    invoice.setPaymentStatus(PaymentStatus.DEPOSITED);
                }
            } else if (payment.getPaymentType() == PaymentType.HOSPITAL_FEE) {
                validPayment = paidAmount >= invoice.getTotal_amount();
                if (validPayment) {
                    invoice.setPaymentStatus(PaymentStatus.PAID);
                }
            }

            if (validPayment) {
                payment.setStatus(PaymentStatus.PAID);
                invoiceRepository.save(invoice);
            } else {
                payment.setStatus(PaymentStatus.FAILED);
            }
        } else {
            payment.setStatus(PaymentStatus.FAILED);
        }

        payment.setVnpTransactionNo(params.get("vnp_TransactionNo"));
        payment.setPaymentTime(params.get("vnp_PayDate"));
        payment.setVerificationStatus(isSuccess && validPayment
                ? PaymentVerificationStatus.VALID_SIGNATURE_SUCCESS
                : PaymentVerificationStatus.VALID_SIGNATURE_FAILED);

        paymentRepository.save(payment);

        return new PaymentResultResponse(
                payment.getStatus(),
                payment.getVerificationStatus(),
                paidAmount,
                payment.getVnpTransactionNo(),
                invoice.getId().toString(),
                payment.getPaymentTime()
        );
    }



    /**
     * Tạo chữ ký HMAC SHA512
     */
    private String hmacSHA512(String key, String data) {
        try {
            Mac hmac = Mac.getInstance("HmacSHA512");
            SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "HmacSHA512");
            hmac.init(secretKey);
            byte[] hashBytes = hmac.doFinal(data.getBytes(StandardCharsets.UTF_8));
            return DatatypeConverter.printHexBinary(hashBytes).toLowerCase();
        } catch (Exception e) {
            throw new RuntimeException("Error generating HMAC", e);
        }
    }
}
