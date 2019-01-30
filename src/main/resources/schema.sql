DROP SCHEMA IF EXISTS `abcproducts` CASCADE;

CREATE SCHEMA IF NOT EXISTS `abcproducts`;

USE `abcproducts`;

DROP TABLE IF EXISTS `abcproducts`.`ProductSource`;

CREATE TABLE `abcproducts`.`ProductSource` (
   source_id int not null AUTO_INCREMENT,
   source_name varchar(20) not null,
   PRIMARY KEY (`source_id`)
) ENGINE = InnoDB;

DROP TABLE IF EXISTS `abcproducts`.`BodyLocation`;

CREATE TABLE `abcproducts`.`BodyLocation` (
   body_location_id int not null AUTO_INCREMENT,
   body_location_name varchar(20) not null,
   PRIMARY KEY (`body_location_id`)
) ENGINE = InnoDB;

DROP TABLE IF EXISTS `abcproducts`.`ProductCategory`;

CREATE TABLE `abcproducts`.`ProductCategory` (
   category_id int not null AUTO_INCREMENT,
   category_name varchar(20) not null,
   PRIMARY KEY (`category_id`)
) ENGINE = InnoDB;

DROP TABLE IF EXISTS `abcproducts`.`Company`;

CREATE TABLE `abcproducts`.`Company` (
   company_id int not null AUTO_INCREMENT,
   company_name varchar(32) not null,
   company_url varchar(255) null,
   company_mapping_location varchar(64) null,
   company_city varchar(32) not null,
   company_u_s_state varchar(24) null,
   company_country varchar(24) null,
   PRIMARY KEY (`company_id`)
) ENGINE = InnoDB;

DROP TABLE IF EXISTS `abcproducts`.`Product`;

CREATE TABLE `abcproducts`.`Product` (
   product_id int not null AUTO_INCREMENT,
   product_name varchar(128) not null,
   product_price decimal(6,2) null,
   body_location_id int null,
   category_id int null,
   source_id int null,
   product_link varchar(2048) null,
   product_image text null,
   company_id int null,
   original_id int null,
   PRIMARY KEY (`product_id`)
) ENGINE = InnoDB;

-- TODO add FKs

DROP TABLE IF EXISTS `abcproducts`.`WearablesRaw`;

create table `abcproducts`.`WearablesRaw` as select * from CSVREAD('classpath:Wearables-DFE.csv',null,'fieldDelimiter=" fieldSeparator=,');

