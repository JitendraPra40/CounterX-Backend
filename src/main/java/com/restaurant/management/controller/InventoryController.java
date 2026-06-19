package com.restaurant.management.controller;

import com.restaurant.management.dto.InventoryRequest;
import com.restaurant.management.dto.InventoryResponse;
import com.restaurant.management.service.InventoryService;
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
@RequestMapping("/api/admin/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping
    public List<InventoryResponse> getAll() {
        return inventoryService.getAll();
    }

    @GetMapping("/{id}")
    public InventoryResponse getById(@PathVariable Long id) {
        return inventoryService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public InventoryResponse create(@Valid @RequestBody InventoryRequest request) {
        return inventoryService.create(request);
    }

    @PutMapping("/{id}")
    public InventoryResponse update(@PathVariable Long id, @Valid @RequestBody InventoryRequest request) {
        return inventoryService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        inventoryService.delete(id);
    }
}
