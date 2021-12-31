USE `product-db`;

CREATE TABLE IF NOT EXISTS product (
    id INT(4) UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    product_id INT(4) UNSIGNED NOT NULL,
    category_id INT(4) UNSIGNED NOT NULL,
    title VARCHAR(8192) NOT NULL,
    price DOUBLE NOT NULL,
    quantity INT(4) UNSIGNED NOT NULL,
    description VARCHAR(8192)
    ) engine=InnoDB;
