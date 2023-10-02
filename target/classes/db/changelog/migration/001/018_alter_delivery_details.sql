--liquibase formatted sql
--changeset junecourieradmin.com:018_alter_delivery_details


ALTER TABLE delivery_details
DROP COLUMN shipping_fee;
