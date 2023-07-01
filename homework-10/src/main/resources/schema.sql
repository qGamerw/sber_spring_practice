create schema ukhinms;
create table ukhinms.clients (price numeric(38,2) not null, id bigserial not null, id_card bigint not null, email varchar(255) not null, name varchar(255) not null, password varchar(255) not null, username varchar(255) not null, primary key (id));
create table ukhinms.product_baskets (amount integer not null, id bigserial not null, id_client bigint not null, id_product bigint not null, primary key (id));
create table ukhinms.products (amount integer not null, price numeric(38,2) not null, id bigserial not null, name varchar(255) not null, primary key (id));
create table ukhinms.promo_codes (discount float(53) not null, id bigserial not null, primary key (id));
alter table if exists ukhinms.product_baskets add constraint FKp9n72nvr9cegx8vqdj3eyhdlu foreign key (id_client) references ukhinms.clients;
alter table if exists ukhinms.product_baskets add constraint FKg84duwvuc496r7hlud3rj9eo0 foreign key (id_product) references ukhinms.products;
