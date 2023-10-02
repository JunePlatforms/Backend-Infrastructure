--liquibase formatted sql
--changeset junecourieradmin.com:004_create_warehouse_clerk_user_table

CREATE TABLE warehouse_clerk_user (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT,
    CONSTRAINT user_id_warehouse_clerk_fk FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE

);

CREATE INDEX idx_warehouse_clerk_user_id ON warehouse_clerk_user (user_id);