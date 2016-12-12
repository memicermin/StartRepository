# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table global_model (
  id                            bigint auto_increment not null,
  name                          varchar(255),
  constraint pk_global_model primary key (id)
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
  create_date                   datetime,
  update_date                   varchar(255),
  verification                  integer,
  user_level                    integer,
  login_count                   integer,
  premium_user                  integer,
  token                         varchar(255),
  constraint uq_user_email unique (email),
  constraint pk_user primary key (id)
);


# --- !Downs

drop table if exists global_model;

drop table if exists user;

