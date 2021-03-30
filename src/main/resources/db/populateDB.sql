DELETE FROM smb_users;
ALTER SEQUENCE smb_seq RESTART WITH 100000;

INSERT INTO smb_users (id, login, password, tablename, subscriber, publisher)
VALUES (10000, 'login1', 'login1', 'login1', false, true),
       (10001, 'login2', 'login2', 'login2', true, false);

