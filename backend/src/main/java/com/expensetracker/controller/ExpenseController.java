package com.expensetracker.controller;

import com.expensetracker.dto.ExpenseDTO;
import com.expensetracker.model.Expense;
import com.expensetracker.service.ExpenseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/expenses")
@CrossOrigin(origins = "*")
public class ExpenseController {
    
    @Autowired
    private ExpenseService expenseService;
    
    // Get all expenses
    @GetMapping
    public ResponseEntity<List<ExpenseDTO>> getAllExpenses() {
        List<ExpenseDTO> expenses = expenseService.getAllExpenses();
        return ResponseEntity.ok(expenses);
    }
    
    // Get expense by ID
    @GetMapping("/{id}")
    public ResponseEntity<ExpenseDTO> getExpenseById(@PathVariable Long id) {
        ExpenseDTO expense = expenseService.getExpenseById(id);
        if (expense != null) {
            return ResponseEntity.ok(expense);
        }
        return ResponseEntity.notFound().build();
    }
    
    // Create new expense
    @PostMapping
    public ResponseEntity<ExpenseDTO> createExpense(@Valid @RequestBody ExpenseDTO expenseDTO) {
        ExpenseDTO createdExpense = expenseService.createExpense(expenseDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdExpense);
    }
    
    // Update expense
    @PutMapping("/{id}")
    public ResponseEntity<ExpenseDTO> updateExpense(@PathVariable Long id, @Valid @RequestBody ExpenseDTO expenseDTO) {
        ExpenseDTO updatedExpense = expenseService.updateExpense(id, expenseDTO);
        if (updatedExpense != null) {
            return ResponseEntity.ok(updatedExpense);
        }
        return ResponseEntity.notFound().build();
    }
    
    // Delete expense
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExpense(@PathVariable Long id) {
        boolean deleted = expenseService.deleteExpense(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
    
    // Get expenses by date range
    @GetMapping("/date-range")
    public ResponseEntity<List<ExpenseDTO>> getExpensesByDateRange(
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate) {
        List<ExpenseDTO> expenses = expenseService.getExpensesByDateRange(startDate, endDate);
        return ResponseEntity.ok(expenses);
    }
    
    // Get expenses by specific date
    @GetMapping("/date/{date}")
    public ResponseEntity<List<ExpenseDTO>> getExpensesByDate(@PathVariable LocalDate date) {
        List<ExpenseDTO> expenses = expenseService.getExpensesByDate(date);
        return ResponseEntity.ok(expenses);
    }
    
    // Get today's expenses
    @GetMapping("/today")
    public ResponseEntity<List<ExpenseDTO>> getTodayExpenses() {
        List<ExpenseDTO> expenses = expenseService.getExpensesByDate(LocalDate.now());
        return ResponseEntity.ok(expenses);
    }
    
    // Get recent expenses (last 30 days)
    @GetMapping("/recent")
    public ResponseEntity<List<ExpenseDTO>> getRecentExpenses() {
        List<ExpenseDTO> expenses = expenseService.getRecentExpenses();
        return ResponseEntity.ok(expenses);
    }
    
    // Get category-wise summary for date range
    @GetMapping("/category-summary")
    public ResponseEntity<List<Object[]>> getCategorySummary(
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate) {
        List<Object[]> summary = expenseService.getCategorySummary(startDate, endDate);
        return ResponseEntity.ok(summary);
    }
}