create schema if not exists ukhinms;

create table if not exists ukhinms.products
(
    id     integer generated always as identity
        primary key,
    name   varchar(255) not null,
    price  numeric      not null,
    amount integer      not null
);

create table if not exists ukhinms.clients
(
    id       integer generated always as identity
        primary key,
    name     varchar(255) not null,
    email    varchar(255) not null,
    id_card  integer      not null,
    username varchar(255) not null,
    password varchar(255) not null
);

create table if not exists ukhinms.promo_codes
(
    id       integer generated always as identity
        primary key,
    discount real not null
);

create table if not exists ukhinms.baskets
(
    id            integer generated always as identity
        primary key,
    id_client     integer not null
        references ukhinms.clients (id) on delete cascade,
    price         integer not null,
    id_promo_code integer not null references ukhinms.promo_codes(id) on delete cascade
);

create table if not exists ukhinms.products_baskets
(
    id_product integer not null
        references ukhinms.products (id) on delete cascade,
    id_basket  integer not null
        references ukhinms.baskets (id) on delete cascade,
    count      integer not null,
    PRIMARY KEY (id_product, id_basket)
);