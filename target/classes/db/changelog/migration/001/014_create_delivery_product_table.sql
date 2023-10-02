--liquibase formatted sql
--changeset junecourieradmin.com:014_create_delivery_product_table

CREATE TABLE delivery_products (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    delivery_id BIGINT,
    product_id BIGINT,
    CONSTRAINT delivery_id_delivery_product_fk FOREIGN KEY (delivery_id) REFERENCES delivery_details(id)
    ON DELETE CASCADE,
    CONSTRAINT product_id_delivery_product_fk FOREIGN KEY (product_id) REFERENCES customer_product_details(id)
    ON DELETE CASCADE

);

CREATE INDEX idx_transaction_transaction_products_id ON delivery_products (delivery_id);
CREATE INDEX idx_product_transaction_products_id ON delivery_products (product_id);