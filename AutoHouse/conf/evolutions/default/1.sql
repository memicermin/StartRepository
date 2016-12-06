# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table brand (
  id                            bigint auto_increment not null,
  brand                         varchar(255),
  part_of_product               smallint,
  constraint pk_brand primary key (id)
);

create table car (
  id                            bigint auto_increment not null,
  brand_id                      bigint,
  type                          varchar(255),
  year                          integer,
  body_type                     varchar(255),
  color                         varchar(255),
  meileage                      integer,
  motor_volume                  varchar(255),
  motor_power                   integer,
  type_of_fuel                  varchar(255),
  transmission                  varchar(255),
  details                       varchar(1500),
  constraint pk_car primary key (id)
);

create table car_parts (
  id                            bigint auto_increment not null,
  brand_id                      bigint,
  constraint pk_car_parts primary key (id)
);

create table car_tires (
  id                            bigint auto_increment not null,
  brand_id                      bigint,
  constraint pk_car_tires primary key (id)
);

create table price (
  id                            bigint auto_increment not null,
  price                         double,
  old_price                     double,
  action                        tinyint(1) default 0,
  constraint pk_price primary key (id)
);

create table product (
  id                            bigint auto_increment not null,
  car_id                        bigint,
  car_parts_id                  bigint,
  car_tires_id                  bigint,
  price_id                      bigint,
  constraint pk_product primary key (id)
);

create table sale (
  id                            bigint auto_increment not null,
  product_id                    bigint,
  avilable                      tinyint(1) default 0,
  admin_id                      bigint,
  date                          datetime(6),
  constraint pk_sale primary key (id)
);

create table user (
  id                            bigint auto_increment not null,
  constraint pk_user primary key (id)
);

alter table car add constraint fk_car_brand_id foreign key (brand_id) references brand (id) on delete restrict on update restrict;
create index ix_car_brand_id on car (brand_id);

alter table car_parts add constraint fk_car_parts_brand_id foreign key (brand_id) references brand (id) on delete restrict on update restrict;
create index ix_car_parts_brand_id on car_parts (brand_id);

alter table car_tires add constraint fk_car_tires_brand_id foreign key (brand_id) references brand (id) on delete restrict on update restrict;
create index ix_car_tires_brand_id on car_tires (brand_id);

alter table product add constraint fk_product_car_id foreign key (car_id) references car (id) on delete restrict on update restrict;
create index ix_product_car_id on product (car_id);

alter table product add constraint fk_product_car_parts_id foreign key (car_parts_id) references car_parts (id) on delete restrict on update restrict;
create index ix_product_car_parts_id on product (car_parts_id);

alter table product add constraint fk_product_car_tires_id foreign key (car_tires_id) references car_tires (id) on delete restrict on update restrict;
create index ix_product_car_tires_id on product (car_tires_id);

alter table product add constraint fk_product_price_id foreign key (price_id) references price (id) on delete restrict on update restrict;
create index ix_product_price_id on product (price_id);

alter table sale add constraint fk_sale_product_id foreign key (product_id) references product (id) on delete restrict on update restrict;
create index ix_sale_product_id on sale (product_id);

alter table sale add constraint fk_sale_admin_id foreign key (admin_id) references user (id) on delete restrict on update restrict;
create index ix_sale_admin_id on sale (admin_id);


# --- !Downs

alter table car drop foreign key fk_car_brand_id;
drop index ix_car_brand_id on car;

alter table car_parts drop foreign key fk_car_parts_brand_id;
drop index ix_car_parts_brand_id on car_parts;

alter table car_tires drop foreign key fk_car_tires_brand_id;
drop index ix_car_tires_brand_id on car_tires;

alter table product drop foreign key fk_product_car_id;
drop index ix_product_car_id on product;

alter table product drop foreign key fk_product_car_parts_id;
drop index ix_product_car_parts_id on product;

alter table product drop foreign key fk_product_car_tires_id;
drop index ix_product_car_tires_id on product;

alter table product drop foreign key fk_product_price_id;
drop index ix_product_price_id on product;

alter table sale drop foreign key fk_sale_product_id;
drop index ix_sale_product_id on sale;

alter table sale drop foreign key fk_sale_admin_id;
drop index ix_sale_admin_id on sale;

drop table if exists brand;

drop table if exists car;

drop table if exists car_parts;

drop table if exists car_tires;

drop table if exists price;

drop table if exists product;

drop table if exists sale;

drop table if exists user;

