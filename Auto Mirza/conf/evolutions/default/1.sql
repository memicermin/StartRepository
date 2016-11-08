# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table rent_a_car (
  id                            bigint auto_increment not null,
  constraint pk_rent_a_car primary key (id)
);


# --- !Downs

drop table if exists rent_a_car;

