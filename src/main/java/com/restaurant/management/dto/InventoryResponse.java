package com.restaurant.management.dto;

import java.math.BigDecimal;

public record InventoryResponse(
        Long id,
        String itemName,
        BigDecimal quantity,
        String unit,
        BigDecimal lowStockThreshold,
        boolean lowStock
) {
}
