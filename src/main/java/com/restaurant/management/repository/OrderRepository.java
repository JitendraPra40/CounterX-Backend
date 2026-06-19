package com.restaurant.management.repository;

import com.restaurant.management.model.CustomerOrder;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<CustomerOrder, Long> {

    @EntityGraph(attributePaths = {"table", "orderItems", "orderItems.menuItem", "payment"})
    java.util.Optional<CustomerOrder> findWithDetailsById(Long id);

    @EntityGraph(attributePaths = {"table", "orderItems", "orderItems.menuItem", "payment"})
    java.util.List<CustomerOrder> findAll();

    @org.springframework.data.jpa.repository.Query("SELECT new com.restaurant.management.dto.TopItemResponse(i.menuItem.name, SUM(i.quantity)) " +
           "FROM CustomerOrder o JOIN o.orderItems i " +
           "WHERE o.status = 'COMPLETED' " +
           "GROUP BY i.menuItem.name " +
           "ORDER BY SUM(i.quantity) DESC")
    java.util.List<com.restaurant.management.dto.TopItemResponse> findTopSellingItems(org.springframework.data.domain.Pageable pageable);
}
