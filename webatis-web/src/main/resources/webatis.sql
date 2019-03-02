/*
 Navicat Premium Data Transfer

 Source Server         : 本地
 Source Server Type    : MySQL
 Source Server Version : 50724
 Source Host           : localhost:3306
 Source Schema         : webatis

 Target Server Type    : MySQL
 Target Server Version : 50724
 File Encoding         : 65001

 Date: 02/03/2019 15:25:17
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `role_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色名称',
  `role_comment` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色说明',
  `deleted` tinyint(1) NOT NULL COMMENT '1=删除',
  `created_at` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES (1, 'admin', NULL, 0, NULL, NULL);
INSERT INTO `role` VALUES (2, 'user', NULL, 0, NULL, NULL);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '姓名',
  `password` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码，测试用明文',
  `user_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户号',
  `real_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名称',
  `deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '1=删除',
  `created_at` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'user1', '123456', NULL, NULL, 0, '2019-02-23 16:51:01', '2019-02-23 16:51:20');
INSERT INTO `user` VALUES (2, 'root', 'admin', NULL, NULL, 0, '2019-02-23 16:51:01', '2019-02-23 17:29:52');

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `role_id` int(11) NULL DEFAULT NULL COMMENT '用户角色id',
  `deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '1=删除',
  `created_at` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES (1, 1, 1, 0, NULL, NULL);
INSERT INTO `user_role` VALUES (2, 2, 1, 0, NULL, NULL);

-- ----------------------------
-- Table structure for webatis_databases
-- ----------------------------
DROP TABLE IF EXISTS `webatis_databases`;
CREATE TABLE `webatis_databases`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `define_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '自定义名称,前端展现使用',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '数据库名',
  `url` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '数据库的ip地址',
  `port` int(8) NOT NULL DEFAULT 3306 COMMENT '数据监听的端口，默认是3306',
  `type` tinyint(1) NULL DEFAULT 1 COMMENT '数据库类型，默认为1，1为mysql，2为oracle。除了mysql暂不支持其他数据库。',
  `username` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '数据库的登录用户名',
  `password` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '数据库的密码',
  `path` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '生成的包路径',
  `deleted` tinyint(1) NULL DEFAULT 0 COMMENT '是否被删除,1已删除,0未删除',
  `created_at` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '最后更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'webatis-generator项目配置数据库表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of webatis_databases
-- ----------------------------
INSERT INTO `webatis_databases` VALUES (11, 'webatis', 'webatis', '127.0.0.1', 3306, 1, 'root', 'mysql', 'tech.zg.webatis', 0, '2019-02-23 13:54:32', '2019-02-23 13:54:32');
INSERT INTO `webatis_databases` VALUES (12, 'a', 'adsfa', '111', 33, 1, 'r', 'p', 't', 1, '2019-03-02 14:38:11', '2019-03-02 14:38:19');

SET FOREIGN_KEY_CHECKS = 1;
