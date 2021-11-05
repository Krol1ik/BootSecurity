create sequence hibernate_sequence start with 2 increment by 1;

create table messages
(
    id       bigint not null,
    filename varchar(255),
    tag      varchar(255),
    text     varchar(2048) not null ,
    user_id  bigint,
    primary key (id)
);

create table user
(
    id              bigint  not null,
    activation_code varchar(255),
    active          boolean not null,
    email           varchar(255),
    password        varchar(255) not null ,
    username        varchar(255) not null ,
    primary key (id)
);

create table user_role
(
    user_id bigint not null,
    roles   varchar(255)
);

alter table if exists messages
    add constraint fk_messages_user foreign key (user_id) references user;

alter table if exists user_role
    add constraint fk_user_role foreign key (user_id) references user;