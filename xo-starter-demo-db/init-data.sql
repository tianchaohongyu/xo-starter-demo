/*
Navicat MySQL Data Transfer

Source Server         : cm
Source Server Version : 50635
Source Host           : 192.168.1.250:3306
Source Database       : xo

Target Server Type    : MYSQL
Target Server Version : 50635
File Encoding         : 65001

Date: 2019-03-18 18:46:03
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for biz_log
-- ----------------------------
DROP TABLE IF EXISTS `biz_log`;
CREATE TABLE `biz_log` (
  `id` char(36) NOT NULL,
  `operator` varchar(20) NOT NULL,
  `operateTime` datetime NOT NULL,
  `message` varchar(800) NOT NULL,
  `entityId` char(36) DEFAULT NULL,
  `origData` varchar(2000) DEFAULT NULL,
  `newData` varchar(2000) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of biz_log
-- ----------------------------

-- ----------------------------
-- Table structure for visit_identity
-- ----------------------------
DROP TABLE IF EXISTS `visit_identity`;
CREATE TABLE `visit_identity` (
  `id` char(36) NOT NULL,
  `name` varchar(20) NOT NULL,
  `code` varchar(20) NOT NULL,
  `type` varchar(3) NOT NULL,
  `createUserId` char(36) NOT NULL,
  `createTime` datetime NOT NULL,
  `updateUserId` char(36) NOT NULL,
  `updateTime` datetime NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UNQ_Identity_code` (`code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of visit_identity
-- ----------------------------
INSERT INTO `visit_identity` VALUES ('ADMINAID-0000-0000-0000-000000000000', '普通用户', 'default', '0', 'ADMINUID-0000-0000-0000-000000000000', '2019-02-27 15:09:38', 'ADMINUID-0000-0000-0000-000000000000', '2019-03-14 21:24:16');

-- ----------------------------
-- Table structure for visit_visitor
-- ----------------------------
DROP TABLE IF EXISTS `visit_visitor`;
CREATE TABLE `visit_visitor` (
  `id` char(36) NOT NULL,
  `identityId` char(36) NOT NULL,
  `imgUrl` varchar(255) NOT NULL,
  `nickName` varchar(20) NOT NULL,
  `mobile` varchar(20) DEFAULT NULL,
  `password` varchar(120) DEFAULT NULL,
  `safePassword` varchar(120) DEFAULT NULL,
  `salt` varchar(120) NOT NULL,
  `status` varchar(3) NOT NULL,
  `createTime` datetime NOT NULL,
  `updateTime` datetime NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UNQ_Worker_phone` (`mobile`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of visit_visitor
-- ----------------------------

-- ----------------------------
-- Table structure for work_actor
-- ----------------------------
DROP TABLE IF EXISTS `work_actor`;
CREATE TABLE `work_actor` (
  `id` char(36) NOT NULL,
  `organId` char(36) NOT NULL,
  `userId` char(36) NOT NULL,
  `roleId` char(36) NOT NULL,
  `name` varchar(60) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_Actor_organId` (`organId`),
  KEY `FK_Actor_userId` (`userId`),
  KEY `FK_Actor_roleId` (`roleId`),
  CONSTRAINT `FK_Actor_organId` FOREIGN KEY (`organId`) REFERENCES `work_organ` (`id`) ON DELETE CASCADE,
  CONSTRAINT `FK_Actor_roleId` FOREIGN KEY (`roleId`) REFERENCES `work_role` (`id`) ON DELETE CASCADE,
  CONSTRAINT `FK_Actor_userId` FOREIGN KEY (`userId`) REFERENCES `work_worker` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of work_actor
-- ----------------------------
INSERT INTO `work_actor` VALUES ('ADMINAID-0000-0000-0000-000000000000', 'ADMINOID-0000-0000-0000-000000000000', 'ADMINUID-0000-0000-0000-000000000000', 'ADMINRID-0000-0000-0000-000000000000', '系统管理员');

-- ----------------------------
-- Table structure for work_organ
-- ----------------------------
DROP TABLE IF EXISTS `work_organ`;
CREATE TABLE `work_organ` (
  `id` char(36) NOT NULL,
  `parentId` char(36) DEFAULT NULL,
  `name` varchar(60) NOT NULL,
  `status` varchar(3) NOT NULL COMMENT '0.停用 1.启用',
  `ordinal` int(11) NOT NULL,
  `createUserId` char(36) NOT NULL,
  `createTime` datetime NOT NULL,
  `updateUserId` char(36) NOT NULL,
  `updateTime` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_Organ_parentId` (`parentId`),
  CONSTRAINT `FK_Organ_parentId` FOREIGN KEY (`parentId`) REFERENCES `work_organ` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of work_organ
-- ----------------------------
INSERT INTO `work_organ` VALUES ('ADMINOID-0000-0000-0000-000000000000', null, '系统根机构', '1', '0', 'ADMINUID-0000-0000-0000-000000000000', '2019-02-27 15:09:38', 'ADMINUID-0000-0000-0000-000000000000', '2019-02-27 15:09:38');

-- ----------------------------
-- Table structure for work_role
-- ----------------------------
DROP TABLE IF EXISTS `work_role`;
CREATE TABLE `work_role` (
  `id` char(36) NOT NULL,
  `name` varchar(60) NOT NULL,
  `privilegs` varchar(2000) DEFAULT NULL,
  `createUserId` char(36) NOT NULL,
  `createTime` datetime NOT NULL,
  `updateUserId` char(36) NOT NULL,
  `updateTime` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of work_role
-- ----------------------------
INSERT INTO `work_role` VALUES ('ADMINRID-0000-0000-0000-000000000000', '系统管理员', 'organ:manage,role:manage,log:view,index:manage,cache:manage,worker:manage,visitor:manage', 'ADMINUID-0000-0000-0000-000000000000', '2019-02-27 15:09:38', 'ADMINUID-0000-0000-0000-000000000000', '2019-02-27 16:32:29');

-- ----------------------------
-- Table structure for work_worker
-- ----------------------------
DROP TABLE IF EXISTS `work_worker`;
CREATE TABLE `work_worker` (
  `id` char(36) NOT NULL,
  `name` varchar(20) NOT NULL,
  `imgUrl` varchar(255) NOT NULL,
  `username` varchar(20) NOT NULL,
  `password` varchar(120) NOT NULL,
  `status` varchar(3) NOT NULL COMMENT '0.停用 1.启用',
  `ordinal` int(11) NOT NULL,
  `defaultActorId` char(36) DEFAULT NULL,
  `createUserId` char(36) NOT NULL,
  `createTime` datetime NOT NULL,
  `updateUserId` char(36) NOT NULL,
  `updateTime` datetime NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UNQ_User_username` (`username`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of work_worker
-- ----------------------------
INSERT INTO `work_worker` VALUES ('ADMINUID-0000-0000-0000-000000000000', 'ADMIN', '/static/images/head/0.jpg', 'admin', 'j6Xdj208bRekjd8a9acwQJL5IbHiws3ncNkgO6Gh9HY=', '1', '0', 'ADMINAID-0000-0000-0000-000000000000', 'ADMINUID-0000-0000-0000-000000000000', '2019-02-27 15:09:38', 'ADMINUID-0000-0000-0000-000000000000', '2019-02-27 15:09:38');
