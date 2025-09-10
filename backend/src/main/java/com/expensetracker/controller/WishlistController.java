package com.expensetracker.controller;

import com.expensetracker.dto.WishlistDTO;
import com.expensetracker.service.WishlistService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/wishlist")
@CrossOrigin(origins = "*")
public class WishlistController {
    
    @Autowired
    private WishlistService wishlistService;
    
    // Get all wishlist items
    @GetMapping
    public ResponseEntity<List<WishlistDTO>> getAllWishlistItems() {
        List<WishlistDTO> items = wishlistService.getAllWishlistItems();
        return ResponseEntity.ok(items);
    }
    
    // Get wishlist item by ID
    @GetMapping("/{id}")
    public ResponseEntity<WishlistDTO> getWishlistItemById(@PathVariable Long id) {
        WishlistDTO item = wishlistService.getWishlistItemById(id);
        if (item != null) {
            return ResponseEntity.ok(item);
        }
        return ResponseEntity.notFound().build();
    }
    
    // Create new wishlist item
    @PostMapping
    public ResponseEntity<WishlistDTO> createWishlistItem(@Valid @RequestBody WishlistDTO wishlistDTO) {
        WishlistDTO createdItem = wishlistService.createWishlistItem(wishlistDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdItem);
    }
    
    // Update wishlist item
    @PutMapping("/{id}")
    public ResponseEntity<WishlistDTO> updateWishlistItem(@PathVariable Long id, @Valid @RequestBody WishlistDTO wishlistDTO) {
        WishlistDTO updatedItem = wishlistService.updateWishlistItem(id, wishlistDTO);
        if (updatedItem != null) {
            return ResponseEntity.ok(updatedItem);
        }
        return ResponseEntity.notFound().build();
    }
    
    // Delete wishlist item
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWishlistItem(@PathVariable Long id) {
        boolean deleted = wishlistService.deleteWishlistItem(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
    
    // Add savings to wishlist item
    @PatchMapping("/{id}/add-savings")
    public ResponseEntity<WishlistDTO> addSavings(@PathVariable Long id, @RequestBody BigDecimal amount) {
        WishlistDTO updatedItem = wishlistService.addSavings(id, amount);
        if (updatedItem != null) {
            return ResponseEntity.ok(updatedItem);
        }
        return ResponseEntity.notFound().build();
    }
    
    // Get wishlist items by priority
    @GetMapping("/priority/{priority}")
    public ResponseEntity<List<WishlistDTO>> getWishlistItemsByPriority(@PathVariable String priority) {
        List<WishlistDTO> items = wishlistService.getWishlistItemsByPriority(priority);
        return ResponseEntity.ok(items);
    }
    
    // Get top priority wishlist item
    @GetMapping("/top-priority")
    public ResponseEntity<WishlistDTO> getTopPriorityItem() {
        WishlistDTO item = wishlistService.getTopPriorityItem();
        if (item != null) {
            return ResponseEntity.ok(item);
        }
        return ResponseEntity.notFound().build();
    }
}