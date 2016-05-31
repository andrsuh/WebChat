CREATE SEQUENCE message_id_seq;
CREATE SEQUENCE users_id_seq;
CREATE SEQUENCE org_id_seq;
CREATE SEQUENCE dep_id_seq;
CREATE SEQUENCE pos_id_seq;

CREATE TYPE sex AS ENUM ('male', 'female');

CREATE TABLE IF NOT EXISTS users (
    user_id INTEGER PRIMARY KEY DEFAULT nextval('users_id_seq'),
    user_username TEXT NOT NULL UNIQUE,
    user_password TEXT NOT NULL,
    user_enabled BOOLEAN NOT NULL,

    user_name TEXT NOT NULL,
    user_sex sex NOT NULL DEFAULT 'male',

    user_details TEXT,

    user_position_id INTEGER REFERENCES positions(pos_id),
    user_department_id INTEGER REFERENCES departments(dep_id),
    user_organisation_id INTEGER REFERENCES organisations(org_id)
);

CREATE TABLE IF NOT EXISTS roles (
    roles_username TEXT PRIMARY KEY,
    roles_role TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS friendShip (
    frd_friend_id INTEGER REFERENCES users(user_id),
    frd_other_friend_id INTEGER REFERENCES users(user_id),

    PRIMARY KEY (frd_friend_id, frd_other_friend_id)
);

CREATE TABLE IF NOT EXISTS messages (
    msg_id INTEGER PRIMARY KEY DEFAULT nextval('message_id_seq'),
    msg_dst_user_id INTEGER REFERENCES users(user_id),
    msg_src_user_id INTEGER REFERENCES users(user_id),

    msg_content TEXT NOT NULL,

    FOREIGN KEY (msg_src_user_id, msg_dst_user_id)
        REFERENCES friendShip(frd_friend_id, frd_other_friend_id)
);

CREATE TABLE IF NOT EXISTS organisations (
    org_id INTEGER PRIMARY KEY DEFAULT nextval('org_id_seq'),
    org_name TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS departments (
    dep_id INTEGER PRIMARY KEY DEFAULT nextval('dep_id_seq'),
    dep_name TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS dep_org (
    org_id INTEGER REFERENCES organisations(org_id),
    dep_id INTEGER REFERENCES departments(dep_id)
);


CREATE TABLE IF NOT EXISTS positions (
    pos_id INTEGER PRIMARY KEY DEFAULT nextval('pos_id_seq'),
    pos_name TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS pos_org (
    org_id INTEGER REFERENCES organisations(org_id),
    pos_id INTEGER REFERENCES positions(pos_id)
);


-- Organisations
INSERT INTO organisations(org_name) VALUES('EMC Corporation');
INSERT INTO organisations(org_name) VALUES('ITMO University');
INSERT INTO organisations(org_name) VALUES('Jet Brains');

-- Departments
INSERT INTO departments(dep_name) VALUES('IT');
INSERT INTO departments(dep_name) VALUES('High perfomance');
INSERT INTO departments(dep_name) VALUES('Perfomance optimisation');
INSERT INTO departments(dep_name) VALUES('Managments');
INSERT INTO departments(dep_name) VALUES('HR');
INSERT INTO departments(dep_name) VALUES('Ressearch');
INSERT INTO departments(dep_name) VALUES('Dean office');
INSERT INTO departments(dep_name) VALUES('Sales');
INSERT INTO departments(dep_name) VALUES('Security');
INSERT INTO departments(dep_name) VALUES('Mathematics chair');

-- Departments - organisations
INSERT INTO dep_org VALUES(1, 1);
INSERT INTO dep_org VALUES(1, 2);
INSERT INTO dep_org VALUES(1, 3);
INSERT INTO dep_org VALUES(1, 4);
INSERT INTO dep_org VALUES(1, 5);
INSERT INTO dep_org VALUES(1, 9);

INSERT INTO dep_org VALUES(2, 1);
INSERT INTO dep_org VALUES(2, 4);
INSERT INTO dep_org VALUES(2, 6);
INSERT INTO dep_org VALUES(2, 7);
INSERT INTO dep_org VALUES(2, 9);
INSERT INTO dep_org VALUES(2, 10);

INSERT INTO dep_org VALUES(3, 1);
INSERT INTO dep_org VALUES(3, 2);
INSERT INTO dep_org VALUES(3, 3);
INSERT INTO dep_org VALUES(3, 4);
INSERT INTO dep_org VALUES(3, 5);
INSERT INTO dep_org VALUES(3, 6);
INSERT INTO dep_org VALUES(3, 8);
INSERT INTO dep_org VALUES(3, 9);

-- Positions
INSERT INTO positions(pos_name) VALUES('Developer');
INSERT INTO positions(pos_name) VALUES('Researcher');
INSERT INTO positions(pos_name) VALUES('Dean');
INSERT INTO positions(pos_name) VALUES('Saler');


-- Positions - organisations
INSERT INTO pos_org VALUES(1, 1);
INSERT INTO pos_org VALUES(1, 4);

INSERT INTO pos_org VALUES(2, 2);
INSERT INTO pos_org VALUES(2, 3);

INSERT INTO pos_org VALUES(3, 1);
INSERT INTO pos_org VALUES(3, 2);
INSERT INTO pos_org VALUES(3, 4);

-- Persons
INSERT INTO users(
    user_username,
    user_password,
    user_enabled,
    user_name,
    user_sex,
    user_details,
    user_position_id,
    user_department_id,
    user_organisation_id
) VALUES
    ('andrsuh', '123', true, 'Andrey Sukhovitskiy', 'male', 'student', 1, 1, 1),
    ('acane', '123', true, 'Lada Trimasova', 'female', 'student', 1, 2, 3),
    ('dean', '123', true, 'Vladimir Parfenov', 'male', 'dean', 3, 7, 2);

-- Roles
INSERT INTO roles VALUES('andrsuh', 'USER');
INSERT INTO roles VALUES('acane', 'USER');
INSERT INTO roles VALUES('dean', 'ADMIN');


-- friendShip
INSERT INTO friendShip VALUES(8, 10);
INSERT INTO friendShip VALUES(8, 11);
INSERT INTO friendShip VALUES(10, 8);
INSERT INTO friendShip VALUES(10, 10);
INSERT INTO friendShip VALUES(11, 10);
INSERT INTO friendShip VALUES(11, 8);

-- Messages
INSERT INTO messages(msg_dst_user_id, msg_src_user_id, msg_content)
VALUES
    (8, 11, 'Hey Lada'),
    (8, 11, 'How are you?'),
    (8, 11, 'Bye!'),
    (11, 8, 'Hey Andrey!'),
    (11, 8, 'How are you?'),
    (11, 8, 'Bye!');
