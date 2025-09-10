package com.expensetracker.repository;

import com.expensetracker.model.WishlistItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WishlistItemRepository extends JpaRepository<WishlistItem, Long> {
    
    // Find wishlist items by priority
    List<WishlistItem> findByPriority(WishlistItem.Priority priority);
    
    // Find wishlist items by priority ordered by creation date
    List<WishlistItem> findByPriorityOrderByCreatedAtAsc(WishlistItem.Priority priority);
    
    // Find all wishlist items ordered by priority and creation date
    @Query("SELECT w FROM WishlistItem w ORDER BY " +
           "CASE w.priority " +
           "WHEN 'HIGH' THEN 1 " +
           "WHEN 'MEDIUM' THEN 2 " +
           "WHEN 'LOW' THEN 3 " +
           "END, w.createdAt ASC")
    List<WishlistItem> findAllOrderedByPriority();
    
    // Find wishlist items with upcoming deadlines
    @Query("SELECT w FROM WishlistItem w WHERE w.deadline IS NOT NULL AND w.deadline <= :date ORDER BY w.deadline ASC")
    List<WishlistItem> findByDeadlineBeforeOrderByDeadlineAsc(java.time.LocalDate date);
    
    // Find completed goals (saved amount >= target amount)
    @Query("SELECT w FROM WishlistItem w WHERE w.savedAmount >= w.targetAmount")
    List<WishlistItem> findCompletedGoals();
    
    // Find goals in progress
    @Query("SELECT w FROM WishlistItem w WHERE w.savedAmount < w.targetAmount ORDER BY w.priority ASC, w.createdAt ASC")
    List<WishlistItem> findGoalsInProgress();
}