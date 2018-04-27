/*
SQLyog Ultimate v11.33 (64 bit)
MySQL - 5.6.37 : Database - webatis
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`webatis` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `webatis`;

/*Table structure for table `webatis_databases` */

DROP TABLE IF EXISTS `webatis_databases`;

CREATE TABLE `webatis_databases` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `define_name` varchar(64) NOT NULL COMMENT '自定义名称,前端展现使用',
  `name` varchar(32) NOT NULL COMMENT '数据库名',
  `url` varchar(64) NOT NULL COMMENT '数据库的ip地址',
  `port` int(8) NOT NULL DEFAULT '3306' COMMENT '数据监听的端口，默认是3306',
  `type` tinyint(1) DEFAULT '1' COMMENT '数据库类型，默认为1，1为mysql，2为oracle。除了mysql暂不支持其他数据库。',
  `username` varchar(32) NOT NULL COMMENT '数据库的登录用户名',
  `password` varchar(32) NOT NULL COMMENT '数据库的密码',
  `path` varchar(128) NOT NULL COMMENT '生成的包路径',
  `deleted` tinyint(1) DEFAULT '0' COMMENT '是否被删除,1已删除,0未删除',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COMMENT='webatis-generator项目配置数据库表';

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
