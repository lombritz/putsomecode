# --- Created by Slick DDL
# To stop Slick DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table "PLAYERS" ("id" BIGINT GENERATED BY DEFAULT AS IDENTITY(START WITH 1) NOT NULL PRIMARY KEY,"nickname" VARCHAR NOT NULL,"wins" INTEGER NOT NULL,"draws" INTEGER NOT NULL,"losses" INTEGER NOT NULL,"user_id" BIGINT NOT NULL);
create table "USER" ("id" BIGINT GENERATED BY DEFAULT AS IDENTITY(START WITH 1) NOT NULL PRIMARY KEY,"firstname" VARCHAR NOT NULL,"lastname" VARCHAR NOT NULL,"username" VARCHAR NOT NULL,"password" VARCHAR NOT NULL,"date_created" DATE NOT NULL,"date_modified" DATE);
alter table "PLAYERS" add constraint "user_fk" foreign key("user_id") references "USER"("id") on update NO ACTION on delete NO ACTION;

# --- !Downs

alter table "PLAYERS" drop constraint "user_fk";
drop table "PLAYERS";
drop table "USER";

