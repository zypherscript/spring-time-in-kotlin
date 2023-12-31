create table if not exists messages (
    id varchar(60) default random_uuid() primary key,
    text varchar(255) not null
);