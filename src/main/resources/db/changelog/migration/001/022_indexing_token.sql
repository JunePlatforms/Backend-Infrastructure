--liquibase formatted sql
--changeset junecourieradmin.com:022_indexing_token


CREATE INDEX idx_token ON token (token);

