DROP TABLE product IF EXISTS;

CREATE TABLE product (
                        product_id          INTEGER IDENTITY PRIMARY KEY,
                        category_id         INTEGER NOT NULL,
                        title               VARCHAR(8192),
                        price               DOUBLE NOT NULL,
                        description         VARCHAR(8192)
);

CREATE INDEX product_wallpaper_id ON product (product_id);
