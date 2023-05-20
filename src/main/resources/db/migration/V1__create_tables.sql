create table if not exists public.species
(
    id   uuid         not null primary key,
    name varchar(255) not null
);
alter table
    public.species
    owner to "user";
create table if not exists public.breed
(
    id         uuid not null primary key,
    name       varchar(255),
    species_id uuid
        constraint fk562cbno8t7070q0m0po1fki2n references public.species
);
alter table
    public.breed
    owner to "user";
create table if not exists public."user"
(
    id         varchar(255) not null primary key,
    created_ts timestamp(6) not null,
    email      varchar(40)  not null,
    password   varchar(100) not null,
    phone      varchar(10),
    role       varchar(255),
    updated_ts timestamp(6)
);
alter table
    public."user"
    owner to "user";
create table if not exists public.address
(
    id        uuid not null primary key,
    address   varchar(255),
    geo_hash  varchar(255),
    latitude  double precision,
    longitude double precision,
    user_id   varchar(255)
        constraint fkibojxnhlre8lcn6ag9a35epr1 references public."user"
);
alter table
    public.address
    owner to "user";
create table if not exists public.profile
(
    id          uuid         not null primary key,
    avatar      varchar(255) not null,
    birthday    timestamp(6),
    created_ts  timestamp(6) not null,
    description varchar(255),
    gender      varchar(255),
    height      double precision,
    name        varchar(100) not null,
    updated_ts  timestamp(6) not null,
    weight      double precision,
    breed_id    uuid
        constraint fkjkh053k1t8hsxswi2cbhcyef2 references public.breed,
    user_id     varchar(255)
        constraint fk34lmibaadehn191lgf4gl9jk7 references public."user"
);
alter table
    public.profile
    owner to "user";
create table if not exists public.gallery
(
    id         uuid not null primary key,
    created_ts timestamp(6),
    gallery    varchar(255),
    updated_ts timestamp(6),
    profile_id uuid
        constraint fk367qm3y3d18tdlgo1gwnx7jeo references public.profile
);
alter table
    public.gallery
    owner to "user";
create table if not exists public.interests
(
    id         uuid not null primary key,
    breed_id   uuid
        constraint fkayftl0njl1j2oy02odx8skogn references public.breed,
    profile_id uuid
        constraint fk6i5adpn02k55kd0lcc977qtsl references public.profile
);
alter table
    public.interests
    owner to "user";
create table if not exists public.match
(
    id         uuid         not null primary key,
    created_ts timestamp(6) not null,
    updated_ts timestamp(6) not null,
    match_from uuid
        constraint fk6yqslwo4cj7uvjtw63n27jpn references public.profile,
    match_to   uuid
        constraint fk5d6r83ne6hvto3u2ea50kqjk0 references public.profile
);
alter table
    public.match
    owner to "user";
create table if not exists public.reaction
(
    id         uuid         not null primary key,
    comment    varchar(255),
    created_ts timestamp(6) not null,
    updated_ts timestamp(6) not null,
    profile_id uuid
        constraint fkfqn92ef8p353gogf99q7wo8u3 references public.profile
);
alter table
    public.reaction
    owner to "user";
create table if not exists public.subscription
(
    id         uuid    not null primary key,
    duration   integer not null,
    name       varchar(255),
    start_from timestamp(6),
    status     varchar(255),
    user_id    varchar(255)
        constraint uk_tq3cq3gmsss8jjyb2l5sb1o6k unique
        constraint fk2a2b2ntxsixvvore38skq06s3 references public."user"
);
alter table
    public.subscription
    owner to "user";
create table if not exists public.token
(
    id         integer not null primary key,
    expired    boolean not null,
    revoked    boolean not null,
    token      varchar(255)
        constraint uk_pddrhgwxnms2aceeku9s2ewy5 unique,
    token_type varchar(255),
    user_id    varchar(255)
        constraint fkl10xjn274m2rkxo54knt2xqvy references public."user"
);
alter table
    public.token
    owner to "user";
