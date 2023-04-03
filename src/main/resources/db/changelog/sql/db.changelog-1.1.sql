--liquibase formatted sql

--changeset anna:5
ALTER table  Requests rename column requesttext to request_text;

--changeset anna:6
ALTER table  Requests rename column dateofcreation to date_of_creation;