USE `user-db`;

CREATE TABLE IF NOT EXISTS user (
    id INT(4) UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    user_id VARCHAR(36) NOT NULL UNIQUE,
    firstName VARCHAR(8192),
    lastName VARCHAR(8192),
    userName VARCHAR(8192),
    password VARCHAR(8192),
    roles VARCHAR(8192)
    ) engine=InnoDB;
