package com.restaurant.management.dto;

import com.restaurant.management.enums.Role;

public record LoginResponse(
        String token,
        String tokenType,
        String username,
        Role role
) {
}
