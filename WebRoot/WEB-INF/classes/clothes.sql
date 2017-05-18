/*
SQLyog Ultimate v11.24 (32 bit)
MySQL - 5.6.17 : Database - clothes
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`clothes` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `clothes`;

/*Table structure for table `admin` */

DROP TABLE IF EXISTS `admin`;

CREATE TABLE `admin` (
  `id` varchar(32) NOT NULL,
  `u_username` varchar(255) NOT NULL,
  `u_pwd` varchar(255) NOT NULL,
  `u_address` varchar(255) DEFAULT NULL,
  `loginDate` datetime DEFAULT NULL,
  `loginIp` varchar(255) DEFAULT NULL,
  `modifyDate` datetime DEFAULT NULL,
  `role` int(2) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `factory_name` varchar(255) DEFAULT NULL,
  `factory_type` varchar(255) NOT NULL DEFAULT 'default',
  `factory_tel` varchar(255) DEFAULT NULL,
  `u_avatar_url` varchar(255) DEFAULT NULL,
  `u_nickname` varchar(255) DEFAULT NULL,
  `u_email` varchar(255) DEFAULT NULL,
  `u_tel` varchar(255) DEFAULT NULL,
  `u_intro` text,
  `factory_img` text,
  `factory_peo` int(10) DEFAULT NULL,
  `factory_device` varchar(255) DEFAULT NULL,
  `factory_acc_type` bit(1) NOT NULL DEFAULT b'1',
  `maxNumber` int(10) DEFAULT NULL,
  `maxItem` int(10) DEFAULT NULL,
  `isAccept` bit(1) NOT NULL DEFAULT b'1',
  `conditionKey` text,
  `sending` text COMMENT '接单信息【id:itemid】',
  PRIMARY KEY (`id`,`u_pwd`,`factory_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `admin` */

insert  into `admin`(`id`,`u_username`,`u_pwd`,`u_address`,`loginDate`,`loginIp`,`modifyDate`,`role`,`createDate`,`factory_name`,`factory_type`,`factory_tel`,`u_avatar_url`,`u_nickname`,`u_email`,`u_tel`,`u_intro`,`factory_img`,`factory_peo`,`factory_device`,`factory_acc_type`,`maxNumber`,`maxItem`,`isAccept`,`conditionKey`,`sending`) values ('AJ88099','123456','123456',NULL,'2016-03-21 00:09:37','0:0:0:0:0:0:0:1','2016-03-21 00:09:37',2,'2016-03-17 22:59:02',NULL,'default',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'',NULL,NULL,'',NULL,NULL),('YU16179','kuaicaifeng','88888888','福建泉州','2016-03-20 23:42:35','0:0:0:0:0:0:0:1','2016-03-20 23:42:35',1,'2016-03-17 22:56:22','XX针织厂','default',NULL,'/upload/image/201603/GQ17764_small.jpg',NULL,NULL,'12213123131',NULL,NULL,NULL,NULL,'',20,4,'','{\n	\"conditions\":\"男装童装女装T恤卫衣马甲丝绸化纤尼龙棉皮草真丝粗呢麻\",\n	\"max\":20\n}','[\n	{\n		\"createDate\":1458230252136,\n		\"deal\":1,\n		\"id\":\"AF92235\"\n	},\n	{\n		\"createDate\":1458266285573,\n		\"deal\":1,\n		\"id\":\"RW65299\"\n	},\n	{\n		\"createDate\":1458266495655,\n		\"deal\":1,\n		\"id\":\"XM63824\"\n	},\n	{\n		\"createDate\":1458312846827,\n		\"deal\":1,\n		\"id\":\"JB50920\"\n	},\n	{\n		\"createDate\":1458312953907,\n		\"deal\":1,\n		\"id\":\"WV44505\"\n	}\n]');

/*Table structure for table `admin_role` */

DROP TABLE IF EXISTS `admin_role`;

CREATE TABLE `admin_role` (
  `adminSet_id` varchar(32) NOT NULL,
  `roleSet_id` varchar(32) NOT NULL,
  PRIMARY KEY (`adminSet_id`,`roleSet_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `admin_role` */

insert  into `admin_role`(`adminSet_id`,`roleSet_id`) values ('1','1');

/*Table structure for table `advice` */

DROP TABLE IF EXISTS `advice`;

CREATE TABLE `advice` (
  `id` int(6) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `creatDate` datetime DEFAULT NULL,
  `context` text NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

/*Data for the table `advice` */

insert  into `advice`(`id`,`name`,`email`,`creatDate`,`context`) values (1,'241','2412@qq.com',NULL,'sddwaf'),(2,'123','123@qq.com',NULL,'123124'),(3,'234','qwq@qq.com',NULL,'23312'),(4,'陈润发','chenrunfa@qq.com',NULL,'safafasfasf'),(5,'w','wq@qq.com',NULL,'231'),(6,'asdas','@',NULL,'s'),(7,'1231','124@',NULL,'da'),(8,'12312','12324',NULL,'123'),(9,'12','12',NULL,'12'),(10,'chenrunfa','chenrunfa@qq.com',NULL,'ceshi');

/*Table structure for table `confirmitems` */

DROP TABLE IF EXISTS `confirmitems`;

CREATE TABLE `confirmitems` (
  `id` varchar(40) NOT NULL,
  `itemid` varchar(40) NOT NULL,
  `cfprice` bigint(10) DEFAULT NULL,
  `cfDate` datetime DEFAULT NULL,
  `fid` varchar(40) NOT NULL,
  `fromId` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `confirmitems` */

insert  into `confirmitems`(`id`,`itemid`,`cfprice`,`cfDate`,`fid`,`fromId`) values ('VI22999','XM63824',10000,'2016-03-18 23:27:38','YU16179','123456');

/*Table structure for table `items` */

DROP TABLE IF EXISTS `items`;

CREATE TABLE `items` (
  `id` varchar(255) NOT NULL,
  `c_userid` varchar(40) NOT NULL,
  `c_type` varchar(20) NOT NULL,
  `c_category` varchar(20) NOT NULL,
  `c_clothe_img` text,
  `c_pattern_img` text,
  `c_describe` varchar(255) DEFAULT NULL COMMENT '其他说明',
  `c_other` varchar(255) DEFAULT NULL,
  `c_isbeoffer` varchar(20) NOT NULL COMMENT '布料类型',
  `s` int(11) DEFAULT NULL,
  `m` int(11) DEFAULT NULL,
  `l` int(11) DEFAULT NULL,
  `xl` int(11) DEFAULT NULL,
  `createDate` datetime NOT NULL,
  `htmlFilePath` varchar(255) DEFAULT NULL,
  `c_payment` bit(1) DEFAULT b'0' COMMENT '厂家报价',
  `c_state` bit(1) DEFAULT b'0' COMMENT '回复状态',
  `replyDate` datetime DEFAULT NULL COMMENT '厂商出价最后时间',
  `modifyDate` datetime DEFAULT NULL COMMENT '最后修改时间',
  `isDelete` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否被撤销',
  `shortDate` datetime DEFAULT NULL COMMENT '发出询价单的临时排序时间',
  `priceList` text COMMENT '厂方出价信息',
  `c_decide` bit(1) DEFAULT b'1' COMMENT '布料是否由工厂提供',
  `lastTime` datetime DEFAULT '2099-12-31 12:00:00' COMMENT '截止时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `items` */

insert  into `items`(`id`,`c_userid`,`c_type`,`c_category`,`c_clothe_img`,`c_pattern_img`,`c_describe`,`c_other`,`c_isbeoffer`,`s`,`m`,`l`,`xl`,`createDate`,`htmlFilePath`,`c_payment`,`c_state`,`replyDate`,`modifyDate`,`isDelete`,`shortDate`,`priceList`,`c_decide`,`lastTime`) values ('AF92235','123456','男装','T恤','[\n	{\n		\"bigProductImagePath\":\"/upload/image/201603/UB23451_big.jpg\",\n		\"id\":\"UB23451\",\n		\"smallProductImagePath\":\"/upload/image/201603/UB23451_small.jpg\",\n		\"sourceProductImagePath\":\"/upload/image/201603/UB23451.jpeg\",\n		\"thumbnailProductImagePath\":\"/upload/image/201603/UB23451_thumbnail.jpg\"\n	}\n]','[\n	{\n		\"bigProductImagePath\":\"/upload/image/201603/GI10587_big.jpg\",\n		\"id\":\"GI10587\",\n		\"smallProductImagePath\":\"/upload/image/201603/GI10587_small.jpg\",\n		\"sourceProductImagePath\":\"/upload/image/201603/GI10587.jpeg\",\n		\"thumbnailProductImagePath\":\"/upload/image/201603/GI10587_thumbnail.jpg\"\n	}\n]','ceshi',NULL,'丝绸',1,0,0,0,'2016-03-17 23:00:44','/html/product_content/201603/MV20246.html','\0','\0',NULL,NULL,'\0','2016-03-17 23:57:32',NULL,'','2016-03-23 00:00:00'),('JB50920','123456','童装','T恤',NULL,NULL,NULL,NULL,'丝绸',2,1,1,0,'2016-03-18 22:54:02','/html/product_content/201603/YI53849.html','\0','\0',NULL,NULL,'\0','2016-03-18 22:54:07',NULL,'','2016-04-22 00:00:00'),('NG24485','123456','童装','T恤',NULL,NULL,NULL,NULL,'尼龙',1,1,0,0,'2016-03-18 18:02:53','/html/product_content/201603/NQ84930.html','\0','\0',NULL,NULL,'\0',NULL,NULL,'','2099-12-31 12:00:00'),('RW65299','123456','男装','T恤','[\n	{\n		\"bigProductImagePath\":\"/upload/image/201603/QK39204_big.jpg\",\n		\"id\":\"QK39204\",\n		\"smallProductImagePath\":\"/upload/image/201603/QK39204_small.jpg\",\n		\"sourceProductImagePath\":\"/upload/image/201603/QK39204.jpeg\",\n		\"thumbnailProductImagePath\":\"/upload/image/201603/QK39204_thumbnail.jpg\"\n	}\n]','[\n	{\n		\"bigProductImagePath\":\"/upload/image/201603/PF96972_big.jpg\",\n		\"id\":\"PF96972\",\n		\"smallProductImagePath\":\"/upload/image/201603/PF96972_small.jpg\",\n		\"sourceProductImagePath\":\"/upload/image/201603/PF96972.jpeg\",\n		\"thumbnailProductImagePath\":\"/upload/image/201603/PF96972_thumbnail.jpg\"\n	}\n]',NULL,NULL,'丝绸',1,0,0,0,'2016-03-17 23:56:44','/html/product_content/201603/VH02270.html','\0','',NULL,NULL,'\0','2016-03-18 09:58:06','[\n	{\n		\"id\":\"RW65299\",\n		\"price\":\"13123\",\n		\"time\":1458315104984\n	}\n]','','2016-03-18 00:00:00'),('VX98154','123456','童装','T恤',NULL,NULL,NULL,NULL,'尼龙',1,1,0,0,'2016-03-18 18:04:17','/html/product_content/201603/DV27166.html','\0','\0',NULL,NULL,'\0',NULL,NULL,'','2099-12-31 12:00:00'),('WV44505','123456','男装','T恤',NULL,NULL,NULL,NULL,'丝绸',2,2,0,0,'2016-03-18 22:55:45','/html/product_content/201603/LJ08207.html','','',NULL,NULL,'\0','2016-03-18 22:55:54','[\n	{\n		\"id\":\"WV44505\",\n		\"price\":\"123322\",\n		\"time\":1458313008252\n	}\n]','','2016-04-22 00:00:00'),('XM63824','123456','男装','卫衣',NULL,NULL,NULL,NULL,'化纤',1,0,0,0,'2016-03-18 10:01:23','/html/product_content/201603/WP20943.html','','',NULL,NULL,'\0','2016-03-18 10:01:36','[\n	{\n		\"id\":\"XM63824\",\n		\"price\":\"10000\",\n		\"time\":1458266548484\n	}\n]','','2016-03-23 00:00:00');

/*Table structure for table `itemscategory` */

DROP TABLE IF EXISTS `itemscategory`;

CREATE TABLE `itemscategory` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(25) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

/*Data for the table `itemscategory` */

insert  into `itemscategory`(`id`,`name`) values (1,'T恤'),(2,'卫衣'),(3,'马甲');

/*Table structure for table `itemssoft` */

DROP TABLE IF EXISTS `itemssoft`;

CREATE TABLE `itemssoft` (
  `id` int(4) NOT NULL AUTO_INCREMENT,
  `name` varchar(25) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

/*Data for the table `itemssoft` */

insert  into `itemssoft`(`id`,`name`) values (1,'丝绸'),(2,'棉'),(3,'尼龙'),(4,'麻'),(5,'化纤'),(6,'皮草'),(7,'真丝'),(8,'粗呢');

/*Table structure for table `itemsstyle` */

DROP TABLE IF EXISTS `itemsstyle`;

CREATE TABLE `itemsstyle` (
  `id` int(4) NOT NULL AUTO_INCREMENT,
  `name` varchar(25) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

/*Data for the table `itemsstyle` */

insert  into `itemsstyle`(`id`,`name`) values (1,'男装'),(2,'童装'),(3,'女装');

/*Table structure for table `log` */

DROP TABLE IF EXISTS `log`;

CREATE TABLE `log` (
  `id` varchar(32) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `modifyDate` datetime DEFAULT NULL,
  `actionClassName` varchar(255) DEFAULT NULL,
  `actionMethodName` varchar(255) DEFAULT NULL,
  `info` text,
  `ip` varchar(255) DEFAULT NULL,
  `operationName` varchar(255) DEFAULT NULL,
  `operator` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `log` */

/*Table structure for table `logconfig` */

DROP TABLE IF EXISTS `logconfig`;

CREATE TABLE `logconfig` (
  `id` varchar(32) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `modifyDate` datetime DEFAULT NULL COMMENT '需要进行日志记录的Action名称',
  `actionClassName` varchar(255) DEFAULT NULL COMMENT '需要进行日志记录的方法名称',
  `actionMethodName` varchar(255) DEFAULT NULL,
  `description` text,
  `operationName` varchar(255) DEFAULT NULL COMMENT '操作名称'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `logconfig` */

/*Table structure for table `menu` */

DROP TABLE IF EXISTS `menu`;

CREATE TABLE `menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `url` varchar(255) DEFAULT NULL,
  `icon` varchar(255) DEFAULT NULL,
  `isEnabled` bit(1) NOT NULL DEFAULT b'1' COMMENT '是否启用 0=禁用(false)，1=启用(true)',
  `orderList` int(11) NOT NULL COMMENT '排序',
  `parent_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '菜单父ID',
  `createDate` datetime DEFAULT NULL COMMENT '创建日期',
  `modifyDate` datetime DEFAULT NULL COMMENT '最后修改日期',
  `role` int(11) DEFAULT '0' COMMENT '默认0：两者可见；1:工厂可见 ；2：设计师可见',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8;

/*Data for the table `menu` */

insert  into `menu`(`id`,`name`,`url`,`icon`,`isEnabled`,`orderList`,`parent_id`,`createDate`,`modifyDate`,`role`) values (1,'询价',NULL,'fa-tasks','',0,0,NULL,NULL,2),(3,'立即询价','/items','','',1,1,NULL,NULL,2),(5,'历史询价','/items/list',NULL,'',2,1,NULL,NULL,2),(7,'新的消息','','fa-envelope-o','',1,0,NULL,NULL,2),(8,'寻单','','fa-tasks','',0,0,NULL,NULL,1),(9,'新的消息','','fa-envelope-o','',2,0,NULL,NULL,1),(11,'新的订单','/items/newConfirmed',NULL,'',1,9,NULL,NULL,1),(13,'询价历史','/items/info',NULL,'',2,9,NULL,NULL,1),(15,'新的报价','/items/getAllPriced','','',1,7,NULL,NULL,2),(17,'个人中心',NULL,'fa-user','',0,0,NULL,NULL,0),(19,'个人资料','/center/user',NULL,'',1,17,NULL,NULL,0),(21,'交易记录','/center/center_trade','','',2,17,NULL,NULL,0),(25,'寻找订单','/items/getOneItem',NULL,'',1,8,NULL,NULL,1),(27,'接单管理','/center/setting','','',3,17,NULL,NULL,1),(31,'注销','',' fa-sign-out','',1,0,NULL,NULL,0),(33,'修改密码','/admin/error','','',2,31,NULL,NULL,0),(35,'退出','/login/logout',NULL,'',0,31,NULL,NULL,0),(37,'联系我们','','fa-paper-plane-o','',0,0,NULL,NULL,0),(39,'联系方式','/advice/showinfo',NULL,'',1,37,NULL,NULL,0),(41,'您的建议','/advice/advice',NULL,'',2,37,NULL,NULL,0);

/*Table structure for table `price` */

DROP TABLE IF EXISTS `price`;

CREATE TABLE `price` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `price` bigint(10) NOT NULL,
  `replyDate` datetime NOT NULL,
  `factorytel` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `itemid` varchar(255) NOT NULL,
  `factoryid` varchar(255) NOT NULL,
  `itemtel` varchar(255) DEFAULT NULL,
  `msg` varchar(255) DEFAULT NULL,
  `ownerid` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8;

/*Data for the table `price` */

insert  into `price`(`id`,`price`,`replyDate`,`factorytel`,`name`,`itemid`,`factoryid`,`itemtel`,`msg`,`ownerid`) values (34,10000,'2016-03-18 10:02:28',NULL,'XX针织厂','XM63824','YU16179',NULL,'测试','123456'),(35,123322,'2016-03-18 22:56:48',NULL,'XX针织厂','WV44505','YU16179',NULL,'12313','123456'),(36,13123,'2016-03-18 23:31:45',NULL,'XX针织厂','RW65299','YU16179',NULL,'','123456');

/*Table structure for table `role` */

DROP TABLE IF EXISTS `role`;

CREATE TABLE `role` (
  `id` varchar(32) NOT NULL,
  `createDate` datetime DEFAULT NULL,
  `modifyDate` datetime DEFAULT NULL,
  `description` text,
  `isSystem` bit(1) NOT NULL,
  `name` varchar(32) NOT NULL,
  `value` varchar(32) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `role` */

/*Table structure for table `role_resource` */

DROP TABLE IF EXISTS `role_resource`;

CREATE TABLE `role_resource` (
  `roleSet_id` varchar(32) NOT NULL,
  `resourceSet_id` varchar(32) NOT NULL,
  PRIMARY KEY (`roleSet_id`,`resourceSet_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `role_resource` */

insert  into `role_resource`(`roleSet_id`,`resourceSet_id`) values ('1','1');

/*Table structure for table `size` */

DROP TABLE IF EXISTS `size`;

CREATE TABLE `size` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `s_l` int(10) DEFAULT NULL,
  `s_xl` int(10) DEFAULT NULL,
  `s_xxl` int(10) DEFAULT NULL,
  `s_m` int(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `size` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
