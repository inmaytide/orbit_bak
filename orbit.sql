-- MySQL dump 10.13  Distrib 5.7.18, for osx10.12 (x86_64)
--
-- Host: localhost    Database: db_orbit
-- ------------------------------------------------------
-- Server version	5.7.18

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
-- Table structure for table `sys_attachment`
--

DROP TABLE IF EXISTS `sys_attachment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_attachment` (
  `id` bigint(20) NOT NULL,
  `original_name` varchar(128) DEFAULT NULL,
  `storage_name` varchar(32) DEFAULT NULL,
  `extension` varchar(16) DEFAULT NULL,
  `storage_address` varchar(255) DEFAULT NULL,
  `group_id` bigint(20) DEFAULT NULL,
  `belong` bigint(20) DEFAULT NULL,
  `size` bigint(20) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `creator` bigint(20) DEFAULT NULL,
  `updater` bigint(20) DEFAULT NULL,
  `version` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_attachment`
--

LOCK TABLES `sys_attachment` WRITE;
/*!40000 ALTER TABLE `sys_attachment` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_attachment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_attachment_group`
--

DROP TABLE IF EXISTS `sys_attachment_group`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_attachment_group` (
  `id` bigint(20) NOT NULL,
  `belong` bigint(20) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `creator` bigint(20) DEFAULT NULL,
  `updater` bigint(20) DEFAULT NULL,
  `version` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_attachment_group`
--

LOCK TABLES `sys_attachment_group` WRITE;
/*!40000 ALTER TABLE `sys_attachment_group` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_attachment_group` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_data_dictionary`
--

DROP TABLE IF EXISTS `sys_data_dictionary`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_data_dictionary` (
  `id` bigint(20) NOT NULL,
  `text` varchar(64) NOT NULL,
  `code` varchar(64) NOT NULL,
  `category` varchar(128) NOT NULL,
  `sort` int(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `creator` bigint(20) DEFAULT NULL,
  `updater` bigint(20) DEFAULT NULL,
  `version` int(11) NOT NULL DEFAULT '0',
  `is_created_by_system` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_data_dictionary`
--

LOCK TABLES `sys_data_dictionary` WRITE;
/*!40000 ALTER TABLE `sys_data_dictionary` DISABLE KEYS */;
INSERT INTO `sys_data_dictionary` VALUES (377564822935437312,'Menu','MENU','permission.category',0,'2017-11-07 21:10:42',NULL,9999,NULL,0,0),(377564822943825920,'Button','BUTTON','permission.category',1,'2017-11-07 21:10:42',NULL,9999,NULL,0,0),(385552000668012544,'GET','GET','permission.method',2,'2017-11-29 22:06:00','2017-11-29 22:06:00',9999,NULL,0,0),(385552317887418368,'POST','POST','permission.method',3,'2017-11-29 22:07:16','2017-11-29 22:07:16',9999,NULL,0,0),(385552744175505408,'PUT','PUT','permission.method',4,'2017-11-29 22:08:57','2017-11-29 22:08:57',9999,NULL,0,0),(385553252411904000,'PATCH','PATCH','permission.method',5,'2017-11-29 22:10:59','2017-11-29 22:10:59',9999,9999,0,0),(385555071636738048,'DELETE','DELETE','permission.method',6,'2017-11-29 22:18:12','2017-11-29 22:18:12',9999,9999,0,0),(392059874948812800,'tachometer','TACHOMETER','common.icon',7,'2017-12-17 21:05:58','2017-12-17 21:05:58',9999,9999,0,1),(392060017261547520,'cog','COG','common.icon',8,'2017-12-17 21:06:32','2017-12-17 21:06:32',9999,9999,0,1);
/*!40000 ALTER TABLE `sys_data_dictionary` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_organization`
--

DROP TABLE IF EXISTS `sys_organization`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_organization` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(32) NOT NULL,
  `name` varchar(128) NOT NULL,
  `category` bigint(20) DEFAULT NULL,
  `parent` bigint(20) DEFAULT NULL,
  `address` varchar(512) DEFAULT NULL,
  `description` varchar(512) DEFAULT NULL,
  `is_removed` int(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `creator` bigint(20) DEFAULT NULL,
  `updater` bigint(20) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `code` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_organization`
--

LOCK TABLES `sys_organization` WRITE;
/*!40000 ALTER TABLE `sys_organization` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_organization` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_permission`
--

DROP TABLE IF EXISTS `sys_permission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_permission` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `parent` bigint(20) NOT NULL DEFAULT '-1',
  `code` varchar(32) NOT NULL,
  `name` varchar(64) NOT NULL,
  `action` varchar(256) DEFAULT NULL,
  `icon` varchar(256) DEFAULT NULL,
  `category` bigint(20) DEFAULT NULL,
  `description` varchar(256) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `creator` bigint(20) DEFAULT NULL,
  `updater` bigint(20) DEFAULT NULL,
  `version` int(11) NOT NULL DEFAULT '0',
  `sort` int(11) NOT NULL,
  `method` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `code` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=392074474314403841 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_permission`
--

LOCK TABLES `sys_permission` WRITE;
/*!40000 ALTER TABLE `sys_permission` DISABLE KEYS */;
INSERT INTO `sys_permission` VALUES (392056576007081984,-1,'dashboard','Dashboard',NULL,'392059874948812800',377564822935437312,NULL,'2017-12-17 20:52:52','2017-12-17 20:52:52',9999,9999,0,0,385552000668012544),(392062345830076416,-1,'sys-management','System Management',NULL,'392060017261547520',377564822935437312,NULL,'2017-12-17 21:15:47','2017-12-17 21:15:47',9999,9999,0,1,385552000668012544),(392062572091805696,392062345830076416,'perm-list','Permission Managment','/permission',NULL,377564822935437312,NULL,'2017-12-17 21:16:41','2017-12-17 21:16:41',9999,9999,0,2,385552000668012544),(392071215931592704,392062572091805696,'perm-insert','Add',NULL,NULL,377564822943825920,NULL,'2017-12-17 21:51:02','2017-12-17 21:51:02',9999,9999,0,3,385552317887418368),(392071714009387008,-1,'234234','234234234',NULL,NULL,377564822935437312,NULL,'2017-12-17 21:53:01','2017-12-17 21:53:01',9999,9999,0,4,385552000668012544),(392071845240770560,-1,'123123','12312312312312312323',NULL,NULL,377564822935437312,NULL,'2017-12-17 21:53:32','2017-12-17 21:53:32',9999,9999,0,5,385552000668012544);
/*!40000 ALTER TABLE `sys_permission` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_role`
--

DROP TABLE IF EXISTS `sys_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(32) NOT NULL,
  `name` varchar(64) NOT NULL,
  `description` varchar(256) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `creator` bigint(20) DEFAULT NULL,
  `updater` bigint(20) DEFAULT NULL,
  `version` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `code` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_role`
--

LOCK TABLES `sys_role` WRITE;
/*!40000 ALTER TABLE `sys_role` DISABLE KEYS */;
INSERT INTO `sys_role` VALUES (9999,'administrator','Administrator',NULL,'2017-09-18 23:09:49',NULL,9999,NULL,0);
/*!40000 ALTER TABLE `sys_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_role_permission`
--

DROP TABLE IF EXISTS `sys_role_permission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_role_permission` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `r_id` bigint(20) NOT NULL,
  `p_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_role_permission`
--

LOCK TABLES `sys_role_permission` WRITE;
/*!40000 ALTER TABLE `sys_role_permission` DISABLE KEYS */;
INSERT INTO `sys_role_permission` VALUES (1,9999,392056576007081984),(2,9999,392062345830076416),(3,9999,392062572091805696);
/*!40000 ALTER TABLE `sys_role_permission` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_user`
--

DROP TABLE IF EXISTS `sys_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(32) NOT NULL,
  `name` varchar(64) NOT NULL,
  `password` varchar(64) DEFAULT NULL,
  `status` varchar(16) DEFAULT NULL,
  `email` varchar(256) DEFAULT NULL,
  `qq` varchar(16) DEFAULT NULL,
  `wechat` varchar(16) DEFAULT NULL,
  `telephone` varchar(16) DEFAULT NULL,
  `cellphone` varchar(16) DEFAULT NULL,
  `avatar` varchar(512) DEFAULT NULL,
  `remark` varchar(512) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `creator` bigint(20) DEFAULT NULL,
  `updater` bigint(20) DEFAULT NULL,
  `version` int(11) NOT NULL DEFAULT '0',
  `brithday` date DEFAULT NULL,
  `education` varchar(512) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user`
--

LOCK TABLES `sys_user` WRITE;
/*!40000 ALTER TABLE `sys_user` DISABLE KEYS */;
INSERT INTO `sys_user` VALUES (9999,'admin','Administrator','$2a$10$8CDOiERjl1Y08dA.IbkaZuBYmrlkCEi.9sbrVaGYyeYKoQe87aYNi','NORMAL',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2017-08-22 21:43:16',NULL,9999,NULL,0,NULL,NULL);
/*!40000 ALTER TABLE `sys_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_user_organization`
--

DROP TABLE IF EXISTS `sys_user_organization`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_user_organization` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `u_id` bigint(20) DEFAULT NULL,
  `o_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user_organization`
--

LOCK TABLES `sys_user_organization` WRITE;
/*!40000 ALTER TABLE `sys_user_organization` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_user_organization` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_user_role`
--

DROP TABLE IF EXISTS `sys_user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_user_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `u_id` bigint(20) NOT NULL,
  `r_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10001 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user_role`
--

LOCK TABLES `sys_user_role` WRITE;
/*!40000 ALTER TABLE `sys_user_role` DISABLE KEYS */;
INSERT INTO `sys_user_role` VALUES (10000,9999,9999);
/*!40000 ALTER TABLE `sys_user_role` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-12-17 22:27:59
