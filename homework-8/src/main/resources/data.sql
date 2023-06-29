create schema if not exists ukhinms;

create table if not exists ukhinms.products
(
    id    integer generated always as identity
        primary key,
    name  varchar(255) not null,
    price numeric      not null
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

create table if not exists ukhinms.baskets
(
    id         integer generated always as identity
        primary key,
    id_client  integer not null
        references ukhinms.clients (id) on delete cascade,
    price      integer not null,
    promo_code varchar(255)
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

CREATE OR REPLACE FUNCTION ukhinms.update_basket_price()
    RETURNS TRIGGER
AS
$$
BEGIN
    UPDATE ukhinms."baskets"
    SET price = (SELECT SUM(p.price * pb.count)
                 FROM ukhinms.products p
                          JOIN ukhinms.products_baskets pb ON pb.id_product = p.id
                 WHERE pb.id_basket = NEW.id_basket)
    WHERE id = NEW.id_basket;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER products_baskets_insert_trigger
    AFTER UPDATE
    ON ukhinms."products_baskets"
    FOR EACH ROW
EXECUTE FUNCTION ukhinms.update_basket_price();

CREATE OR REPLACE TRIGGER products_baskets_insert_trigger
    AFTER INSERT
    ON ukhinms.products_baskets
    FOR EACH ROW
EXECUTE FUNCTION ukhinms.update_basket_price();