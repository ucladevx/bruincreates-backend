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
    `id`               int unsigned auto_increment comment 'primary key',
    `transaction_id`   varchar(200) not null unique comment 'id with business meaning',
    `buyer`            varchar(32) not null,
    `seller`           varchar(32) not null,
    `type`             tinyint unsigned not null comment 'type：1:item，2:service',
    `status`           tinyint unsigned not null comment 'status：1:created, 2:paid, 3:shipped, 4:completed, 5:cancelled',
    `shipping`         decimal(10, 2) unsigned not null default 0.00 comment 'delivery fee',
    `total`            decimal(10, 2) unsigned not null comment 'total charge',
    `create_time`      timestamp not null default now(),
    index idx_code (`transaction_id`),
    index idx_buyer_id (buyer),
    index idx_seller_id (seller),
    index idx_type (type),
    index idx_status (`status`),
    index idx_create_time (create_time),
    primary key (`id`)
) engine = InnoDB default charset = utf8mb4;





