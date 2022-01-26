DROP TABLE cart IF EXISTS;

CREATE TABLE cart (
                         id                  INTEGER IDENTITY PRIMARY KEY,
                         product_id          VARCHAR(36) NOT NULL UNIQUE,
                         category_id         INTEGER NOT NULL,
                         title               VARCHAR(8192),
                         price               DOUBLE NOT NULL,
                         quantity            INTEGER NOT NULL,
                         description         VARCHAR(8192)
);

CREATE INDEX product_wallpaper_id ON cart (product_id);
