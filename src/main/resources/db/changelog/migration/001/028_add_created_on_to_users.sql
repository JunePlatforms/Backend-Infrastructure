--liquibase formatted sql
--changeset junecourieradmin.com:028_add_created_on_to_users

ALTER TABLE user
ADD COLUMN created_on TIMESTAMP;



