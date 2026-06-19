package com.restaurant.management.service;

import com.restaurant.management.dto.CreatePaymentRequest;
import com.restaurant.management.dto.PaymentResponse;
import com.restaurant.management.enums.OrderStatus;
import com.restaurant.management.enums.PaymentStatus;
import com.restaurant.management.exception.BadRequestException;
import com.restaurant.management.exception.ResourceNotFoundException;
import com.restaurant.management.model.CustomerOrder;
import com.restaurant.management.model.Payment;
import com.restaurant.management.repository.OrderRepository;
import com.restaurant.management.repository.PaymentRepository;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;

    @Transactional
    public PaymentResponse completePayment(CreatePaymentRequest request) {
        CustomerOrder order = orderRepository.findWithDetailsById(request.orderId())
                .orElseThrow(() -> new ResourceNotFoundException("Order not found: " + request.orderId()));

        if (order.getStatus() == OrderStatus.COMPLETED) {
            throw new BadRequestException("Order is already completed");
        }
        if (order.getStatus() == OrderStatus.CANCELLED) {
            throw new BadRequestException("Cancelled orders cannot be paid");
        }
        paymentRepository.findByOrderId(order.getId()).ifPresent(payment -> {
            throw new BadRequestException("Payment already exists for order: " + order.getId());
        });

        Payment payment = new Payment();
        payment.setOrder(order);
        payment.setAmount(order.getTotalAmount());
        payment.setMethod(request.paymentMethod());
        payment.setStatus(PaymentStatus.SUCCESS);
        payment.setTransactionId("TXN-" + UUID.randomUUID().toString().replace("-", "").substring(0, 12).toUpperCase());
        payment.setPaidAt(LocalDateTime.now());

        order.setStatus(OrderStatus.COMPLETED);
        order.setPayment(payment);

        Payment saved = paymentRepository.save(payment);
        orderRepository.save(order);
        return toResponse(saved);
    }

    @Transactional(readOnly = true)
    public java.util.List<PaymentResponse> getAllPayments() {
        return paymentRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    private PaymentResponse toResponse(Payment payment) {
        return new PaymentResponse(
                payment.getId(),
                payment.getOrder().getId(),
                payment.getAmount(),
                payment.getMethod(),
                payment.getStatus(),
                payment.getTransactionId(),
                payment.getOrder().getStatus(),
                payment.getPaidAt());
    }
}
