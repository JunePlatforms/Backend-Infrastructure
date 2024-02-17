--liquibase formatted sql
--changeset junecourieradmin.com:025_alter_customer_package_details_add_jun_id


ALTER TABLE customer_product_details
ADD COLUMN JUNId VARCHAR(50);
