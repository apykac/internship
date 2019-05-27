-- Database: vskDB

-- DROP DATABASE "vskDB";

CREATE DATABASE "vskDB"
    WITH
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'Russian_Russia.1251'
    LC_CTYPE = 'Russian_Russia.1251'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;

CREATE TABLE users
(
    user_id         serial NOT NULL,
    name            text   NOT NULL,
    surname         text   NOT NULL,
    patronymic      text   NOT NULL,
    birthday        text   NOT NULL,
    passport_number bigint NOT NULL,
    income          bigint NOT NULL,
    CONSTRAINT users_pkey PRIMARY KEY (user_id)
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
    user_id    bigint NOT NULL,
    address_id bigint NOT NULL,
    CONSTRAINT user_address_pkey PRIMARY KEY (user_id, address_id),
    CONSTRAINT "address_FK" FOREIGN KEY (address_id)
        REFERENCES public.addresses (address_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE CASCADE,
    CONSTRAINT "user_FK" FOREIGN KEY (user_id)
        REFERENCES public.users (user_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE CASCADE
);