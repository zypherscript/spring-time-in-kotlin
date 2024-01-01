create extension if not exists "uuid-ossp";

create table if not exists messages (
    id varchar(50) default uuid_generate_v4()::text,
    text varchar(500),
    constraint id_messages primary key (id)
);