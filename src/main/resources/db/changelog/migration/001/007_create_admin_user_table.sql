--liquibase formatted sql
--changeset junecourieradmin.com:007_create_admin_user_table

CREATE TABLE admin_user (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT,
    CONSTRAINT user_id_admin_fk FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE

);

CREATE INDEX idx_admin_user_id ON admin_user (user_id);