-- public.species definition

-- Drop table

-- DROP TABLE species;

CREATE TABLE species (
                         id uuid NOT NULL,
                         "name" varchar(255) NOT NULL,
                         CONSTRAINT species_pkey PRIMARY KEY (id)
);


-- public."user" definition

-- Drop table

-- DROP TABLE "user";

CREATE TABLE "user" (
                        id varchar(255) NOT NULL,
                        email varchar(40) NOT NULL,
                        "password" varchar(40) NOT NULL,
                        phone varchar(255) NULL,
                        CONSTRAINT user_pkey PRIMARY KEY (id)
);


-- public.address definition

-- Drop table

-- DROP TABLE address;

CREATE TABLE address (
                         id uuid NOT NULL,
                         address varchar(255) NULL,
                         geo_hash varchar(255) NULL,
                         latitude float8 NULL,
                         longitude float8 NULL,
                         user_id varchar(255) NULL,
                         CONSTRAINT address_pkey PRIMARY KEY (id),
                         CONSTRAINT fkibojxnhlre8lcn6ag9a35epr1 FOREIGN KEY (user_id) REFERENCES "user"(id)
);


-- public.breed definition

-- Drop table

-- DROP TABLE breed;

CREATE TABLE breed (
                       id uuid NOT NULL,
                       "name" varchar(255) NULL,
                       species_id uuid NULL,
                       CONSTRAINT breed_pkey PRIMARY KEY (id),
                       CONSTRAINT fk562cbno8t7070q0m0po1fki2n FOREIGN KEY (species_id) REFERENCES species(id)
);


-- public.profile definition

-- Drop table

-- DROP TABLE profile;

CREATE TABLE profile (
                         id uuid NOT NULL,
                         "attribute" varchar(255) NULL,
                         birth_date timestamp(6) NULL,
                         description varchar(255) NULL,
                         gender varchar(255) NULL,
                         "name" varchar(100) NOT NULL,
                         bread_id uuid NULL,
                         user_id varchar(255) NULL,
                         CONSTRAINT profile_pkey PRIMARY KEY (id),
                         CONSTRAINT fk34lmibaadehn191lgf4gl9jk7 FOREIGN KEY (user_id) REFERENCES "user"(id),
                         CONSTRAINT fkrxnpeb8nob30dg9umid0w3xk6 FOREIGN KEY (bread_id) REFERENCES breed(id)
);


-- public.profile_gallery definition

-- Drop table

-- DROP TABLE profile_gallery;

CREATE TABLE profile_gallery (
                                 profile_id uuid NOT NULL,
                                 gallery varchar(255) NULL,
                                 CONSTRAINT fk53gm4cq3hwssc617m9fmhghg FOREIGN KEY (profile_id) REFERENCES profile(id)
);


-- public.profile_interests definition

-- Drop table

-- DROP TABLE profile_interests;

CREATE TABLE profile_interests (
                                   profile_id uuid NOT NULL,
                                   interests varchar(255) NULL,
                                   CONSTRAINT fkl6bihw5xlah32qbt3i3laqljk FOREIGN KEY (profile_id) REFERENCES profile(id)
);


-- public.reaction definition

-- Drop table

-- DROP TABLE reaction;

CREATE TABLE reaction (
                          id uuid NOT NULL,
                          "comment" varchar(255) NULL,
                          profile_id uuid NULL,
                          CONSTRAINT reaction_pkey PRIMARY KEY (id),
                          CONSTRAINT fkfqn92ef8p353gogf99q7wo8u3 FOREIGN KEY (profile_id) REFERENCES profile(id)
);


-- public."subscription" definition

-- Drop table

-- DROP TABLE "subscription";

CREATE TABLE "subscription" (
                                id uuid NOT NULL,
                                duration int4 NOT NULL,
                                "name" varchar(255) NULL,
                                start_from timestamp(6) NULL,
                                status varchar(255) NULL,
                                user_id varchar(255) NULL,
                                CONSTRAINT subscription_pkey PRIMARY KEY (id),
                                CONSTRAINT uk_tq3cq3gmsss8jjyb2l5sb1o6k UNIQUE (user_id),
                                CONSTRAINT fk2a2b2ntxsixvvore38skq06s3 FOREIGN KEY (user_id) REFERENCES "user"(id)
);


-- public."match" definition

-- Drop table

-- DROP TABLE "match";

CREATE TABLE "match" (
                         id uuid NOT NULL,
                         profile_id uuid NULL,
                         CONSTRAINT match_pkey PRIMARY KEY (id),
                         CONSTRAINT fksfpyug42103cuurt5ku584fov FOREIGN KEY (profile_id) REFERENCES profile(id)
);-- public.species definition

