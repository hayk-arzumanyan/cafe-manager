CREATE SCHEMA IF NOT EXISTS public;

CREATE TABLE IF NOT EXISTS public."order" (
                                id bigint NOT NULL,
                                order_name character varying(255),
                                order_status character varying(255),
                                table_id bigint
);
CREATE SEQUENCE IF NOT EXISTS public.order_sequence
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE IF NOT EXISTS public.product (
                                id bigint NOT NULL,
                                product_name character varying(255)
);

CREATE TABLE IF NOT EXISTS public.product_in_order (
                                         id bigint NOT NULL,
                                         amount integer,
                                         name character varying(255),
                                         status character varying(255),
                                         order_id bigint,
                                         product_id bigint
);

CREATE SEQUENCE IF NOT EXISTS public.product_in_order_sequence
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE SEQUENCE IF NOT EXISTS public.product_sequence
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE IF NOT EXISTS public."table" (
                                id bigint NOT NULL,
                                is_assigned boolean,
                                table_name character varying(255),
                                waiter_name character varying(255),
                                user_id bigint
);

CREATE SEQUENCE IF NOT EXISTS public.table_sequence
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE IF NOT EXISTS public."user" (
                               id bigint NOT NULL,
                               first_name character varying(255),
                               last_name character varying(255),
                               password_hash character varying(255),
                               role character varying(255) NOT NULL,
                               username character varying(255) NOT NULL
);

CREATE SEQUENCE IF NOT EXISTS public.user_sequence
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER TABLE ONLY public."order"
    ADD CONSTRAINT order_pkey PRIMARY KEY (id);


ALTER TABLE ONLY public.product_in_order
    ADD CONSTRAINT product_in_order_pkey PRIMARY KEY (id);


ALTER TABLE ONLY public.product
    ADD CONSTRAINT product_pkey PRIMARY KEY (id);


ALTER TABLE ONLY public."table"
    ADD CONSTRAINT table_pkey PRIMARY KEY (id);


ALTER TABLE ONLY public."user"
    ADD CONSTRAINT uk_sb8bbouer5wak8vyiiy4pf2bx UNIQUE (username);


ALTER TABLE ONLY public."user"
    ADD CONSTRAINT user_pkey PRIMARY KEY (id);

ALTER TABLE ONLY public.product_in_order
    ADD CONSTRAINT fk7qpekps6q9cyrhax6hfu3m2gm FOREIGN KEY (product_id) REFERENCES public.product(id);


ALTER TABLE ONLY public.product_in_order
    ADD CONSTRAINT fkct4m2qganatgdg0j65laqrtf3 FOREIGN KEY (order_id) REFERENCES public."order"(id);


ALTER TABLE ONLY public."order"
    ADD CONSTRAINT fke9h6ecfdg3ixsr416awbqyoj3 FOREIGN KEY (table_id) REFERENCES public."table"(id);


ALTER TABLE ONLY public."table"
    ADD CONSTRAINT fkeqevxxdyugj1pg5xpkv0j08o9 FOREIGN KEY (user_id) REFERENCES public."user"(id);


INSERT INTO "user" (id, username, first_name, last_name, password_hash, role)
VALUES (-1, 'ahayk', 'Hayk', 'Arzumanyan', '$2a$12$tfOHcNggyD6xxnqk4FB6suJ0KiJlS0vy0uCeYy4ruu3xJNBhN2Og.', 'MANAGER')
