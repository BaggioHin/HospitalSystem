package com.example.HospitalSystem.entity.paymentsAndInvoices;

import com.example.HospitalSystem.constant.PaymentMethod;
import com.example.HospitalSystem.constant.PaymentStatus;
import com.example.HospitalSystem.constant.PaymentType;
import com.example.HospitalSystem.constant.PaymentVerificationStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Payments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
//    private Enum payment_method;
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;
    @Enumerated(EnumType.STRING)
    private PaymentStatus status;
    @Enumerated(EnumType.STRING)
    private PaymentVerificationStatus verificationStatus;
    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;

    private Long amount;
    private LocalDate paid_at;
    private String transaction_code;
    private String orderInfo;
    private String vnpTxnRef;
    private String vnpTransactionNo;
    private String paymentTime;

    @ManyToOne
    @JoinColumn(name = "invoices_id")
    private Invoices invoice;
}
