-- This script runs once at database initialization

---- Auth ----

-- Create the users table
CREATE TABLE users(
    username VARCHAR(50) NOT NULL PRIMARY KEY,
    password VARCHAR(60) NOT NULL, -- Adjusted to accommodate BCrypt hash length
    enabled BOOLEAN NOT NULL
);
-- Create the authorities table
CREATE TABLE authorities (
    username VARCHAR(50) NOT NULL,
    authority VARCHAR(50) NOT NULL,
    CONSTRAINT fk_authorities_users FOREIGN KEY(username) REFERENCES users(username)
);
CREATE UNIQUE INDEX ix_auth_username ON authorities (username, authority);
-- Insert users with BCrypt-hashed passwords
INSERT INTO users (username, password, enabled) VALUES ('user', '$2a$12$En57ktPRxHXZ//TX69iQL.9ev7gOZogzt5X.dC5SP3/hroAbhOBFW', TRUE);
INSERT INTO users (username, password, enabled) VALUES ('employee', '$2a$12$HPvHUy7bHO.2oaoqtIM1OuGKc0FoBVESnd5g4s4BmOuFLpc7epxju', TRUE);
INSERT INTO users (username, password, enabled) VALUES ('admin', '$2a$12$ko/6pCsEaDAh5Yqt4rkw.unex7R0HP17DJsUPB/rpBHzu2pry2IRK', TRUE);
-- Insert authorities
INSERT INTO authorities (username, authority) VALUES ('user', 'ROLE_USER');
INSERT INTO authorities (username, authority) VALUES ('employee', 'ROLE_EMPLOYEE');
INSERT INTO authorities (username, authority) VALUES ('admin', 'ROLE_ADMIN');

---- Data ----

-- Create the item table
CREATE TABLE item (
    id SERIAL PRIMARY KEY,
    unit_price DOUBLE PRECISION,
    quantity BIGINT,
    name VARCHAR(255)
);
-- Create the supplier table
CREATE TABLE supplier (
    id SERIAL PRIMARY KEY,
    email VARCHAR(255),
    name VARCHAR(255),
    phone_number VARCHAR(255)
);
-- Create the supplier_items table
CREATE TABLE supplier_items (
    items_id INTEGER NOT NULL,
    suppliers_id INTEGER NOT NULL,
    PRIMARY KEY (items_id, suppliers_id)
);
-- Add foreign key constraints to supplier_items with meaningful names
ALTER TABLE IF EXISTS supplier_items
ADD CONSTRAINT fk_supplier_items_item_id FOREIGN KEY (items_id) REFERENCES item(id);
ALTER TABLE IF EXISTS supplier_items
ADD CONSTRAINT fk_supplier_items_supplier_id FOREIGN KEY (suppliers_id) REFERENCES supplier(id);
