-- Insert Suppliers
INSERT INTO supplier (name, email, phone_number) VALUES ('Supplier One', 'supplier1@example.com', '123-456-7890');
INSERT INTO supplier (name, email, phone_number) VALUES ('Supplier Two', 'supplier2@example.com', '234-567-8901');
INSERT INTO supplier (name, email, phone_number) VALUES ('Supplier Three', 'supplier3@example.com', '345-678-9012');

-- Insert Items
INSERT INTO item (name, quantity, unit_price) VALUES ('Item One', 10, 10.99);
INSERT INTO item (name, quantity, unit_price) VALUES ('Item Two', 20, 20.99);
INSERT INTO item (name, quantity, unit_price) VALUES ('Item Three', 30, 30.99);

-- Establish Connections Between Items and Suppliers
-- Assuming the join table is named 'item_supplier' and has columns 'item_id' and 'supplier_id'
INSERT INTO supplier_items (items_id, suppliers_id) VALUES (1, 1); -- Connects Item One with Supplier One
INSERT INTO supplier_items (items_id, suppliers_id) VALUES (2, 1); -- Connects Item Two with Supplier One
INSERT INTO supplier_items (items_id, suppliers_id) VALUES (3, 2); -- Connects Item Three with Supplier Two
INSERT INTO supplier_items (items_id, suppliers_id) VALUES (1, 3); -- Connects Item One with Supplier Three
