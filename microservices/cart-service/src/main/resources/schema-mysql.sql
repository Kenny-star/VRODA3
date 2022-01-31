USE `cart-db`;

CREATE TABLE IF NOT EXISTS cart (
    id INT(4) UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    product_id VARCHAR(36) NOT NULL UNIQUE,
    category_id INT(4) UNSIGNED NOT NULL,
    title VARCHAR(8192) NOT NULL,
    price DOUBLE NOT NULL,
    quantity INT(4) UNSIGNED NOT NULL,
    description VARCHAR(8192),
    image VARCHAR(8192)
    ) engine=InnoDB;
