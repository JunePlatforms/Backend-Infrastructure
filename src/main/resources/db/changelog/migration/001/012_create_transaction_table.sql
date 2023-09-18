--liquibase formatted sql
--changeset junecourieradmin.com:012_create_transaction_table

CREATE TABLE transaction_details (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    was_delivered BOOLEAN NOT NULL,
    created_on TIMESTAMP NOT NULL,
    total_spent int NOT NULL,
    payment_type VARCHAR(150) NOT NULL,
    customer_id BIGINT,
    courier_id BIGINT,
    CONSTRAINT customer_id_transaction_fk FOREIGN KEY (customer_id) REFERENCES user(id) ON DELETE CASCADE,
    CONSTRAINT courier_id_transaction_fk FOREIGN KEY (courier_id) REFERENCES user(id) ON DELETE CASCADE

);

CREATE INDEX idx_customer_transaction_details_id ON transaction_details (customer_id);
CREATE INDEX idx_courier_transaction_details_id ON transaction_details (courier_id);