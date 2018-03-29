create table users (
	id bigint not null, 
	account_non_expired boolean not null, 
	account_non_locked boolean not null, 
	credentials_non_expired boolean not null, 
	enabled boolean not null, 
	password varchar(255), 
	username varchar(255), 
	primary key (id));
	
create sequence users_sequence start with 1 increment by 1;

create table user_roles (
   user_id bigint not null, 
   roles varchar(255)
);