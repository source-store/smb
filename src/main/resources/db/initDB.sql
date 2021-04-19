DROP TABLE IF EXISTS USER_ROLES;
DROP TABLE IF EXISTS smb_users;
DROP TABLE IF EXISTS smb_box;
DROP SEQUENCE IF EXISTS smb_seq;

CREATE SEQUENCE smb_seq START WITH 100000;

CREATE TABLE smb_users
(
    id               INTEGER PRIMARY KEY DEFAULT nextval('smb_seq'),
    login            VARCHAR(255)                      NOT NULL,
    password         VARCHAR(255)                      NOT NULL,
    tablename        VARCHAR(30)                       NOT NULL,
    registered       TIMESTAMP           DEFAULT now() NOT NULL,
    enabled          BOOL                DEFAULT TRUE  NOT NULL,
    subscriber       BOOL                DEFAULT FALSE NOT NULL,
    publisher        BOOL                DEFAULT FALSE NOT NULL
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
    id               INTEGER PRIMARY KEY DEFAULT nextval(' smb_seq'),
    tablename        VARCHAR(30)	 NOT NULL,
    box              VARCHAR             NOT NULL,
    registered       TIMESTAMP           DEFAULT now() NOT NULL,
    starttime        TIMESTAMP           DEFAULT NULL,
    endtime          TIMESTAMP           DEFAULT NULL
);
