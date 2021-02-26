CREATE TABLE IF NOT EXISTS public."table" (
                                              id bigint NOT NULL,
                                              is_assigned boolean,
                                              table_name character varying(255),
                                              waiter_name character varying(255),
                                              user_id bigint
);

CREATE SEQUENCE IF NOT EXISTS table_sequence INCREMENT BY 50;

CREATE TABLE IF NOT EXISTS public.product (
                                              id bigint NOT NULL,
                                              product_name character varying(255)
);

CREATE SEQUENCE IF NOT EXISTS product_sequence INCREMENT BY 50;

CREATE TABLE IF NOT EXISTS public.product_in_order (
                                                       id bigint NOT NULL,
                                                       amount integer,
                                                       order_name character varying(255),
                                                       status character varying(255),
                                                       order_id bigint,
                                                       product_id bigint
);

CREATE SEQUENCE IF NOT EXISTS productInOrder_sequence INCREMENT BY 50;

CREATE TABLE IF NOT EXISTS public."order" (
                                              id bigint NOT NULL,
                                              order_name character varying(255),
                                              order_status character varying(255),
                                              table_id bigint
);

CREATE SEQUENCE IF NOT EXISTS order_sequence INCREMENT BY 50;

CREATE TABLE IF NOT EXISTS public."user" (
                                             id bigint NOT NULL,
                                             first_name character varying(255),
                                             last_name character varying(255),
                                             password_hash character varying(255),
                                             role character varying(255) NOT NULL,
                                             username character varying(255) NOT NULL
);

CREATE SEQUENCE IF NOT EXISTS user_sequence INCREMENT BY 50;

INSERT INTO "user" (id, username, first_name, last_name, password_hash, role)
VALUES (1, 'ahayk', 'Hayk', 'Arzumanyan', '$2a$12$tfOHcNggyD6xxnqk4FB6suJ0KiJlS0vy0uCeYy4ruu3xJNBhN2Og.', 'MANAGER')
