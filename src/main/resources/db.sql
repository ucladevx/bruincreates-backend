create database bruincreates;
use bruincreates;

create table if not exists `user` (
    `id`                int(11) not null auto_increment,
    `username`          varchar(32) not null unique,
    `password`          varchar(256) not null,
    `email`             varchar(64) not null unique,
    `mobile`            int(11) not null default '1000000000',
    `profile_name`      varchar(32) not null default 'User',
    `avatar_url`        varchar(256) not null default '',
    `bio`               varchar(512) not null default '',
    `data_created`      datetime not null default current_timestamp,
    `role`              int(1) not null default 0,
    `verified`          int(1) not null default 0,
    `locked`            int(1) not null default 0,
    `disabled`          int(1) not null default 0,
    `deleted`           int(1) not null default 0,
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

create table if not exists `product_category` (
    `id`            int(11) not null auto_increment,
    `category`      varchar(16) not null unique,
    primary key (`id`)
) engine = InnoDB default charset = utf8mb4;

create table if not exists `product` (
    `id`                int(11) not null auto_increment,
    `username`          varchar(32) not null,
    `product_id`        varchar(32) not null unique,
    `title`             varchar(32) not null,
    `description`       varchar(512) not null,
    `images`            varchar(512) not null,
    `stock`             int(11) not null,
    `price`             int(11) not null,
    `category_id`       int(11) not null,
    `type`              int(1) not null default 0,
    foreign key (category_id) references product_category(id),
    primary key (`id`)
) engine = InnoDB default charset = utf8mb4;

create table if not exists `product_review` (
    `id`                  int(11) not null auto_increment,
    `username`            varchar(32) not null,
    `product_id`          varchar(32) not null,
    `review`              varchar(512) not null,
    foreign key (username) references User(username),
    foreign key (product_id) references Product(product_id),
    primary key (`id`)
) engine = InnoDB default charset = utf8mb4;







