package com.expensetracker.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "wishlist_items")
public class WishlistItem {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank
    @Column(name = "title")
    private String title;
    
    @NotNull
    @PositiveOrZero
    @Column(name = "target_amount", precision = 10, scale = 2)
    private BigDecimal targetAmount;
    
    @PositiveOrZero
    @Column(name = "saved_amount", precision = 10, scale = 2)
    private BigDecimal savedAmount = BigDecimal.ZERO;
    
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "priority")
    private Priority priority;
    
    @Column(name = "deadline")
    private LocalDate deadline;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    public enum Priority {
        HIGH, MEDIUM, LOW
    }
    
    // Constructors
    public WishlistItem() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.savedAmount = BigDecimal.ZERO;
    }
    
    public WishlistItem(String title, BigDecimal targetAmount, Priority priority) {
        this();
        this.title = title;
        this.targetAmount = targetAmount;
        this.priority = priority;
    }
    
    // Calculated field for progress percentage
    public double getProgressPercentage() {
        if (targetAmount.compareTo(BigDecimal.ZERO) == 0) {
            return 0.0;
        }
        return savedAmount.divide(targetAmount, 4, BigDecimal.ROUND_HALF_UP)
                         .multiply(BigDecimal.valueOf(100))
                         .doubleValue();
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
    
    public Priority getPriority() {
        return priority;
    }
    
    public void setPriority(Priority priority) {
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
    
    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}