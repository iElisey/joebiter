create sequence hibernate_sequence start 1 increment 1;

CREATE TABLE users
(
    id       INT8         not null,
    email    VARCHAR(255),
    password VARCHAR(255) not null,
    username VARCHAR(255) not null,
    PRIMARY KEY (id)
);
CREATE TABLE message
(
    id        INT8          not null,
    created   TIMESTAMP,
    text      VARCHAR(2048) not null,
    author_id INT8,
    PRIMARY KEY (id),
    FOREIGN KEY (author_id) REFERENCES users
);

CREATE TABLE comment
(
    id         INT8          not null,
    text       VARCHAR(2048) not null,
    author_id  INT8,
    message_id INT8,
    PRIMARY KEY (id),
    FOREIGN KEY (author_id) REFERENCES users,
    FOREIGN KEY (message_id) REFERENCES message
);


CREATE TABLE user_roles
(
    user_id INT8 NOT NULL,
    roles   VARCHAR(255),
    PRIMARY KEY (user_id),
    FOREIGN KEY (user_id) REFERENCES users
);

CREATE TABLE user_subscriptions
(
    subscriber_id INT8 NOT NULL,
    channel_id    INT8 NOT NULL,
    PRIMARY KEY (channel_id, subscriber_id),
    FOREIGN KEY (channel_id) REFERENCES users,
    FOREIGN KEY (subscriber_id) REFERENCES users
);



ALTER TABLE IF EXISTS comment
    ADD CONSTRAINT FK_comment_author_id
    FOREIGN KEY (author_id) REFERENCES users;

ALTER TABLE IF EXISTS comment
    ADD CONSTRAINT FK_comment_message_id
    FOREIGN KEY (message_id) REFERENCES message;

ALTER TABLE IF EXISTS message
    ADD CONSTRAINT FK_message_author_id
    FOREIGN KEY (author_id) REFERENCES users;

ALTER TABLE IF EXISTS user_roles
    ADD CONSTRAINT FK_user_roles_user_id
    FOREIGN KEY (user_id) REFERENCES users;

ALTER TABLE IF EXISTS user_subscriptions
    ADD CONSTRAINT FK_user_subscriptions_channel_id
    FOREIGN KEY (channel_id) REFERENCES users;

ALTER TABLE IF EXISTS user_subscriptions
    ADD CONSTRAINT FK_user_subscriptions_subscriber_id
    FOREIGN KEY (subscriber_id) REFERENCES users;
