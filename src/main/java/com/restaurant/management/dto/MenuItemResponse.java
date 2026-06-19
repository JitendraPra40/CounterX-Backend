package com.restaurant.management.dto;

import com.restaurant.management.enums.Category;
import java.math.BigDecimal;

public record MenuItemResponse(
        Long id,
        String name,
        Category category,
        BigDecimal price,
        Boolean available,
        String imageUrl
) {
}
