package com.restaurant.management.controller;

import com.restaurant.management.dto.ReportResponse;
import com.restaurant.management.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/reports")
@RequiredArgsConstructor
public class AdminReportController {

    private final ReportService reportService;

    @GetMapping
    public ReportResponse getReports() {
        return reportService.getDashboardReports();
    }
}
