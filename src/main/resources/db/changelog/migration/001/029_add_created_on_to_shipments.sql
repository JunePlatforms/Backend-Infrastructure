--liquibase formatted sql
--changeset junecourieradmin.com:029_add_created_on_to_shipments

ALTER TABLE shipment
ADD COLUMN created_on TIMESTAMP;



