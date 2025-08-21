package com.example.HospitalSystem.mapper;

import com.example.HospitalSystem.dto.request.InvoiceEditRequest;
import com.example.HospitalSystem.dto.request.InvoiceRequest;
import com.example.HospitalSystem.dto.response.InvoiceResponse;
import com.example.HospitalSystem.entity.paymentsAndInvoices.Invoices;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface InvoiceMapper {
    Invoices requestToInvoice(InvoiceRequest invoices);

    InvoiceResponse invoiceToResponse(Invoices invoices);

    Invoices updateInvoice(InvoiceEditRequest invoiceEditRequest, @MappingTarget Invoices invoices);
}
