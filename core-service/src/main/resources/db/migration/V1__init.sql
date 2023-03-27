CREATE TABLE categories (
      id             bigserial primary key,  -- bigserial  ->  bigint + auto increment, bigint maps into long
      title          varchar(255),
      created_at     timestamp default current_timestamp,  --timestamp maps into LocalDateTime, annotation @CreationTimestamp
      updated_at     timestamp default current_timestamp  --annotation @UpdateTimestamp
);

INSERT INTO categories(title) VALUES
('Food'),
('Electronics');

CREATE TABLE products (
      id             bigserial primary key,
      title          varchar(255),
      price          numeric(8,2),
      category_id    bigint references categories (id),
      created_at     timestamp default current_timestamp,
      updated_at     timestamp default current_timestamp
);

INSERT INTO products(title, price, category_id) VALUES
('Bread', 30.00, 1),
('Milk', 80.00, 1),
('Butter', 250.00, 1),
('Cheese', 500.00, 1),
('Buckwheat', 59.00, 1),
('Pasta', 99.00, 1),
('Bacon', 149.99, 1),
('Eggs', 120.00, 1),
('Potato',59.99, 1),
('Tomatoes', 250.00, 1),
('Onion', 30.00, 1),
('Apples', 100.00, 1),
('Pineapple', 299.99, 1),
('Lemons', 99.00, 1),
('Beef', 600.00, 1),
('Chicken', 300.00, 1),
('Flour', 99.00, 1),
('Salmon', 2400.00, 1),
('Tea', 150.00, 1),
('Coffee', 700.00, 1),
('Orange juice', 130.00, 1),
('Water 19l', 230.00, 1),
('Chocolate', 50.00, 1),
('Beer', 199.00, 1),
('Champagne', 499.99, 1),
('Red wine', 999.99, 1),
('Widow Clique', 6999.99, 1);



CREATE TABLE orders (
       id           bigserial primary key,
       username     varchar(255),
       price        numeric(8,2),
       phone        varchar(255),
       address      varchar(255),
       created_at   timestamp default current_timestamp,
       updated_at   timestamp default current_timestamp
);

CREATE TABLE order_items (
      id                  bigserial primary key,
      order_id            bigint references orders(id),
      product_id          bigint references products(id),
      price_per_product   numeric(8,2),
      quantity            int,
      price               numeric(8,2),
      created_at          timestamp default current_timestamp,
      updated_at          timestamp default current_timestamp
);
