--liquibase formatted sql
--changeset junecourieradmin.com:006_create_shipment_table

CREATE TABLE shipment (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    shipment_manifest VARCHAR(500),
    airway_invoice VARCHAR(500),
    shipment_type VARCHAR(50) NOT NULL,
    status VARCHAR(50) NOT NULL,
    departure_date DATE,
    arrival_date DATE,
    was_deleted BOOLEAN NOT NULL DEFAULT FALSE
);