package com.expensetracker.service;

import com.expensetracker.dto.CategorySummaryDTO;
import com.expensetracker.dto.DashboardDTO;
import com.expensetracker.dto.WishlistDTO;
import com.expensetracker.model.WishlistItem;
import com.expensetracker.repository.ExpenseRepository;
import com.expensetracker.repository.WishlistItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DashboardService {
    
    @Autowired
    private ExpenseRepository expenseRepository;
    
    @Autowired
    private WishlistItemRepository wishlistItemRepository;
    
    @Autowired
    private WishlistService wishlistService;
    
    public DashboardDTO getDashboardData() {
        DashboardDTO dashboard = new DashboardDTO();
        
        // Calculate date ranges
        LocalDate today = LocalDate.now();
        LocalDate startOfMonth = today.withDayOfMonth(1);
        LocalDate startOfLastMonth = startOfMonth.minusMonths(1);
        LocalDate endOfLastMonth = startOfMonth.minusDays(1);
        
        // Today's expense
        BigDecimal todayExpense = expenseRepository.getTotalAmountByDate(today);
        dashboard.setTodayExpense(todayExpense);
        
        // This month's expense
        BigDecimal monthExpense = expenseRepository.getTotalAmountByDateRange(startOfMonth, today);
        dashboard.setMonthExpense(monthExpense);
        
        // Last month's expense for comparison
        BigDecimal lastMonthExpense = expenseRepository.getTotalAmountByDateRange(startOfLastMonth, endOfLastMonth);
        dashboard.setLastMonthExpense(lastMonthExpense);
        
        // Calculate month-over-month comparison
        String monthComparison = calculateMonthComparison(monthExpense, lastMonthExpense);
        dashboard.setMonthComparison(monthComparison);
        
        // Total savings from wishlist
        BigDecimal totalSavings = calculateTotalSavings();
        dashboard.setTotalSavings(totalSavings);
        
        // Top priority goal
        WishlistDTO topGoal = getTopPriorityGoal();
        dashboard.setTopGoal(topGoal);
        
        // Top categories for last 7 days
        List<CategorySummaryDTO> topCategories = getTopCategories();
        dashboard.setTopCategories(topCategories);
        
        return dashboard;
    }
    
    private String calculateMonthComparison(BigDecimal currentMonth, BigDecimal lastMonth) {
        if (lastMonth.compareTo(BigDecimal.ZERO) == 0) {
            return currentMonth.compareTo(BigDecimal.ZERO) > 0 ? "+100%" : "0%";
        }
        
        BigDecimal difference = currentMonth.subtract(lastMonth);
        BigDecimal percentage = difference.divide(lastMonth, 4, RoundingMode.HALF_UP)
                                         .multiply(BigDecimal.valueOf(100));
        
        String sign = percentage.compareTo(BigDecimal.ZERO) >= 0 ? "+" : "";
        return sign + percentage.setScale(1, RoundingMode.HALF_UP) + "%";
    }
    
    private BigDecimal calculateTotalSavings() {
        List<WishlistItem> wishlistItems = wishlistItemRepository.findAll();
        return wishlistItems.stream()
                           .map(WishlistItem::getSavedAmount)
                           .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
    
    private WishlistDTO getTopPriorityGoal() {
        List<WishlistItem> highPriorityItems = wishlistItemRepository.findByPriorityOrderByCreatedAtAsc(WishlistItem.Priority.HIGH);
        
        if (!highPriorityItems.isEmpty()) {
            return wishlistService.convertToDTO(highPriorityItems.get(0));
        }
        
        // If no high priority items, get the first medium priority item
        List<WishlistItem> mediumPriorityItems = wishlistItemRepository.findByPriorityOrderByCreatedAtAsc(WishlistItem.Priority.MEDIUM);
        if (!mediumPriorityItems.isEmpty()) {
            return wishlistService.convertToDTO(mediumPriorityItems.get(0));
        }
        
        return null;
    }
    
    private List<CategorySummaryDTO> getTopCategories() {
        LocalDate sevenDaysAgo = LocalDate.now().minusDays(7);
        List<Object[]> categoryData = expenseRepository.getTopCategoriesByAmount(sevenDaysAgo, LocalDate.now(), 3);
        
        return categoryData.stream()
                          .map(row -> new CategorySummaryDTO(
                              (String) row[0],
                              (BigDecimal) row[1],
                              0L // Transaction count would need additional query
                          ))
                          .collect(Collectors.toList());
    }
    
    public Object getMonthlyComparison() {
        LocalDate today = LocalDate.now();
        LocalDate startOfMonth = today.withDayOfMonth(1);
        LocalDate startOfLastMonth = startOfMonth.minusMonths(1);
        LocalDate endOfLastMonth = startOfMonth.minusDays(1);
        
        BigDecimal currentMonth = expenseRepository.getTotalAmountByDateRange(startOfMonth, today);
        BigDecimal lastMonth = expenseRepository.getTotalAmountByDateRange(startOfLastMonth, endOfLastMonth);
        
        Map<String, Object> comparison = new HashMap<>();
        comparison.put("currentMonth", currentMonth);
        comparison.put("lastMonth", lastMonth);
        comparison.put("difference", currentMonth.subtract(lastMonth));
        comparison.put("percentageChange", calculateMonthComparison(currentMonth, lastMonth));
        
        return comparison;
    }
    
    public Object getCategorySpendingLast30Days() {
        LocalDate thirtyDaysAgo = LocalDate.now().minusDays(30);
        List<Object[]> categoryData = expenseRepository.getCategorySummaryByDateRange(thirtyDaysAgo, LocalDate.now());
        
        Map<String, BigDecimal> categorySpending = new HashMap<>();
        for (Object[] row : categoryData) {
            categorySpending.put((String) row[0], (BigDecimal) row[1]);
        }
        
        return categorySpending;
    }
    
    public Object getWeeklyTrend() {
        List<Map<String, Object>> weeklyData = new ArrayList<>();
        
        for (int i = 6; i >= 0; i--) {
            LocalDate date = LocalDate.now().minusDays(i);
            BigDecimal dayTotal = expenseRepository.getTotalAmountByDate(date);
            
            Map<String, Object> dayData = new HashMap<>();
            dayData.put("date", date.toString());
            dayData.put("amount", dayTotal);
            dayData.put("dayOfWeek", date.getDayOfWeek().toString());
            
            weeklyData.add(dayData);
        }
        
        return weeklyData;
    }
}