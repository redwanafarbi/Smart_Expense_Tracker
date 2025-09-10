package com.expensetracker.service;

import com.expensetracker.dto.WishlistDTO;
import com.expensetracker.model.WishlistItem;
import com.expensetracker.repository.WishlistItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WishlistService {
    
    @Autowired
    private WishlistItemRepository wishlistItemRepository;
    
    // Convert Entity to DTO
    public WishlistDTO convertToDTO(WishlistItem wishlistItem) {
        WishlistDTO dto = new WishlistDTO();
        dto.setId(wishlistItem.getId());
        dto.setTitle(wishlistItem.getTitle());
        dto.setTargetAmount(wishlistItem.getTargetAmount());
        dto.setSavedAmount(wishlistItem.getSavedAmount());
        dto.setPriority(wishlistItem.getPriority().toString());
        dto.setDeadline(wishlistItem.getDeadline());
        dto.setCreatedAt(wishlistItem.getCreatedAt());
        dto.setUpdatedAt(wishlistItem.getUpdatedAt());
        dto.setProgressPercentage(wishlistItem.getProgressPercentage());
        return dto;
    }
    
    // Convert DTO to Entity
    private WishlistItem convertToEntity(WishlistDTO dto) {
        WishlistItem wishlistItem = new WishlistItem();
        if (dto.getId() != null) {
            wishlistItem.setId(dto.getId());
        }
        wishlistItem.setTitle(dto.getTitle());
        wishlistItem.setTargetAmount(dto.getTargetAmount());
        wishlistItem.setSavedAmount(dto.getSavedAmount() != null ? dto.getSavedAmount() : BigDecimal.ZERO);
        wishlistItem.setPriority(WishlistItem.Priority.valueOf(dto.getPriority()));
        wishlistItem.setDeadline(dto.getDeadline());
        return wishlistItem;
    }
    
    // Get all wishlist items
    public List<WishlistDTO> getAllWishlistItems() {
        List<WishlistItem> items = wishlistItemRepository.findAllOrderedByPriority();
        return items.stream()
                   .map(this::convertToDTO)
                   .collect(Collectors.toList());
    }
    
    // Get wishlist item by ID
    public WishlistDTO getWishlistItemById(Long id) {
        Optional<WishlistItem> item = wishlistItemRepository.findById(id);
        return item.map(this::convertToDTO).orElse(null);
    }
    
    // Create new wishlist item
    public WishlistDTO createWishlistItem(WishlistDTO wishlistDTO) {
        WishlistItem wishlistItem = convertToEntity(wishlistDTO);
        WishlistItem savedItem = wishlistItemRepository.save(wishlistItem);
        return convertToDTO(savedItem);
    }
    
    // Update wishlist item
    public WishlistDTO updateWishlistItem(Long id, WishlistDTO wishlistDTO) {
        Optional<WishlistItem> existingItem = wishlistItemRepository.findById(id);
        if (existingItem.isPresent()) {
            WishlistItem item = existingItem.get();
            item.setTitle(wishlistDTO.getTitle());
            item.setTargetAmount(wishlistDTO.getTargetAmount());
            item.setSavedAmount(wishlistDTO.getSavedAmount() != null ? wishlistDTO.getSavedAmount() : BigDecimal.ZERO);
            item.setPriority(WishlistItem.Priority.valueOf(wishlistDTO.getPriority()));
            item.setDeadline(wishlistDTO.getDeadline());
            
            WishlistItem updatedItem = wishlistItemRepository.save(item);
            return convertToDTO(updatedItem);
        }
        return null;
    }
    
    // Delete wishlist item
    public boolean deleteWishlistItem(Long id) {
        if (wishlistItemRepository.existsById(id)) {
            wishlistItemRepository.deleteById(id);
            return true;
        }
        return false;
    }
    
    // Add savings to wishlist item
    public WishlistDTO addSavings(Long id, BigDecimal amount) {
        Optional<WishlistItem> existingItem = wishlistItemRepository.findById(id);
        if (existingItem.isPresent() && amount.compareTo(BigDecimal.ZERO) > 0) {
            WishlistItem item = existingItem.get();
            BigDecimal newSavedAmount = item.getSavedAmount().add(amount);
            
            // Don't exceed target amount
            if (newSavedAmount.compareTo(item.getTargetAmount()) > 0) {
                newSavedAmount = item.getTargetAmount();
            }
            
            item.setSavedAmount(newSavedAmount);
            WishlistItem updatedItem = wishlistItemRepository.save(item);
            return convertToDTO(updatedItem);
        }
        return null;
    }
    
    // Get wishlist items by priority
    public List<WishlistDTO> getWishlistItemsByPriority(String priority) {
        WishlistItem.Priority priorityEnum;
        try {
            priorityEnum = WishlistItem.Priority.valueOf(priority.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid priority: " + priority);
        }
        
        List<WishlistItem> items = wishlistItemRepository.findByPriority(priorityEnum);
        return items.stream()
                   .map(this::convertToDTO)
                   .collect(Collectors.toList());
    }
    
    // Get top priority wishlist item
    public WishlistDTO getTopPriorityItem() {
        List<WishlistItem> highPriorityItems = wishlistItemRepository.findByPriorityOrderByCreatedAtAsc(WishlistItem.Priority.HIGH);
        
        if (!highPriorityItems.isEmpty()) {
            return convertToDTO(highPriorityItems.get(0));
        }
        
        List<WishlistItem> mediumPriorityItems = wishlistItemRepository.findByPriorityOrderByCreatedAtAsc(WishlistItem.Priority.MEDIUM);
        if (!mediumPriorityItems.isEmpty()) {
            return convertToDTO(mediumPriorityItems.get(0));
        }
        
        List<WishlistItem> lowPriorityItems = wishlistItemRepository.findByPriorityOrderByCreatedAtAsc(WishlistItem.Priority.LOW);
        if (!lowPriorityItems.isEmpty()) {
            return convertToDTO(lowPriorityItems.get(0));
        }
        
        return null;
    }
    
    // Get completed goals
    public List<WishlistDTO> getCompletedGoals() {
        List<WishlistItem> completedGoals = wishlistItemRepository.findCompletedGoals();
        return completedGoals.stream()
                            .map(this::convertToDTO)
                            .collect(Collectors.toList());
    }
    
    // Get goals in progress
    public List<WishlistDTO> getGoalsInProgress() {
        List<WishlistItem> goalsInProgress = wishlistItemRepository.findGoalsInProgress();
        return goalsInProgress.stream()
                             .map(this::convertToDTO)
                             .collect(Collectors.toList());
    }
    
    // Get total savings across all goals
    public BigDecimal getTotalSavings() {
        List<WishlistItem> allItems = wishlistItemRepository.findAll();
        return allItems.stream()
                      .map(WishlistItem::getSavedAmount)
                      .reduce(BigDecimal.ZERO, BigDecimal::add);
    }