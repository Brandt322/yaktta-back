-- Crear la tabla ROLES
CREATE TABLE IF NOT EXISTS roles (
    id_rol INT PRIMARY KEY,
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
    password VARCHAR(50),
    id_rol INT,
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
