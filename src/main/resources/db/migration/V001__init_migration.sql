create schema if not exists my_app;

create sequence if not exists my_app.my_app_id_seq;

create table if not exists my_app.user
(
    id   integer not null default nextval('my_app.my_app_id_seq') primary key,
    login  text    not null,
    password  text    not null
    );

create table if not exists my_app.contact
(
    id       integer not null default nextval('my_app.my_app_id_seq') primary key,
    contact_name     text    not null unique ,
    fk_user_id integer constraint fk_user_primary_key references my_app.user
    );


create table if not exists my_app.email
(
    id       integer not null default nextval('my_app.my_app_id_seq') primary key,
    contact_email     text    not null unique ,
    fk_contact_id     integer constraint fk_contact_primary_key references my_app.contact
    );


create table if not exists my_app.phone
(
    id       integer not null default nextval('my_app.my_app_id_seq') primary key,
    phone_number     text    not null unique ,
    fk_contact_id     integer constraint fk_contact1_primary_key references my_app.contact
    );
