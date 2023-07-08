create schema if not exists ukhinms;


-- Перед регистрацией пользователей выполнить, иначе выйдет ошибка о не существовании роли
insert into ukhinms.roles (name)
values ('ROLE_USER');
insert into ukhinms.roles (name)
values ('ROLE_ADMIN');
