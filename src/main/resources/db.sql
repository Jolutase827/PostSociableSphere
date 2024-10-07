-- Tabla Usuarios
CREATE TABLE Usuarios (
    id BIGSERIAL PRIMARY KEY,
    user_name VARCHAR(255) NOT NULL UNIQUE,
    name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    photo VARCHAR(255),
    description TEXT,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(255) NOT NULL,
    wallet DECIMAL(10, 2),
    api_token VARCHAR(255) UNIQUE,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL
);

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
    user_id BIGINT REFERENCES usuarios(id),
    PRIMARY KEY (post_id, user_id)
);

-- Tabla likes (Para registrar los likes de los usuarios a los posts)
CREATE TABLE likes (
    post_id BIGINT REFERENCES posts(id),
    user_id BIGINT REFERENCES usuarios(id),
    PRIMARY KEY (post_id, user_id)
);
