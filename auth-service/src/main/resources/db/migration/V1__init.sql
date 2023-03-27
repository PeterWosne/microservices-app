CREATE TABLE roles (
      id             bigserial primary key,
      title          varchar(50) not null unique,
      created_at     timestamp default current_timestamp,
      updated_at     timestamp default current_timestamp
);

CREATE TABLE users (
      id             bigserial primary key,
      username       varchar(50) not null unique,
      password       varchar(80) not null,
      email          varchar(80) not null unique,
      created_at     timestamp default current_timestamp,
      updated_at     timestamp default current_timestamp
);

CREATE TABLE users_roles (
      user_id        bigint not null references users(id),
      role_id        bigint not null references roles(id),
      primary key(user_id, role_id)
);

INSERT INTO roles(title) VALUES
('ROLE_USER'),
('ROLE_ADMIN');

INSERT INTO users(username, password, email) VALUES
('bob', '$2a$12$Fz0i8S3MGaywuRiom28aW.D9y0uJSjKkVYnlKvamjp1aPMX0qqp92', 'bob_johnson@gmail.com'),
('john', '$2a$12$Fz0i8S3MGaywuRiom28aW.D9y0uJSjKkVYnlKvamjp1aPMX0qqp92', 'john_johnson@gmail.com');

INSERT INTO users_roles(user_id, role_id) VALUES
(1,1),
(2,2);
