DELETE
FROM smb_users;
ALTER SEQUENCE smb_seq RESTART WITH 100000;

INSERT INTO smb_users (id, login, password, tablename, subscriber, publisher)
VALUES (10000, 'login1', '{noop}login1', 'login1', false, true),
       (10001, 'login2', '{noop}login2', 'login2', true, false);

INSERT INTO USER_ROLES (ROLE, USER_ID)
VALUES ('ADMIN', 10000),
       ('USER', 10001);
