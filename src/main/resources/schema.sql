create table if not exists Users (
id serial primary key,
login varchar (255) not null,
password varchar (255) not null,
role varchar (255) not null
 );
 insert into Users (login, password, role)
 values ('user1', '$2y$10$hIG4yTPcg8lltDVFTLPXre9QOOMsDgckx6GH71H7wVHhOIvzkjg12', 'ROLE_USER');
 insert into Users (login, password, role)
 values ('user2', '$2y$10$rXoT/uuSXT/iJ1/hHxhkfe5NcaHzIkaPMVTKtSUIehq3S1XZSbtJm', 'ROLE_USER');

-- create table if not exists Files(
-- id serial primary key,
-- name varchar (255) not null,
-- content_type varchar (255) not null,
-- size1 int8,
-- data bytea
-- );