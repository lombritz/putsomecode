# --- Created by Slick DDL
# To stop Slick DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table "USER" ("id" BIGINT GENERATED BY DEFAULT AS IDENTITY(START WITH 1) NOT NULL PRIMARY KEY,"firstname" VARCHAR NOT NULL,"lastname" VARCHAR NOT NULL,"username" VARCHAR NOT NULL,"password" VARCHAR NOT NULL,"date_created" DATE NOT NULL,"date_modified" DATE);

# --- !Downs

drop table "USER";
