--user:password
insert into users (id, username, password, ACCOUNT_NON_EXPIRED, ACCOUNT_NON_LOCKED, CREDENTIALS_NON_EXPIRED, enabled) 
values (1, 'user', '$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG', true, true, true, true);
insert into user_roles values (1, 'ROLE_USER');