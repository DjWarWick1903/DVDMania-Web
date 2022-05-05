-- MySQL dump 10.13  Distrib 8.0.22, for Win64 (x86_64)
--
-- Host: localhost    Database: dvdmania
-- ------------------------------------------------------
-- Server version	8.0.22

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `albume`
--

DROP TABLE IF EXISTS `albume`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `albume` (
  `id_album` int NOT NULL AUTO_INCREMENT,
  `trupa` varchar(45) NOT NULL,
  `titlu` varchar(45) NOT NULL,
  `nr_mel` int NOT NULL,
  `gen` varchar(45) NOT NULL,
  `casa_disc` varchar(45) NOT NULL,
  `an` year NOT NULL,
  PRIMARY KEY (`id_album`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `albume`
--

LOCK TABLES `albume` WRITE;
/*!40000 ALTER TABLE `albume` DISABLE KEYS */;
INSERT INTO `albume` VALUES (1,'Five Finger Death Punch','F8',16,'Rock','Better Noise Music',2020),(2,'Five Finger Death Punch','And Justice for None',13,'Rock','Prospect Park',2018),(3,'Five Finger Death Punch','A Decade Of Destruction',16,'Rock','Prospect Park',2018),(4,'Five Finger Death Punch','Got Your Six',14,'Rock','Prospect Park',2015),(5,'Stormzy','Heavy Is The Head',16,'Rap','Merky Records',2019),(6,'Stormzy','Gang Signs & Prayer',16,'Rap','Merky Records',2017),(7,'Stormzy','Dreamers Disease',7,'Rap','Merky Records',2014),(8,'Smiley','Confesiune',13,'Pop','Cat Music',2017),(9,'Smiley','Acasa',18,'Pop','Cat Music',2013);
/*!40000 ALTER TABLE `albume` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `angajati`
--

DROP TABLE IF EXISTS `angajati`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `angajati` (
  `id_angaj` int NOT NULL AUTO_INCREMENT,
  `nume` varchar(45) NOT NULL,
  `pren` varchar(45) NOT NULL,
  `adresa` varchar(45) NOT NULL,
  `oras` varchar(45) NOT NULL,
  `datan` date NOT NULL,
  `cnp` varchar(13) NOT NULL DEFAULT '9999999999999',
  `tel` varchar(45) NOT NULL,
  `email` varchar(45) DEFAULT NULL,
  `functie` varchar(45) NOT NULL,
  `salariu` int NOT NULL,
  `activ` varchar(8) NOT NULL DEFAULT 'Activ',
  `id_mag` int NOT NULL,
  PRIMARY KEY (`id_angaj`),
  UNIQUE KEY `cnp_UNIQUE` (`cnp`),
  UNIQUE KEY `email_UNIQUE` (`email`),
  KEY `id_mag_idx` (`id_mag`),
  CONSTRAINT `fk_id_mag_angajati` FOREIGN KEY (`id_mag`) REFERENCES `magazin` (`id_mag`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `angajati`
--

LOCK TABLES `angajati` WRITE;
/*!40000 ALTER TABLE `angajati` DISABLE KEYS */;
INSERT INTO `angajati` VALUES (1,'Robert','Popescu','str. Gheorghe Doja nr. 6','Galati','1999-02-19','7291966515373','0758992117','robertpopescu@gmail.com','Director',4000,'Activ',1),(2,'Ionescu','Andra','str. Brailei nr. 1','Galati','1990-10-23','1723969396851','0732502682','AndraIonescu@gmail.com','Vanzator',1800,'Activ',1),(3,'Bradu','Iulian','str. Lalelelor nr. 5','Braila','1983-05-10','1526802681679','0732402552','BraduIul@gmail.com','Manager',3500,'Activ',2),(4,'Ghetoi','Andrei','str. Victoriei nr. 1','Braila','1997-07-17','5162718929679','0737492458','Andrei.Ghetoi@gmail.com','Vanzator',1800,'Activ',2);
/*!40000 ALTER TABLE `angajati` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `clienti`
--

DROP TABLE IF EXISTS `clienti`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `clienti` (
  `id_cl` int NOT NULL AUTO_INCREMENT,
  `nume` varchar(45) NOT NULL,
  `pren` varchar(45) NOT NULL,
  `adresa` varchar(45) NOT NULL,
  `oras` varchar(45) NOT NULL,
  `datan` date NOT NULL,
  `cnp` varchar(13) NOT NULL DEFAULT '9999999999999',
  `tel` varchar(45) NOT NULL,
  `email` varchar(45) DEFAULT NULL,
  `loialitate` int NOT NULL DEFAULT '5',
  PRIMARY KEY (`id_cl`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `clienti`
--

LOCK TABLES `clienti` WRITE;
/*!40000 ALTER TABLE `clienti` DISABLE KEYS */;
INSERT INTO `clienti` VALUES (1,'Popa','George','str. Eroilor nr. 3','Galati','2000-02-15','1929549868664','0752959231','popa.george@gmail.com',6),(2,'Iarizu','Alexandru','str. Mazepa nr. 2','Galati','2002-11-11','1624396989698','0753949638',NULL,5),(3,'Shakir','Abdul','str. Lalelelor nr. 4','Braila','1998-01-01','1234567891234','0753965673',NULL,5),(4,'Balan','Cristi','str. Eroilor nr. 1','Braila','1978-05-18','1326458719259','0753345933','balan.cristi@gmail.com',1);
/*!40000 ALTER TABLE `clienti` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `conturi`
--

DROP TABLE IF EXISTS `conturi`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `conturi` (
  `id_cont` int NOT NULL AUTO_INCREMENT,
  `util` varchar(45) NOT NULL,
  `parola` varchar(45) NOT NULL,
  `data_creat` date NOT NULL,
  `id_cl` int DEFAULT NULL,
  `id_angaj` int DEFAULT NULL,
  PRIMARY KEY (`id_cont`),
  KEY `fk_id_cl_cont_idx` (`id_cl`),
  KEY `fk_id_angaj_cont_idx` (`id_angaj`),
  CONSTRAINT `fk_id_angaj_cont` FOREIGN KEY (`id_angaj`) REFERENCES `angajati` (`id_angaj`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_id_cl_cont` FOREIGN KEY (`id_cl`) REFERENCES `clienti` (`id_cl`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `conturi`
--

LOCK TABLES `conturi` WRITE;
/*!40000 ALTER TABLE `conturi` DISABLE KEYS */;
INSERT INTO `conturi` VALUES (1,'RP1','robpop20','2020-07-06',NULL,1),(2,'IA2','parolaandra','2020-07-06',NULL,2),(3,'BI3','bradgros','2020-07-06',NULL,3),(4,'GA4','iarna','2020-07-06',NULL,4),(5,'georgepopa','popa','2020-07-06',1,NULL),(6,'alexandruiar','alex','2020-07-06',2,NULL),(7,'abdul4ever','allahuakbar','2020-07-06',3,NULL),(8,'balancristi','chicabomb','2020-07-06',4,NULL);
/*!40000 ALTER TABLE `conturi` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `filme`
--

DROP TABLE IF EXISTS `filme`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `filme` (
  `id_film` int NOT NULL AUTO_INCREMENT,
  `titlu` varchar(45) NOT NULL,
  `actor_pr` varchar(45) NOT NULL,
  `director` varchar(45) NOT NULL,
  `durata` int NOT NULL,
  `gen` varchar(45) NOT NULL,
  `an` year NOT NULL,
  `audienta` int NOT NULL,
  PRIMARY KEY (`id_film`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `filme`
--

LOCK TABLES `filme` WRITE;
/*!40000 ALTER TABLE `filme` DISABLE KEYS */;
INSERT INTO `filme` VALUES (1,'The Dark Knight','Christian Bale','Christopher Nolan',152,'Actiune',2008,15),(2,'The Dark Knight Rises','Christian Bale','Christopher Nolan',163,'Actiune',2012,16),(3,'Transformers','Shia LaBeouf','Michael Bay',144,'Actiune',2007,13),(4,'Bumblebee','Hailee Steinfeld','Travis Knight',114,'Actiune',2018,13),(5,'Annabelle','Ward Horton','John R. Leonetti',99,'Groaza',2014,18),(6,'Annabelle: Creation','Anthony LaPaglia','David F. Sandberg',109,'Groaza',2017,18),(7,'The Hangover','Zach Galifianakis','Todd Phillips',100,'Comedie',2009,18),(8,'The Hangover 2','Zach Galifianakis','Todd Phillips',102,'Comedie',2011,18),(9,'The Hangover 3','Zach Galifianakis','Todd Phillips',100,'Comedie',2013,18),(13,'Zombieland','Jesse Eisenberg','Ruben Fleischer',88,'Groaza',2009,18);
/*!40000 ALTER TABLE `filme` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `imprumuturi`
--

DROP TABLE IF EXISTS `imprumuturi`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `imprumuturi` (
  `id_prod` int NOT NULL,
  `id_cl` int NOT NULL,
  `id_angaj` int NOT NULL,
  `id_mag` int NOT NULL,
  `data_imp` date NOT NULL,
  `data_ret` date DEFAULT NULL,
  `pret` int NOT NULL,
  KEY `fk_id_prod_imp_idx` (`id_prod`),
  KEY `fk_id_cl_imp_idx` (`id_cl`),
  KEY `fk_id_angaj_imp_idx` (`id_angaj`),
  KEY `fk_id_mag_imp_idx` (`id_mag`),
  CONSTRAINT `fk_id_angaj_imp` FOREIGN KEY (`id_angaj`) REFERENCES `angajati` (`id_angaj`),
  CONSTRAINT `fk_id_cl_imp` FOREIGN KEY (`id_cl`) REFERENCES `clienti` (`id_cl`),
  CONSTRAINT `fk_id_mag_imp` FOREIGN KEY (`id_mag`) REFERENCES `magazin` (`id_mag`),
  CONSTRAINT `fk_id_prod_imp` FOREIGN KEY (`id_prod`) REFERENCES `produse` (`id_prod`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `imprumuturi`
--

LOCK TABLES `imprumuturi` WRITE;
/*!40000 ALTER TABLE `imprumuturi` DISABLE KEYS */;
INSERT INTO `imprumuturi` VALUES (2,1,1,1,'2020-07-16','2020-07-16',7),(22,1,1,1,'2020-07-16','2020-07-16',12),(17,1,1,1,'2020-07-16','2020-12-02',8),(8,2,1,1,'2020-12-02',NULL,10),(21,4,1,1,'2021-01-13','2021-01-13',13);
/*!40000 ALTER TABLE `imprumuturi` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `jocuri`
--

DROP TABLE IF EXISTS `jocuri`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `jocuri` (
  `id_joc` int NOT NULL AUTO_INCREMENT,
  `titlu` varchar(45) NOT NULL,
  `an` year NOT NULL,
  `platforma` varchar(45) NOT NULL,
  `developer` varchar(45) NOT NULL,
  `publisher` varchar(45) NOT NULL,
  `gen` varchar(45) NOT NULL,
  `audienta` int NOT NULL DEFAULT '18',
  PRIMARY KEY (`id_joc`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `jocuri`
--

LOCK TABLES `jocuri` WRITE;
/*!40000 ALTER TABLE `jocuri` DISABLE KEYS */;
INSERT INTO `jocuri` VALUES (1,'God Of War',2018,'PS4','Santa Monica Studio','Sony Interactive Entertainment','Actiune',18),(2,'Uncharted: Drakes Fortune',2007,'PS3','Naughty Dog','Sony Interactive Entertainment','Actiune',18),(3,'Uncharted 2: Among Thieves',2009,'PS3','Naughty Dog','Sony Interactive Entertainment','Actiune',18),(4,'Uncharted 3: Drakes Deception',2011,'PS3','Naughty Dog','Sony Interactive Entertainment','Actiune',18),(5,'Uncharted 4: A Thiefs End',2016,'PS4','Naughty Dog','Sony Interactive Entertainment','Actiune',18),(6,'Uncharted: The Lost Legacy',2017,'PS4','Naughty Dog','Sony Interactive Entertainment','Actiune',18),(7,'Gears of War',2006,'XBOX 360','Epic Games','Microsoft Studios','Actiune',18),(8,'Gears of War 2',2008,'XBOX 360','Epic Games','Microsoft Studios','Actiune',18),(9,'Gears of War 3',2011,'XBOX 360','Epic Games','Microsoft Studios','Actiune',18),(10,'Gears of War: Judgement',2013,'XBOX 360','Epic Games','Microsoft Studios','Actiune',18),(11,'Gears of War 4',2016,'XBOX ONE','Epic Games','Microsoft Studios','Actiune',18),(12,'Divinity Original Sin 2',2017,'PC','Larian Studios','Bandai Namco Entertainment','Actiune',18),(13,'The Evil Within',2014,'PC','Tango Gameworks','Bethesda Softworks','Groaza',18),(14,'The Evil Within 2',2017,'PC','Tango Gameworks','Bethesda Softworks','Groaza',18);
/*!40000 ALTER TABLE `jocuri` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `log`
--

DROP TABLE IF EXISTS `log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `log` (
  `id` int NOT NULL AUTO_INCREMENT,
  `id_angajat` int NOT NULL,
  `action` varchar(100) NOT NULL,
  `data` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_LOG_IDANG_idx` (`id_angajat`),
  CONSTRAINT `FK_LOG_IDANG` FOREIGN KEY (`id_angajat`) REFERENCES `angajati` (`id_angaj`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `log`
--

LOCK TABLES `log` WRITE;
/*!40000 ALTER TABLE `log` DISABLE KEYS */;
INSERT INTO `log` VALUES (1,1,'Logged in.','2021-01-11 23:00:42'),(2,1,'Logged in.','2021-01-11 23:55:01'),(3,1,'Edited data for movie with id 2 in store with id 1','2021-01-11 23:56:14'),(4,1,'Logged in.','2021-01-13 14:52:55'),(5,1,'Rented game to client with id 4','2021-01-13 14:55:10'),(6,1,'Finished order with id 21 for client with id 4 started on date 2021-01-13','2021-01-13 14:55:41'),(7,1,'Logged in.','2021-01-13 15:01:04');
/*!40000 ALTER TABLE `log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `magazin`
--

DROP TABLE IF EXISTS `magazin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `magazin` (
  `id_mag` int NOT NULL AUTO_INCREMENT,
  `adresa` varchar(45) NOT NULL,
  `oras` varchar(45) NOT NULL,
  `tel` varchar(45) NOT NULL,
  PRIMARY KEY (`id_mag`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `magazin`
--

LOCK TABLES `magazin` WRITE;
/*!40000 ALTER TABLE `magazin` DISABLE KEYS */;
INSERT INTO `magazin` VALUES (1,'str. Gheorghe Doja nr. 6','Galati','0759239424'),(2,'str. Lalelelor nr. 2','Braila','0759369814'),(3,'Str. Razboiului nr. 19','Constanta','0759386200');
/*!40000 ALTER TABLE `magazin` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `muzica`
--

DROP TABLE IF EXISTS `muzica`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `muzica` (
  `id_muzica` int NOT NULL AUTO_INCREMENT,
  `nume` varchar(45) NOT NULL,
  `durata` int NOT NULL,
  `id_album` int NOT NULL,
  PRIMARY KEY (`id_muzica`),
  KEY `id_album_idx` (`id_album`),
  CONSTRAINT `id_album` FOREIGN KEY (`id_album`) REFERENCES `albume` (`id_album`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=134 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `muzica`
--

LOCK TABLES `muzica` WRITE;
/*!40000 ALTER TABLE `muzica` DISABLE KEYS */;
INSERT INTO `muzica` VALUES (1,'Can I Get a',222,9),(2,'Acasa',222,9),(3,'Hot In July',225,9),(4,'Inapoi In Viitor',212,9),(5,'Dead Man Walking',201,9),(6,'Pantofii Altcuiva',210,9),(7,'I Wish',246,9),(8,'Letter to You and Me',227,9),(9,'O Ard Trist',227,9),(10,'Criminal',202,9),(11,'Pretindeai',233,9),(12,'Hi, Ce Faci?',208,9),(13,'Stupid Man',204,9),(14,'Conversatie',224,9),(15,'Another Day',216,9),(16,'Nu Deranjati!',216,9),(17,'Wonderful Life',221,9),(18,'Nemuritori',229,9),(19,'De Unde Vii La Ora Asta?',216,8),(20,'Flori de Plastic',208,8),(21,'Indragostit',229,8),(22,'Domnu Smiley',238,8),(23,'Rara',202,8),(24,'O Alta Ea',206,8),(25,'Insomnii',216,8),(26,'Sa-Mi Fie Vara',198,8),(27,'Ce Ma Fac Cu Tine De Azi?',217,8),(28,'Pierdut Printre Femei',222,8),(29,'Vals',224,8),(30,'O Poveste',218,8),(31,'Confesiune',218,8),(32,'Intro',162,7),(33,'Forever',224,7),(34,'Not That Deep',226,7),(35,'No Way',275,7),(36,'Dreamers Disease',229,7),(37,'Storm Trooper',218,7),(38,'Jupa',233,7),(39,'First Things First',208,6),(40,'Cold',157,6),(41,'Bad Boys',247,6),(42,'Blinded By Your Grace 1',161,6),(43,'Blinded By Your Grace 2',231,6),(44,'Big For Your Boots',238,6),(45,'Velvet',340,6),(46,'Mr Skeng',197,6),(47,'Cigarettes & Cush',350,6),(48,'21 Gun Salute',146,6),(49,'Return Of The Rucksack',184,6),(50,'100 Bags',218,6),(51,'Dont Cry For Me',214,6),(52,'Crazy Titch',162,6),(53,'Shut Up',180,6),(54,'Lay Me Bare',305,6),(55,'Big Michael',146,5),(56,'Audacity',246,5),(57,'Crown',213,5),(58,'Rainfall',198,5),(59,'Rachaels Little Brother',338,5),(60,'Handsome',153,5),(61,'Do Better',249,5),(62,'Dont Forget To Breathe',141,5),(63,'One Second',241,5),(64,'Pop Boy',152,5),(65,'Own It',217,5),(66,'Wiley Flow',207,5),(67,'Bronze',145,5),(68,'Superheroes',183,5),(69,'Lessons',188,5),(70,'Vossi Bop',196,5),(71,'Trouble',192,3),(72,'Gone Away',275,3),(73,'Lift Me Up',246,3),(74,'Wash It All Away',225,3),(75,'Bad Company',262,3),(76,'Under And Over It',218,3),(77,'Wrong Side Of Heaven',271,3),(78,'House Of The Rising Sun',247,3),(79,'I Apologize',243,3),(80,'The Bleeding',269,3),(81,'Jekyll and Hyde',207,3),(82,'Remember Everything',279,3),(83,'Coming Down',241,3),(84,'My Nemesis',216,3),(85,'Battle Born',224,3),(86,'Far From Home',212,3),(87,'Got Your Six',178,4),(88,'Jekyll and Hyde',207,4),(89,'Wash It All Away',225,4),(90,'Aint My Last Dance',209,4),(91,'My Nemesis',216,4),(92,'No Sudden Movement',201,4),(93,'Question Everything',306,4),(94,'Hell To Pay',188,4),(95,'Digging My Own Grave',227,4),(96,'Meet My Maker',180,4),(97,'Boots And Blood',166,4),(98,'You-re Not My Kind',201,4),(99,'This Is My War',175,4),(100,'I Apologize',243,4),(101,'Trouble',192,2),(102,'Fake',211,2),(103,'Top Of The World',162,2),(104,'Sham Pain',210,2),(105,'Blue On Black',275,2),(106,'Fire In The Hole',177,2),(107,'I Refuse',219,2),(108,'It Doesnt Matter',226,2),(109,'When The Seasons Change',227,2),(110,'Stuck In My Ways',237,2),(111,'Rock Bottom',152,2),(112,'Gone Away',275,2),(113,'Bloody',229,2),(114,'Will The Sun Ever Rise',236,2),(115,'Bad Seed',239,2),(116,'Save Your Breath',207,2),(117,'F8',75,1),(118,'Inside Out',226,1),(119,'Full Circle',202,1),(120,'Living The Dream',215,1),(121,'A Little Bit Off',191,1),(122,'Bottom Of The Top',210,1),(123,'To Be Alone',225,1),(124,'Mother May I',234,1),(125,'Darkness Settles In',282,1),(126,'This Is War',192,1),(127,'Leave It All Behind',211,1),(128,'Scar Tissue',173,1),(129,'Brighter Side Of Grey',270,1),(130,'Making Monsters',183,1),(131,'Death Punch Therapy',188,1),(132,'Inside Out (Radio Edit)',253,1);
/*!40000 ALTER TABLE `muzica` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `produse`
--

DROP TABLE IF EXISTS `produse`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `produse` (
  `id_prod` int NOT NULL AUTO_INCREMENT,
  `id_film` int DEFAULT NULL,
  `id_joc` int DEFAULT NULL,
  `id_album` int DEFAULT NULL,
  `id_mag` int NOT NULL,
  `cant` int NOT NULL DEFAULT '0',
  `pret` int NOT NULL,
  PRIMARY KEY (`id_prod`),
  KEY `id_film_idx` (`id_film`),
  KEY `id_joc_idx` (`id_joc`),
  KEY `id_album_idx` (`id_album`),
  KEY `id_mag_idx` (`id_mag`),
  CONSTRAINT `fk_id_album_prod` FOREIGN KEY (`id_album`) REFERENCES `albume` (`id_album`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_id_film_prod` FOREIGN KEY (`id_film`) REFERENCES `filme` (`id_film`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_id_joc_prod` FOREIGN KEY (`id_joc`) REFERENCES `jocuri` (`id_joc`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_id_mag_prod` FOREIGN KEY (`id_mag`) REFERENCES `magazin` (`id_mag`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=62 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `produse`
--

LOCK TABLES `produse` WRITE;
/*!40000 ALTER TABLE `produse` DISABLE KEYS */;
INSERT INTO `produse` VALUES (2,1,NULL,NULL,2,3,7),(3,2,NULL,NULL,2,5,10),(4,2,NULL,NULL,1,10,100),(5,3,NULL,NULL,1,5,5),(6,4,NULL,NULL,1,15,2),(7,4,NULL,NULL,2,9,8),(8,5,NULL,NULL,1,10,10),(9,5,NULL,NULL,2,5,7),(10,6,NULL,NULL,2,4,2),(11,7,NULL,NULL,1,7,12),(12,8,NULL,NULL,1,6,11),(13,8,NULL,NULL,2,5,6),(14,9,NULL,NULL,1,12,7),(15,NULL,1,NULL,1,12,10),(16,NULL,1,NULL,2,15,12),(17,NULL,2,NULL,2,10,8),(18,NULL,2,NULL,1,8,9),(19,NULL,3,NULL,1,15,15),(20,NULL,3,NULL,2,5,20),(21,NULL,4,NULL,1,20,13),(22,NULL,5,NULL,1,6,12),(23,NULL,5,NULL,2,8,8),(24,NULL,6,NULL,2,7,13),(25,NULL,6,NULL,1,2,16),(26,NULL,7,NULL,1,4,7),(27,NULL,7,NULL,2,13,9),(28,NULL,8,NULL,2,12,10),(29,NULL,8,NULL,1,5,7),(30,NULL,9,NULL,1,8,13),(31,NULL,9,NULL,2,10,4),(32,NULL,10,NULL,2,12,15),(33,NULL,10,NULL,1,7,8),(34,NULL,11,NULL,1,5,10),(35,NULL,12,NULL,1,20,11),(36,NULL,12,NULL,2,22,8),(37,NULL,13,NULL,2,5,5),(38,NULL,13,NULL,1,7,6),(39,NULL,14,NULL,1,8,10),(40,NULL,NULL,1,1,5,11),(41,NULL,NULL,1,2,5,10),(42,NULL,NULL,2,2,4,11),(43,NULL,NULL,2,1,7,8),(44,NULL,NULL,3,1,6,11),(45,NULL,NULL,4,1,8,15),(46,NULL,NULL,4,2,9,9),(47,NULL,NULL,5,2,15,7),(48,NULL,NULL,5,1,8,9),(49,NULL,NULL,6,1,10,13),(50,NULL,NULL,6,2,14,12),(51,NULL,NULL,7,2,15,9),(52,NULL,NULL,7,1,13,8),(54,NULL,NULL,8,2,10,5),(55,NULL,NULL,9,2,11,17),(56,NULL,NULL,9,1,8,15),(57,13,NULL,NULL,1,5,20);
/*!40000 ALTER TABLE `produse` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-01-13 23:18:02
