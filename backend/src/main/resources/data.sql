-- Sample Expenses Data
INSERT INTO expenses (expense_date, category, amount, note, created_at, updated_at) VALUES
('2025-09-09', 'FOOD_GROCERY', 25.50, 'Breakfast at cafe', NOW(), NOW()),
('2025-09-09', 'TRANSPORT', 8.75, 'Bus fare', NOW(), NOW()),
('2025-09-08', 'FOOD_GROCERY', 85.20, 'Weekly groceries', NOW(), NOW()),
('2025-09-08', 'BILLS_UTILITIES', 120.00, 'Electricity bill', NOW(), NOW()),
('2025-09-07', 'ENTERTAINMENT', 45.00, 'Movie tickets', NOW(), NOW()),
('2025-09-07', 'FOOD_GROCERY', 12.30, 'Coffee', NOW(), NOW()),
('2025-09-06', 'TRANSPORT', 15.00, 'Taxi fare', NOW(), NOW()),
('2025-09-06', 'HEALTHCARE', 65.00, 'Doctor visit', NOW(), NOW()),
('2025-09-05', 'FOOD_GROCERY', 58.90, 'Lunch and snacks', NOW(), NOW()),
('2025-09-05', 'EDUCATION', 200.00, 'Online course', NOW(), NOW()),
('2025-09-04', 'FOOD_GROCERY', 32.40, 'Dinner', NOW(), NOW()),
('2025-09-04', 'OTHERS', 25.00, 'Miscellaneous', NOW(), NOW()),
('2025-09-03', 'TRANSPORT', 22.50, 'Uber ride', NOW(), NOW()),
('2025-09-03', 'ENTERTAINMENT', 35.75, 'Concert tickets', NOW(), NOW()),
('2025-09-02', 'FOOD_GROCERY', 76.80, 'Grocery shopping', NOW(), NOW()),
('2025-09-01', 'HOUSE_RENT', 800.00, 'Monthly rent', NOW(), NOW()),
('2025-08-31', 'FOOD_GROCERY', 94.50, 'Monthly groceries', NOW(), NOW()),
('2025-08-30', 'BILLS_UTILITIES', 85.30, 'Internet bill', NOW(), NOW()),
('2025-08-29', 'TRANSPORT', 45.60, 'Gas for car', NOW(), NOW()),
('2025-08-28', 'ENTERTAINMENT', 78.90, 'Weekend activities', NOW(), NOW());

-- Sample Wishlist Items Data
INSERT INTO wishlist_items (title, target_amount, saved_amount, priority, deadline, created_at, updated_at) VALUES
('New Smartphone', 800.00, 320.00, 'HIGH', '2025-12-31', NOW(), NOW()),
('Vacation to Japan', 2500.00, 850.00, 'MEDIUM', '2026-06-15', NOW(), NOW()),
('Gaming Laptop', 1200.00, 450.00, 'LOW', '2025-11-30', NOW(), NOW()),
('Emergency Fund', 5000.00, 1200.00, 'HIGH', '2026-01-01', NOW(), NOW()),
('New Camera', 600.00, 150.00, 'MEDIUM', '2025-10-15', NOW(), NOW());