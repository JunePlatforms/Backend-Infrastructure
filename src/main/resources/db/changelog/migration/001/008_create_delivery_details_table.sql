--liquibase formatted sql
--changeset junecourieradmin.com:008_create_delivery_details_table

CREATE TABLE delivery_details (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    pick_up_location VARCHAR(200) NOT NULL,
    drop_off_location VARCHAR(200) NOT NULL,
    special_instructions VARCHAR(500),
    package_description VARCHAR(200),
    status VARCHAR(50) NOT NULL,
    customer_id BIGINT NOT NULL,
    courier_id BIGINT,
    package_id BIGINT NOT NULL,
    CONSTRAINT customer_id_delivery_details_fk FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE,
    CONSTRAINT courier_id_delivery_details_fk FOREIGN KEY (courier_id) REFERENCES user(id) ON DELETE CASCADE
    CONSTRAINT package_id_delivery_details_fk FOREIGN KEY (package_id) REFERENCES customer_product_details(id) ON DELETE CASCADE

);

CREATE INDEX idx_customer_product_id ON delivery_details (user_id);
CREATE INDEX idx_customer_product_shipment_id ON delivery_details (shipment_id);
CREATE INDEX idx_customer_product_shipment_id ON delivery_details (shipment_id);