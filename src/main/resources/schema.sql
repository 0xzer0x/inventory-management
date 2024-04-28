-- Create the users table
CREATE TABLE users(
    username VARCHAR_IGNORECASE(50) NOT NULL PRIMARY KEY,
    password VARCHAR_IGNORECASE(60) NOT NULL, -- Adjusted to accommodate BCrypt hash length
    enabled BOOLEAN NOT NULL
);

-- Create the authorities table
CREATE TABLE authorities (
    username VARCHAR_IGNORECASE(50) NOT NULL,
    authority VARCHAR_IGNORECASE(50) NOT NULL,
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

