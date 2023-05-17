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
                        created_ts timestamp(6) NOT NULL,
                        email varchar(40) NOT NULL,
                        "password" varchar(100) NOT NULL,
                        phone varchar(10) NULL,
                        "role" varchar(255) NULL,
                        updated_ts timestamp(6) NULL,
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
                         avatar varchar(255) NOT NULL,
                         birthday timestamp(6) NULL,
                         created_ts timestamp(6) NOT NULL,
                         description varchar(255) NULL,
                         gender varchar(255) NULL,
                         height float8 NULL,
                         "name" varchar(100) NOT NULL,
                         updated_ts timestamp(6) NOT NULL,
                         weight float8 NULL,
                         breed_id uuid NULL,
                         user_id varchar(255) NULL,
                         CONSTRAINT profile_pkey PRIMARY KEY (id),
                         CONSTRAINT fk34lmibaadehn191lgf4gl9jk7 FOREIGN KEY (user_id) REFERENCES "user"(id),
                         CONSTRAINT fkjkh053k1t8hsxswi2cbhcyef2 FOREIGN KEY (breed_id) REFERENCES breed(id)
);


-- public.reaction definition

-- Drop table

-- DROP TABLE reaction;

CREATE TABLE reaction (
                          id uuid NOT NULL,
                          "comment" varchar(255) NULL,
                          created_ts timestamp(6) NOT NULL,
                          updated_ts timestamp(6) NOT NULL,
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


-- public.gallery definition

-- Drop table

-- DROP TABLE gallery;

CREATE TABLE gallery (
                         id uuid NOT NULL,
                         created_ts timestamp(6) NULL,
                         gallery varchar(255) NULL,
                         updated_ts timestamp(6) NULL,
                         profile_id uuid NULL,
                         CONSTRAINT gallery_pkey PRIMARY KEY (id),
                         CONSTRAINT fk367qm3y3d18tdlgo1gwnx7jeo FOREIGN KEY (profile_id) REFERENCES profile(id)
);


-- public.interests definition

-- Drop table

-- DROP TABLE interests;

CREATE TABLE interests (
                           id uuid NOT NULL,
                           breed_id uuid NULL,
                           profile_id uuid NULL,
                           CONSTRAINT interests_pkey PRIMARY KEY (id),
                           CONSTRAINT fk6i5adpn02k55kd0lcc977qtsl FOREIGN KEY (profile_id) REFERENCES profile(id),
                           CONSTRAINT fkayftl0njl1j2oy02odx8skogn FOREIGN KEY (breed_id) REFERENCES breed(id)
);


-- public."match" definition

-- Drop table

-- DROP TABLE "match";

CREATE TABLE "match" (
                         id uuid NOT NULL,
                         created_ts timestamp(6) NOT NULL,
                         updated_ts timestamp(6) NOT NULL,
                         match_from uuid NULL,
                         match_to uuid NULL,
                         CONSTRAINT match_pkey PRIMARY KEY (id),
                         CONSTRAINT fk5d6r83ne6hvto3u2ea50kqjk0 FOREIGN KEY (match_to) REFERENCES profile(id),
                         CONSTRAINT fk6yqslwo4cj7uvjtw63n27jpn FOREIGN KEY (match_from) REFERENCES profile(id)
);