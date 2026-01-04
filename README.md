# Rainy Spring Security

☔️ Experiments with Spring Security.

## Setup Database

> MySQL

```sql
-- 1. Create database
CREATE DATABASE IF NOT EXISTS spring_security_test;
USE spring_security_test;

-- 2. User table
CREATE TABLE sys_user (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    email  VARCHAR(100),
    enabled BOOLEAN DEFAULT TRUE,
    account_non_expired BOOLEAN DEFAULT TRUE,
    account_non_locked BOOLEAN DEFAULT TRUE,
    credentials_non_expired BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- 3. Insert users for testing
-- passwords are bcrypt hash of "test123" for all test users
INSERT INTO sys_user (username, password, email, enabled) VALUES
    ('admin', '$2a$12$TMymFyDdnuF2d8od6XyMZOzk1UmttIA/Dq4xX4ovPUvaKlzb4My6G', 'admin@test.com', TRUE),
    ('user', '$2a$12$TMymFyDdnuF2d8od6XyMZOzk1UmttIA/Dq4xX4ovPUvaKlzb4My6G', 'user@test.com', TRUE),
    ('user2', '$2a$12$TMymFyDdnuF2d8od6XyMZOzk1UmttIA/Dq4xX4ovPUvaKlzb4My6G', 'user2@test.com', FALSE)
```

## Configuration

1. Define a proper `JWT_KEY` in env

```bash
# generate JWT_KEY
openssl rand -base64 32
```