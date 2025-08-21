package com.example.HospitalSystem.controller;

import com.example.HospitalSystem.constant.PaymentMethod;
import com.example.HospitalSystem.constant.PaymentStatus;
import com.example.HospitalSystem.constant.PaymentType;
import com.example.HospitalSystem.constant.PaymentVerificationStatus;
import com.example.HospitalSystem.dto.response.PaymentResultResponse;
import com.example.HospitalSystem.entity.paymentsAndInvoices.Invoices;
import com.example.HospitalSystem.entity.paymentsAndInvoices.Payments;
import com.example.HospitalSystem.exception.AppException;
import com.example.HospitalSystem.exception.ErrorCode;
import com.example.HospitalSystem.repository.InvoiceRepository;
import com.example.HospitalSystem.repository.PaymentRepository;
import com.example.HospitalSystem.service.PaymentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/vnpay")
@RequiredArgsConstructor
public class PaymentController {

    private final InvoiceRepository invoiceRepository;
    private final PaymentRepository paymentRepository;
    private final PaymentService paymentService;

    @PostMapping("/create")
    public ResponseEntity<String> createPayment(
            @RequestParam Long invoiceId,
            @RequestParam PaymentType paymentType,
            HttpServletRequest request) throws AccessDeniedException {

        var context = SecurityContextHolder.getContext();
        var jwt = (Jwt) context.getAuthentication().getPrincipal();
        Long userId = jwt.getClaim("Id");

        Invoices invoice = invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new RuntimeException("Invoice not found"));

        if (!invoice.getPatient().getUser().getId().equals(userId)) {
            throw new AppException(ErrorCode.EMAIL_EXISTED);
        }

        // Tạo mã giao dịch duy nhất cho VNPay
        String vnpTxnRef = generateTxnRef();

        // Xác định số tiền
        Long amount;
        if (paymentType == PaymentType.DEPOSIT) {
            amount = 100_000L; // Đặt cọc cố định 100k
        } else {
            amount = invoice.getTotal_amount(); // Thanh toán toàn bộ
        }

        String orderInfo = paymentType.name() + "|userId=" + userId + "|invoiceId=" + invoiceId;

        Payments payment = Payments.builder()
                .invoice(invoice)
                .amount(amount)
                .paymentMethod(PaymentMethod.VNPAY)
                .status(PaymentStatus.PENDING)
                .verificationStatus(PaymentVerificationStatus.UNVERIFIED)
                .paymentType(paymentType)
                .vnpTxnRef(vnpTxnRef)
                .orderInfo(orderInfo)
                .build();

        paymentRepository.save(payment);

        String paymentUrl = paymentService.createPayment(payment, request.getRemoteAddr());
        return ResponseEntity.ok(paymentUrl);
    }


    @GetMapping("/return")
    public ResponseEntity<PaymentResultResponse> vnpayReturn(@RequestParam Map<String, String> params) {
        PaymentResultResponse result = paymentService.handleVNPayReturn(params);
        return ResponseEntity.ok(result);
    }

    private String generateTxnRef() {
        // Tạo mã 20 ký tự duy nhất
        return UUID.randomUUID().toString().replace("-", "").substring(0, 20);
    }
}
