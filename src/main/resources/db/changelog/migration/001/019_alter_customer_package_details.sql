--liquibase formatted sql
--changeset junecourieradmin.com:019_alter_customer_package_details


ALTER TABLE customer_product_details
ADD COLUMN shipping_fee int NOT NULL DEFAULT 0;
