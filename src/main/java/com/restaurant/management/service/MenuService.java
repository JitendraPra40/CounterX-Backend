package com.restaurant.management.service;

import com.restaurant.management.dto.MenuItemResponse;
import com.restaurant.management.dto.MenuRequest;
import com.restaurant.management.model.MenuItem;
import com.restaurant.management.repository.MenuItemRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuItemRepository menuItemRepository;

    public List<MenuItemResponse> getAvailableMenu() {
        return menuItemRepository.findByAvailableTrue()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public List<MenuItemResponse> getAll() {
        return menuItemRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public MenuItemResponse getById(Long id) {
        return toResponse(menuItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Menu item not found")));
    }

    public MenuItemResponse create(MenuRequest request) {
        MenuItem item = new MenuItem();
        item.setName(request.name());
        item.setCategory(request.category());
        item.setPrice(request.price());
        item.setAvailable(request.available());
        item.setImageUrl(request.imageUrl());
        
        MenuItem savedItem = menuItemRepository.save(item);
        return toResponse(savedItem);
    }

    public MenuItemResponse update(Long id, MenuRequest request) {
        MenuItem item = menuItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Menu item not found"));
        
        item.setName(request.name());
        item.setCategory(request.category());
        item.setPrice(request.price());
        item.setAvailable(request.available());
        item.setImageUrl(request.imageUrl());
        
        MenuItem savedItem = menuItemRepository.save(item);
        return toResponse(savedItem);
    }

    public void delete(Long id) {
        if (!menuItemRepository.existsById(id)) {
            throw new RuntimeException("Menu item not found");
        }
        menuItemRepository.deleteById(id);
    }

    private MenuItemResponse toResponse(MenuItem item) {
        return new MenuItemResponse(item.getId(), item.getName(), item.getCategory(), item.getPrice(), item.getAvailable(), item.getImageUrl());
    }
}
