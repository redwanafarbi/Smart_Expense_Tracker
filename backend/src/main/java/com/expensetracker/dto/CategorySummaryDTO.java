package com.expensetracker.dto;

import java.math.BigDecimal;

public class CategorySummaryDTO {
    
    private String category;
    private BigDecimal totalAmount;
    private Long transactionCount;
    
    // Constructors
    public CategorySummaryDTO() {}
    
    public CategorySummaryDTO(String category, BigDecimal totalAmount, Long transactionCount) {
        this.category = category;
        this.totalAmount = totalAmount;
        this.transactionCount = transactionCount;
    }
    
    // Getters and Setters
    public String getCategory() {
        return category;
    }
    
    public void setCategory(String category) {
        this.category = category;
    }
    
    public BigDecimal getTotalAmount() {
        return totalAmount;
    }
    
    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }
    
    public Long getTransactionCount() {
        return transactionCount;
    }
    
    public void setTransactionCount(Long transactionCount) {
        this.transactionCount = transactionCount;
    }
}