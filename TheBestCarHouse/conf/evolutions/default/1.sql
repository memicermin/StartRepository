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
  brand_id                      bigint,
  type                          varchar(255),
  year                          varchar(255),
  body_type                     varchar(255),
  color                         varchar(255),
  mileage                       varchar(255),
  motor_volume                  varchar(255),
  motor_power                   varchar(255),
  type_of_fuel                  varchar(255),
  transmission                  varchar(255),
  constraint pk_car primary key (id)
);

create table car_parts (
  id                            bigint auto_increment not null,
  constraint pk_car_parts primary key (id)
);

create table car_tires (
  id                            bigint auto_increment not null,
  constraint pk_car_tires primary key (id)
);

create table image (
  id                            bigint auto_increment not null,
  public_id                     varchar(255),
  secret_image_url              varchar(255),
  image_url                     varchar(255),
  car_id                        bigint,
  constraint pk_image primary key (id)
);

create table price (
  id                            bigint auto_increment not null,
  current_price                 float,
  old_price                     float,
  action                        integer,
  constraint pk_price primary key (id)
);

create table rent_a_car (
  id                            bigint auto_increment not null,
  car_id                        bigint,
  price_id                      bigint,
  description                   varchar(255),
  available                     integer,
  availability_date             varchar(255),
  active_car                    integer,
  constraint pk_rent_a_car primary key (id)
);

create table sale (
  id                            bigint auto_increment not null,
  part_of_sale                  integer,
  car_id                        bigint,
  car_parts_id                  bigint,
  tires_id                      bigint,
  price_id                      bigint,
  description                   varchar(1500),
  available                     integer(1),
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

alter table car add constraint fk_car_brand_id foreign key (brand_id) references brand (id) on delete restrict on update restrict;
create index ix_car_brand_id on car (brand_id);

alter table image add constraint fk_image_car_id foreign key (car_id) references car (id) on delete restrict on update restrict;
create index ix_image_car_id on image (car_id);

alter table rent_a_car add constraint fk_rent_a_car_car_id foreign key (car_id) references car (id) on delete restrict on update restrict;
create index ix_rent_a_car_car_id on rent_a_car (car_id);

alter table rent_a_car add constraint fk_rent_a_car_price_id foreign key (price_id) references price (id) on delete restrict on update restrict;
create index ix_rent_a_car_price_id on rent_a_car (price_id);

alter table sale add constraint fk_sale_car_id foreign key (car_id) references car (id) on delete restrict on update restrict;
create index ix_sale_car_id on sale (car_id);

alter table sale add constraint fk_sale_car_parts_id foreign key (car_parts_id) references car_parts (id) on delete restrict on update restrict;
create index ix_sale_car_parts_id on sale (car_parts_id);

alter table sale add constraint fk_sale_tires_id foreign key (tires_id) references car_tires (id) on delete restrict on update restrict;
create index ix_sale_tires_id on sale (tires_id);

alter table sale add constraint fk_sale_price_id foreign key (price_id) references price (id) on delete restrict on update restrict;
create index ix_sale_price_id on sale (price_id);


# --- !Downs

alter table car drop foreign key fk_car_brand_id;
drop index ix_car_brand_id on car;

alter table image drop foreign key fk_image_car_id;
drop index ix_image_car_id on image;

alter table rent_a_car drop foreign key fk_rent_a_car_car_id;
drop index ix_rent_a_car_car_id on rent_a_car;

alter table rent_a_car drop foreign key fk_rent_a_car_price_id;
drop index ix_rent_a_car_price_id on rent_a_car;

alter table sale drop foreign key fk_sale_car_id;
drop index ix_sale_car_id on sale;

alter table sale drop foreign key fk_sale_car_parts_id;
drop index ix_sale_car_parts_id on sale;

alter table sale drop foreign key fk_sale_tires_id;
drop index ix_sale_tires_id on sale;

alter table sale drop foreign key fk_sale_price_id;
drop index ix_sale_price_id on sale;

drop table if exists brand;

drop table if exists car;

drop table if exists car_parts;

drop table if exists car_tires;

drop table if exists image;

drop table if exists price;

drop table if exists rent_a_car;

drop table if exists sale;

drop table if exists user;

