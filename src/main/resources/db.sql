create database bruincreates;
use bruincreates;

create table if not exists `user` (
    `id`                int(11) not null auto_increment,
    `username`          varchar(32) not null unique,
    `password`          varchar(32) not null,
    `email`             varchar(64) not null unique,
    `mobile`            int(11) not null default '0000000000',
    `profile_name`      varchar(32) not null default 'User',
    `avatar_url`        varchar(256) not null default '',
    `bio`               varchar(256) not null default '',
    `data_created`      datetime not null default current_timestamp,
    `role`              int(1) not null default 0,
    `email_verified`    int(1) not null default 0,
    `account_locked`    int(1) not null default 0,
    `account_disabled`  int(1) not null default 0,
    `account_deleted`   int(1) not null default 0,
    primary key (`id`)
) engine = InnoDB default charset = utf8mb4;

create table if not exists `login` (
    `id`                  int(11) not null auto_increment,
    `username`            varchar(32) not null unique,
    `last_login_ip`       varchar(32) not null,
    `last_login_time`     datetime not null default current_timestamp,
    `last_logout_time`     datetime not null default current_timestamp,
    `failed_login_attempts`  int(11) not null default 0,
    primary key (`id`)
) engine = InnoDB default charset = utf8mb4;






