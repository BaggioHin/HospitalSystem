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

    @Override
    @Transactional
    public Map<String, String> handleVNPayIpn(Map<String, String> params) {
        Map<String, String> response = new HashMap<>();

        String secureHash = params.remove("vnp_SecureHash");
        params.remove("vnp_SecureHashType");

        // 1. Verify TmnCode
        if (!vnpTmnCode.equals(params.get("vnp_TmnCode"))) {
            response.put("RspCode", "03");
            response.put("Message", "Invalid merchant");
            return response;
        }

        // 2. Verify signature
        String sorted = params.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(e -> e.getKey() + "=" + e.getValue())
                .collect(Collectors.joining("&"));

        String calculated = hmacSHA512(vnpHashSecret, sorted);
        if (!calculated.equalsIgnoreCase(secureHash)) {
            response.put("RspCode", "97");
            response.put("Message", "Invalid signature");
            return response;
        }

        // 3. Validate order existence
        String txnRef = params.get("vnp_TxnRef");
        Payments payment = paymentRepository.findByVnpTxnRef(txnRef)
                .orElse(null);

        if (payment == null) {
            response.put("RspCode", "01");
            response.put("Message", "Order not found");
            return response;
        }

        // 4. Prevent duplicate IPN processing
        if (payment.getStatus() == PaymentStatus.PAID) {
            response.put("RspCode", "00");
            response.put("Message", "Order already confirmed");
            return response;
        }

        // 5. Process payment result
        String rspCode = params.get("vnp_ResponseCode");
        String transactionStatus = params.get("vnp_TransactionStatus");
        long paidAmount = Long.parseLong(params.get("vnp_Amount")) / 100;

        Invoices invoice = payment.getInvoice();
        boolean isSuccess = "00".equals(rspCode) && "00".equals(transactionStatus);

        boolean validPayment = false;

        if (isSuccess) {
            if (payment.getPaymentType() == PaymentType.DEPOSIT) {
                validPayment = paidAmount == 100_000;
                if (validPayment) {
                    invoice.setPaymentStatus(PaymentStatus.DEPOSITED);
                }
            } else {
                validPayment = paidAmount >= invoice.getTotal_amount();
                if (validPayment) {
                    invoice.setPaymentStatus(PaymentStatus.PAID);
                }
            }
        }

        payment.setStatus(validPayment ? PaymentStatus.PAID : PaymentStatus.FAILED);
        payment.setVerificationStatus(validPayment
                ? PaymentVerificationStatus.VALID_SIGNATURE_SUCCESS
                : PaymentVerificationStatus.VALID_SIGNATURE_FAILED);

        // 6. Save additional bank info
        payment.setVnpBankCode(params.get("vnp_BankCode"));
        payment.setVnpBankTranNo(params.get("vnp_BankTranNo"));
        payment.setVnpCardType(params.get("vnp_CardType"));
        payment.setVnpTransactionNo(params.get("vnp_TransactionNo"));
        payment.setPaymentTime(params.get("vnp_PayDate"));
        payment.setOrderInfoFull(sorted);

        invoiceRepository.save(invoice);
        paymentRepository.save(payment);

        // 7. Return JSON according to VNPay spec
        response.put("RspCode", "00");
        response.put("Message", "Confirm Success");

        return response;
    }


    /**
     * Xử lý phản hồi từ VNPay sau khi thanh toán
     */
    @Override
    public PaymentResultResponse handleVNPayReturn(Map<String, String> params) {

        String secureHash = params.remove("vnp_SecureHash");
        params.remove("vnp_SecureHashType");

        // (Optional) verify signature cho giao diện
        String sorted = params.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(e -> e.getKey() + "=" + e.getValue())
                .collect(Collectors.joining("&"));

        String calculated = hmacSHA512(vnpHashSecret, sorted);
        boolean signatureValid = calculated.equalsIgnoreCase(secureHash);

        String responseCode = params.get("vnp_ResponseCode");
        String transactionStatus = params.get("vnp_TransactionStatus");

        boolean isSuccess = "00".equals(responseCode) && "00".equals(transactionStatus);

        long paidAmount = Long.parseLong(params.get("vnp_Amount")) / 100;

        // Chỉ để hiển thị – không cập nhật DB
        return new PaymentResultResponse(
                isSuccess ? PaymentStatus.PAID : PaymentStatus.FAILED,
                signatureValid
                        ? PaymentVerificationStatus.VALID_SIGNATURE_SUCCESS
                        : PaymentVerificationStatus.VALID_SIGNATURE_FAILED,
                paidAmount,
                params.get("vnp_TransactionNo"),
                params.get("vnp_TxnRef"),
                params.get("vnp_PayDate")
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
