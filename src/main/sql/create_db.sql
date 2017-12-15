create schema products;

create table products.products (
    id SERIAL PRIMARY KEY,
    name TEXT NOT NULL,
    price NUMERIC(10, 2) NOT NULL
);

insert into products.products (name, price)
values ('Produkt 1', 10.00);
insert into products.products (name, price)
values ('Produkt 2', 1.99);
insert into products.products (name, price)
values ('Produkt 3', 15.00);
insert into products.products (name, price)
values ('Produkt 4', 15.99);
insert into products.products (name, price)
values ('Produkt 5', 101.10);
insert into products.products (name, price)
values ('Produkt 6', 6.00);
