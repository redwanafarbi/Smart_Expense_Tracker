package com.expensetracker.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class WishlistDTO {
    
    private Long id;
    
    @NotBlank(message = "Title is required")
    private String title;
    
    @NotNull(message = "Target amount is required")
    @PositiveOrZero(message = "Target amount must be positive or zero")
    private BigDecimal targetAmount;
    
    @PositiveOrZero(message = "Saved amount must be positive or zero")
    private BigDecimal savedAmount = BigDecimal.ZERO;
    
    @NotNull(message = "Priority is required")
    private String priority;
    
    private LocalDate deadline;
    
    private LocalDateTime createdAt;
    
    private LocalDateTime updatedAt;
    
    private Double progressPercentage;
    
    // Constructors
    public WishlistDTO() {}
    
    public WishlistDTO(String title, BigDecimal targetAmount, String priority) {
        this.title = title;
        this.targetAmount = targetAmount;
        this.priority = priority;
        this.savedAmount = BigDecimal.ZERO;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public BigDecimal getTargetAmount() {
        return targetAmount;
    }
    
    public void setTargetAmount(BigDecimal targetAmount) {
        this.targetAmount = targetAmount;
    }
    
    public BigDecimal getSavedAmount() {
        return savedAmount;
    }
    
    public void setSavedAmount(BigDecimal savedAmount) {
        this.savedAmount = savedAmount;
    }
    
    public String getPriority() {
        return priority;
    }
    
    public void setPriority(String priority) {
        this.priority = priority;
    }
    
    public LocalDate getDeadline() {
        return deadline;
    }
    
    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    public Double getProgressPercentage() {
        return progressPercentage;
    }
    
    public void setProgressPercentage(Double progressPercentage) {
        this.progressPercentage = progressPercentage;
    }
}