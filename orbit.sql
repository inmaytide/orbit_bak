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
INSERT INTO `sys_data_dictionary` VALUES (377564822935437312,'Menu','MENU','permission.category',0,'2017-11-07 21:10:42',NULL,9999,NULL,0,0),(377564822943825920,'Button','BUTTON','permission.category',1,'2017-11-07 21:10:42',NULL,9999,NULL,0,0),(385552000668012544,'GET','get','permission.method',2,'2017-11-29 22:06:00','2017-11-29 22:06:00',9999,NULL,0,0),(385552317887418368,'POST','post','permission.method',3,'2017-11-29 22:07:16','2017-11-29 22:07:16',9999,NULL,0,0),(385552744175505408,'PUT','put','permission.method',4,'2017-11-29 22:08:57','2017-11-29 22:08:57',9999,NULL,0,0),(385553252411904000,'PATCH','patch','permission.method',5,'2017-11-29 22:10:59','2017-11-29 22:10:59',9999,9999,0,0),(385555071636738048,'DELETE','delete','permission.method',6,'2017-11-29 22:18:12','2017-11-29 22:18:12',9999,9999,0,0);
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
  `category` varchar(16) DEFAULT NULL,
  `description` varchar(256) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `creator` bigint(20) DEFAULT NULL,
  `updater` bigint(20) DEFAULT NULL,
  `version` int(11) NOT NULL DEFAULT '0',
  `sort` int(11) NOT NULL,
  `method` varchar(8) NOT NULL DEFAULT 'ALL',
  PRIMARY KEY (`id`),
  UNIQUE KEY `code` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=385187976067551233 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_permission`
--

LOCK TABLES `sys_permission` WRITE;
/*!40000 ALTER TABLE `sys_permission` DISABLE KEYS */;
INSERT INTO `sys_permission` VALUES (10000,-1,'homepage','首页',NULL,'home','MENU',NULL,'2017-09-03 14:03:03','2017-09-10 20:27:14',9999,9999,0,1,'ALL'),(10001,-1,'sys:managment','系统管理',NULL,'setting','MENU',NULL,'2017-09-03 14:05:02','2017-09-10 20:27:14',9999,9999,0,2,'ALL'),(10002,10001,'permission:list','菜单管理','permission',NULL,'MENU',NULL,'2017-09-03 14:05:25','2017-10-25 21:06:57',9999,9999,20,4,'ALL'),(10019,10001,'log:list','日志管理','home/log',NULL,'MENU',NULL,'2017-09-03 21:06:27','2017-10-25 21:06:57',9999,9999,31,14,'ALL'),(10030,10001,'role:list','角色管理','home/role',NULL,'MENU',NULL,'2017-09-10 20:29:41','2017-10-25 21:06:57',9999,9999,25,5,'ALL'),(359810505696743424,10019,'log:export','导出',NULL,NULL,'BUTTON',NULL,'2017-09-19 21:18:30',NULL,9999,NULL,0,6,'ALL'),(359812029197979648,10002,'permission:add','添加',NULL,NULL,'BUTTON',NULL,'2017-09-19 21:24:33','2017-10-20 22:06:21',9999,9999,3,7,'ALL'),(359820012694933504,10002,'permission:remove','删除',NULL,NULL,'BUTTON',NULL,'2017-09-19 21:56:16','2017-10-20 22:06:21',9999,9999,5,8,'ALL'),(371056416762499072,10002,'permission:update','编辑','',NULL,'BUTTON',NULL,'2017-10-20 22:05:44','2017-10-20 22:06:29',9999,9999,3,9,'ALL'),(371056712335101952,10030,'role:add','添加',NULL,NULL,'BUTTON',NULL,'2017-10-20 22:06:54',NULL,9999,NULL,0,11,'ALL'),(371056782681968640,10030,'role:update','编辑',NULL,NULL,'BUTTON',NULL,'2017-10-20 22:07:11','2017-10-21 20:27:07',9999,9999,1,12,'ALL'),(371056874285568000,10030,'role:delete','删除',NULL,NULL,'BUTTON',NULL,'2017-10-20 22:07:33',NULL,9999,NULL,0,13,'ALL'),(372853463685664768,10001,'user:list','用户管理','home/user',NULL,'MENU',NULL,'2017-10-25 21:06:33','2017-10-25 21:06:57',9999,9999,4,3,'ALL'),(377588206327173120,10001,'workflow','流程管理','12321312323',NULL,'MENU','娃娃辅导费','2017-11-07 22:40:44',NULL,9999,NULL,0,15,'ALL'),(385187976067551232,-1,'permission_edit','编辑111',NULL,NULL,'BUTTON',NULL,'2017-11-28 21:59:30','2017-11-28 21:59:30',9999,9999,0,16,'PUT');
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
) ENGINE=InnoDB AUTO_INCREMENT=10109 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_role_permission`
--

LOCK TABLES `sys_role_permission` WRITE;
/*!40000 ALTER TABLE `sys_role_permission` DISABLE KEYS */;
INSERT INTO `sys_role_permission` VALUES (10102,9999,10000),(10103,9999,10001),(10104,9999,372853463685664768),(10105,9999,10002),(10106,9999,10030),(10107,9999,10019),(10108,9999,359810505696743424);
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

-- Dump completed on 2017-12-04 22:27:22
