create table "user"
(
    "id"       bigserial UNIQUE     NOT NULL,
    "name"     varchar(90)          NOT NULL,
    "email"    varchar(90) UNIQUE   NOT NULL,
    "password" varchar(255)         NOT NULL,
    "role"     bigint               NOT NULL,
    PRIMARY KEY ("id")
)