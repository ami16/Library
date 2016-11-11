drop database if exists ownlib;
create database ownlib;
use ownlib;

create table books
(id int primary key auto_increment,
ISBN varchar (25),
author varchar (45),
title varchar (45),
publYear date,
writYear date,
genre varchar (45)
);

create table users
(id int primary key auto_increment,
firstName varchar (45), 
lastName varchar (45),
eMail varchar (45),
mobileNum varchar (45),
address varchar (45),
dateOfRegistration date,
role varchar (45)
);

create table credentials
(id int primary key auto_increment,
pass varchar (255)
);

create table registry
(user_id int primary key auto_increment,
book_id int
);