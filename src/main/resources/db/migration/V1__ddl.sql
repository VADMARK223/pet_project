create table if not exists users
(
    id bigint generated by default as identity constraint users_pkey primary key,
    avatar bytea,
    email varchar(100) constraint uk_6dotkott2kjsp8vw4d0m25fb7 unique,
    password varchar(500) not null,
    username varchar(20) not null constraint uk_r43af9ap4edm43mmtq01oddj6 unique
    );

alter table users owner to postgres;

create table if not exists roles
(
    id bigint generated by default as identity constraint roles_pkey primary key,
    name varchar(255)
);

alter table roles owner to postgres;

create table if not exists users_roles
(
    user_entity_id bigint not null constraint users_roles_users_fk references users,
    roles_id bigint not null constraint users_roles_roles_fk references roles,
    constraint users_roles_pkey primary key (user_entity_id, roles_id)
);

alter table users_roles owner to postgres;