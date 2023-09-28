create table "good"
(
    "id"         bigserial UNIQUE     NOT NULL,
    "name"       varchar(90)          NOT NULL,
    "price"      float                NOT NULL,
    PRIMARY KEY ("id")
)