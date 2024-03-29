drop table if exists todos;

create table todos (
 id bigint generated by default as identity,
 content varchar(2000),
 created_at timestamp,
 done boolean,
 modified_at timestamp,
 primary key (id)
);