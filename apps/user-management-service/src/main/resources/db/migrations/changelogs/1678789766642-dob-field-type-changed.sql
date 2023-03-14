-- liquibase formatted sql

-- changeset noman1001:1678789766642-1
ALTER TABLE "user"
    DROP COLUMN dob;

-- changeset noman1001:1678789766642-2
ALTER TABLE "user"
    ADD dob TIMESTAMP WITHOUT TIME ZONE NOT NULL;

