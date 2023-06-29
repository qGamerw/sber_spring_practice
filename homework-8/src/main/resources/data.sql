create schema if not exists ukhinms;

create table ukhinms.product
(
    id    integer generated always as identity
    primary key,
    name  varchar(255) not null,
    price numeric      not null
    );

create table ukhinms.cart
(
    id        integer generated always as identity
    primary key,
    promocode varchar(255)
    );

create table ukhinms.client
(
    id       integer generated always as identity,
    name     varchar(255) not null,
    username varchar(255) not null,
    password varchar(255) not null,
    email varchar(255) not null,
    cart_id  integer      not null
    constraint client_cart_id_fk
    references ukhinms.cart
    );

create table ukhinms.product_client
(
    id         integer generated always as identity
    primary key,
    id_product integer not null
    constraint product_client_products_id_fk
    references ukhinms.product,
    id_cart    integer not null
        constraint product_client_cart_id_fk
            references ukhinms.cart,
    count  integer      not null


);