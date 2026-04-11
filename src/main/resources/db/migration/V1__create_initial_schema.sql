CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE places (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    category VARCHAR(100),
    address VARCHAR(255) NOT NULL,
    city VARCHAR(255) NOT NULL,
    country VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE reviews (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    place_id BIGINT NOT NULL,
    rating INTEGER NOT NULL CHECK (rating >= 1 AND rating <= 5) ,
    comment TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_reviews_users
        FOREIGN KEY (user_id) REFERENCES users(id),

    CONSTRAINT fk_reviews_places
        FOREIGN KEY (place_id) REFERENCES places(id) ON DELETE CASCADE,

    CONSTRAINT unique_user_place UNIQUE(user_id, place_id)
);

CREATE TABLE images (
    id BIGSERIAL PRIMARY KEY,
    review_id BIGINT NULL,
    place_id  BIGINT NULL,
    image_url VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_images_reviews
        FOREIGN KEY (review_id) REFERENCES reviews(id) ON DELETE CASCADE,

    CONSTRAINT fk_images_places
        FOREIGN KEY (place_id) REFERENCES places(id) ON DELETE CASCADE,

    CONSTRAINT chk_image_owner CHECK(
        (review_id IS NOT NULL AND place_id IS NULL) OR
        (review_id IS NULL AND place_id IS NOT NULL)
    )
);