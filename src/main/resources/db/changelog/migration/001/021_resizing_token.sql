--liquibase formatted sql
--changeset junecourieradmin.com:021_resizing_token


ALTER TABLE token MODIFY COLUMN token VARCHAR(255);

