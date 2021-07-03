DROP TABLE IF EXISTS USER_ROLES;
DROP TABLE IF EXISTS smb_users;
DROP TABLE IF EXISTS smb_box;

DROP TABLE IF EXISTS ut_login1;
DROP TABLE IF EXISTS ut_login2;

DROP SEQUENCE IF EXISTS smb_seq;
DROP SEQUENCE IF EXISTS ut_login1_seq;
DROP SEQUENCE IF EXISTS ut_login2_seq;


CREATE SEQUENCE smb_seq START WITH 100000;

CREATE TABLE smb_users
(
    id         INTEGER PRIMARY KEY DEFAULT nextval('smb_seq'),
    login      VARCHAR(255)                      NOT NULL,
    password   VARCHAR(255)                      NOT NULL,
    tablename  VARCHAR(30)                       NOT NULL,
    path       VARCHAR(200)                      NOT NULL,
    registered TIMESTAMP           DEFAULT now() NOT NULL,
    buchsize   INTEGER             DEFAULT 10    NOT NULL,
    enabled    BOOL                DEFAULT TRUE  NOT NULL,
    subscriber BOOL                DEFAULT FALSE NOT NULL,
    publisher  BOOL                DEFAULT FALSE NOT NULL
);
CREATE UNIQUE INDEX users_unique_login_idx ON smb_users (login);

CREATE TABLE USER_ROLES
(
    user_id INTEGER NOT NULL,
    role    VARCHAR(255),
    CONSTRAINT user_roles_idx UNIQUE (user_id, role),
    FOREIGN KEY (user_id) REFERENCES smb_users (id) ON DELETE CASCADE
);


CREATE TABLE smb_box
(
    id         INTEGER PRIMARY KEY DEFAULT nextval(' smb_seq'),
    tablename  VARCHAR(30)                       NOT NULL,
    box        VARCHAR                           NOT NULL,
    registered TIMESTAMP           DEFAULT now() NOT NULL
);


CREATE SEQUENCE ut_login1_seq START WITH 100000;

CREATE TABLE ut_login1
(
    id         INTEGER PRIMARY KEY DEFAULT nextval(' ut_login1_seq'),
    tablename  VARCHAR(30)                       NOT NULL,
    box        VARCHAR                           NOT NULL,
    registered TIMESTAMP           DEFAULT now() NOT NULL
);

CREATE SEQUENCE ut_login2_seq START WITH 100000;

CREATE TABLE ut_login2
(
    id         INTEGER PRIMARY KEY DEFAULT nextval(' ut_login2_seq'),
    tablename  VARCHAR(30)                       NOT NULL,
    box        VARCHAR                           NOT NULL,
    registered TIMESTAMP           DEFAULT now() NOT NULL
);
