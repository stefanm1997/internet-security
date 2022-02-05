-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: localhost    Database: waf
-- ------------------------------------------------------
-- Server version	5.7.21-log

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
-- Dumping data for table `artikl`
--

LOCK TABLES `artikl` WRITE;
/*!40000 ALTER TABLE `artikl` DISABLE KEYS */;
INSERT INTO `artikl` VALUES ('r100','ThinkPad','Lenovo',1500,NULL,1),('d102','Inspirion','Dell',2100,NULL,3),('z111','ZBook','HP',3000,NULL,4),('l111','HP500','Helwet',1555,NULL,12);
/*!40000 ALTER TABLE `artikl` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `korisnik`
--

LOCK TABLES `korisnik` WRITE;
/*!40000 ALTER TABLE `korisnik` DISABLE KEYS */;
INSERT INTO `korisnik` VALUES (1,'stefanm','5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5','admin'),(2,'admin','8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918','admin-artikl'),(4,'mils','a88dcac2e79563c36e4f537b75235a6e90ba3b9c87f6e7449e88b92f1b30038c','admin'),(7,'gojkos','cdb7ce74bf414d60da8439778c4027db99c84b1fdb0397511f95ddca37df31bc','kupac'),(10,'stefan','43d75f44b1bc1cb333b21b96e82dbcd1996c6b465c53abb7d9be81177a0417ea','kupac'),(14,'antes','d2f348861a29eced5f47b4a299bc85ed3ac491432b121428190302fa6df39f34','admin');
/*!40000 ALTER TABLE `korisnik` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-03-01 23:02:15