# password = admin
use security;

DROP TABLE if exists users_roles;
DROP TABLE if exists users;

CREATE TABLE if not exists users(
    id int PRIMARY KEY AUTO_INCREMENT,
    email varchar(64) NOT NULL UNIQUE,
    name varchar(50) NOT NULL UNIQUE,
    password varchar(255) NOT NULL
);

INSERT INTO users(email, name, password) VALUE ('admin@gmail.com', 'admin',
                                                '$2a$12$ohNUouCf4VurVFKdz7xsVOui.6/xMJaLYNAhK0KE9TIdTBebJWwFG');

CREATE TABLE if not exists users_roles(
    user_id int NOT NULL,
    roles varchar(20) NOT NULL,
    FOREIGN KEY (user_id) references users(id)
);

INSERT INTO users_roles(user_id, roles) VALUE (1, 'ROLE_ADMIN');