CREATE TABLE app_user
(
    id       BIGSERIAL PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    consent  BOOLEAN      NOT NULL,
    role     VARCHAR(50)  NOT NULL
);

CREATE TABLE orders
(
    id          BIGSERIAL PRIMARY KEY,
    username    VARCHAR(255)     NOT NULL,
    total_price DOUBLE PRECISION NOT NULL,
    order_date  DATE             NOT NULL,
    user_id     BIGINT           NOT NULL,

    CONSTRAINT fk_orders_user
        FOREIGN KEY (user_id)
            REFERENCES app_user (id)
            ON DELETE CASCADE
);

CREATE TABLE product
(
    id          BIGSERIAL PRIMARY KEY,
    name        VARCHAR(255)     NOT NULL,
    price       DOUBLE PRECISION NOT NULL,
    category    VARCHAR(255)     NOT NULL,
    picture_url VARCHAR(500)
);

CREATE TABLE order_items
(
    id           BIGSERIAL PRIMARY KEY,
    product_name VARCHAR(255)     NOT NULL,
    quantity     INTEGER          NOT NULL,
    price        DOUBLE PRECISION NOT NULL,
    order_id     BIGINT           NOT NULL,
    product_id   BIGINT,

    CONSTRAINT fk_order_items_order
        FOREIGN KEY (order_id)
            REFERENCES orders (id)
            ON DELETE CASCADE,

    CONSTRAINT fk_order_items_product
        FOREIGN KEY (product_id)
            REFERENCES product (id)
);