DROP TABLE IF EXISTS smb_subscribers;
DROP TABLE IF EXISTS smb_publishers;
DROP TABLE IF EXISTS smb_users;
DROP SEQUENCE IF EXISTS smb_seq;

CREATE SEQUENCE smb_seq START WITH 100000;

CREATE TABLE smb_users
(
    id               INTEGER PRIMARY KEY DEFAULT nextval('smb_seq'),
    login            VARCHAR                           NOT NULL,
    password         VARCHAR                           NOT NULL,
    registered       TIMESTAMP           DEFAULT now() NOT NULL,
    enabled          BOOL                DEFAULT TRUE  NOT NULL,
    subscriber       BOOL                DEFAULT FALSE NOT NULL,
    publisher        BOOL                DEFAULT FALSE NOT NULL
);
CREATE UNIQUE INDEX users_unique_email_idx ON smb_users (login);

CREATE TABLE smb_subscribers
(
    user_id          INTEGER NOT NULL,
    tablename        VARCHAR NOT NULL,
    CONSTRAINT smb_subscribers_idx UNIQUE (user_id, tablename),
    FOREIGN KEY (user_id) REFERENCES smb_users (id) ON DELETE CASCADE
);

CREATE TABLE smb_publishers
(
    user_id          INTEGER NOT NULL,
    tablename        VARCHAR NOT NULL,
    CONSTRAINT smb_publishers_idx UNIQUE (user_id, tablename),
    FOREIGN KEY (user_id) REFERENCES smb_users (id) ON DELETE CASCADE
);
