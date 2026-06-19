package com.restaurant.management.config;

import com.restaurant.management.enums.Category;
import com.restaurant.management.enums.Role;
import com.restaurant.management.model.Inventory;
import com.restaurant.management.model.MenuItem;
import com.restaurant.management.model.RestaurantTable;
import com.restaurant.management.model.User;
import com.restaurant.management.repository.InventoryRepository;
import com.restaurant.management.repository.MenuItemRepository;
import com.restaurant.management.repository.RestaurantTableRepository;
import com.restaurant.management.repository.UserRepository;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class DataSeeder {

    @Bean
    CommandLineRunner seedDemoData(
            RestaurantTableRepository tableRepository,
            MenuItemRepository menuItemRepository,
            InventoryRepository inventoryRepository,
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {
        return args -> {
            if (userRepository.count() == 0) {
                User admin = new User();
                admin.setUsername("admin");
                admin.setPassword(passwordEncoder.encode("admin123"));
                admin.setRole(Role.ADMIN);
                userRepository.save(admin);
            }

            if (tableRepository.count() == 0) {
                for (int tableNumber = 1; tableNumber <= 5; tableNumber++) {
                    RestaurantTable table = new RestaurantTable();
                    table.setTableNumber(tableNumber);
                    table.setQrCodeUrl("/order/" + tableNumber);
                    tableRepository.save(table);
                }
            }

            if (menuItemRepository.count() == 0) {
                saveMenuItem(menuItemRepository, "Masala Dosa", Category.BREAKFAST, "80.00");
                saveMenuItem(menuItemRepository, "Tea", Category.BEVERAGE, "15.00");
                saveMenuItem(menuItemRepository, "Veg Meals", Category.LUNCH, "120.00");
            }

            if (inventoryRepository.count() == 0) {
                saveInventory(inventoryRepository, "Rice", "kg", "25.00", "5.00");
                saveInventory(inventoryRepository, "Sugar", "kg", "10.00", "2.00");
                saveInventory(inventoryRepository, "Oil", "litre", "8.00", "2.00");
            }
        };
    }

    private void saveMenuItem(MenuItemRepository repository, String name, Category category, String price) {
        MenuItem item = new MenuItem();
        item.setName(name);
        item.setCategory(category);
        item.setPrice(new BigDecimal(price));
        item.setAvailable(true);
        repository.save(item);
    }

    private void saveInventory(InventoryRepository repository, String itemName, String unit, String quantity, String lowStockThreshold) {
        Inventory inventory = new Inventory();
        inventory.setItemName(itemName);
        inventory.setUnit(unit);
        inventory.setQuantity(new BigDecimal(quantity));
        inventory.setLowStockThreshold(new BigDecimal(lowStockThreshold));
        repository.save(inventory);
    }
}
