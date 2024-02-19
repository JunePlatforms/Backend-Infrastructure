--liquibase formatted sql
--changeset junecourieradmin.com:027_alter_customer_package_details_add_updated_on


ALTER TABLE customer_product_details
ADD COLUMN updated_on TIMESTAMP;