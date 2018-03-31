create table users (
	id bigint not null, 
	account_non_expired boolean not null, 
	account_non_locked boolean not null, 
	credentials_non_expired boolean not null, 
	enabled boolean not null, 
	password varchar(255), 
	username varchar(255), 
	primary key (id));
	
create sequence oauth_users_sequence start with 2 increment by 1;

create table oauth_user_roles (
   oauth_user_id bigint not null, 
   roles varchar(255)
);



create table oauth_client_details (
  client_id VARCHAR(255) PRIMARY KEY,
  resource_ids VARCHAR(255),
  client_secret VARCHAR(255),
  scope VARCHAR(255),
  authorized_grant_types VARCHAR(255),
  web_server_redirect_uri VARCHAR(255),
  authorities VARCHAR(255),
  access_token_validity INTEGER,
  refresh_token_validity INTEGER,
  additional_information VARCHAR(4096),
  autoapprove VARCHAR(255)
);

create table if not exists oauth_code (
  code VARCHAR(255), authentication LONGVARBINARY
);

create table if not exists oauth_approvals (
	userId VARCHAR(255),
	clientId VARCHAR(255),
	scope VARCHAR(255),
	status VARCHAR(10),
	expiresAt TIMESTAMP,
	lastModifiedAt TIMESTAMP
);
