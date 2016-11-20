# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table brand (
  id                            bigint auto_increment not null,
  brand                         varchar(255),
  constraint pk_brand primary key (id)
);

create table image (
  id                            bigint auto_increment not null,
  public_id                     varchar(255),
  secret_image_url              varchar(255),
  image_url                     varchar(255),
  rent_a_car_id                 bigint,
  sale_id                       bigint,
  constraint pk_image primary key (id)
);

create table rent_a_car (
  id                            bigint auto_increment not null,
  constraint pk_rent_a_car primary key (id)
);

create table sale (
  id                            bigint auto_increment not null,
  brand_id                      bigint,
  type                          varchar(255),
  year                          integer,
  price                         double,
  body_type                     varchar(255),
  color                         varchar(255),
  meileage                      integer,
  motor_volume                  varchar(255),
  motor_power                   integer,
  type_of_fuel                  varchar(255),
  transmission                  varchar(255),
  details                       varchar(255),
  is_avilable                   tinyint(1) default 0,
  constraint pk_sale primary key (id)
);

create table service (
);

create table user (
);

alter table image add constraint fk_image_rent_a_car_id foreign key (rent_a_car_id) references rent_a_car (id) on delete restrict on update restrict;
create index ix_image_rent_a_car_id on image (rent_a_car_id);

alter table image add constraint fk_image_sale_id foreign key (sale_id) references sale (id) on delete restrict on update restrict;
create index ix_image_sale_id on image (sale_id);

alter table sale add constraint fk_sale_brand_id foreign key (brand_id) references brand (id) on delete restrict on update restrict;
create index ix_sale_brand_id on sale (brand_id);


# --- !Downs

alter table image drop foreign key fk_image_rent_a_car_id;
drop index ix_image_rent_a_car_id on image;

alter table image drop foreign key fk_image_sale_id;
drop index ix_image_sale_id on image;

alter table sale drop foreign key fk_sale_brand_id;
drop index ix_sale_brand_id on sale;

drop table if exists brand;

drop table if exists image;

drop table if exists rent_a_car;

drop table if exists sale;

drop table if exists service;

drop table if exists user;

