package com.restaurant.management.dto;

import java.math.BigDecimal;

public record OrderItemResponse(
        Long menuItemId,
        String itemName,
        Integer quantity,
        BigDecimal price,
        BigDecimal lineTotal
) {
}
