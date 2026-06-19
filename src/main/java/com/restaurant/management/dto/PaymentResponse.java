package com.restaurant.management.dto;

import com.restaurant.management.enums.OrderStatus;
import com.restaurant.management.enums.PaymentMethod;
import com.restaurant.management.enums.PaymentStatus;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PaymentResponse(
        Long paymentId,
        Long orderId,
        BigDecimal amount,
        PaymentMethod paymentMethod,
        PaymentStatus paymentStatus,
        String transactionId,
        OrderStatus orderStatus,
        LocalDateTime paidAt
) {
}
