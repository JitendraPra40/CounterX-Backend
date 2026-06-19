package com.restaurant.management.repository;

import com.restaurant.management.model.RestaurantTable;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantTableRepository extends JpaRepository<RestaurantTable, Long> {

    Optional<RestaurantTable> findByTableNumber(Integer tableNumber);
}
