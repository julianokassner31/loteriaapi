create table users (
	id serial NOT NULL,
	user_name varchar(255) NULL DEFAULT NULL,
	full_name varchar(255) NULL DEFAULT NULL,
	"password" varchar(255) NULL DEFAULT NULL,
	account_non_expired boolean NULL DEFAULT false,
	account_non_locked bool NULL DEFAULT false,
	credentials_non_expired boolean NULL DEFAULT false,
	enabled boolean NULL DEFAULT false,
	CONSTRAINT users_pkey PRIMARY KEY (id),
	CONSTRAINT users_user_name_key UNIQUE (user_name)
);