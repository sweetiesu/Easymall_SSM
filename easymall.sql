/*
Navicat MySQL Data Transfer

Source Server         : mysql
Source Server Version : 80021
Source Host           : localhost:3306
Source Database       : easymall

Target Server Type    : MYSQL
Target Server Version : 80021
File Encoding         : 65001

Date: 2021-04-20 16:29:26
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(100) DEFAULT NULL,
  `password` varchar(100) DEFAULT NULL,
  `nickname` varchar(100) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'admin', '123', '炒鸡管理员', 'admin@123.com');
INSERT INTO `user` VALUES ('2', '张飞', '123', '管理员', 'fei@123.com');
INSERT INTO `user` VALUES ('3', '关羽', '123', '关二爷', 'admin@111.cn');
SET FOREIGN_KEY_CHECKS=1;
