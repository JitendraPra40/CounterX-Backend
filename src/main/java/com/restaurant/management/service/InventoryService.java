package com.restaurant.management.service;

import com.restaurant.management.dto.InventoryRequest;
import com.restaurant.management.dto.InventoryResponse;
import com.restaurant.management.exception.ResourceNotFoundException;
import com.restaurant.management.model.Inventory;
import com.restaurant.management.repository.InventoryRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    @Transactional(readOnly = true)
    public List<InventoryResponse> getAll() {
        return inventoryRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public InventoryResponse getById(Long id) {
        return toResponse(findInventory(id));
    }

    @Transactional
    public InventoryResponse create(InventoryRequest request) {
        Inventory inventory = new Inventory();
        copyRequest(request, inventory);
        return toResponse(inventoryRepository.save(inventory));
    }

    @Transactional
    public InventoryResponse update(Long id, InventoryRequest request) {
        Inventory inventory = findInventory(id);
        copyRequest(request, inventory);
        return toResponse(inventoryRepository.save(inventory));
    }

    @Transactional
    public void delete(Long id) {
        Inventory inventory = findInventory(id);
        inventoryRepository.delete(inventory);
    }

    private Inventory findInventory(Long id) {
        return inventoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Inventory item not found: " + id));
    }

    private void copyRequest(InventoryRequest request, Inventory inventory) {
        inventory.setItemName(request.itemName());
        inventory.setQuantity(request.quantity());
        inventory.setUnit(request.unit());
        inventory.setLowStockThreshold(request.lowStockThreshold());
    }

    private InventoryResponse toResponse(Inventory inventory) {
        boolean lowStock = inventory.getQuantity().compareTo(inventory.getLowStockThreshold()) <= 0;
        return new InventoryResponse(
                inventory.getId(),
                inventory.getItemName(),
                inventory.getQuantity(),
                inventory.getUnit(),
                inventory.getLowStockThreshold(),
                lowStock);
    }
}
