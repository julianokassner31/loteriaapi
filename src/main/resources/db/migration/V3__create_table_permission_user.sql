create table user_permission (
	id_user bigserial NOT NULL,
	id_permission bigserial NOT NULL,
	CONSTRAINT user_permission_pkey PRIMARY KEY (id_user, id_permission)
);

ALTER TABLE user_permission ADD CONSTRAINT user_permission_fk_user FOREIGN KEY (id_user) REFERENCES users(id);
ALTER TABLE user_permission ADD CONSTRAINT user_permission_fk_permission FOREIGN KEY (id_permission) REFERENCES permission(id);