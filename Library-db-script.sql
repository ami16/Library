SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
--  Table structure for `books`
-- ----------------------------
DROP TABLE IF EXISTS `books`;
CREATE TABLE `books` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `isbn` int(11) NOT NULL,
  `author` varchar(45) DEFAULT NULL,
  `title` varchar(45) DEFAULT NULL,
  `publ_year` date DEFAULT NULL,
  `writ_year` date DEFAULT NULL,
  `genre` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=19 DEFAULT CHARSET=latin1;

-- ----------------------------
--  Table structure for `credentials`
-- ----------------------------
DROP TABLE IF EXISTS `credentials`;
CREATE TABLE `credentials` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pass` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=101 DEFAULT CHARSET=latin1;

-- ----------------------------
--  Table structure for `registry`
-- ----------------------------
DROP TABLE IF EXISTS `registry`;
CREATE TABLE `registry` (
  `user_id` int(11) NOT NULL,
  `book_isbn` int(11) DEFAULT NULL
) ENGINE=MyISAM AUTO_INCREMENT=19 DEFAULT CHARSET=latin1;

-- ----------------------------
--  Table structure for `users`
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `first_name` varchar(45) DEFAULT NULL,
  `last_name` varchar(45) DEFAULT NULL,
  `e_mail` varchar(45) DEFAULT NULL,
  `mobile_num` varchar(45) DEFAULT NULL,
  `address` varchar(45) DEFAULT NULL,
  `date_of_registration` date DEFAULT NULL,
  `role` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=26 DEFAULT CHARSET=latin1;

-- ----------------------------
--  Records 
-- ----------------------------
INSERT INTO `books` VALUES ('1','159','Suzanne Collins','The Hunger Games','2010-07-12','2008-09-14','FICTION;FANTASY;MYSTERY'), ('2','357','J.K. Rowling','Harry Potter and the Order of the Phoenix','2004-08-10','2000-01-07','FANTASY'), ('3','456','Arthur Golden','Memoirs of a Geisha','2005-11-15','1999-04-11','FICTION;HISTORY'), ('4','789','Stephen King','Salem\'s Lot','1991-02-27','1975-04-03','MYSTERY;HORROR'), ('18','789','Stephen King','Salem\'s Lot','1991-02-27','1975-04-03','MYSTERY;HORROR'), ('6','123','Asde','Title','2016-11-11','1999-01-01','FICTION;FANTASY;HORROR'), ('7','111','Author Second','TEN MILES FROM THE HEART','2015-01-01','1942-10-10','FICTION;FANTASY;MYSTERY'), ('8','222','The Third Author','Fifty pennies','2015-01-10','1805-01-01','FICTION;FANTASY;COMEDY'), ('9','444','Nellie McQueen','Hennesy Is An Excellent Choice','2016-11-01','1926-12-25','HISTORY;COMEDY'), ('10','17','Leo Tolstoy','Anna Karenina','2013-04-18','1910-01-01','HISTORY'), ('17','17','Leo Tolstoy','Anna Karenina','2013-04-18','1910-01-01','HISTORY'), ('12','13','Danny DeVito','My Closure','2010-10-15','1989-08-29','HISTORY;COMEDY'), ('13','123456789','Brandon Stanford','About Midnight','2016-11-11','1999-01-20','FICTION;MYSTERY'), ('14','27','Mark Twain','The Adventures of Huckleberry Finn','1998-03-25','1900-06-24','FICTION'), ('15','27','Mark Twain','The Adventures of Huckleberry Finn','1998-03-25','1900-06-24','FICTION');
INSERT INTO `credentials` VALUES ('1','peter'), ('2','lois'), ('3','doughnut'), ('4','shit'), ('5','brave'), ('6','1234'), ('7','admin7'), ('8','8888'), ('9','9999'), ('10','0'), ('11','1234'), ('12','1234'), ('13','qwerty'), ('14','1234'), ('15','1234'), ('24','4321'), ('17','1234'), ('18','1234'), ('19','1234'), ('20','1234'), ('21','4321'), ('22','1234'), ('23','1234'), ('25','2222');
INSERT INTO `registry` VALUES ('25','13'), ('14','456'), ('18','357'), ('14','111'), ('14','123456789');
INSERT INTO `users` VALUES ('1','Olesya','Parashchak','oleskafeska@gmail.com','637235443','Drohobytska st.38 Drohobych','2015-12-12','USER'), ('2','Petro','Homenko','petroha@gmail.com','735478541','Lvivska st.5 Lviv','2016-11-09','USER'), ('3','Andrew','Petrenko','andrewcool@gmail.com','565478542','Lychakivska st.15 Lviv','2014-07-09','ADMIN'), ('4','Oleh','Shmanko','shman@gmail.com','987548745','Patona st.56 Kyiv','2014-04-08','USER'), ('5','Maria','Petrova','petrova@gmail.com','970548754','Chyprinka st.45 Kharkiv','2016-07-03','USER'), ('6','Julia','Khajnus','beautifull@gmail.com','631548754','Nova st.4 Drohobych','2015-03-07','USER'), ('7','Ivan','Babyrnych','ivanko@gmail.com','670245120','Svitla st.56 Sambir','2014-09-10','USER'), ('8','Teodor','Homyk','dido@gmail.com','685478452','Nova st.4 Drohobych','2016-10-12','USER'), ('9','Oleh','Koval','koval@gmail.com','687458475','Zelena st.78 Odessa','2015-12-12','USER'), ('10','Zenyk','Lytvyn','zeno@gmail.com','974525895','Sambirska st.56 Komarno','2013-07-12','ADMIN'), ('11','Andrew','Lloyd-Webber','m@m.z','123456789','Adress is my home kagbe','2016-10-17','USER'), ('12','andrew2','qwertyu','m@m.c','321321321','asdf rewdsf fgjrgt adrf qte','2016-10-17','USER'), ('13','Qwerty','Salomon','m@m.s','321321321','Adress on fictional shit','2016-10-17','USER'), ('14','Admin','Adminovyk','m@m.x','987654321','Admin Street 52/4. Stack City','2016-10-20','ADMIN'), ('15','Liza2','Moninsky','l@l.z','222222229','Adress is my Home... Cap?','2016-10-21','USER'), ('17','MyFriend','Johnson','m1@m.c','333222111','AdrAdrAdrAdr','2016-10-24','USER'), ('18','Vasyl','Hnativ','v@v.v','100000000','Adddrrreeesss','2016-10-26','USER'), ('19','Newie','Newston','n@n.n','444444444','NewAddressHere Right now','2016-10-27','USER'), ('20','Leonid','Faykovych','l@l.l','123123123','asdasdasdasd','2016-10-27','ADMIN'), ('21','Kon','Pedalnyj','k@k.k','333333333','asd kjsdfgkkk kkKKKK','2016-11-01','USER'), ('22','Bentley','Bordisson','b@b.b','111111111','banteley street 10','2016-11-10','ADMIN'), ('23','Funky','Shlink','f@f.f','999999999','asdqweasdzxc','2016-11-10','USER'), ('25','Rocky','Ballboa','r@r.r','123321123','Adrewssdfsd efdewrb','2016-11-16','USER');
