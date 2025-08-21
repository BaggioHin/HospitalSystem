package com.example.HospitalSystem.service.impl;

import com.example.HospitalSystem.constant.PaymentStatus;
import com.example.HospitalSystem.dto.request.InvoiceEditRequest;
import com.example.HospitalSystem.dto.request.InvoiceRequest;
import com.example.HospitalSystem.dto.response.InvoiceResponse;
import com.example.HospitalSystem.entity.paymentsAndInvoices.Invoices;
import com.example.HospitalSystem.mapper.InvoiceMapper;
import com.example.HospitalSystem.repository.InvoiceRepository;
import com.example.HospitalSystem.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    @Autowired
    InvoiceRepository invoiceRepository;
    @Autowired
    InvoiceMapper invoiceMapper;

    @Override
    public InvoiceResponse addAppointment(InvoiceRequest invoiceRequest) {
        Invoices invoices = invoiceMapper.requestToInvoice(invoiceRequest);
        invoices.setTotal_amount(invoiceRequest.getTotal_amount()-100_000);
        invoices.setPaymentStatus(PaymentStatus.PENDING);
        invoiceRepository.save(invoices);
        return invoiceMapper.invoiceToResponse(invoices);
    }

    @Override
    public InvoiceResponse editInvoice(InvoiceEditRequest invoiceEditRequest,Long Id) {
        Invoices invoices = invoiceRepository.findById(Id).get();
        invoiceMapper.updateInvoice(invoiceEditRequest, invoices);
        return invoiceMapper.invoiceToResponse(invoices);
    }

    @Override
    public InvoiceResponse getInvoice(Long id) {
        Invoices invoices = invoiceRepository.findById(id).get();
        return invoiceMapper.invoiceToResponse(invoices);
    }
}
