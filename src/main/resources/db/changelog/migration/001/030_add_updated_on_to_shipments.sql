--liquibase formatted sql
--changeset junecourieradmin.com:030_add_updated_on_to_shipments

ALTER TABLE shipment
ADD COLUMN updated_on TIMESTAMP;



