--liquibase formatted sql
--changeset junecourieradmin.com:010_alter_delivery_details_table

ALTER TABLE delivery_details
ADD COLUMN delivery_date_time DATETIME NOT NULL;