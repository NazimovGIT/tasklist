create schema if not exists tasklist;

create table if not exists "user"
(
    id       bigserial primary key,
    name     varchar(255) not null,
    username varchar(255) not null unique,
    password varchar(255) not null
);

create table if not exists task
(
    id              bigserial primary key,
    title           varchar(255) not null,
    description     varchar(255) null,
    status          varchar(255) not null,
    expiration_date timestamp    null,
    user_id         bigint       not null,
    constraint fk_user foreign key (user_id) references "user" (id)
);

create table if not exists user_role
(
    user_id bigint       not null,
    role    varchar(255) not null,
    primary key (user_id, role),
    constraint fk_users_roles foreign key (user_id) references "user" (id) on delete cascade on update no action
);