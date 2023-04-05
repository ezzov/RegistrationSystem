--liquibase formatted sql

--changeset anna:7
ALTER TABLE users
    ALTER COLUMN password TYPE varchar(200);

