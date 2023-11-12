--liquibase formatted sql
--changeset junecourieradmin.com:020_indexing_user_id


CREATE INDEX idx_user_id ON user (id);

