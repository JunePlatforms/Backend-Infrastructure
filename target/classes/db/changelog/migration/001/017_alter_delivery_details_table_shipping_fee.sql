--liquibase formatted sql
--changeset junecourieradmin.com:017_alter_delivery_details_table_shipping_fee

ALTER TABLE delivery_details
MODIFY COLUMN shipping_fee INT NOT NULL DEFAULT 0;