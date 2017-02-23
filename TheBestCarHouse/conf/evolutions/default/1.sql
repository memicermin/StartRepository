# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table brand (
  id                            bigint auto_increment not null,
  brand                         varchar(255),
  part_of_brand                 integer,
  constraint pk_brand primary key (id)
);

create table car (
  id                            bigint auto_increment not null,
  model                         varchar(255),
  constraint pk_car primary key (id)
);

create table price (
  id                            bigint auto_increment not null,
  current_price                 float,
  old_price                     float,
  action                        integer,
  constraint pk_price primary key (id)
);

create table product (
  id                            bigint auto_increment not null,
  part_of_service               integer,
  constraint pk_product primary key (id)
);

create table sale (
  id                            bigint auto_increment not null,
  part_of_sale                  integer,
  constraint pk_sale primary key (id)
);

create table user (
  id                            bigint auto_increment not null,
  username                      varchar(50),
  email                         varchar(100),
  password                      varchar(100),
  first_name                    varchar(100),
  last_name                     varchar(100),
  birth_date                    varchar(255),
  gender                        integer(1),
  location                      varchar(150),
  phone_number                  varchar(15),
  create_date                   varchar(255),
  update_date                   varchar(255),
  last_login                    varchar(255),
  token                         varchar(255),
  notes                         varchar(25000),
  user_type                     integer,
  premium_user                  integer,
  login_count                   integer,
  constraint uq_user_email unique (email),
  constraint pk_user primary key (id)
);


# --- !Downs

drop table if exists brand;

drop table if exists car;

drop table if exists price;

drop table if exists product;

drop table if exists sale;

drop table if exists user;

