package com.restaurant.management.service;

import com.restaurant.management.dto.CreateOrderRequest;
import com.restaurant.management.dto.OrderItemResponse;
import com.restaurant.management.dto.OrderResponse;
import com.restaurant.management.enums.OrderStatus;
import com.restaurant.management.exception.BadRequestException;
import com.restaurant.management.exception.ResourceNotFoundException;
import com.restaurant.management.model.CustomerOrder;
import com.restaurant.management.model.MenuItem;
import com.restaurant.management.model.OrderItem;
import com.restaurant.management.model.RestaurantTable;
import com.restaurant.management.repository.MenuItemRepository;
import com.restaurant.management.repository.OrderRepository;
import com.restaurant.management.repository.RestaurantTableRepository;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final RestaurantTableRepository tableRepository;
    private final MenuItemRepository menuItemRepository;

    @Transactional
    public OrderResponse createOrder(CreateOrderRequest request) {
        RestaurantTable table = tableRepository.findByTableNumber(request.tableNumber())
                .filter(RestaurantTable::getActive)
                .orElseThrow(() -> new ResourceNotFoundException("Active table not found: " + request.tableNumber()));

        CustomerOrder order = new CustomerOrder();
        order.setTable(table);
        order.setStatus(OrderStatus.PENDING_PAYMENT);

        BigDecimal total = BigDecimal.ZERO;
        for (var itemRequest : request.items()) {
            MenuItem menuItem = menuItemRepository.findById(itemRequest.menuItemId())
                    .orElseThrow(() -> new ResourceNotFoundException("Menu item not found: " + itemRequest.menuItemId()));
            if (!Boolean.TRUE.equals(menuItem.getAvailable())) {
                throw new BadRequestException("Menu item is not available: " + menuItem.getName());
            }

            BigDecimal lineTotal = menuItem.getPrice().multiply(BigDecimal.valueOf(itemRequest.quantity()));
            OrderItem orderItem = new OrderItem();
            orderItem.setMenuItem(menuItem);
            orderItem.setQuantity(itemRequest.quantity());
            orderItem.setPrice(menuItem.getPrice());
            orderItem.setLineTotal(lineTotal);
            order.addOrderItem(orderItem);
            total = total.add(lineTotal);
        }

        order.setTotalAmount(total);
        return toResponse(orderRepository.save(order));
    }

    @Transactional(readOnly = true)
    public OrderResponse getOrder(Long id) {
        CustomerOrder order = orderRepository.findWithDetailsById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found: " + id));
        return toResponse(order);
    }

    @Transactional(readOnly = true)
    public java.util.List<OrderResponse> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    public OrderResponse toResponse(CustomerOrder order) {
        return new OrderResponse(
                order.getId(),
                order.getTable().getTableNumber(),
                order.getTotalAmount(),
                order.getStatus(),
                order.getCreatedAt(),
                order.getOrderItems().stream()
                        .map(item -> new OrderItemResponse(
                                item.getMenuItem().getId(),
                                item.getMenuItem().getName(),
                                item.getQuantity(),
                                item.getPrice(),
                                item.getLineTotal()))
                        .toList());
    }
}
