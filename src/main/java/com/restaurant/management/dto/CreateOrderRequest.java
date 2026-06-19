package com.restaurant.management.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record CreateOrderRequest(
        @NotNull Integer tableNumber,
        @Valid @NotEmpty List<CreateOrderItemRequest> items
) {
}
