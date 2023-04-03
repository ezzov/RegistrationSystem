--liquibase formatted sql

--changeset anna:1
CREATE TABLE IF NOT EXISTS Roles (
    id 		            serial PRIMARY KEY,
    role 		        varchar(30) NOT NULL
);

--changeset anna:2
CREATE TABLE IF NOT EXISTS Users (
    id 		            serial PRIMARY KEY,
    userName 	        varchar(60) NOT NULL,
    password 	        varchar(30) NOT NULL,
    email 		        varchar(60) NOT NULL
);

--changeset anna:3
CREATE TABLE IF NOT EXISTS Users_Roles (
    user_id 	        integer NOT NULL,
    role_id 	        integer NOT NULL,
    primary key (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES Users (id),
    FOREIGN KEY (role_id) REFERENCES Roles (id)
);

--changeset anna:4
CREATE TABLE IF NOT EXISTS Requests (
    id 		            serial PRIMARY KEY,
    status 	            varchar(20) NOT NULL,
    requestText         varchar(500) NOT NULL,
    dateOfCreation 	    date NOT NULL,
    user_id 		    integer NOT NULL,
    FOREIGN KEY (user_id) REFERENCES Users (id)
);