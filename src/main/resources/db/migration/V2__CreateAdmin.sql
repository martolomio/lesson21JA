insert into user (user_id, first_name, last_name, email, password, active)
values (1, 'Приймальна комісія', 'Адмін', 'partutamarta@gmail.com', '123456', true);

insert into access_level (user_id, access_levels)
values (1, 'ADMIN');