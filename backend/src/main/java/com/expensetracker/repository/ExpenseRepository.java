package com.expensetracker.repository;

import com.expensetracker.model.Expense;
import com.expensetracker.model.ExpenseCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    
    // Find expenses by date range
    List<Expense> findByExpenseDateBetween(LocalDate startDate, LocalDate endDate);
    
    // Find expenses by specific date
    List<Expense> findByExpenseDate(LocalDate date);
    
    // Find expenses by category
    List<Expense> findByCategory(ExpenseCategory category);
    
    // Find expenses by category and date range
    List<Expense> findByCategoryAndExpenseDateBetween(ExpenseCategory category, LocalDate startDate, LocalDate endDate);
    
    // Calculate total amount by date range
    @Query("SELECT COALESCE(SUM(e.amount), 0) FROM Expense e WHERE e.expenseDate BETWEEN :startDate AND :endDate")
    BigDecimal getTotalAmountByDateRange(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
    
    // Calculate total amount by date
    @Query("SELECT COALESCE(SUM(e.amount), 0) FROM Expense e WHERE e.expenseDate = :date")
    BigDecimal getTotalAmountByDate(@Param("date") LocalDate date);
    
    // Calculate total amount by category and date range
    @Query("SELECT COALESCE(SUM(e.amount), 0) FROM Expense e WHERE e.category = :category AND e.expenseDate BETWEEN :startDate AND :endDate")
    BigDecimal getTotalAmountByCategoryAndDateRange(@Param("category") ExpenseCategory category, 
                                                   @Param("startDate") LocalDate startDate, 
                                                   @Param("endDate") LocalDate endDate);
    
    // Get category-wise summary for date range
    @Query("SELECT e.category as category, COALESCE(SUM(e.amount), 0) as total FROM Expense e WHERE e.expenseDate BETWEEN :startDate AND :endDate GROUP BY e.category ORDER BY total DESC")
    List<Object[]> getCategorySummaryByDateRange(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
    
    // Get recent expenses (last 30 days)
    @Query("SELECT e FROM Expense e WHERE e.expenseDate >= :thirtyDaysAgo ORDER BY e.expenseDate DESC, e.createdAt DESC")
    List<Expense> findRecentExpenses(@Param("thirtyDaysAgo") LocalDate thirtyDaysAgo);
    
    // Get top categories by amount for date range
    @Query(value = "SELECT category, SUM(amount) as total FROM expenses WHERE expense_date BETWEEN :startDate AND :endDate GROUP BY category ORDER BY total DESC LIMIT :limit", nativeQuery = true)
    List<Object[]> getTopCategoriesByAmount(@Param("startDate") LocalDate startDate, 
                                          @Param("endDate") LocalDate endDate, 
                                          @Param("limit") int limit);
}