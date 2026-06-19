package com.restaurant.management.controller;

import com.restaurant.management.dto.MenuItemResponse;
import com.restaurant.management.service.MenuService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/menu")
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;

    @GetMapping
    public List<MenuItemResponse> getAvailableMenu() {
        return menuService.getAvailableMenu();
    }
}
