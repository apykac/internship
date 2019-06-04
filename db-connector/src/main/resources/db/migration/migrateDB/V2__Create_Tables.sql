-- Database: vskDB

CREATE TABLE users
(
    user_id         serial UNIQUE NOT NULL,
    name            text   NOT NULL,
    surname         text   NOT NULL,
    patronymic      text   NOT NULL,
    birthday        text   NOT NULL,
    passport_number bigint NOT NULL,
    income          bigint NOT NULL,
    CONSTRAINT users_pkey PRIMARY KEY (passport_number)
);

CREATE TABLE addresses
(
    address_id       serial NOT NULL,
    country          text   NOT NULL,
    region           text   NOT NULL,
    city             text   NOT NULL,
    street           text   NOT NULL,
    house_number     text   NOT NULL,
    apartment_number text   NOT NULL,
    CONSTRAINT addresses_pkey PRIMARY KEY (address_id)
);

CREATE TABLE user_address
(
    user_passport_number    bigint NOT NULL,
    address_id bigint NOT NULL,
    CONSTRAINT user_address_pkey PRIMARY KEY (user_passport_number, address_id),
    CONSTRAINT "address_FK" FOREIGN KEY (address_id)
        REFERENCES public.addresses (address_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE CASCADE,
    CONSTRAINT "user_FK" FOREIGN KEY (user_passport_number)
        REFERENCES public.users (passport_number) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE CASCADE
);