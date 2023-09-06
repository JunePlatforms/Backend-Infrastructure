--liquibase formatted sql
--changeset junecourieradmin.com:005_create_customer_package_details_table

CREATE TABLE customer_product_details (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    supplier_name VARCHAR(150) NOT NULL,
    weight VARCHAR(50) NOT NULL,
    shipment_type VARCHAR(50),
    status VARCHAR(50) NOT NULL,
    tracking_number VARCHAR(50) NOT NULL,
    description VARCHAR(200) NOT NULL,
    was_deleted BOOLEAN DEFAULT false NOT NULL,
    user_id BIGINT,
    CONSTRAINT user_id_customer_product_fk FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE

);

CREATE INDEX idx_customer_product_id ON customer_product_details (user_id);