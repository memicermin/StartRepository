# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table rent_a_car (
  id                            bigint auto_increment not null,
  constraint pk_rent_a_car primary key (id)
);

create table sale (
);

create table service (
);

create table user (
);


# --- !Downs

drop table if exists rent_a_car;

drop table if exists sale;

drop table if exists service;

drop table if exists user;

