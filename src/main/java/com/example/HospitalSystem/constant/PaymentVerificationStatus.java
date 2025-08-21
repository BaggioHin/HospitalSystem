package com.example.HospitalSystem.constant;

public enum PaymentVerificationStatus {
    INVALID_SIGNATURE,          // Chữ ký sai
    VALID_SIGNATURE_SUCCESS,    // Chữ ký đúng, thanh toán thành công
    VALID_SIGNATURE_FAILED,      // Chữ ký đúng, thanh toán thất bại
    MISSING_PARAMETERS,
    EXPIRED_REQUEST,
    UNKNOWN_STATUS,
    VERIFIED_BUT_REJECTED,
    UNVERIFIED
}
