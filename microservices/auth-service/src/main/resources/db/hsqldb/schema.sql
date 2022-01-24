DROP TABLE user IF EXISTS;

CREATE TABLE user (
                        id               INTEGER IDENTITY PRIMARY KEY,
                        user_id          INTEGER NOT NULL,
                        firstName        VARCHAR(8192),
                        lastName         VARCHAR(8192),
                        userName         VARCHAR(8192),
                        password         VARCHAR(8192),
                        roles            VARCHAR(8192)
);

CREATE INDEX user_wallpaper_id ON user (user_id);
