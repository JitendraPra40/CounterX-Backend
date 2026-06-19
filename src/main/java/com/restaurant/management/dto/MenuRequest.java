package com.restaurant.management.dto;

import com.restaurant.management.enums.Category;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record MenuRequest(
        @NotBlank(message = "Name is required")
        String name,
        
        @NotNull(message = "Category is required")
        Category category,
        
        @NotNull(message = "Price is required")
        @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
        BigDecimal price,
        
        @NotNull(message = "Availability status is required")
        Boolean available,
        
        String imageUrl
) {
}
