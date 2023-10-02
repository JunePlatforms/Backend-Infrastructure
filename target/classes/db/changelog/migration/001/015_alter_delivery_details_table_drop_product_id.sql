--liquibase formatted sql
--changeset junecourieradmin.com:015_alter_delivery_details_table_drop_product_id

ALTER TABLE delivery_details
DROP FOREIGN KEY package_id_delivery_details_fk;

ALTER TABLE delivery_details
DROP COLUMN package_id;