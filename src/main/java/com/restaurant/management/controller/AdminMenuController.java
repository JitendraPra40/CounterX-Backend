package com.restaurant.management.controller;

import com.restaurant.management.dto.MenuItemResponse;
import com.restaurant.management.dto.MenuRequest;
import com.restaurant.management.service.MenuService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/menu")
@RequiredArgsConstructor
public class AdminMenuController {

    private final MenuService menuService;

    @GetMapping
    public List<MenuItemResponse> getAll() {
        return menuService.getAll();
    }

    @GetMapping("/{id}")
    public MenuItemResponse getById(@PathVariable Long id) {
        return menuService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MenuItemResponse create(@Valid @RequestBody MenuRequest request) {
        return menuService.create(request);
    }

    @PutMapping("/{id}")
    public MenuItemResponse update(@PathVariable Long id, @Valid @RequestBody MenuRequest request) {
        return menuService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        menuService.delete(id);
    }
}
