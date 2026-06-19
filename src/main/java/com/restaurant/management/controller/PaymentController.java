package com.restaurant.management.controller;

import com.restaurant.management.dto.CreatePaymentRequest;
import com.restaurant.management.dto.PaymentResponse;
import com.restaurant.management.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PaymentResponse completePayment(@Valid @RequestBody CreatePaymentRequest request) {
        return paymentService.completePayment(request);
    }
}
