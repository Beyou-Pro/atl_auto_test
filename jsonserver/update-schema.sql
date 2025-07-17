CREATE TABLE addresses
(
    id           UUID NOT NULL,
    street       VARCHAR(255),
    city         VARCHAR(255),
    zipcode      VARCHAR(255),
    country      VARCHAR(255),
    address_type VARCHAR(255),
    user_id      UUID,
    CONSTRAINT pk_addresses PRIMARY KEY (id)
);

CREATE TABLE customers
(
    id                         UUID NOT NULL,
    name                       VARCHAR(255),
    email                      VARCHAR(255),
    default_ship_address_id    UUID,
    default_billing_address_id UUID,
    CONSTRAINT pk_customers PRIMARY KEY (id)
);

CREATE TABLE order_items
(
    id          UUID             NOT NULL,
    order_id    UUID,
    product_id  VARCHAR(255),
    quantity    INTEGER          NOT NULL,
    unit_price  DOUBLE PRECISION NOT NULL,
    total_price DOUBLE PRECISION NOT NULL,
    session_id  VARCHAR(255),
    CONSTRAINT pk_order_items PRIMARY KEY (id)
);

CREATE TABLE orders
(
    id                  UUID             NOT NULL,
    order_date          TIMESTAMP WITHOUT TIME ZONE,
    status              VARCHAR(255),
    customer_id         UUID,
    shipping_address_id UUID,
    billing_address_id  UUID,
    carrier_id          VARCHAR(255),
    payment_id          VARCHAR(255),
    order_total         DOUBLE PRECISION NOT NULL,
    CONSTRAINT pk_orders PRIMARY KEY (id)
);

ALTER TABLE customers
    ADD CONSTRAINT uc_customers_default_billing_address UNIQUE (default_billing_address_id);

ALTER TABLE customers
    ADD CONSTRAINT uc_customers_default_ship_address UNIQUE (default_ship_address_id);

ALTER TABLE addresses
    ADD CONSTRAINT FK_ADDRESSES_ON_USER FOREIGN KEY (user_id) REFERENCES customers (id);

ALTER TABLE customers
    ADD CONSTRAINT FK_CUSTOMERS_ON_DEFAULT_BILLING_ADDRESS FOREIGN KEY (default_billing_address_id) REFERENCES addresses (id);

ALTER TABLE customers
    ADD CONSTRAINT FK_CUSTOMERS_ON_DEFAULT_SHIP_ADDRESS FOREIGN KEY (default_ship_address_id) REFERENCES addresses (id);

ALTER TABLE orders
    ADD CONSTRAINT FK_ORDERS_ON_BILLING_ADDRESS FOREIGN KEY (billing_address_id) REFERENCES addresses (id);

ALTER TABLE orders
    ADD CONSTRAINT FK_ORDERS_ON_CUSTOMER FOREIGN KEY (customer_id) REFERENCES customers (id);

ALTER TABLE orders
    ADD CONSTRAINT FK_ORDERS_ON_SHIPPING_ADDRESS FOREIGN KEY (shipping_address_id) REFERENCES addresses (id);

ALTER TABLE order_items
    ADD CONSTRAINT FK_ORDER_ITEMS_ON_ORDER FOREIGN KEY (order_id) REFERENCES orders (id);