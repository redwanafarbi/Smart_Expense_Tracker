package com.expensetracker.service;

import com.expensetracker.dto.ExpenseDTO;
import com.expensetracker.model.Expense;
import com.expensetracker.model.ExpenseCategory;
import com.expensetracker.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ExpenseService {
    
    @Autowired
    private ExpenseRepository expenseRepository;
    
    // Convert Entity to DTO
    private ExpenseDTO convertToDTO(Expense expense) {
        ExpenseDTO dto = new ExpenseDTO();
        dto.setId(expense.getId());
        dto.setExpenseDate(expense.getExpenseDate());
        dto.setCategory(expense.getCategory().getDisplayName());
        dto.setAmount(expense.getAmount());
        dto.setNote(expense.getNote());
        dto.setCreatedAt(expense.getCreatedAt());
        dto.setUpdatedAt(expense.getUpdatedAt());
        return dto;
    }
    
    // Convert DTO to Entity
    private Expense convertToEntity(ExpenseDTO dto) {
        Expense expense = new Expense();
        if (dto.getId() != null) {
            expense.setId(dto.getId());
        }
        expense.setExpenseDate(dto.getExpenseDate());
        expense.setCategory(ExpenseCategory.fromDisplayName(dto.getCategory()));
        expense.setAmount(dto.getAmount());
        expense.setNote(dto.getNote());
        return expense;
    }
    
    // Get all expenses
    public List<ExpenseDTO> getAllExpenses() {
        List<Expense> expenses = expenseRepository.findAll();
        return expenses.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    // Get expense by ID
    public ExpenseDTO getExpenseById(Long id) {
        Optional<Expense> expense = expenseRepository.findById(id);
        return expense.map(this::convertToDTO).orElse(null);
    }
    
    // Create new expense
    public ExpenseDTO createExpense(ExpenseDTO expenseDTO) {
        Expense expense = convertToEntity(expenseDTO);
        Expense savedExpense = expenseRepository.save(expense);
        return convertToDTO(savedExpense);
    }
    
    // Update expense
    public ExpenseDTO updateExpense(Long id, ExpenseDTO expenseDTO) {
        Optional<Expense> existingExpense = expenseRepository.findById(id);
        if (existingExpense.isPresent()) {
            Expense expense = existingExpense.get();
            expense.setExpenseDate(expenseDTO.getExpenseDate());
            expense.setCategory(ExpenseCategory.fromDisplayName(expenseDTO.getCategory()));
            expense.setAmount(expenseDTO.getAmount());
            expense.setNote(expenseDTO.getNote());
            
            Expense updatedExpense = expenseRepository.save(expense);
            return convertToDTO(updatedExpense);
        }
        return null;
    }
    
    // Delete expense
    public boolean deleteExpense(Long id) {
        if (expenseRepository.existsById(id)) {
            expenseRepository.deleteById(id);
            return true;
        }
        return false;
    }
    
    // Get expenses by date range
    public List<ExpenseDTO> getExpensesByDateRange(LocalDate startDate, LocalDate endDate) {
        List<Expense> expenses = expenseRepository.findByExpenseDateBetween(startDate, endDate);
        return expenses.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    // Get expenses by specific date
    public List<ExpenseDTO> getExpensesByDate(LocalDate date) {
        List<Expense> expenses = expenseRepository.findByExpenseDate(date);
        return expenses.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    // Get recent expenses (last 30 days)
    public List<ExpenseDTO> getRecentExpenses() {
        LocalDate thirtyDaysAgo = LocalDate.now().minusDays(30);
        List<Expense> expenses = expenseRepository.findRecentExpenses(thirtyDaysAgo);
        return expenses.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    // Get total amount by date range
    public BigDecimal getTotalAmountByDateRange(LocalDate startDate, LocalDate endDate) {
        return expenseRepository.getTotalAmountByDateRange(startDate, endDate);
    }
    
    // Get total amount by date
    public BigDecimal getTotalAmountByDate(LocalDate date) {
        return expenseRepository.getTotalAmountByDate(date);
    }
    
    // Get category-wise summary
    public List<Object[]> getCategorySummary(LocalDate startDate, LocalDate endDate) {
        return expenseRepository.getCategorySummaryByDateRange(startDate, endDate);
    }