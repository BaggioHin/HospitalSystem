package com.example.HospitalSystem.Utils;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class VnpayUtil {
    public String generateTxnRef() {
        return UUID.randomUUID().toString().replace("-", "").substring(0, 20);
    }
}
