package com.expensetracker.model;

public enum ExpenseCategory {
    FOOD_GROCERY("Food & Grocery"),
    HOUSE_RENT("House Rent"),
    TRANSPORT("Transport"),
    EDUCATION("Education"),
    HEALTHCARE("Healthcare"),
    BILLS_UTILITIES("Bills & Utilities"),
    ENTERTAINMENT("Entertainment"),
    OTHERS("Others");
    
    private final String displayName;
    
    ExpenseCategory(String displayName) {
        this.displayName = displayName;
    }
    
    public String getDisplayName() {
        return displayName;
    }
    
    public static ExpenseCategory fromDisplayName(String displayName) {
        for (ExpenseCategory category : ExpenseCategory.values()) {
            if (category.displayName.equals(displayName)) {
                return category;
            }
        }
        throw new IllegalArgumentException("No category found for display name: " + displayName);
    }
}