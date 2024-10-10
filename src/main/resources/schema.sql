-- Tabla posts
CREATE TABLE posts (
    id BIGINT PRIMARY KEY NOT NULL,
    content TEXT NOT NULL,
    type VARCHAR NOT NULL CHECK (type IN ('text', 'image', 'video')),
    is_paid BOOLEAN DEFAULT FALSE,
    cost NUMERIC(10,2) NULL,
    is_ad BOOLEAN DEFAULT FALSE,
    max_views INTEGER NULL,
    views_remaining INTEGER NULL,
    footer TEXT NULL,
    parent_id BIGINT REFERENCES posts(id),
    likes INTEGER DEFAULT 0
);

-- Tabla post_users (Relación muchos a muchos entre posts y usuarios)
CREATE TABLE post_users (
    post_id BIGINT REFERENCES posts(id),
    user_id BIGINT,
    PRIMARY KEY (post_id, user_id)
);

-- Tabla likes (Para registrar los likes de los usuarios a los posts)
CREATE TABLE likes (
    post_id BIGINT REFERENCES posts(id),
    user_id BIGINT,
    PRIMARY KEY (post_id, user_id)
);
