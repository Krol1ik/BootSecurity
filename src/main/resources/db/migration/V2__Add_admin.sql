insert into user (id, username, password, email, active)
values (1, 'admin', 'admin', 'mail@mail.ru', true);

insert into user (id, username, password, email, active)
values (2, 'user', 'user', 'mail@mail.ru', true);

insert into user_role (user_id, roles)
values (1, 'USER'), (1, 'ADMIN');

insert into user_role (user_id, roles)
values (2, 'USER');