-- Tabla posts
CREATE TABLE Post (
    id BIGSERIAL PRIMARY KEY,
    content TEXT,
    type VARCHAR(255),
    is_paid BOOLEAN,
    cost NUMERIC(10, 2),
    is_ad BOOLEAN,
    max_views INTEGER,
    views_remaining INTEGER,
    footer TEXT,
    parent_id BIGINT,
    created_at TIMESTAMP WITHOUT TIME ZONE,
    updated_at TIMESTAMP WITHOUT TIME ZONE
);


-- Tabla post_users (Relación muchos a muchos entre posts y usuarios)
CREATE TABLE Likes (
    id BIGSERIAL PRIMARY KEY,
    post_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE
);


-- Tabla likes (Para registrar los likes de los usuarios a los posts)
CREATE TABLE PostUser (
    id BIGSERIAL PRIMARY KEY,
    post_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL
);

