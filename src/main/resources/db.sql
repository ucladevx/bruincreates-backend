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
    `id`                  int(11) not null auto_increment,
    `product_id`          varchar(32) not null unique,
    `seller_id`           varchar(32) not null,
    `product_type`        tinyint unsigned not null comment 'type：1:item，2:service',
    `product_title`       varchar(32) not null,
    `product_category`    varchar(32) not null,
    `keywords`            varchar(64) not null,
    `product_description` varchar(512) not null,
    `product_images`      varchar(512) not null,
    `product_stock`       int(11) not null,
    `product_price`       decimal(10, 2) unsigned not null comment 'price',
    `date_created`        datetime not null default current_timestamp,
    `deleted`             boolean default false,
    index idx_product_id (`product_id`),
    index idx_product_type (`product_type`),
    primary key (`id`)
) engine = InnoDB default charset = utf8mb4;

create table if not exists `product_review` (
    `id`                  int(11) not null auto_increment,
    `buyer_id`            varchar(32) not null,
    `product_id`          varchar(32) not null,
    `product_review`      varchar(512) not null,
    `date_created`        datetime not null default current_timestamp,
    `deleted`             boolean default false,
    index idx_product_id (`product_id`),
    index idx_create_time (`date_created`),
    primary key (`id`)
) engine = InnoDB default charset = utf8mb4;

create table if not exists `order` (
    `id`               int unsigned auto_increment comment 'primary key',
    `order_id`         varchar(200) not null unique comment 'id with business meaning',
    `product_id`       varchar(32) not null,
    `buyer_id`         varchar(32) not null,
    `seller_id`        varchar(32) not null,
    `order_type`       tinyint unsigned not null comment 'type：1:item，2:service',
    `order_status`     tinyint unsigned not null comment 'status：1:created, 2:paid, 3:shipped, 4:completed, 5:cancelled',
    `shipping_charge`  decimal(10, 2) unsigned not null default 0.00 comment 'delivery charge',
    `total_charge`     decimal(10, 2) unsigned not null comment 'total charge',
    `date_created`     datetime not null default current_timestamp,
    `deleted`             boolean default false,
    index idx_order_id (`order_id`),
    index idx_buyer_id (`buyer_id`),
    index idx_seller_id (`seller_id`),
    index idx_type (`order_type`),
    index idx_status (`order_status`),
    index idx_create_time (`date_created`),
    primary key (`id`)
) engine = InnoDB default charset = utf8mb4;





