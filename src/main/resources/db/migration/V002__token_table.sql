create sequence if not exists my_app.token_seq;

create table if not exists my_app.token
(
    id       integer not null default nextval('my_app.token_seq') primary key,
    token_val     text    not null unique ,
    token_type      text    not null,
    revoked boolean not null ,
    expired boolean not null ,
    fk_user_id     integer constraint fk_user_primary_key references my_app.user
    );
