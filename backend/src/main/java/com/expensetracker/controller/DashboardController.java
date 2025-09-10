package com.expensetracker.controller;

import com.expensetracker.dto.DashboardDTO;
import com.expensetracker.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin(origins = "*")
public class DashboardController {
    
    @Autowired
    private DashboardService dashboardService;
    
    // Get dashboard data
    @GetMapping
    public ResponseEntity<DashboardDTO> getDashboardData() {
        DashboardDTO dashboardData = dashboardService.getDashboardData();
        return ResponseEntity.ok(dashboardData);
    }
    
    // Get monthly comparison data
    @GetMapping("/monthly-comparison")
    public ResponseEntity<Object> getMonthlyComparison() {
        Object comparison = dashboardService.getMonthlyComparison();
        return ResponseEntity.ok(comparison);
    }
    
    // Get category-wise spending for last 30 days
    @GetMapping("/category-spending")
    public ResponseEntity<Object> getCategorySpending() {
        Object categorySpending = dashboardService.getCategorySpendingLast30Days();
        return ResponseEntity.ok(categorySpending);
    }
    
    // Get weekly spending trend
    @GetMapping("/weekly-trend")
    public ResponseEntity<Object> getWeeklyTrend() {
        Object weeklyTrend = dashboardService.getWeeklyTrend();
        return ResponseEntity.ok(weeklyTrend);
    }
}