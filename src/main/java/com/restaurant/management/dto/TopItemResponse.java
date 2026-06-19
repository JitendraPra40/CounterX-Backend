package com.restaurant.management.dto;

public record TopItemResponse(
        String itemName,
        Long quantitySold
) {
}
