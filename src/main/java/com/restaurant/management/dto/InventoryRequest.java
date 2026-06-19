package com.restaurant.management.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record InventoryRequest(
        @NotBlank String itemName,
        @NotNull @DecimalMin(value = "0.00") BigDecimal quantity,
        @NotBlank String unit,
        @NotNull @DecimalMin(value = "0.00") BigDecimal lowStockThreshold
) {
}
