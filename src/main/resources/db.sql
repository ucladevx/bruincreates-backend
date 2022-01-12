create database bruincreates;
use bruincreates;

create table if not exists `user` (
    `id`                int(11) not null auto_increment,
    `role`              enum('user', 'admin') default 'user',
    `profile_name`      varchar(32) not null default 'user',
    `username`          varchar(32) not null unique,
    `email`             varchar(64) not null unique,
    `password`          varchar(256) not null,
    `avatar_url`        varchar(256) not null default '',
    `payment_url`       varchar(256) not null default '',
    `bio`               varchar(512) not null default '',
    `date_created`      datetime not null default current_timestamp,
    `verified`          boolean default false,
    `disabled`          boolean default false,
    `locked`            boolean default false,
    `deleted`           boolean default false,
    index(username),
    primary key (`id`)
) engine = InnoDB default charset = utf8mb4;

create table if not exists `product` (
    `id`                int(11) not null auto_increment,
    `type`              enum('item', 'service'),
    `product_id`        varchar(32) not null unique,
    `seller`            varchar(32) not null,
    `title`             varchar(32) not null,
    `category`          varchar(32) not null,
    `keywords`          varchar(64) not null,
    `description`       varchar(512) not null,
    `images`            varchar(512) not null,
    `stock`             int(11) not null,
    `price`             int(11) not null,
    `date_created`      datetime not null default current_timestamp,
    `deleted`           boolean default false,
    index(product_id),
    primary key (`id`)
) engine = InnoDB default charset = utf8mb4;

create table if not exists `product_review` (
    `id`                  int(11) not null auto_increment,
    `product_id`          varchar(32) not null,
    `reviewer`            varchar(32) not null,
    `review`              varchar(512) not null,
    `date_created`        datetime not null default current_timestamp,
    `deleted`             boolean default false,
    primary key (`id`)
) engine = InnoDB default charset = utf8mb4;

create table if not exists `order` (
    `id`                int(11) not null auto_increment,
    `order_id`          varchar(32) not null unique,
    `product_id`        varchar(32) not null,
    `buyer`             varchar(32) not null,
    `seller`            varchar(32) not null,
    `status`            enum('created', 'paid','shipped','completed', 'cancelled'),
    index(order_id),
    primary key (`id`)
) engine = InnoDB default charset = utf8mb4;







