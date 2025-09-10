package com.expensetracker.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class DashboardDTO {
    
    private BigDecimal todayExpense;
    private BigDecimal monthExpense;
    private BigDecimal lastMonthExpense;
    private String monthComparison;
    private BigDecimal totalSavings;
    private WishlistDTO topGoal;
    private List<CategorySummaryDTO> topCategories;
    private Map<String, BigDecimal> categorySpending;
    private List<Object> weeklyTrend;
    
    // Constructors
    public DashboardDTO() {}
    
    // Getters and Setters
    public BigDecimal getTodayExpense() {
        return todayExpense;
    }
    
    public void setTodayExpense(BigDecimal todayExpense) {
        this.todayExpense = todayExpense;
    }
    
    public BigDecimal getMonthExpense() {
        return monthExpense;
    }
    
    public void setMonthExpense(BigDecimal monthExpense) {
        this.monthExpense = monthExpense;
    }
    
    public BigDecimal getLastMonthExpense() {
        return lastMonthExpense;
    }
    
    public void setLastMonthExpense(BigDecimal lastMonthExpense) {
        this.lastMonthExpense = lastMonthExpense;
    }
    
    public String getMonthComparison() {
        return monthComparison;
    }
    
    public void setMonthComparison(String monthComparison) {
        this.monthComparison = monthComparison;
    }
    
    public BigDecimal getTotalSavings() {
        return totalSavings;
    }
    
    public void setTotalSavings(BigDecimal totalSavings) {
        this.totalSavings = totalSavings;
    }
    
    public WishlistDTO getTopGoal() {
        return topGoal;
    }
    
    public void setTopGoal(WishlistDTO topGoal) {
        this.topGoal = topGoal;
    }
    
    public List<CategorySummaryDTO> getTopCategories() {
        return topCategories;
    }
    
    public void setTopCategories(List<CategorySummaryDTO> topCategories) {
        this.topCategories = topCategories;
    }
    
    public Map<String, BigDecimal> getCategorySpending() {
        return categorySpending;
    }
    
    public void setCategorySpending(Map<String, BigDecimal> categorySpending) {
        this.categorySpending = categorySpending;
    }
    
    public List<Object> getWeeklyTrend() {
        return weeklyTrend;
    }
    
    public void setWeeklyTrend(List<Object> weeklyTrend) {
        this.weeklyTrend = weeklyTrend;
    }
}