-- TODO schema
-- example
create table members
(
    id                 bigint primary key      not null auto_increment,
    name               varchar(50)             not null,
    created_date       timestamp default now() not null,
    last_modified_date timestamp
);

CREATE TABLE users
(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    name VARCHAR(50) NOT NULL,
    role VARCHAR(20) NOT NULL
);

CREATE TABLE contents
(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    description TEXT,
    view_count BIGINT NOT NULL,
    created_date TIMESTAMP,
    created_by VARCHAR(50) NOT NULL,
    last_modified_date TIMESTAMP,
    last_modified_by VARCHAR(50)
)