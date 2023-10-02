--liquibase formatted sql
--changeset junecourieradmin.com:005_create_customer_user_table

CREATE TABLE customer_user (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    customer_number VARCHAR(20),
    mail_box VARCHAR(50),
    username VARCHAR(50),
    profile_image VARCHAR(500),
    accepted_terms_and_conditions BOOLEAN NOT NULL,
    user_id BIGINT,
    CONSTRAINT user_id_customer_fk FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE

);

CREATE INDEX idx_customer_user_id ON customer_user (user_id);