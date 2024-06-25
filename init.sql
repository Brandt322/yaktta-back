-- Crear la tabla ROLES
CREATE TABLE IF NOT EXISTS roles (
    id_rol INT PRIMARY KEY AUTO_INCREMENT,
    rol VARCHAR(100)
);

-- Crear la tabla USERS
CREATE TABLE IF NOT EXISTS users (
    id_user INT PRIMARY KEY AUTO_INCREMENT,
    first_name VARCHAR(100),
    last_name VARCHAR(100),
    email VARCHAR(100),
    city VARCHAR(100),
    postal_code VARCHAR(20),
    password VARCHAR(250),
    is_account_non_expired BOOLEAN,
    is_account_non_locked BOOLEAN,
    is_credentials_non_expired BOOLEAN,
    is_enabled BOOLEAN
);

CREATE TABLE IF NOT EXISTS user_roles (
    id_user INT,
    id_rol INT,
    PRIMARY KEY (id_user, id_rol),
    FOREIGN KEY (id_user) REFERENCES users(id_user),
    FOREIGN KEY (id_rol) REFERENCES roles(id_rol)
);

-- Crear la tabla BRANDS
CREATE TABLE IF NOT EXISTS brands (
    id_brands INT PRIMARY KEY AUTO_INCREMENT,
    brand_name VARCHAR(50) UNIQUE NOT NULL
);

-- Crear la tabla PRODUCT
CREATE TABLE IF NOT EXISTS product (
    id_product INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    description TEXT NOT NULL,
    stock INT,
    price DECIMAL(10, 2),
    discount DECIMAL(5, 2),
    product_type VARCHAR(100) NOT NULL,
    image LONGBLOB,
    id_brands INT,
    FOREIGN KEY (id_brands) REFERENCES brands(id_brands)
);

-- Crear la tabla PAYMENT_METHOD
CREATE TABLE IF NOT EXISTS payment_method (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100)
);

-- Crear la tabla ORDERS
CREATE TABLE IF NOT EXISTS orders (
    id_orders INT PRIMARY KEY AUTO_INCREMENT,
    client_id INT,
    order_date DATE,
    product_names VARCHAR(500),
    total_price DECIMAL(10, 2),
    FOREIGN KEY (client_id) REFERENCES users(id_user)
);

-- Crear la tabla ORDER_DETAIL
CREATE TABLE IF NOT EXISTS order_detail (
    id_order_product INT PRIMARY KEY AUTO_INCREMENT,
    id_order INT,
    id_product INT,
    quantity_products_ordered INT,
    total_price DECIMAL(10, 2),
    FOREIGN KEY (id_order) REFERENCES orders(id_orders),
    FOREIGN KEY (id_product) REFERENCES product(id_product)
);

-- Crear la tabla BILLING
CREATE TABLE IF NOT EXISTS billing (
    id_billing INT PRIMARY KEY AUTO_INCREMENT,
    id_client INT,
    payment_date DATE,
    payment_amount DECIMAL(10, 2),
    id_payment_method INT,
    FOREIGN KEY (id_client) REFERENCES users(id_user),
    FOREIGN KEY (id_payment_method) REFERENCES payment_method(id)
    );

-- Insertar datos en la tabla BRANDS
INSERT INTO brands (brand_name) VALUES ('Red Label');
INSERT INTO brands (brand_name) VALUES ('Ruskaya');
INSERT INTO brands (brand_name) VALUES ('Jhonny Walker');

-- Insertar roles en la tabla ROLES
INSERT INTO roles (rol) VALUES ('USER');
INSERT INTO roles (rol) VALUES ('ADMIN');

-- Insertar un usuario en la tabla USERS
INSERT INTO users (first_name, last_name, email, city, postal_code, password, is_account_non_expired, is_account_non_locked, is_credentials_non_expired, is_enabled)
VALUES ('Admin', 'User', 'admin@example.com', 'Admin City', '12345', '$2a$12$i7or5zMmyCUXLjto4nDjWuW7S6M/91DKbP4UitV5ZoowjVFkRkRN2', true, true, true, true);

-- Obtener el id_user del usuario que acabas de insertar
SET @admin_user_id = (SELECT id_user FROM users WHERE email = 'admin@example.com');

-- Obtener el id_rol del rol de administrador
SET @admin_role_id = (SELECT id_rol FROM roles WHERE rol = 'ADMIN');

-- Asignar el rol de administrador al usuario
INSERT INTO user_roles (id_user, id_rol) VALUES (@admin_user_id, @admin_role_id);