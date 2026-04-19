/*
 Navicat Premium Data Transfer

 Source Server         : centos7-mysql
 Source Server Type    : MySQL
 Source Server Version : 80045
 Source Host           : 192.168.150.101:3306
 Source Schema         : libre

 Target Server Type    : MySQL
 Target Server Version : 80045
 File Encoding         : 65001

 Date: 19/04/2026 16:34:49
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for tb_author
-- ----------------------------
DROP TABLE IF EXISTS `tb_author`;
CREATE TABLE `tb_author`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `author_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '作者名',
  `is_delete` bigint NULL DEFAULT 0 COMMENT '是否删除(0未删除 [时间戳]删除)',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_name_delete`(`author_name` ASC, `is_delete` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 26 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '作者表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_author
-- ----------------------------
INSERT INTO `tb_author` VALUES (1, '周志明', 0, '2026-04-07 05:16:07', '2026-04-07 06:38:01');
INSERT INTO `tb_author` VALUES (2, 'Martin Fowler', 0, '2026-04-07 05:16:07', '2026-04-07 05:16:07');
INSERT INTO `tb_author` VALUES (3, 'Robert C. Martin', 0, '2026-04-07 05:16:07', '2026-04-07 05:16:07');
INSERT INTO `tb_author` VALUES (4, 'Joshua Bloch', 0, '2026-04-07 05:16:07', '2026-04-07 05:16:07');
INSERT INTO `tb_author` VALUES (5, 'Thomas H. Cormen', 0, '2026-04-07 05:16:07', '2026-04-07 05:16:07');
INSERT INTO `tb_author` VALUES (6, 'James Kurose', 0, '2026-04-07 05:16:07', '2026-04-07 05:16:07');
INSERT INTO `tb_author` VALUES (7, 'Eric Matthes', 0, '2026-04-07 05:16:07', '2026-04-07 05:16:07');
INSERT INTO `tb_author` VALUES (8, 'Abraham Silberschatz', 0, '2026-04-07 05:16:07', '2026-04-07 05:16:07');
INSERT INTO `tb_author` VALUES (9, 'Andrew S. Tanenbaum', 0, '2026-04-07 05:16:07', '2026-04-07 05:16:07');
INSERT INTO `tb_author` VALUES (10, '刘慈欣', 0, '2026-04-07 05:16:07', '2026-04-07 05:16:07');
INSERT INTO `tb_author` VALUES (17, 'ce', 1775549102130, '2026-04-07 07:13:57', '2026-04-07 08:05:01');
INSERT INTO `tb_author` VALUES (20, 'ce', 1775549517833, '2026-04-07 08:11:54', '2026-04-07 08:11:57');
INSERT INTO `tb_author` VALUES (21, 'ce', 0, '2026-04-07 08:11:59', '2026-04-07 08:11:59');
INSERT INTO `tb_author` VALUES (22, '123', 0, '2026-04-11 06:36:46', '2026-04-11 06:36:46');
INSERT INTO `tb_author` VALUES (23, '1234', 0, '2026-04-11 06:36:49', '2026-04-11 06:36:49');
INSERT INTO `tb_author` VALUES (24, '12345', 1775891178627, '2026-04-11 06:36:53', '2026-04-11 07:06:17');
INSERT INTO `tb_author` VALUES (25, '123456', 1775891178627, '2026-04-11 06:36:57', '2026-04-11 07:06:17');

-- ----------------------------
-- Table structure for tb_book
-- ----------------------------
DROP TABLE IF EXISTS `tb_book`;
CREATE TABLE `tb_book`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `book_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '书名',
  `author_id` bigint NOT NULL COMMENT '作者id',
  `publisher_id` bigint NOT NULL COMMENT '出版商id',
  `cover_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '图书封面',
  `isbn` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '国际标准书号',
  `introduction` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '简介',
  `language` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '中文' COMMENT '语言',
  `publish_date` date NULL DEFAULT NULL COMMENT '出版时间',
  `price` bigint NULL DEFAULT 0 COMMENT '价格(分)',
  `number` int NULL DEFAULT 0 COMMENT '总库存(最小为0)',
  `is_delete` bigint NULL DEFAULT 0 COMMENT '是否删除(0未删除 [时间戳]删除)',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_isbn_delete`(`isbn` ASC, `is_delete` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '图书表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_book
-- ----------------------------
INSERT INTO `tb_book` VALUES (1, '深入理解Java虚拟机', 1, 3, NULL, '9787111651222', NULL, '中文', '2012-03-08', 12900, 100, 0, '2026-04-15 05:16:07', '2026-04-16 08:46:50');
INSERT INTO `tb_book` VALUES (2, '重构', 2, 5, NULL, '9787115508645', NULL, '中文', '2026-04-15', 9900, 50, 0, '2026-04-07 05:16:07', '2026-04-07 13:40:44');
INSERT INTO `tb_book` VALUES (3, '代码整洁之道', 3, 2, NULL, '9787115509420', NULL, '中文', '2026-04-06', 7900, 80, 0, '2026-04-07 05:16:07', '2026-04-07 13:40:47');
INSERT INTO `tb_book` VALUES (4, 'Effective Java', 4, 1, NULL, '9787111612728', NULL, '中文', '2026-03-31', 11500, 120, 0, '2026-04-07 05:16:07', '2026-04-07 13:40:50');
INSERT INTO `tb_book` VALUES (5, '算法导论', 5, 10, NULL, '9787111407010', NULL, '中文', '2026-03-10', 12800, 30, 0, '2026-04-07 05:16:07', '2026-04-07 13:40:55');
INSERT INTO `tb_book` VALUES (6, '计算机网络', 6, 4, NULL, '9787111611080', NULL, '中文', '2026-03-10', 9900, 60, 0, '2026-04-07 05:16:07', '2026-04-07 13:41:02');
INSERT INTO `tb_book` VALUES (7, 'Python编程', 7, 7, NULL, '9787115546081', NULL, '中文', '2026-03-06', 8900, 200, 0, '2026-04-07 05:16:07', '2026-04-07 13:41:07');
INSERT INTO `tb_book` VALUES (8, '数据库系统概念', 8, 8, NULL, '9787111678229', NULL, '中文', '2026-04-02', 13900, 45, 0, '2026-04-07 05:16:07', '2026-04-07 13:41:56');
INSERT INTO `tb_book` VALUES (9, '现代操作系统', 9, 3, NULL, '9787111571490', NULL, '中文', '2026-04-06', 8500, 15, 0, '2026-04-07 05:16:07', '2026-04-07 13:41:59');
INSERT INTO `tb_book` VALUES (10, '三体', 10, 6, NULL, '9787229030933', NULL, '中文', '2026-04-04', 9300, 500, 0, '2026-04-07 05:16:07', '2026-04-07 13:42:02');
INSERT INTO `tb_book` VALUES (11, 'test123', 1, 1, '', '123', '', '中文', NULL, 0, 0, 1775566908552, '2026-04-07 13:01:40', '2026-04-07 13:01:47');
INSERT INTO `tb_book` VALUES (12, 'test', 1, 1, '', '123', '', '中文', '2026-04-13', 1050, 20, 0, '2026-04-08 06:46:43', '2026-04-08 06:46:43');

-- ----------------------------
-- Table structure for tb_lend
-- ----------------------------
DROP TABLE IF EXISTS `tb_lend`;
CREATE TABLE `tb_lend`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` bigint NOT NULL COMMENT '借阅用户id',
  `book_id` bigint NOT NULL COMMENT '借阅图书',
  `state` int NOT NULL COMMENT '借阅状态(1借阅 2归还 3逾期)',
  `renew_count` int NULL DEFAULT 0 COMMENT '续借次数(初始0)',
  `return_time` datetime NULL DEFAULT NULL COMMENT '归还日期',
  `due_time` datetime NULL DEFAULT NULL COMMENT '应还日期',
  `is_delete` bigint NULL DEFAULT 0 COMMENT '是否删除(0未删除 [时间戳]删除)',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 23 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '借阅表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_lend
-- ----------------------------
INSERT INTO `tb_lend` VALUES (1, 1, 1, 2, 0, '2026-04-18 13:58:06', '2026-03-01 00:00:00', 0, '2026-02-01 00:00:00', '2026-04-18 05:58:25');
INSERT INTO `tb_lend` VALUES (2, 2, 5, 2, 0, '2026-04-18 13:58:07', '2026-04-15 00:00:00', 0, '2026-03-16 00:00:00', '2026-04-18 05:58:25');
INSERT INTO `tb_lend` VALUES (3, 3, 1, 3, 2, '2026-04-18 13:58:08', '2026-04-24 16:37:51', 0, '2026-02-10 00:00:00', '2026-04-18 05:58:26');
INSERT INTO `tb_lend` VALUES (4, 4, 8, 2, 0, '2026-04-18 13:58:11', '2026-03-20 00:00:00', 0, '2026-02-20 00:00:00', '2026-04-18 05:58:26');
INSERT INTO `tb_lend` VALUES (5, 5, 2, 2, 0, '2026-04-18 13:58:12', '2026-04-30 00:00:00', 0, '2026-04-07 00:00:00', '2026-04-18 05:58:25');
INSERT INTO `tb_lend` VALUES (6, 6, 10, 2, 2, '2026-04-18 13:58:13', '2026-05-15 00:00:00', 0, '2026-03-15 00:00:00', '2026-04-18 05:58:25');
INSERT INTO `tb_lend` VALUES (7, 7, 4, 2, 0, '2026-04-18 13:58:14', '2026-02-15 00:00:00', 0, '2026-01-15 00:00:00', '2026-04-18 05:58:25');
INSERT INTO `tb_lend` VALUES (8, 8, 7, 3, 0, '2026-04-18 13:58:15', '2026-03-01 00:00:00', 0, '2026-02-01 00:00:00', '2026-04-18 05:58:25');
INSERT INTO `tb_lend` VALUES (9, 9, 6, 2, 0, '2026-04-18 13:58:15', '2026-05-02 00:00:00', 0, '2026-04-02 00:00:00', '2026-04-18 05:58:25');
INSERT INTO `tb_lend` VALUES (10, 10, 9, 2, 3, '2026-04-18 13:58:16', '2026-04-20 00:00:00', 0, '2026-02-20 00:00:00', '2026-04-18 05:58:25');
INSERT INTO `tb_lend` VALUES (11, 1, 1, 2, 0, '2026-04-18 13:58:17', '2026-04-16 16:55:33', 1775724992317, '2026-04-09 08:55:30', '2026-04-18 05:58:25');
INSERT INTO `tb_lend` VALUES (12, 1, 2, 2, 0, '2026-04-18 19:42:01', '2026-04-23 16:28:38', 0, '2026-04-16 08:28:36', '2026-04-18 11:41:59');
INSERT INTO `tb_lend` VALUES (13, 1, 4, 2, 0, '2026-04-18 13:58:21', '2026-04-23 16:42:05', 0, '2026-04-16 08:42:04', '2026-04-18 05:58:25');
INSERT INTO `tb_lend` VALUES (14, 1, 4, 2, 0, '2026-04-18 13:58:22', '2026-04-23 16:46:00', 0, '2026-04-16 08:45:58', '2026-04-18 05:58:25');
INSERT INTO `tb_lend` VALUES (15, 1, 4, 2, 0, '2026-04-18 13:58:22', '2026-04-23 22:16:02', 0, '2026-04-16 14:16:01', '2026-04-18 05:58:25');
INSERT INTO `tb_lend` VALUES (16, 1, 4, 2, 2, '2026-04-18 13:58:23', '2026-05-01 16:22:14', 0, '2026-04-16 14:16:04', '2026-04-18 05:58:25');
INSERT INTO `tb_lend` VALUES (17, 1, 2, 2, 0, '2026-04-18 19:42:01', '2026-04-25 13:43:13', 0, '2026-04-18 05:43:11', '2026-04-18 11:41:59');
INSERT INTO `tb_lend` VALUES (18, 1, 2, 2, 3, '2026-04-18 19:42:01', '2026-05-16 13:58:37', 0, '2026-04-18 05:58:35', '2026-04-18 11:41:59');
INSERT INTO `tb_lend` VALUES (19, 1, 1, 2, 1, '2026-04-18 19:33:00', '2026-05-02 16:36:48', 0, '2026-04-18 08:36:46', '2026-04-18 11:33:00');
INSERT INTO `tb_lend` VALUES (20, 1, 1, 2, 0, '2026-04-18 19:41:52', '2026-04-25 19:33:12', 0, '2026-04-18 11:33:10', '2026-04-18 11:41:53');
INSERT INTO `tb_lend` VALUES (21, 1, 2, 2, 0, '2026-04-18 19:42:01', '2026-04-25 19:40:59', 0, '2026-04-18 11:40:57', '2026-04-18 11:41:59');
INSERT INTO `tb_lend` VALUES (22, 1, 2, 1, 0, NULL, '2026-04-25 19:42:05', 0, '2026-04-18 11:42:03', '2026-04-18 11:42:03');

-- ----------------------------
-- Table structure for tb_publisher
-- ----------------------------
DROP TABLE IF EXISTS `tb_publisher`;
CREATE TABLE `tb_publisher`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `publisher_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '出版商名',
  `is_delete` bigint NULL DEFAULT 0 COMMENT '是否删除(0未删除 [时间戳]删除)',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_name_delete`(`publisher_name` ASC, `is_delete` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '出版商表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_publisher
-- ----------------------------
INSERT INTO `tb_publisher` VALUES (1, '电子工业出版社', 0, '2026-04-07 05:16:07', '2026-04-07 08:32:04');
INSERT INTO `tb_publisher` VALUES (2, '人民邮电出版社', 0, '2026-04-07 05:16:07', '2026-04-07 05:16:07');
INSERT INTO `tb_publisher` VALUES (3, '机械工业出版社', 0, '2026-04-07 05:16:07', '2026-04-07 05:16:07');
INSERT INTO `tb_publisher` VALUES (4, '清华大学出版社', 0, '2026-04-07 05:16:07', '2026-04-07 05:16:07');
INSERT INTO `tb_publisher` VALUES (5, '图灵程序设计丛书', 0, '2026-04-07 05:16:07', '2026-04-07 05:16:07');
INSERT INTO `tb_publisher` VALUES (6, '四川文艺出版社', 0, '2026-04-07 05:16:07', '2026-04-07 05:16:07');
INSERT INTO `tb_publisher` VALUES (7, '博文视点', 0, '2026-04-07 05:16:07', '2026-04-07 05:16:07');
INSERT INTO `tb_publisher` VALUES (8, '高等教育出版社', 0, '2026-04-07 05:16:07', '2026-04-07 05:16:07');
INSERT INTO `tb_publisher` VALUES (9, '中信出版社', 0, '2026-04-07 05:16:07', '2026-04-07 05:16:07');
INSERT INTO `tb_publisher` VALUES (10, '北京大学出版社', 0, '2026-04-07 05:16:07', '2026-04-07 05:16:07');
INSERT INTO `tb_publisher` VALUES (11, 'ce', 1775550732441, '2026-04-07 08:32:08', '2026-04-07 08:32:11');
INSERT INTO `tb_publisher` VALUES (12, 'ce', 1775550740302, '2026-04-07 08:32:14', '2026-04-07 08:32:19');
INSERT INTO `tb_publisher` VALUES (13, 'ssdsdsdd', 1775631673756, '2026-04-08 07:01:10', '2026-04-08 07:01:12');

-- ----------------------------
-- Table structure for tb_role
-- ----------------------------
DROP TABLE IF EXISTS `tb_role`;
CREATE TABLE `tb_role`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `role_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '角色名',
  `is_delete` bigint NULL DEFAULT 0 COMMENT '是否删除(0未删除 [时间戳]删除)',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_role_name_delete`(`role_name` ASC, `is_delete` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_role
-- ----------------------------
INSERT INTO `tb_role` VALUES (1, '超级管理员', 0, '2026-04-07 05:16:07', '2026-04-07 05:16:07');
INSERT INTO `tb_role` VALUES (2, '管理员', 0, '2026-04-07 05:16:07', '2026-04-07 05:16:07');
INSERT INTO `tb_role` VALUES (3, '读者', 0, '2026-04-07 05:16:07', '2026-04-07 05:16:07');
INSERT INTO `tb_role` VALUES (4, 'test', 1775649947070, '2026-04-08 12:05:44', '2026-04-08 12:05:45');
INSERT INTO `tb_role` VALUES (6, 'test', 1775650154329, '2026-04-08 12:08:46', '2026-04-08 12:09:13');
INSERT INTO `tb_role` VALUES (7, 'test123', 1775650153434, '2026-04-08 12:09:07', '2026-04-08 12:09:12');

-- ----------------------------
-- Table structure for tb_user
-- ----------------------------
DROP TABLE IF EXISTS `tb_user`;
CREATE TABLE `tb_user`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '密码、加密存储',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '姓名',
  `is_delete` bigint NULL DEFAULT 0 COMMENT '是否删除(0未删除 [时间戳]删除)',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_username_delete`(`username` ASC, `is_delete` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_user
-- ----------------------------
INSERT INTO `tb_user` VALUES (1, 'admin', '$2a$10$5X.cwH0efYcx4BzF7AZZs.SCsX27crpqFo4Zj5Xr58IblhoY4iIJm', '系统管理员', 0, '2026-04-07 05:16:07', '2026-04-19 06:02:01');
INSERT INTO `tb_user` VALUES (2, 'zhangsan', '$2a$10$D2I9qvShfEaIZy8LLU2/hegSfnOfHFHmLxXbBdNnEWCpuBFqPYKKO', '张三', 0, '2026-04-07 05:16:07', '2026-04-19 06:02:01');
INSERT INTO `tb_user` VALUES (3, 'lisi', '$2a$10$F2zYt5E.FcpDlaza5sJBk.ovT6rP6BIb7BUzqKb0U4L2YJT.W4H6a', '李四', 0, '2026-04-07 05:16:07', '2026-04-19 06:02:01');
INSERT INTO `tb_user` VALUES (4, 'wangwu', '$2a$10$meb2DaXlYC1UYzxXCw7afebfbdwomuuUt0efGxkCJkKU.2iXVNOx2', '王五', 0, '2026-04-07 05:16:07', '2026-04-19 06:02:01');
INSERT INTO `tb_user` VALUES (5, 'zhaoliu', '$2a$10$jB9IkTPMmRpPh5pV7f7Fs.aGJt6PrhDP3ScrpLQaQV9821afYFSjO', '赵六', 0, '2026-04-07 05:16:07', '2026-04-19 06:02:01');
INSERT INTO `tb_user` VALUES (6, 'sunqi', '$2a$10$66krNDfOsnidOVdsAhAdC.f0aCBPPlqTuvYawZS733Usp0QjJsfGS', '孙七', 0, '2026-04-07 05:16:07', '2026-04-19 06:02:01');
INSERT INTO `tb_user` VALUES (7, 'zhouba', '$2a$10$PyjLHgXxw/OihmrsvWyrce3dyjyqWCar8XjMoE1ACQdCERdHncH42', '周八', 0, '2026-04-07 05:16:07', '2026-04-19 06:02:01');
INSERT INTO `tb_user` VALUES (8, 'wujiu', '$2a$10$AxXv/ecMWNjkYj4renrHU.WtYid5epl9USlSKF/I9EuebhK8GEkIK', '吴九', 0, '2026-04-07 05:16:07', '2026-04-19 06:02:01');
INSERT INTO `tb_user` VALUES (9, 'zhengshi', '$2a$10$TIXi7NkKKUBK0ze9M1KCGO7qIFJz18NSKMvhJt9T1Fw1UbdTiiRRq', '郑十', 0, '2026-04-07 05:16:07', '2026-04-19 06:02:01');
INSERT INTO `tb_user` VALUES (10, 'alice', '$2a$10$no5mJ4.R55jTyshSWCa7fOalUsl2cOGj7pp8zAmXRuQeEJSWrN2yW', '爱丽丝', 0, '2026-04-07 05:16:07', '2026-04-19 06:02:01');
INSERT INTO `tb_user` VALUES (11, 'test', '$2a$10$iuTJNkwYjJ/eNc0t6OlsyeesLgCO.GIYZKgac0ABkhwuxu1L06JU.', 'test', 0, '2026-04-08 11:39:56', '2026-04-19 06:02:01');
INSERT INTO `tb_user` VALUES (12, '测试', '$2a$10$VZ6.9W7p/YsPP8QbbK3.lukspZVoW4Tcle8YvAFH63wdNcWj.RjxC', 'test', 1775648697308, '2026-04-08 11:44:51', '2026-04-19 06:06:47');
INSERT INTO `tb_user` VALUES (13, 'test123', '$2a$10$boLFDaRdBJMVxOF9T5KGteZQE.VL/vEx7qvO1p.V3xli1UoqUyRTW', 'test123', 0, '2026-04-12 12:11:49', '2026-04-19 06:02:01');
INSERT INTO `tb_user` VALUES (14, 'test1', '$2a$10$8Bad89APIPhvam1kLmfyiuBonEVVluFXDu2TdnzpadpNOeTuuixZq', 'test1', 0, '2026-04-14 12:04:27', '2026-04-19 06:02:01');

-- ----------------------------
-- Table structure for tb_user_role
-- ----------------------------
DROP TABLE IF EXISTS `tb_user_role`;
CREATE TABLE `tb_user_role`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` bigint NOT NULL COMMENT '用户id',
  `role_id` bigint NOT NULL COMMENT '角色id',
  `is_delete` bigint NULL DEFAULT 0 COMMENT '是否删除(0未删除 [时间戳]删除)',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_user_role_delete`(`user_id` ASC, `role_id` ASC, `is_delete` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_user_role
-- ----------------------------
INSERT INTO `tb_user_role` VALUES (1, 1, 1, 0, '2026-04-07 05:16:07');
INSERT INTO `tb_user_role` VALUES (2, 2, 2, 0, '2026-04-07 05:16:07');
INSERT INTO `tb_user_role` VALUES (3, 3, 2, 0, '2026-04-07 05:16:07');
INSERT INTO `tb_user_role` VALUES (4, 4, 3, 0, '2026-04-07 05:16:07');
INSERT INTO `tb_user_role` VALUES (5, 5, 3, 0, '2026-04-07 05:16:07');
INSERT INTO `tb_user_role` VALUES (6, 6, 3, 0, '2026-04-07 05:16:07');
INSERT INTO `tb_user_role` VALUES (7, 7, 3, 0, '2026-04-07 05:16:07');
INSERT INTO `tb_user_role` VALUES (8, 8, 3, 0, '2026-04-07 05:16:07');
INSERT INTO `tb_user_role` VALUES (9, 9, 3, 0, '2026-04-07 05:16:07');
INSERT INTO `tb_user_role` VALUES (10, 10, 3, 0, '2026-04-07 05:16:07');
INSERT INTO `tb_user_role` VALUES (11, 11, 3, 1775652974828, '2026-04-08 12:56:00');
INSERT INTO `tb_user_role` VALUES (12, 11, 1, 1775653003906, '2026-04-08 12:56:23');
INSERT INTO `tb_user_role` VALUES (13, 11, 2, 1775652994290, '2026-04-08 12:56:27');

SET FOREIGN_KEY_CHECKS = 1;
