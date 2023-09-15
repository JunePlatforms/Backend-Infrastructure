--liquibase formatted sql
--changeset junecourieradmin.com:011_alter_delivery_details_table

ALTER TABLE delivery_details
DROP COLUMN package_description;