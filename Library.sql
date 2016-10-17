drop database if exists Library;
create database Library;
use Library;

create table books
(id_book int primary key auto_increment,
ISBN varchar (45),
author varchar (45),
title varchar (45),
publYear date,
writYea date,
genre varchar (45)
);

create table users
(id_user int primary key auto_increment,
firstName varchar (45), 
lastName varchar (45),
eMail varchar (45),
mobileNum varchar (45),
address varchar (45),
dateOfRegistration date,
role varchar (45)
);

