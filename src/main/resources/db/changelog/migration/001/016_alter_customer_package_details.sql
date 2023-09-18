--liquibase formatted sql
--changeset junecourieradmin.com:015_alter_delivery_details_table_drop_product_id


ALTER TABLE delivery_details
ADD COLUMN shipping_fee int NOT NULL;
