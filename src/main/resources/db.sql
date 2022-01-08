create database bruincreates;
use bruincreates;

create table if not exists `user_info` (
    `id`            int(11) not null auto_increment,
    `role`          int(1) not null default 0,
    `username`      varchar(32) not null unique,
    `profile_name`  varchar(32) not null default 'User',
    `avatar_url`    varchar(256) not null default '',
    `bio`           varchar(256) not null default '',
    primary key (`id`)
) engine = InnoDB default charset = utf8mb4;

create table if not exists `user_auth` (
    `id`                int(11) not null auto_increment,
    `username`          varchar(32) not null,
    `auth_provider`     varchar(16) not null default '',
    `auth_uid`          varchar(255) not null default '',
    `auth_token`        varchar(255) not null default '',
    foreign key (username) references user_info(username),
    primary key (`id`)
 ) engine = InnoDB default charset = utf8mb4;






