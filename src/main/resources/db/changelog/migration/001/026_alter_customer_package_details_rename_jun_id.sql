--liquibase formatted sql
--changeset junecourieradmin.com:026_alter_customer_package_details_rename_jun_id


ALTER TABLE customer_product_details
CHANGE COLUMN JUNId jun_id VARCHAR(50);