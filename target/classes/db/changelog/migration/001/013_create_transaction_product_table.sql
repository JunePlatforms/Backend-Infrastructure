--liquibase formatted sql
--changeset junecourieradmin.com:012_create_transaction_table

CREATE TABLE transaction_products (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    transaction_id BIGINT,
    product_id BIGINT,
    CONSTRAINT transaction_id_transaction_product_fk FOREIGN KEY (transaction_id) REFERENCES transaction_details(id)
    ON DELETE CASCADE,
    CONSTRAINT product_id_transaction_product_fk FOREIGN KEY (product_id) REFERENCES customer_product_details(id)
    ON DELETE CASCADE

);

CREATE INDEX idx_transaction_transaction_products_id ON transaction_products (transaction_id);
CREATE INDEX idx_product_transaction_products_id ON transaction_products (product_id);