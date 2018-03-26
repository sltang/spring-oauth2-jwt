--user:password
insert into users (id, username, password, ACCOUNT_NON_EXPIRED, ACCOUNT_NON_LOCKED, CREDENTIALS_NON_EXPIRED, enabled) 
values (1, 'user', '$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG', true, true, true, true);
insert into oauth_user_roles values (1, 'ROLE_USER');

INSERT INTO oauth_client_details
	(client_id, resource_ids, client_secret, scope, authorized_grant_types,
	web_server_redirect_uri, authorities, access_token_validity,
	refresh_token_validity, additional_information, autoapprove)
VALUES
	('code-react', 'foo-resource', '$2a$10$6GGO.OR8JRLcozdIuQwoeOKp7dXl3ddLt5l24r0lCLjrUrT.0Q.Zi', 'foo,read,write',
'password,authorization_code,refresh_token', null, null, 36000, 36000, null, true);

INSERT INTO oauth_client_details
	(client_id, resource_ids, client_secret, scope, authorized_grant_types,
	web_server_redirect_uri, authorities, access_token_validity,
	refresh_token_validity, additional_information, autoapprove)
VALUES
	('code-angular', 'foo-resource', '$2a$10$6GGO.OR8JRLcozdIuQwoeOKp7dXl3ddLt5l24r0lCLjrUrT.0Q.Zi', 'foo,read,write',
'password,authorization_code,refresh_token', null, null, 36000, 36000, null, true);

--web-app:secret
INSERT INTO oauth_client_details
	(client_id, resource_ids, client_secret, scope, authorized_grant_types,
	web_server_redirect_uri, authorities, access_token_validity,
	refresh_token_validity, additional_information, autoapprove)
VALUES
	('web-app', 'foo-resource,bar_resource', '$2a$10$xmXgNO1SAhgesRYwVKbljeq/D1.RkUgR9e3MIFL6wazEmmwECU5se', 'foo,read,write',
'client_credentials', null, 'ROLE_TRUSTED_APP', 36000, null, null, true);