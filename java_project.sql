/*
SQLyog Ultimate v10.42 
MySQL - 5.5.5-10.4.21-MariaDB : Database - java_project
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`java_project` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci */;

USE `java_project`;

/*Table structure for table `access_token` */

DROP TABLE IF EXISTS `access_token`;

CREATE TABLE `access_token` (
  `id` int(125) NOT NULL AUTO_INCREMENT,
  `user_id` int(125) NOT NULL,
  `token` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `create` timestamp NOT NULL DEFAULT current_timestamp(),
  `expires` timestamp NULL DEFAULT NULL,
  `refresh_token_id` int(125) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=60 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

/*Data for the table `access_token` */

/*Table structure for table `approve` */

DROP TABLE IF EXISTS `approve`;

CREATE TABLE `approve` (
  `id` int(225) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(225) NOT NULL,
  `randomId` varbinary(225) NOT NULL,
  `timeExpires` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

/*Data for the table `approve` */

insert  into `approve`(`id`,`user_id`,`randomId`,`timeExpires`) values (4,52,'3dfd968e-4f36-4741-8400-2fea9faddaef','2021-12-29 18:03:27');

/*Table structure for table `refresh_token` */

DROP TABLE IF EXISTS `refresh_token`;

CREATE TABLE `refresh_token` (
  `id` int(125) NOT NULL AUTO_INCREMENT,
  `user_id` int(125) NOT NULL,
  `token` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `create` timestamp NOT NULL DEFAULT current_timestamp(),
  `expires` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=60 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

/*Data for the table `refresh_token` */

/*Table structure for table `users` */

DROP TABLE IF EXISTS `users`;

CREATE TABLE `users` (
  `id` int(190) unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(190) COLLATE utf8mb4_unicode_ci NOT NULL,
  `firstName` varchar(190) COLLATE utf8mb4_unicode_ci NOT NULL,
  `lastName` varchar(190) COLLATE utf8mb4_unicode_ci NOT NULL,
  `email` varchar(190) COLLATE utf8mb4_unicode_ci NOT NULL,
  `password` varchar(190) COLLATE utf8mb4_unicode_ci NOT NULL,
  `status` int(190) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=53 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

/*Data for the table `users` */

insert  into `users`(`id`,`username`,`firstName`,`lastName`,`email`,`password`,`status`) values (3,'luiza55','luiza','harutyunyan','javatestemail64@gmail.com','$2a$10$gA/2gd/HbJi9bH8mgnbZqeZZUXCmKetB0YwGW3u7L4CqTypM8P07m',1),(4,'luiza554','luiza','harutyunyan','javatestemail644@gmail.com','$2a$10$G0AherEt6zXR16rMchzNd.pe8jptfIAXQB8TMRZ2/NaDc6j8H0x.e',1),(5,'luiza5544','luiza','harutyunyan','javatestemail6444@gmail.com','$2a$10$nyRJb4xKF5cR0rq5ojXlxePAR3WcBrCwX.vCsKUOSD/Zo88f8FkrS',1),(6,'luiza55445','luiza','harutyunyan','javatestemail64454@gmail.com','$2a$10$QjHiTyco/Egu4Nh5eh/LAe1BpRek8jtTYk2nIDz7sprTw4f/QD6by',1),(7,'luiza554445','luiza','harutyunyan','javatestemail644454@gmail.com','$2a$10$4uIIIK2OooNuDJxiX6WZiu0EiASKivX14P.xuI51JK.Z/3ev0DzmG',1),(8,'luiza5544458','luiza','harutyunyan','javatestemail6445454@gmail.com','$2a$10$kkVcDhvl3ApkX41UXebaDehu38UYPJXwyfmm3XDicqvhp29b3Pjse',1),(9,'luiza55445458','luiza','harutyunyan','javatestemail64454554@gmail.com','$2a$10$QX7YJR7py1u2L9kccsSzLe2aoRWvXXbAjBXbeerDAnwkYGOBO36Ze',1),(10,'luiza5544545814','luiza','harutyunyan','javatestemail644454554@gmail.com','$2a$10$BUgVOHNkXG25GuRrd/hNBOkiMuhAmz1iA9O0iWQ/F2KntjONX6mPa',1),(11,'luiza5544545144814','luiza','harutyunyan','javatestemail64445144554@gmail.com','$2a$10$sIdtems0XF23n2abMQfEveNoAUUE80vSIaHd0Fiml3FaKt6CKFVue',1),(12,'luiza55445451441814','luiza','harutyunyan','javatestemail644452144554@gmail.com','$2a$10$vME/SclfR8H9dFh1Wrq14.l4hl6UTUi4LPJj00a.pUA3SElHjr436',1),(13,'luiza554454514415814','luiza','harutyunyan','javatestemail6444521445854@gmail.com','$2a$10$0isP3SXiNeDl89.gCjrfo.cFpCpDZPAI3GJnaff7juH7BZ25x3rPS',1),(52,'luiza','luiza','harutyunyan','javatestemail6@gmail.com','$2a$10$/icwhXj46882HZ05MJ0DNuqwzKbTjZjJsrXWdfInCzJhf0xuHBVFS',1);

/*!50106 set global event_scheduler = 1*/;

/* Event structure for event `registration_conforme` */

/*!50106 DROP EVENT IF EXISTS `registration_conforme`*/;

DELIMITER $$

/*!50106 CREATE DEFINER=`root`@`localhost` EVENT `registration_conforme` ON SCHEDULE EVERY 1 MINUTE STARTS '2021-12-28 18:16:21' ON COMPLETION NOT PRESERVE ENABLE DO DELETE FROM approve WHERE `timeExpires` < current_timestamp */$$
DELIMITER ;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
