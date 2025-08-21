package com.example.HospitalSystem.service;

import com.example.HospitalSystem.dto.request.InvoiceEditRequest;
import com.example.HospitalSystem.dto.request.InvoiceRequest;
import com.example.HospitalSystem.dto.response.InvoiceResponse;

public interface InvoiceService {
    InvoiceResponse addAppointment(InvoiceRequest invoiceRequest);

    InvoiceResponse editInvoice(InvoiceEditRequest invoiceEditRequest,Long id);

    InvoiceResponse getInvoice(Long id);
}
