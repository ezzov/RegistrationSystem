DELETE FROM users;
DELETE FROM requests;

ALTER SEQUENCE users_id_seq RESTART WITH 1;
ALTER SEQUENCE requests_id_seq RESTART WITH 1;

INSERT INTO users( username, password, email)
VALUES ('Mark', 'Mark', 'mark@gmail.com'),
       ('Katrin', 'Katrin', 'katrin@gmail.com'),
       ('Adam', 'Adam', 'adam@gmail.com'),
       ('Harry', 'Harry', 'harry@gmail.com'),
       ('Keily', 'Keily', 'keily@gmail.com'),
       ('Valery', 'Valery', 'valery@gmail.com');

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

