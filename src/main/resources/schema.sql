CREATE TABLE IF NOT EXISTS user_types (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    phone VARCHAR(50),
    email VARCHAR(255),
    owner BOOLEAN
);

CREATE TABLE IF NOT EXISTS restaurants (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    address VARCHAR(255),
    cuisine_type VARCHAR(100),
    opening_hours VARCHAR(100),
    owner_id BIGINT,
    FOREIGN KEY (owner_id) REFERENCES user_types(id)
);