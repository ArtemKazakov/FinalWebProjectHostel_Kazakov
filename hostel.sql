CREATE DATABASE  IF NOT EXISTS `hostel` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `hostel`;
-- MySQL dump 10.13  Distrib 5.7.12, for Win64 (x86_64)
--
-- Host: localhost    Database: hostel
-- ------------------------------------------------------
-- Server version	5.7.15-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `administrators`
--

DROP TABLE IF EXISTS `administrators`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `administrators` (
  `id_administrator` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Идентификатор администратора. Автоинкрементируется по мере добавения новых администраторов.',
  `login` varchar(40) NOT NULL COMMENT 'Уникальный логин администратора.',
  `password` varchar(45) NOT NULL COMMENT 'Пароль администратора.',
  `id_passport` int(10) unsigned NOT NULL COMMENT 'Идентификатор паспорта администратора',
  PRIMARY KEY (`id_administrator`),
  UNIQUE KEY `login_UNIQUE` (`login`),
  UNIQUE KEY `id_passport_UNIQUE` (`id_passport`),
  CONSTRAINT `fk_passports_administrators` FOREIGN KEY (`id_passport`) REFERENCES `passports` (`id_passport`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='Администраторы системы.';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `administrators`
--

LOCK TABLES `administrators` WRITE;
/*!40000 ALTER TABLE `administrators` DISABLE KEYS */;
INSERT INTO `administrators` VALUES (1,'artem_kazakov','Qwe123',1),(2,'vladislav_shamyonok','228leto',2),(3,'sergey_podgayskiy','123abc',13);
/*!40000 ALTER TABLE `administrators` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `clients`
--

DROP TABLE IF EXISTS `clients`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `clients` (
  `id_client` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Идентификатор клиента. Автоинкрементируется по мере добавения новых клиентов.',
  `login` varchar(40) NOT NULL COMMENT 'Логин пользователя, вводимый при регистрации. Является уникальным.',
  `password` varchar(45) NOT NULL COMMENT 'Пароль пользователя, вводимый при регистрации.',
  `id_passport` int(10) unsigned NOT NULL COMMENT 'Идентификатор паспорта, является уникальным, т.к. у пользователей не могут быть одинаковые паспорта.',
  `banned` tinyint(1) NOT NULL DEFAULT '0' COMMENT 'Переменная хранящая 1, если пользовательный забанен и 0 - если нет. По умолчанию значение устанавливается 0.',
  `visits_number` smallint(5) unsigned NOT NULL DEFAULT '0' COMMENT 'Количество посещений клиентом хостела.',
  PRIMARY KEY (`id_client`),
  UNIQUE KEY `id_passport_UNIQUE` (`id_passport`),
  UNIQUE KEY `login_UNIQUE` (`login`),
  CONSTRAINT `fk_passports_clients` FOREIGN KEY (`id_passport`) REFERENCES `passports` (`id_passport`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8 COMMENT='Регистрационные данные, статус бана, количество посещений хостела. Связана 1к1 с таблицей passports, т.к. у клиентов уникальные паспорта.';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `clients`
--

LOCK TABLES `clients` WRITE;
/*!40000 ALTER TABLE `clients` DISABLE KEYS */;
INSERT INTO `clients` VALUES (1,'ivanov_ivan','Aa1234',3,0,18),(2,'gus_vladislav','4235gdf',4,0,2),(3,'petrov_dmitriy','23gfd',5,1,0),(4,'dmitriev_anatoliy','sfgds423',6,0,1),(5,'ozdoev_pavel','jml6634',7,0,2),(6,'kirienko_svetlana','bj636',8,0,2),(7,'koroleva_margarita','nk236',9,0,1),(8,'pavlov_artem','fsd79s',10,0,1),(9,'poroshenko_petr','nk4jk3',11,0,1),(10,'dzagoeva_anastasia','b374',12,0,1),(11,'mironov_yan','b235k',14,1,1),(12,'artem','Aa1234',15,0,26),(13,'ikloo','22848',16,0,0);
/*!40000 ALTER TABLE `clients` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `discounts`
--

DROP TABLE IF EXISTS `discounts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `discounts` (
  `id_discount` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Идентификатор скидки. Автоинкрементируется по мере добавения новых скидок клиентам.',
  `id_client` int(10) unsigned NOT NULL COMMENT 'Идентификатор клиента, которому выдана скидка.',
  `discount_value` int(10) unsigned NOT NULL COMMENT 'Значени скидки.',
  `id_administrator` int(10) unsigned NOT NULL COMMENT 'Идентификатор администратора, который сделал скидку.',
  `discount_used` tinyint(1) NOT NULL DEFAULT '0' COMMENT 'Переменная, которая принимает значение по умолчанию 0 и означает что скидка не была использована клиентом, и 1 - когда клиент воспользовался скидкой.',
  PRIMARY KEY (`id_discount`),
  KEY `FK_clients_discounts_idx` (`id_client`),
  KEY `fk_administrators_discounts_idx` (`id_administrator`),
  CONSTRAINT `fk_administrators_discounts` FOREIGN KEY (`id_administrator`) REFERENCES `administrators` (`id_administrator`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_clients_discounts` FOREIGN KEY (`id_client`) REFERENCES `clients` (`id_client`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8 COMMENT='Таблица скидок для пользователей, которые назначаются администратором.';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `discounts`
--

LOCK TABLES `discounts` WRITE;
/*!40000 ALTER TABLE `discounts` DISABLE KEYS */;
INSERT INTO `discounts` VALUES (1,5,5,2,0),(2,2,15,3,0),(3,7,12,1,0),(5,6,14,2,1),(6,1,18,2,1),(13,1,20,2,1),(14,1,33,1,0),(15,1,12,1,1),(16,4,11,1,0),(18,4,12,1,0),(21,13,4,1,0),(23,10,2,1,0),(24,10,5,1,0),(34,9,39,1,0);
/*!40000 ALTER TABLE `discounts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `passports`
--

DROP TABLE IF EXISTS `passports`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `passports` (
  `id_passport` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Идентификатор паспорта. Автоинкрементируется по мере добавения новых паспортов.',
  `identification_number` int(10) unsigned NOT NULL COMMENT 'Идентификационный номер паспорта клиента.',
  `series` varchar(2) NOT NULL COMMENT 'Серия паспорта. Содержит 2 символа ( например ВМ)',
  `surname` varchar(40) NOT NULL COMMENT 'Фамилия в пасорте',
  `name` varchar(40) NOT NULL COMMENT 'Имя в паспорте',
  `patronymic` varchar(40) DEFAULT NULL COMMENT 'Отчествов паспорте. Может быть не указано, т.к. не увсех есть.',
  `birthday` date NOT NULL COMMENT 'Дата рождения в паспорте.',
  PRIMARY KEY (`id_passport`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8 COMMENT='Паспортные данные. Таблица разделена с таблицей clients для увеличения производительности за счет того, что данные из таблицы passports будут нужны реже.';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `passports`
--

LOCK TABLES `passports` WRITE;
/*!40000 ALTER TABLE `passports` DISABLE KEYS */;
INSERT INTO `passports` VALUES (1,2064723,'BM','Казаков','Артём','Викторович','1997-08-15'),(2,2743124,'BM','Шамёнок','Владислав','Леонидович','1993-07-09'),(3,2345245,'MP','Иванов','Иван','Иванович','1996-10-15'),(4,2678364,'MP','Гус','Владислав','Александрович','1996-08-31'),(5,2543543,'HB','Петров','Дмитрий','Петрович','1973-02-10'),(6,2678535,'KH','Дмитриев','Анатолий','Павлович','1968-05-28'),(7,1678636,'AB','Оздоев','Павел','Юрьевич','1988-08-14'),(8,2644745,'MC','Кириенко','Светлана','Владимировна','1990-01-24'),(9,2543535,'BM','Королёва','Маргарита','Сергеевна','1985-06-03'),(10,1867523,'KB','Павлов','Артём','Эдуардович','1994-11-13'),(11,2589454,'AB','Порошенко','Пётр','Андреевич','1964-10-10'),(12,1984535,'PP','Дзагоева','Анастасия','Григорьевна','1991-09-27'),(13,2125635,'MP','Подгайский','Сергей','Эдуардович','1996-05-12'),(14,1953534,'PP','Миронов','Ян','Александрович','1992-02-01'),(15,1234577,'FG','Азаза','Артём ','Виктрович','1987-11-10'),(16,5352342,'DD','Будевич','Кирилл','Валерьевич','1997-11-13'),(17,1234567,'FG','Аврамов','Кирилл','Юрьевич','1993-12-17');
/*!40000 ALTER TABLE `passports` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `requests`
--

DROP TABLE IF EXISTS `requests`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `requests` (
  `id_request` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Идентификатор запроса. Автоинкрементируется по мере добавения новых запросов.',
  `id_client` int(10) unsigned NOT NULL COMMENT 'Идентификатор клиента, заказавшего номер.',
  `id_administrator` int(10) unsigned DEFAULT NULL COMMENT 'Идентификатор администратора, обработавшего запрос.',
  `seats_number` tinyint(3) unsigned NOT NULL COMMENT 'Количество заказанных мест.',
  `checkin_date` date NOT NULL COMMENT 'Дата заселения в номер.',
  `days_stay` tinyint(3) unsigned NOT NULL COMMENT 'Количестов дней аренды номера.',
  `request_type` tinyint(1) NOT NULL COMMENT '0 если пользователь выбрал бронирование. 1 - полная оплата.',
  `payment` int(10) unsigned DEFAULT NULL COMMENT 'Платеж клиента.',
  `request_status` tinyint(1) DEFAULT NULL COMMENT 'Статус заказа, который 0 и означает, что заяка отказана, и 1, когда заявка одобрена администратором( при условии что заказ полностью оплачен, а не забронирован) и добалена в ордер.',
  PRIMARY KEY (`id_request`),
  KEY `fk_administrators_requests_idx` (`id_administrator`),
  KEY `fk_clients_requests_idx` (`id_client`),
  CONSTRAINT `fk_administrators_requests` FOREIGN KEY (`id_administrator`) REFERENCES `administrators` (`id_administrator`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_clients_requests` FOREIGN KEY (`id_client`) REFERENCES `clients` (`id_client`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=54 DEFAULT CHARSET=utf8 COMMENT='Заявки пользователя на аренду номера.';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `requests`
--

LOCK TABLES `requests` WRITE;
/*!40000 ALTER TABLE `requests` DISABLE KEYS */;
INSERT INTO `requests` VALUES (1,1,1,2,'2016-02-01',5,0,NULL,1),(2,2,2,3,'2016-03-05',10,1,190,1),(3,3,1,2,'2016-03-06',3,0,0,0),(4,5,2,1,'2016-05-07',6,0,NULL,1),(5,7,3,1,'2016-06-07',5,1,60,1),(6,10,1,3,'2016-07-07',8,0,NULL,1),(7,1,1,1,'2016-07-08',5,0,NULL,0),(8,6,2,5,'2016-07-12',10,1,300,1),(9,8,3,4,'2016-08-14',15,0,NULL,1),(10,9,3,3,'2016-08-16',8,0,NULL,1),(11,11,2,5,'2016-09-20',7,1,224,1),(12,1,1,5,'2016-09-21',11,0,NULL,0),(13,3,3,2,'2016-09-24',12,0,NULL,0),(14,5,3,2,'2016-09-28',10,1,150,1),(15,2,1,3,'2016-10-02',7,0,NULL,1),(16,6,2,1,'2016-10-10',8,0,NULL,1),(17,1,1,5,'2016-10-11',4,1,48,1),(18,3,3,4,'2016-11-21',2,0,NULL,0),(50,1,1,3,'2016-12-17',6,0,0,1),(51,1,1,3,'2016-12-23',4,0,0,0),(53,1,NULL,4,'2016-12-15',5,1,92,NULL);
/*!40000 ALTER TABLE `requests` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rooms`
--

DROP TABLE IF EXISTS `rooms`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rooms` (
  `id_room` int(10) unsigned NOT NULL COMMENT 'Номер комнаты хостела.',
  `seats_number` tinyint(3) unsigned NOT NULL COMMENT 'Количество мест в номере.',
  `perday_cost` int(10) unsigned NOT NULL COMMENT 'Цена аренды номера за день.',
  PRIMARY KEY (`id_room`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Комнаты хостела.';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rooms`
--

LOCK TABLES `rooms` WRITE;
/*!40000 ALTER TABLE `rooms` DISABLE KEYS */;
INSERT INTO `rooms` VALUES (101,2,15),(102,1,10),(103,4,25),(104,3,20),(105,3,20),(106,2,15),(107,3,20),(108,4,25),(109,5,30),(110,2,17),(201,3,22),(202,3,22),(203,2,17),(204,2,17),(205,1,12),(206,5,32),(207,4,27),(208,2,19),(209,3,22),(210,2,14);
/*!40000 ALTER TABLE `rooms` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sсhedule_records`
--

DROP TABLE IF EXISTS `sсhedule_records`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sсhedule_records` (
  `id_record` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Идентификатор расписания. Автоинкрементируется по мере добавения новых заказов на номер.',
  `id_room` int(10) unsigned NOT NULL COMMENT 'Идентификатор комнаты, занятой в зданный период.',
  `id_request` int(10) unsigned NOT NULL,
  `checkin_date` date NOT NULL COMMENT 'Дата заезда в номер.',
  `checkout_date` date NOT NULL COMMENT 'Дата выселения из номера.',
  `payment_duty` int(11) NOT NULL DEFAULT '0' COMMENT 'Задолженность клиента по оплате номера.',
  PRIMARY KEY (`id_record`),
  UNIQUE KEY `id_requset_UNIQUE` (`id_request`),
  KEY `fk_rooms_shedule_records_idx` (`id_room`),
  KEY `fk_requests_schedule_records_idx` (`id_request`),
  CONSTRAINT `fk_requests_schedule_records` FOREIGN KEY (`id_request`) REFERENCES `requests` (`id_request`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_rooms_sсhedule_records` FOREIGN KEY (`id_room`) REFERENCES `rooms` (`id_room`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8 COMMENT='Хранит данные о том, когда занята конкертная кмоната.';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sсhedule_records`
--

LOCK TABLES `sсhedule_records` WRITE;
/*!40000 ALTER TABLE `sсhedule_records` DISABLE KEYS */;
INSERT INTO `sсhedule_records` VALUES (1,101,1,'2016-09-01','2016-09-06',65),(2,104,2,'2016-09-05','2016-09-15',0),(3,102,4,'2016-09-07','2016-09-13',60),(4,205,5,'2016-09-07','2016-09-12',0),(5,105,6,'2016-09-07','2016-09-15',160),(6,109,8,'2016-09-12','2016-09-22',0),(7,103,9,'2016-09-14','2016-09-29',375),(8,107,10,'2016-09-16','2016-09-24',160),(9,206,11,'2016-09-20','2016-09-27',0),(10,101,14,'2016-09-28','2016-10-08',0),(11,104,15,'2016-10-02','2016-10-09',140),(12,102,16,'2016-10-10','2016-10-18',80),(13,205,17,'2016-10-11','2016-10-15',0),(16,107,50,'2016-12-17','2016-12-23',132),(19,103,53,'2016-12-15','2016-12-20',0);
/*!40000 ALTER TABLE `sсhedule_records` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-01-25 13:00:48
