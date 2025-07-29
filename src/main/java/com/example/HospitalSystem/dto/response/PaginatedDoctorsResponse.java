package com.example.HospitalSystem.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaginatedDoctorsResponse {
    private Long id;
    private Map<String, List<DoctorResponse>> pages;
    private Meta meta;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Meta {
        private int currentPage;
        private int totalPages;
        private int pageSize;
        private long totalElements;
    }
}

