-- liquibase formatted sql

-- changeset noman1001:1678625868230-1
ALTER TABLE "user"
    ADD account_status VARCHAR(255);
ALTER TABLE "user"
    ADD user_role VARCHAR(255);

-- changeset noman1001:1678625868230-2
ALTER TABLE "user"
    ALTER COLUMN account_status SET NOT NULL;

-- changeset noman1001:1678625868230-4
ALTER TABLE "user"
    ALTER COLUMN user_role SET NOT NULL;

