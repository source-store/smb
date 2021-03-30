DELETE FROM smb_subscribers;
DELETE FROM smb_publishers;
DELETE FROM smb_users;
ALTER SEQUENCE smb_seq RESTART WITH 100000;

INSERT INTO smb_users (id, login, password, subscriber, publisher)
VALUES (10000, 'login1', 'login1', true, false),
       (10001, 'login2', 'login2', false, true);

INSERT INTO smb_subscribers (user_id, tablename)
VALUES (10000, 'UT_BUS');

INSERT INTO smb_publishers (user_id, tablename)
VALUES (10001, 'UT_BUS');