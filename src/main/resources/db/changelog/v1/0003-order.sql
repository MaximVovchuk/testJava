create table "order"
(
    "id"         bigserial UNIQUE     NOT NULL,
    "full_price" float                NOT NULL,
    "paid"       bool                 NOT NULL,
    "client_id"  bigint               NOT NULL,
    "created_at" timestamp            NOT NULL DEFAULT now(),
    FOREIGN KEY ("client_id") REFERENCES "user" ("id") ON DELETE CASCADE,
    PRIMARY KEY ("id")
);

create table "order_position"
(
    "id"         bigserial UNIQUE  NOT NULL,
    "order_id"   bigint            NOT NULL,
    "good_id"    bigint            NOT NULL,
    "quantity"   int          NOT NULL,
    FOREIGN KEY ("order_id") REFERENCES "order" ("id") ON DELETE CASCADE,
    FOREIGN KEY ("good_id") REFERENCES "good" ("id") ON DELETE CASCADE,
    PRIMARY KEY ("id")
);
