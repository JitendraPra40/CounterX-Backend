package com.restaurant.management.dto;

import com.restaurant.management.enums.PaymentMethod;
import jakarta.validation.constraints.NotNull;

public record CreatePaymentRequest(
        @NotNull Long orderId,
        @NotNull PaymentMethod paymentMethod
) {
}
