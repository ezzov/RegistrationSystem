DELETE FROM users;
DELETE FROM requests;
DELETE FROM roles;
DELETE FROM users_roles;

ALTER SEQUENCE users_id_seq RESTART WITH 1;
ALTER SEQUENCE requests_id_seq RESTART WITH 1;
ALTER SEQUENCE roles_id_seq RESTART WITH 1;

INSERT INTO users( username, password, email)
VALUES ('Mark', '$2a$12$dKuce2XHGTTdkiWNdnz6RuyBj3YCTuL.9EDICHmBEfF9Ct.EOisd.', 'mark@gmail.com'),
       ('Katrin', '$2a$12$WzLtGJWi5qdHlb.aW5w69.Pj0Le4C0Qmh7KAKDbAXLi5IvYsuwNDG', 'katrin@gmail.com'),
       ('Adam', '$2a$12$a6LmI5AqJcyc8VzJ5qRkoOl0C9cU0sLHRiTU/YasN9ghK0av0Rje6', 'adam@gmail.com'),
       ('Harry', '$2a$12$VCXWrMt5fwwQxEzgJZ9LFuioYMzgJWyOPxYpSOwAiOVoO1/hFUFXe', 'harry@gmail.com'),
       ('Keily', '$2a$12$eCCLakVKwqgedH6cyTx.SOiViU/kdfi0wFa0E.LSKlObywbkFaDkO', 'keily@gmail.com'),
       ('Valery', '$2a$12$49WAd6gjfk5rPWqIzPSkCu/nV/lo.8fgIHGRVmx3foXMBsKQehS8m', 'valery@gmail.com');

INSERT INTO requests(status, request_text, date_of_creation, user_id)
VALUES ('DRAFT', 'Mark Text1', '2022-12-14 13:06:28.580532', 1),
       ('DRAFT', 'Mark Text2', '2022-10-14 13:06:28.580532', 1),
       ('SENT', 'Mark Text3', '2022-09-14 13:06:28.580532', 1),
       ('ACCEPTED', 'Mark Text4', '2022-08-14 13:06:28.580532', 1),
       ('REJECTED', 'Mark Text5', '2022-07-14 13:06:28.580532', 1),
       ('DRAFT', 'Katrin Text1', '2022-12-14 17:06:28.580532', 2),
       ('SENT', 'Katrin Text2', '2022-12-12 13:06:28.580532', 2),
       ('ACCEPTED', 'Katrin Text3', '2022-12-10 13:06:28.580532', 2),
       ('REJECTED', 'Katrin Text4', '2022-12-16 13:06:28.580532', 2),
       ('DRAFT', 'Adam Text1', '2022-12-14 11:06:28.580532', 3),
       ('SENT', 'Adam Text2', '2022-11-14 13:06:28.580532', 3),
       ('DRAFT', 'Harry Text1', '2022-12-14 15:06:28.580532', 4);

INSERT INTO roles(role)
VALUES ('USER'),
       ('OPERATOR'),
       ('ADMIN');

INSERT INTO users_roles(user_id, role_id)
VALUES (1, 1),
       (2, 1),
       (3, 1),
       (4, 1),
       (4, 2),
       (5, 2),
       (6, 3);

