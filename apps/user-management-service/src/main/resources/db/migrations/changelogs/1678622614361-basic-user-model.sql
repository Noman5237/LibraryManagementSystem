-- liquibase formatted sql

-- changeset noman1001:1678622614361-1
CREATE TABLE "user"
(
    email           VARCHAR(255) NOT NULL,
    first_name      VARCHAR(255) NOT NULL,
    last_name       VARCHAR(255) NOT NULL,
    dob             VARCHAR(255) NOT NULL,
    hashed_password VARCHAR(255) NOT NULL,
    CONSTRAINT pk_user PRIMARY KEY (email)
);

