/*
 Navicat Premium Data Transfer

 Source Server         : local
 Source Server Type    : MySQL
 Source Server Version : 50712
 Source Host           : localhost
 Source Database       : clothes

 Target Server Type    : MySQL
 Target Server Version : 50712
 File Encoding         : utf-8

 Date: 05/19/2017 16:54:41 PM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `admin`
-- ----------------------------
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

-- ----------------------------
--  Records of `admin`
-- ----------------------------
BEGIN;
INSERT INTO `admin` VALUES ('AJ88099', '123456', '123456', null, '2017-05-18 17:34:31', '0:0:0:0:0:0:0:1', '2017-05-18 17:34:31', '2', '2016-03-17 22:59:02', null, 'default', null, '/upload/image/201705/GX47694_small.jpg', null, null, null, null, null, null, null, b'1', null, null, b'1', null, null), ('YU16179', 'kuaicaifeng', '88888888', '福建泉州', '2017-05-18 17:28:35', '0:0:0:0:0:0:0:1', '2017-05-18 17:28:35', '1', '2016-03-17 22:56:22', 'XX针织厂', '加工', null, '/upload/image/201705/DW50417_small.jpg', null, null, '15505907667', null, '[\n	{\n		\"bigProductImagePath\":\"/upload/image/201705/GT16030_big.jpg\",\n		\"id\":\"GT16030\",\n		\"smallProductImagePath\":\"/upload/image/201705/GT16030_small.jpg\",\n		\"sourceProductImagePath\":\"/upload/image/201705/GT16030.jpeg\",\n		\"thumbnailProductImagePath\":\"/upload/image/201705/GT16030_thumbnail.jpg\"\n	},\n	{\n		\"bigProductImagePath\":\"/upload/image/201705/KX58279_big.jpg\",\n		\"id\":\"KX58279\",\n		\"smallProductImagePath\":\"/upload/image/201705/KX58279_small.jpg\",\n		\"sourceProductImagePath\":\"/upload/image/201705/KX58279.jpeg\",\n		\"thumbnailProductImagePath\":\"/upload/image/201705/KX58279_thumbnail.jpg\"\n	}\n]', null, null, b'1', '99', '99', b'1', '{\n	\"conditions\":\"男装童装女装T恤卫衣马甲丝绸化纤尼龙棉皮草真丝粗呢麻\",\n	\"max\":99\n}', '[\n	{\n		\"createDate\":1458230252136,\n		\"deal\":1,\n		\"id\":\"AF92235\"\n	},\n	{\n		\"createDate\":1458266285573,\n		\"deal\":1,\n		\"id\":\"RW65299\"\n	},\n	{\n		\"createDate\":1458266495655,\n		\"deal\":1,\n		\"id\":\"XM63824\"\n	},\n	{\n		\"createDate\":1458312846827,\n		\"deal\":1,\n		\"id\":\"JB50920\"\n	},\n	{\n		\"createDate\":1458312953907,\n		\"deal\":1,\n		\"id\":\"WV44505\"\n	},\n	{\n		\"createDate\":1495087610883,\n		\"deal\":1,\n		\"id\":\"AG41744\"\n	},\n	{\n		\"createDate\":1495087877181,\n		\"deal\":1,\n		\"id\":\"YS15437\"\n	},\n	{\n		\"createDate\":1495092418496,\n		\"deal\":1,\n		\"id\":\"IK97937\"\n	},\n	{\n		\"createDate\":1495095007448,\n		\"deal\":1,\n		\"id\":\"BA69552\"\n	}\n]');
COMMIT;

-- ----------------------------
--  Table structure for `admin_role`
-- ----------------------------
DROP TABLE IF EXISTS `admin_role`;
CREATE TABLE `admin_role` (
  `adminSet_id` varchar(32) NOT NULL,
  `roleSet_id` varchar(32) NOT NULL,
  PRIMARY KEY (`adminSet_id`,`roleSet_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `admin_role`
-- ----------------------------
BEGIN;
INSERT INTO `admin_role` VALUES ('1', '1');
COMMIT;

-- ----------------------------
--  Table structure for `advice`
-- ----------------------------
DROP TABLE IF EXISTS `advice`;
CREATE TABLE `advice` (
  `id` int(6) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `creatDate` datetime DEFAULT NULL,
  `context` text NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `advice`
-- ----------------------------
BEGIN;
INSERT INTO `advice` VALUES ('1', '241', '2412@qq.com', null, 'sddwaf'), ('2', '123', '123@qq.com', null, '123124'), ('3', '234', 'qwq@qq.com', null, '23312'), ('4', '陈润发', 'chenrunfa@qq.com', null, 'safafasfasf'), ('5', 'w', 'wq@qq.com', null, '231'), ('6', 'asdas', '@', null, 's'), ('7', '1231', '124@', null, 'da'), ('8', '12312', '12324', null, '123'), ('9', '12', '12', null, '12'), ('10', 'chenrunfa', 'chenrunfa@qq.com', null, 'ceshi');
COMMIT;

-- ----------------------------
--  Table structure for `confirmitems`
-- ----------------------------
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

-- ----------------------------
--  Records of `confirmitems`
-- ----------------------------
BEGIN;
INSERT INTO `confirmitems` VALUES ('VI22999', 'XM63824', '10000', '2016-03-18 23:27:38', 'YU16179', '123456');
COMMIT;

-- ----------------------------
--  Table structure for `items`
-- ----------------------------
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

-- ----------------------------
--  Records of `items`
-- ----------------------------
BEGIN;
INSERT INTO `items` VALUES ('AF12806', '123456', '男装', 'T恤', '[\n	{\n		\"bigProductImagePath\":\"/upload/image/201705/WA92468_big.jpg\",\n		\"id\":\"WA92468\",\n		\"smallProductImagePath\":\"/upload/image/201705/WA92468_small.jpg\",\n		\"sourceProductImagePath\":\"/upload/image/201705/WA92468.jpeg\",\n		\"thumbnailProductImagePath\":\"/upload/image/201705/WA92468_thumbnail.jpg\"\n	},\n	{\n		\"bigProductImagePath\":\"/upload/image/201705/OH26310_big.jpg\",\n		\"id\":\"OH26310\",\n		\"smallProductImagePath\":\"/upload/image/201705/OH26310_small.jpg\",\n		\"sourceProductImagePath\":\"/upload/image/201705/OH26310.jpeg\",\n		\"thumbnailProductImagePath\":\"/upload/image/201705/OH26310_thumbnail.jpg\"\n	}\n]', '[\n	{\n		\"bigProductImagePath\":\"/upload/image/201705/MT15613_big.jpg\",\n		\"id\":\"MT15613\",\n		\"smallProductImagePath\":\"/upload/image/201705/MT15613_small.jpg\",\n		\"sourceProductImagePath\":\"/upload/image/201705/MT15613.jpeg\",\n		\"thumbnailProductImagePath\":\"/upload/image/201705/MT15613_thumbnail.jpg\"\n	}\n]', '需要如图几件服装，报价请尽快给出！谢谢', null, '化纤', '1', '1', '1', '1', '2017-05-18 17:21:00', '/html/product_content/201705/KW88034.html', b'0', b'0', null, null, b'0', null, null, b'1', '2017-05-26 00:00:00'), ('AF92235', '123456', '男装', 'T恤', '[\n	{\n		\"bigProductImagePath\":\"/upload/image/201603/UB23451_big.jpg\",\n		\"id\":\"UB23451\",\n		\"smallProductImagePath\":\"/upload/image/201603/UB23451_small.jpg\",\n		\"sourceProductImagePath\":\"/upload/image/201603/UB23451.jpeg\",\n		\"thumbnailProductImagePath\":\"/upload/image/201603/UB23451_thumbnail.jpg\"\n	}\n]', '[\n	{\n		\"bigProductImagePath\":\"/upload/image/201603/GI10587_big.jpg\",\n		\"id\":\"GI10587\",\n		\"smallProductImagePath\":\"/upload/image/201603/GI10587_small.jpg\",\n		\"sourceProductImagePath\":\"/upload/image/201603/GI10587.jpeg\",\n		\"thumbnailProductImagePath\":\"/upload/image/201603/GI10587_thumbnail.jpg\"\n	}\n]', 'ceshi', null, '丝绸', '1', '0', '0', '0', '2016-03-17 23:00:44', '/html/product_content/201603/MV20246.html', b'0', b'1', null, null, b'0', '2016-03-17 23:57:32', '[\n	{\n		\"id\":\"AF92235\",\n		\"price\":\"1231231\",\n		\"time\":1495095037158\n	}\n]', b'1', '2016-03-23 00:00:00'), ('AG41744', '123456', '男装', 'T恤', '[\n	{\n		\"bigProductImagePath\":\"/upload/image/201705/EX90724_big.jpg\",\n		\"id\":\"EX90724\",\n		\"smallProductImagePath\":\"/upload/image/201705/EX90724_small.jpg\",\n		\"sourceProductImagePath\":\"/upload/image/201705/EX90724.jpeg\",\n		\"thumbnailProductImagePath\":\"/upload/image/201705/EX90724_thumbnail.jpg\"\n	}\n]', '[\n	{\n		\"bigProductImagePath\":\"/upload/image/201705/VH21423_big.jpg\",\n		\"id\":\"VH21423\",\n		\"smallProductImagePath\":\"/upload/image/201705/VH21423_small.jpg\",\n		\"sourceProductImagePath\":\"/upload/image/201705/VH21423.jpeg\",\n		\"thumbnailProductImagePath\":\"/upload/image/201705/VH21423_thumbnail.jpg\"\n	}\n]', '测试', null, '丝绸', '1', '1', '1', '1', '2017-05-18 14:05:54', '/html/product_content/201705/DT33340.html', b'0', b'0', null, null, b'1', '2017-05-18 14:06:51', null, b'1', '2017-05-25 00:00:00'), ('BA69552', '123456', '男装', '卫衣', '[\n	{\n		\"bigProductImagePath\":\"/upload/image/201705/OL17598_big.jpg\",\n		\"id\":\"OL17598\",\n		\"smallProductImagePath\":\"/upload/image/201705/OL17598_small.jpg\",\n		\"sourceProductImagePath\":\"/upload/image/201705/OL17598.jpeg\",\n		\"thumbnailProductImagePath\":\"/upload/image/201705/OL17598_thumbnail.jpg\"\n	}\n]', null, 'dddd', null, '化纤', '1', '1', '0', '0', '2017-05-18 16:10:03', '/html/product_content/201705/HK04425.html', b'0', b'0', null, null, b'0', '2017-05-18 16:10:07', null, b'1', '2017-05-26 00:00:00'), ('IK97937', '123456', '童装', '卫衣', '[\n	{\n		\"bigProductImagePath\":\"/upload/image/201705/CN76514_big.jpg\",\n		\"id\":\"CN76514\",\n		\"smallProductImagePath\":\"/upload/image/201705/CN76514_small.jpg\",\n		\"sourceProductImagePath\":\"/upload/image/201705/CN76514.jpeg\",\n		\"thumbnailProductImagePath\":\"/upload/image/201705/CN76514_thumbnail.jpg\"\n	}\n]', null, 'dd', null, '尼龙', '1', '0', '0', '0', '2017-05-18 15:26:54', '/html/product_content/201705/RQ13810.html', b'0', b'0', null, null, b'0', '2017-05-18 15:26:58', null, b'1', '2017-05-25 00:00:00'), ('IX47547', '123456', '童装', '卫衣', '[\n	{\n		\"bigProductImagePath\":\"/upload/image/201705/QK09249_big.jpg\",\n		\"id\":\"QK09249\",\n		\"smallProductImagePath\":\"/upload/image/201705/QK09249_small.jpg\",\n		\"sourceProductImagePath\":\"/upload/image/201705/QK09249.jpeg\",\n		\"thumbnailProductImagePath\":\"/upload/image/201705/QK09249_thumbnail.jpg\"\n	}\n]', '[\n	{\n		\"bigProductImagePath\":\"/upload/image/201705/OE96085_big.jpg\",\n		\"id\":\"OE96085\",\n		\"smallProductImagePath\":\"/upload/image/201705/OE96085_small.jpg\",\n		\"sourceProductImagePath\":\"/upload/image/201705/OE96085.jpeg\",\n		\"thumbnailProductImagePath\":\"/upload/image/201705/OE96085_thumbnail.jpg\"\n	}\n]', '数据库还好', null, '皮草', '0', '2', '2', '0', '2017-05-18 17:04:51', '/html/product_content/201705/GX30659.html', b'0', b'0', null, null, b'0', null, null, b'0', '2017-05-20 00:00:00'), ('JB50920', '123456', '童装', 'T恤', null, null, null, null, '丝绸', '2', '1', '1', '0', '2016-03-18 22:54:02', '/html/product_content/201603/YI53849.html', b'0', b'0', null, null, b'0', '2016-03-18 22:54:07', null, b'1', '2016-04-22 00:00:00'), ('NG24485', '123456', '童装', 'T恤', null, null, null, null, '尼龙', '1', '1', '0', '0', '2016-03-18 18:02:53', '/html/product_content/201603/NQ84930.html', b'0', b'0', null, null, b'0', null, null, b'1', '2099-12-31 12:00:00'), ('RW65299', '123456', '男装', 'T恤', '[\n	{\n		\"bigProductImagePath\":\"/upload/image/201603/QK39204_big.jpg\",\n		\"id\":\"QK39204\",\n		\"smallProductImagePath\":\"/upload/image/201603/QK39204_small.jpg\",\n		\"sourceProductImagePath\":\"/upload/image/201603/QK39204.jpeg\",\n		\"thumbnailProductImagePath\":\"/upload/image/201603/QK39204_thumbnail.jpg\"\n	}\n]', '[\n	{\n		\"bigProductImagePath\":\"/upload/image/201603/PF96972_big.jpg\",\n		\"id\":\"PF96972\",\n		\"smallProductImagePath\":\"/upload/image/201603/PF96972_small.jpg\",\n		\"sourceProductImagePath\":\"/upload/image/201603/PF96972.jpeg\",\n		\"thumbnailProductImagePath\":\"/upload/image/201603/PF96972_thumbnail.jpg\"\n	}\n]', null, null, '丝绸', '1', '0', '0', '0', '2016-03-17 23:56:44', '/html/product_content/201603/VH02270.html', b'0', b'1', null, null, b'0', '2016-03-18 09:58:06', '[\n	{\n		\"id\":\"RW65299\",\n		\"price\":\"13123\",\n		\"time\":1458315104984\n	}\n]', b'1', '2016-03-18 00:00:00'), ('SY91683', '123456', '童装', '卫衣', '[\n	{\n		\"bigProductImagePath\":\"/upload/image/201705/OX41245_big.jpg\",\n		\"id\":\"OX41245\",\n		\"smallProductImagePath\":\"/upload/image/201705/OX41245_small.jpg\",\n		\"sourceProductImagePath\":\"/upload/image/201705/OX41245.jpeg\",\n		\"thumbnailProductImagePath\":\"/upload/image/201705/OX41245_thumbnail.jpg\"\n	}\n]', '[\n	{\n		\"bigProductImagePath\":\"/upload/image/201705/CJ08149_big.jpg\",\n		\"id\":\"CJ08149\",\n		\"smallProductImagePath\":\"/upload/image/201705/CJ08149_small.jpg\",\n		\"sourceProductImagePath\":\"/upload/image/201705/CJ08149.jpeg\",\n		\"thumbnailProductImagePath\":\"/upload/image/201705/CJ08149_thumbnail.jpg\"\n	}\n]', 'dd', null, '棉', '1', '0', '1', '0', '2017-05-18 17:04:18', '/html/product_content/201705/DV32418.html', b'0', b'0', null, null, b'0', null, null, b'0', '2017-05-19 00:00:00'), ('VX98154', '123456', '童装', 'T恤', null, null, null, null, '尼龙', '1', '1', '0', '0', '2016-03-18 18:04:17', '/html/product_content/201603/DV27166.html', b'0', b'0', null, null, b'0', null, null, b'1', '2099-12-31 12:00:00'), ('WV44505', '123456', '男装', 'T恤', null, null, null, null, '丝绸', '2', '2', '0', '0', '2016-03-18 22:55:45', '/html/product_content/201603/LJ08207.html', b'1', b'1', null, null, b'1', '2016-03-18 22:55:54', '[\n	{\n		\"id\":\"WV44505\",\n		\"price\":\"123322\",\n		\"time\":1458313008252\n	}\n]', b'1', '2016-04-22 00:00:00'), ('XM63824', '123456', '男装', '卫衣', null, null, null, null, '化纤', '1', '0', '0', '0', '2016-03-18 10:01:23', '/html/product_content/201603/WP20943.html', b'1', b'1', null, null, b'0', '2016-03-18 10:01:36', '[\n	{\n		\"id\":\"XM63824\",\n		\"price\":\"10000\",\n		\"time\":1458266548484\n	}\n]', b'1', '2016-03-23 00:00:00'), ('YA66269', '123456', '男装', '卫衣', '[\n	{\n		\"bigProductImagePath\":\"/upload/image/201705/GX19333_big.jpg\",\n		\"id\":\"GX19333\",\n		\"smallProductImagePath\":\"/upload/image/201705/GX19333_small.jpg\",\n		\"sourceProductImagePath\":\"/upload/image/201705/GX19333.jpeg\",\n		\"thumbnailProductImagePath\":\"/upload/image/201705/GX19333_thumbnail.jpg\"\n	}\n]', '[\n	{\n		\"bigProductImagePath\":\"/upload/image/201705/SB84524_big.jpg\",\n		\"id\":\"SB84524\",\n		\"smallProductImagePath\":\"/upload/image/201705/SB84524_small.jpg\",\n		\"sourceProductImagePath\":\"/upload/image/201705/SB84524.jpeg\",\n		\"thumbnailProductImagePath\":\"/upload/image/201705/SB84524_thumbnail.jpg\"\n	}\n]', '大幅度发', null, '化纤', '1', '1', '2', '2', '2017-05-18 14:10:07', '/html/product_content/201705/BO39744.html', b'0', b'0', null, null, b'0', null, null, b'1', '2017-05-23 00:00:00'), ('YF46423', '123456', '男装', '卫衣', '[\n	{\n		\"bigProductImagePath\":\"/upload/image/201705/ZO71122_big.jpg\",\n		\"id\":\"ZO71122\",\n		\"smallProductImagePath\":\"/upload/image/201705/ZO71122_small.jpg\",\n		\"sourceProductImagePath\":\"/upload/image/201705/ZO71122.jpeg\",\n		\"thumbnailProductImagePath\":\"/upload/image/201705/ZO71122_thumbnail.jpg\"\n	}\n]', null, null, null, '化纤', '1', '1', '2', '2', '2017-05-18 14:09:49', '/html/product_content/201705/UW66956.html', b'0', b'0', null, null, b'0', null, null, b'1', null), ('YS15437', '123456', '男装', 'T恤', '[\n	{\n		\"bigProductImagePath\":\"/upload/image/201705/OR93140_big.jpg\",\n		\"id\":\"OR93140\",\n		\"smallProductImagePath\":\"/upload/image/201705/OR93140_small.jpg\",\n		\"sourceProductImagePath\":\"/upload/image/201705/OR93140.jpeg\",\n		\"thumbnailProductImagePath\":\"/upload/image/201705/OR93140_thumbnail.jpg\"\n	}\n]', '[\n	{\n		\"bigProductImagePath\":\"/upload/image/201705/XS84548_big.jpg\",\n		\"id\":\"XS84548\",\n		\"smallProductImagePath\":\"/upload/image/201705/XS84548_small.jpg\",\n		\"sourceProductImagePath\":\"/upload/image/201705/XS84548.jpeg\",\n		\"thumbnailProductImagePath\":\"/upload/image/201705/XS84548_thumbnail.jpg\"\n	}\n]', '测试', null, '丝绸', '3', '2', '2', '2', '2017-05-18 14:11:06', '/html/product_content/201705/QF72043.html', b'0', b'0', null, null, b'0', '2017-05-18 14:11:17', null, b'1', '2017-05-27 00:00:00');
COMMIT;

-- ----------------------------
--  Table structure for `itemscategory`
-- ----------------------------
DROP TABLE IF EXISTS `itemscategory`;
CREATE TABLE `itemscategory` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(25) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `itemscategory`
-- ----------------------------
BEGIN;
INSERT INTO `itemscategory` VALUES ('1', 'T恤'), ('2', '卫衣'), ('3', '马甲');
COMMIT;

-- ----------------------------
--  Table structure for `itemssoft`
-- ----------------------------
DROP TABLE IF EXISTS `itemssoft`;
CREATE TABLE `itemssoft` (
  `id` int(4) NOT NULL AUTO_INCREMENT,
  `name` varchar(25) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `itemssoft`
-- ----------------------------
BEGIN;
INSERT INTO `itemssoft` VALUES ('1', '丝绸'), ('2', '棉'), ('3', '尼龙'), ('4', '麻'), ('5', '化纤'), ('6', '皮草'), ('7', '真丝'), ('8', '粗呢');
COMMIT;

-- ----------------------------
--  Table structure for `itemsstyle`
-- ----------------------------
DROP TABLE IF EXISTS `itemsstyle`;
CREATE TABLE `itemsstyle` (
  `id` int(4) NOT NULL AUTO_INCREMENT,
  `name` varchar(25) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `itemsstyle`
-- ----------------------------
BEGIN;
INSERT INTO `itemsstyle` VALUES ('1', '男装'), ('2', '童装'), ('3', '女装');
COMMIT;

-- ----------------------------
--  Table structure for `log`
-- ----------------------------
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

-- ----------------------------
--  Table structure for `logconfig`
-- ----------------------------
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

-- ----------------------------
--  Table structure for `menu`
-- ----------------------------
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

-- ----------------------------
--  Records of `menu`
-- ----------------------------
BEGIN;
INSERT INTO `menu` VALUES ('1', '询价', null, 'fa-tasks', b'1', '0', '0', null, null, '2'), ('3', '立即询价', '/items', '', b'1', '1', '1', null, null, '2'), ('5', '历史询价', '/items/list', null, b'1', '2', '1', null, null, '2'), ('7', '新的消息', '', 'fa-envelope-o', b'1', '1', '0', null, null, '2'), ('8', '寻单', '', 'fa-tasks', b'1', '0', '0', null, null, '1'), ('9', '新的消息', '', 'fa-envelope-o', b'1', '2', '0', null, null, '1'), ('11', '新的订单', '/items/newConfirmed', null, b'1', '1', '9', null, null, '1'), ('13', '询价历史', '/items/info', null, b'1', '2', '9', null, null, '1'), ('15', '新的报价', '/items/getAllPriced', '', b'1', '1', '7', null, null, '2'), ('17', '个人中心', null, 'fa-user', b'1', '0', '0', null, null, '0'), ('19', '个人资料', '/center/user', null, b'1', '1', '17', null, null, '0'), ('21', '交易记录', '/center/center_trade', '', b'1', '2', '17', null, null, '0'), ('25', '寻找订单', '/items/getOneItem', null, b'1', '1', '8', null, null, '1'), ('27', '接单管理', '/center/setting', '', b'1', '3', '17', null, null, '1'), ('31', '注销', '', ' fa-sign-out', b'1', '1', '0', null, null, '0'), ('33', '修改密码', '/admin/error', '', b'1', '2', '31', null, null, '0'), ('35', '退出', '/login/logout', null, b'1', '0', '31', null, null, '0'), ('37', '联系我们', '', 'fa-paper-plane-o', b'1', '0', '0', null, null, '0'), ('39', '联系方式', '/advice/showinfo', null, b'1', '1', '37', null, null, '0'), ('41', '您的建议', '/advice/advice', null, b'1', '2', '37', null, null, '0');
COMMIT;

-- ----------------------------
--  Table structure for `price`
-- ----------------------------
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
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `price`
-- ----------------------------
BEGIN;
INSERT INTO `price` VALUES ('34', '10000', '2016-03-18 10:02:28', null, 'XX针织厂', 'XM63824', 'YU16179', null, '测试', '123456'), ('35', '123322', '2016-03-18 22:56:48', null, 'XX针织厂', 'WV44505', 'YU16179', null, '12313', '123456'), ('36', '13123', '2016-03-18 23:31:45', null, 'XX针织厂', 'RW65299', 'YU16179', null, '', '123456'), ('37', '1231231', '2017-05-18 16:10:37', null, 'XX针织厂', 'AF92235', 'YU16179', null, '123', '123456');
COMMIT;

-- ----------------------------
--  Table structure for `role`
-- ----------------------------
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

-- ----------------------------
--  Table structure for `role_resource`
-- ----------------------------
DROP TABLE IF EXISTS `role_resource`;
CREATE TABLE `role_resource` (
  `roleSet_id` varchar(32) NOT NULL,
  `resourceSet_id` varchar(32) NOT NULL,
  PRIMARY KEY (`roleSet_id`,`resourceSet_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `role_resource`
-- ----------------------------
BEGIN;
INSERT INTO `role_resource` VALUES ('1', '1');
COMMIT;

-- ----------------------------
--  Table structure for `size`
-- ----------------------------
DROP TABLE IF EXISTS `size`;
CREATE TABLE `size` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `s_l` int(10) DEFAULT NULL,
  `s_xl` int(10) DEFAULT NULL,
  `s_xxl` int(10) DEFAULT NULL,
  `s_m` int(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

SET FOREIGN_KEY_CHECKS = 1;