-- Drop table

-- DROP TABLE species;

CREATE TABLE species (
                         id uuid NOT NULL,
                         "name" varchar(255) NOT NULL,
                         CONSTRAINT species_pkey PRIMARY KEY (id)
);


-- public."user" definition

-- Drop table

-- DROP TABLE "user";

CREATE TABLE "user" (
                        id varchar(255) NOT NULL,
                        email varchar(40) NOT NULL,
                        "password" varchar(40) NOT NULL,
                        phone varchar(255) NULL,
                        CONSTRAINT user_pkey PRIMARY KEY (id)
);


-- public.address definition

-- Drop table

-- DROP TABLE address;

CREATE TABLE address (
                         id uuid NOT NULL,
                         address varchar(255) NULL,
                         geo_hash varchar(255) NULL,
                         latitude float8 NULL,
                         longitude float8 NULL,
                         user_id varchar(255) NULL,
                         CONSTRAINT address_pkey PRIMARY KEY (id),
                         CONSTRAINT fkibojxnhlre8lcn6ag9a35epr1 FOREIGN KEY (user_id) REFERENCES "user"(id)
);


-- public.breed definition

-- Drop table

-- DROP TABLE breed;

CREATE TABLE breed (
                       id uuid NOT NULL,
                       "name" varchar(255) NULL,
                       species_id uuid NULL,
                       CONSTRAINT breed_pkey PRIMARY KEY (id),
                       CONSTRAINT fk562cbno8t7070q0m0po1fki2n FOREIGN KEY (species_id) REFERENCES species(id)
);


-- public.profile definition

-- Drop table

-- DROP TABLE profile;

CREATE TABLE profile (
                         id uuid NOT NULL,
                         "attribute" varchar(255) NULL,
                         birth_date timestamp(6) NULL,
                         description varchar(255) NULL,
                         gender varchar(255) NULL,
                         "name" varchar(100) NOT NULL,
                         bread_id uuid NULL,
                         user_id varchar(255) NULL,
                         CONSTRAINT profile_pkey PRIMARY KEY (id),
                         CONSTRAINT fk34lmibaadehn191lgf4gl9jk7 FOREIGN KEY (user_id) REFERENCES "user"(id),
                         CONSTRAINT fkrxnpeb8nob30dg9umid0w3xk6 FOREIGN KEY (bread_id) REFERENCES breed(id)
);


-- public.profile_gallery definition

-- Drop table

-- DROP TABLE profile_gallery;

CREATE TABLE profile_gallery (
                                 profile_id uuid NOT NULL,
                                 gallery varchar(255) NULL,
                                 CONSTRAINT fk53gm4cq3hwssc617m9fmhghg FOREIGN KEY (profile_id) REFERENCES profile(id)
);


-- public.profile_interests definition

-- Drop table

-- DROP TABLE profile_interests;

CREATE TABLE profile_interests (
                                   profile_id uuid NOT NULL,
                                   interests varchar(255) NULL,
                                   CONSTRAINT fkl6bihw5xlah32qbt3i3laqljk FOREIGN KEY (profile_id) REFERENCES profile(id)
);


-- public.reaction definition

-- Drop table

-- DROP TABLE reaction;

CREATE TABLE reaction (
                          id uuid NOT NULL,
                          "comment" varchar(255) NULL,
                          profile_id uuid NULL,
                          CONSTRAINT reaction_pkey PRIMARY KEY (id),
                          CONSTRAINT fkfqn92ef8p353gogf99q7wo8u3 FOREIGN KEY (profile_id) REFERENCES profile(id)
);


-- public."subscription" definition

-- Drop table

-- DROP TABLE "subscription";

CREATE TABLE "subscription" (
                                id uuid NOT NULL,
                                duration int4 NOT NULL,
                                "name" varchar(255) NULL,
                                start_from timestamp(6) NULL,
                                status varchar(255) NULL,
                                user_id varchar(255) NULL,
                                CONSTRAINT subscription_pkey PRIMARY KEY (id),
                                CONSTRAINT uk_tq3cq3gmsss8jjyb2l5sb1o6k UNIQUE (user_id),
                                CONSTRAINT fk2a2b2ntxsixvvore38skq06s3 FOREIGN KEY (user_id) REFERENCES "user"(id)
);


-- public."match" definition

-- Drop table

-- DROP TABLE "match";

CREATE TABLE "match" (
                         id uuid NOT NULL,
                         profile_id uuid NULL,
                         CONSTRAINT match_pkey PRIMARY KEY (id),
                         CONSTRAINT fksfpyug42103cuurt5ku584fov FOREIGN KEY (profile_id) REFERENCES profile(id)
);