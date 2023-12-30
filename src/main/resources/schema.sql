create table if not exists messages (
    id UUID primary key,
    text varchar(255) not null
);