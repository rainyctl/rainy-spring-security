# Rainy Spring Security

☔️ Experiments with Spring Security.

## Setup Database

> MySQL

```sql
-- 1. Create database
CREATE DATABASE IF NOT EXISTS spring_security_test;
USE spring_security_test;

-- 2. User table
CREATE TABLE sys_user
(
    id                      BIGINT PRIMARY KEY AUTO_INCREMENT,
    username                VARCHAR(50) UNIQUE NOT NULL,
    password                VARCHAR(255)       NOT NULL,
    email                   VARCHAR(100),
    enabled                 BOOLEAN   DEFAULT TRUE,
    account_non_expired     BOOLEAN   DEFAULT TRUE,
    account_non_locked      BOOLEAN   DEFAULT TRUE,
    credentials_non_expired BOOLEAN   DEFAULT TRUE,
    created_at              TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at              TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- 3. Insert users for testing
-- passwords are bcrypt hash of "test123" for all test users
INSERT INTO sys_user (username, password, email, enabled)
VALUES ('admin', '$2a$12$TMymFyDdnuF2d8od6XyMZOzk1UmttIA/Dq4xX4ovPUvaKlzb4My6G', 'admin@test.com', TRUE),
       ('user', '$2a$12$TMymFyDdnuF2d8od6XyMZOzk1UmttIA/Dq4xX4ovPUvaKlzb4My6G', 'user@test.com', TRUE),
       ('user2', '$2a$12$TMymFyDdnuF2d8od6XyMZOzk1UmttIA/Dq4xX4ovPUvaKlzb4My6G', 'user2@test.com', FALSE);

-- 4. Tables for role based access control (RBAC)
-- with some mock data

-- Role Table
CREATE TABLE sys_role (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL COMMENT 'role display name',
    role_key VARCHAR(50) NOT NULL UNIQUE COMMENT 'role identifier, e.g. admin / reader',
    status TINYINT DEFAULT 1 COMMENT '1=enabled, 0=disabled',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Menu (Permission) Table
CREATE TABLE sys_menu (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL COMMENT 'resource display name',
    perm_key VARCHAR(100) NOT NULL COMMENT 'permission key, e.g. sys:book:read',
    status TINYINT DEFAULT 1 COMMENT '1=enabled, 0=disabled',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- User Role Mapping
CREATE TABLE sys_user_role (
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, role_id)
);

-- Role Menu (Permission) Mapping
CREATE TABLE sys_role_menu (
    role_id BIGINT NOT NULL,
    menu_id BIGINT NOT NULL,
    PRIMARY KEY (role_id, menu_id)
);

INSERT INTO sys_role (name, role_key) VALUES
('administrator', 'admin'), -- 1
('reader', 'reader'); -- 2

INSERT INTO sys_menu (name, perm_key) VALUES
('View Book', 'sys:book:view'), -- 1
('View Book List', 'sys:book:list'), -- 2
('Delete Book', 'sys:book:delete'); -- 3

INSERT INTO sys_user_role (user_id, role_id) VALUES
(1, 1), -- admin user -> admin role
(2, 2); -- user -> reader role

INSERT INTO sys_role_menu (role_id, menu_id) VALUES
(1, 1),
(1, 2),
(1, 3), -- admin role -> perm 1, 2, 3
(2, 1),
(2, 2); -- reader role -> perm 1, 2

```

## Configuration

Define a proper `JWT_KEY` in env:

```bash
# generate JWT_KEY
openssl rand -base64 32
```

Set Redis connection in `application.properties`:

```properties
spring.redis.host=localhost
spring.redis.port=6379
spring.redis.password=
```

## Frontend (Optional)

Frontend project for testing CORS / APIs:
[Rainy-AI-Frontend](https://github.com/rainyctl/rainy-ai-frontend)

## Notes

Commonly Used Spring Security Exceptions:

| Scenario           | What to throw                         | HTTP |
|--------------------|---------------------------------------|------|
| No token           | `InsufficientAuthenticationException` | 401  |
| Token parse failed | `BadCredentialsException`             | 401  |
| Token expired      | `CredentialsExpiredException`         | 401  |
| User disabled      | `DisabledException`                   | 401  |
| No permission      | `AccessDeniedException`               | 403  |
