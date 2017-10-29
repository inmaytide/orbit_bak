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
-- Table structure for table `sys_log`
--

DROP TABLE IF EXISTS `sys_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_log` (
  `id` bigint(20) NOT NULL,
  `name` varchar(255) DEFAULT NULL COMMENT '日志名称',
  `operator` bigint(20) DEFAULT NULL COMMENT '操作人',
  `class_name` varchar(128) DEFAULT NULL COMMENT '类名',
  `method_name` varchar(128) DEFAULT NULL COMMENT '方法名',
  `time` datetime DEFAULT NULL COMMENT '日志时间',
  `is_succeed` varchar(128) DEFAULT NULL COMMENT '是否成功',
  `message` varchar(1024) DEFAULT NULL,
  `ip_address` varchar(16) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统日志';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_log`
--

LOCK TABLES `sys_log` WRITE;
/*!40000 ALTER TABLE `sys_log` DISABLE KEYS */;
INSERT INTO `sys_log` VALUES (373210842751176704,'系统登录',9999,'com.inmaytide.orbit.auz.handler.AuzHandler','login','2017-10-26 20:46:39','登录成功',NULL,'127.0.0.1'),(373212344538501120,'系统登录',9999,'com.inmaytide.orbit.auz.handler.AuzHandler','login','2017-10-26 20:52:37','登录成功',NULL,'127.0.0.1'),(373213005523062784,'系统登录',9999,'com.inmaytide.orbit.auz.handler.AuzHandler','login','2017-10-26 20:55:14','登录成功',NULL,'127.0.0.1'),(373217507210825728,'系统登录',9999,'com.inmaytide.orbit.auz.handler.AuzHandler','login','2017-10-26 21:13:08','登录成功',NULL,'127.0.0.1'),(373218778575671296,'系统登录',9999,'com.inmaytide.orbit.auz.handler.AuzHandler','login','2017-10-26 21:18:11','登录成功',NULL,'127.0.0.1'),(373219253572210688,'系统登录',9999,'com.inmaytide.orbit.auz.handler.AuzHandler','login','2017-10-26 21:20:04','登录成功',NULL,'127.0.0.1'),(373219704870932480,'系统登录',9999,'com.inmaytide.orbit.auz.handler.AuzHandler','login','2017-10-26 21:21:52','登录成功',NULL,'127.0.0.1'),(373220065874677760,'系统登录',9999,'com.inmaytide.orbit.auz.handler.AuzHandler','login','2017-10-26 21:23:18','登录成功',NULL,'127.0.0.1'),(373220381844180992,'系统登录',9999,'com.inmaytide.orbit.auz.handler.AuzHandler','login','2017-10-26 21:24:33','登录成功',NULL,'127.0.0.1'),(373223156007178240,'系统登录',9999,'com.inmaytide.orbit.auz.handler.AuzHandler','login','2017-10-26 21:35:35','登录成功',NULL,'127.0.0.1'),(373223814470963200,'系统登录',9999,'com.inmaytide.orbit.auz.handler.AuzHandler','login','2017-10-26 21:38:12','登录成功',NULL,'127.0.0.1'),(373224682553479168,'系统登录',9999,'com.inmaytide.orbit.auz.handler.AuzHandler','login','2017-10-26 21:41:39','登录成功',NULL,'127.0.0.1'),(373225450299854848,'系统登录',9999,'com.inmaytide.orbit.auz.handler.AuzHandler','login','2017-10-26 21:44:42','登录成功',NULL,'127.0.0.1'),(373226304927043584,'系统登录',9999,'com.inmaytide.orbit.auz.handler.AuzHandler','login','2017-10-26 21:48:05','登录成功',NULL,'127.0.0.1'),(373226819224211456,'系统登录',9999,'com.inmaytide.orbit.auz.handler.AuzHandler','login','2017-10-26 21:50:08','登录成功',NULL,'127.0.0.1'),(373227234649051136,'系统登录',9999,'com.inmaytide.orbit.auz.handler.AuzHandler','login','2017-10-26 21:51:47','登录成功',NULL,'127.0.0.1'),(373230108007337984,'系统登录',9999,'com.inmaytide.orbit.auz.handler.AuzHandler','login','2017-10-26 22:03:12','登录成功',NULL,'127.0.0.1'),(373230434026393600,'系统登录',9999,'com.inmaytide.orbit.auz.handler.AuzHandler','login','2017-10-26 22:04:30','登录成功',NULL,'127.0.0.1'),(373233097136803840,'系统登录',9999,'com.inmaytide.orbit.auz.handler.AuzHandler','login','2017-10-26 22:15:05','登录成功',NULL,'127.0.0.1'),(373233743785234432,'系统登录',9999,'com.inmaytide.orbit.auz.handler.AuzHandler','login','2017-10-26 22:17:39','登录成功',NULL,'127.0.0.1'),(373234779195314176,'系统登录',9999,'com.inmaytide.orbit.auz.handler.AuzHandler','login','2017-10-26 22:21:46','登录成功',NULL,'127.0.0.1'),(373576393474314240,'系统登录',9999,'com.inmaytide.orbit.auz.handler.AuzHandler','login','2017-10-27 20:59:13','登录失败','java.lang.NoClassDefFoundError => javax/xml/bind/DatatypeConverter','127.0.0.1'),(373576831460315136,'系统登录',9999,'com.inmaytide.orbit.auz.handler.AuzHandler','login','2017-10-27 21:00:57','登录成功',NULL,'127.0.0.1'),(373577283912470528,'系统登录',9999,'com.inmaytide.orbit.auz.handler.AuzHandler','login','2017-10-27 21:02:45','登录成功',NULL,'127.0.0.1'),(373579250357374976,'系统登录',9999,'com.inmaytide.orbit.auz.handler.AuzHandler','login','2017-10-27 21:10:34','登录成功',NULL,'127.0.0.1'),(373581542515150848,'系统登录',9999,'com.inmaytide.orbit.auz.handler.AuzHandler','login','2017-10-27 21:19:41','登录成功',NULL,'127.0.0.1'),(373585424523857920,'系统登录',9999,'com.inmaytide.orbit.auz.handler.AuzHandler','login','2017-10-27 21:35:06','登录成功',NULL,'127.0.0.1'),(373586825996013568,'系统登录',9999,'com.inmaytide.orbit.auz.handler.AuzHandler','login','2017-10-27 21:40:40','登录成功',NULL,'127.0.0.1'),(373587448359424000,'系统登录',9999,'com.inmaytide.orbit.auz.handler.AuzHandler','login','2017-10-27 21:43:09','登录成功',NULL,'127.0.0.1');
/*!40000 ALTER TABLE `sys_log` ENABLE KEYS */;
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
  PRIMARY KEY (`id`),
  UNIQUE KEY `code` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=372853463685664769 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_permission`
--

LOCK TABLES `sys_permission` WRITE;
/*!40000 ALTER TABLE `sys_permission` DISABLE KEYS */;
INSERT INTO `sys_permission` VALUES (10000,-1,'homepage','首页',NULL,'home','MENU',NULL,'2017-09-03 14:03:03','2017-09-10 20:27:14',9999,9999,0,1),(10001,-1,'sys:managment','系统管理',NULL,'cog','MENU',NULL,'2017-09-03 14:05:02','2017-09-10 20:27:14',9999,9999,0,2),(10002,10001,'permission:list','菜单管理','home/permission',NULL,'MENU',NULL,'2017-09-03 14:05:25','2017-10-25 21:06:57',9999,9999,20,4),(10019,10001,'log:list','日志管理','home/log',NULL,'MENU',NULL,'2017-09-03 21:06:27','2017-10-25 21:06:57',9999,9999,31,14),(10030,10001,'role:list','角色管理','home/role',NULL,'MENU',NULL,'2017-09-10 20:29:41','2017-10-25 21:06:57',9999,9999,25,5),(359810505696743424,10019,'log:export','导出',NULL,NULL,'BUTTON',NULL,'2017-09-19 21:18:30',NULL,9999,NULL,0,6),(359812029197979648,10002,'permission:add','添加',NULL,NULL,'BUTTON',NULL,'2017-09-19 21:24:33','2017-10-20 22:06:21',9999,9999,3,7),(359820012694933504,10002,'permission:remove','删除',NULL,NULL,'BUTTON',NULL,'2017-09-19 21:56:16','2017-10-20 22:06:21',9999,9999,5,8),(371056416762499072,10002,'permission:update','编辑','',NULL,'BUTTON',NULL,'2017-10-20 22:05:44','2017-10-20 22:06:29',9999,9999,3,9),(371056712335101952,10030,'role:add','添加',NULL,NULL,'BUTTON',NULL,'2017-10-20 22:06:54',NULL,9999,NULL,0,11),(371056782681968640,10030,'role:update','编辑',NULL,NULL,'BUTTON',NULL,'2017-10-20 22:07:11','2017-10-21 20:27:07',9999,9999,1,12),(371056874285568000,10030,'role:delete','删除',NULL,NULL,'BUTTON',NULL,'2017-10-20 22:07:33',NULL,9999,NULL,0,13),(372853463685664768,10001,'user:list','用户管理','home/user',NULL,'MENU',NULL,'2017-10-25 21:06:33','2017-10-25 21:06:57',9999,9999,4,3);
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
) ENGINE=InnoDB AUTO_INCREMENT=372148463271546881 DEFAULT CHARSET=utf8;
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
  `password` varchar(32) NOT NULL,
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
INSERT INTO `sys_user` VALUES (9999,'admin','Administrator','431759ab506d1e4af78e7fe08b818edb','NORMAL',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2017-08-22 21:43:16',NULL,9999,NULL,0,NULL,NULL);
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

-- Dump completed on 2017-10-27 22:22:13
