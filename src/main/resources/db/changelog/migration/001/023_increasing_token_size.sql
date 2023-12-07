--liquibase formatted sql
--changeset junecourieradmin.com:023_adding_foreign_key_to_token

ALTER TABLE token ADD CONSTRAINT user_id_fk FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE;





