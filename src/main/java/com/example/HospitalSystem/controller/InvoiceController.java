package com.example.HospitalSystem.controller;

import com.example.HospitalSystem.dto.request.InvoiceEditRequest;
import com.example.HospitalSystem.dto.request.InvoiceRequest;
import com.example.HospitalSystem.dto.response.ApiResponse;
import com.example.HospitalSystem.dto.response.InvoiceResponse;
import com.example.HospitalSystem.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/invoice")
@RestController
public class InvoiceController {

    @Autowired
    InvoiceService invoiceService;

    @PostMapping("/create")
    public ApiResponse<InvoiceResponse> createAppointment(@RequestBody InvoiceRequest invoiceRequest) {
        return ApiResponse.<InvoiceResponse>builder()
                .result(invoiceService.addAppointment(invoiceRequest))
                .build();
    }

//    benh nhan co the xem qua thong tin cua hoa don dua ra y kien neu co sai sot
    @PostMapping("/edit/{id}")
    public ApiResponse<InvoiceResponse> editAppointment(@RequestBody InvoiceEditRequest invoiceEditRequest,
                                                        @PathVariable Long id) {
        return ApiResponse.<InvoiceResponse>builder()
                .result(invoiceService.editInvoice(invoiceEditRequest,id))
                .build();
    }

    @GetMapping("/infInvoice/{id}")
    public ApiResponse<InvoiceResponse> getInvoiceById(@PathVariable Long id) {
        return ApiResponse.<InvoiceResponse>builder()
                .result(invoiceService.getInvoice(id))
                .build();
    }
}
