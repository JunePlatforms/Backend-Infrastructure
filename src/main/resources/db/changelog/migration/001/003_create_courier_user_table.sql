--liquibase formatted sql
--changeset junecourieradmin.com:003_create_courier_user_table

CREATE TABLE courier_user (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    assessment_score INT NOT NULL,
    rating INT NOT NULL DEFAULT 0,
    is_available BOOLEAN NOT NULL DEFAULT TRUE,
    accepted_terms_and_conditions BOOLEAN NOT NULL,
    police_record VARCHAR(150),
    drivers_license VARCHAR(150),
    application_status VARCHAR(50) NOT NULL,
    reason VARCHAR(150),
    vehicle_make VARCHAR(50) NOT NULL,
    vehicle_model VARCHAR(50) NOT NULL,
    vehicle_type VARCHAR(50) NOT NULL,
    license_plate_number VARCHAR(50) NOT NULL,
    user_id BIGINT,
    CONSTRAINT user_id_courier_fk FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE

);

CREATE INDEX idx_courier_user_id ON courier_user (user_id);