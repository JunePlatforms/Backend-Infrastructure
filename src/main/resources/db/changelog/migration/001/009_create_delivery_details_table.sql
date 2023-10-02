--liquibase formatted sql
--changeset junecourieradmin.com:009_create_delivery_details_table

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
    CONSTRAINT customer_id_delivery_details_fk FOREIGN KEY (customer_id) REFERENCES user(id) ON DELETE CASCADE,
    CONSTRAINT courier_id_delivery_details_fk FOREIGN KEY (courier_id) REFERENCES user(id) ON DELETE CASCADE,
    CONSTRAINT package_id_delivery_details_fk FOREIGN KEY (package_id) REFERENCES customer_product_details(id) ON DELETE CASCADE
);

CREATE INDEX idx_customer_delivery_details_id ON delivery_details (customer_id);
CREATE INDEX idx_courier_delivery_details_id ON delivery_details (courier_id);
CREATE INDEX idx_package_delivery_details_id ON delivery_details (package_id);