package com.restaurant.management.dto;

import java.math.BigDecimal;
import java.util.List;

public record ReportResponse(
        BigDecimal dailySales,
        BigDecimal monthlySales,
        List<TopItemResponse> topSellingItems
) {
}
