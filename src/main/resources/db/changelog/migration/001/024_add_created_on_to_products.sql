--liquibase formatted sql
--changeset junecourieradmin.com:024_add_created_on_to_products

ALTER TABLE customer_product_details
ADD COLUMN created_on TIMESTAMP;



