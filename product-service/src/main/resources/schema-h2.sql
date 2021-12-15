DROP TABLE product IF EXISTS;

CREATE TABLE product (
                         id                  INTEGER IDENTITY PRIMARY KEY,
                         product_id          INTEGER NOT NULL,
                         category_id         INTEGER NOT NULL,
                         title               VARCHAR(8192),
                         price               DOUBLE NOT NULL,
                         quantity            INTEGER NOT NULL,
                         description         VARCHAR(8192)
);

CREATE INDEX product_wallpaper_id ON product (product_id);
