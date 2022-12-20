/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50739
 Source Host           : localhost:3306
 Source Schema         : turingoss

 Target Server Type    : MySQL
 Target Server Version : 50739
 File Encoding         : 65001

 Date: 20/12/2022 13:08:29
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for tb_bucket
-- ----------------------------
DROP TABLE IF EXISTS `tb_bucket`;
CREATE TABLE `tb_bucket`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `total_size` double NULL DEFAULT NULL,
  `used_size` double NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `uid` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_bucket
-- ----------------------------
INSERT INTO `tb_bucket` VALUES (1, 'bucket1', 11111, 11, '2022-12-16 17:35:53', 1);
INSERT INTO `tb_bucket` VALUES (2, 'bucket2', 22122, 22222, '2022-12-08 11:14:44', 1);
INSERT INTO `tb_bucket` VALUES (3, 'string', 0, 0, '2022-12-17 03:51:12', 1);
INSERT INTO `tb_bucket` VALUES (4, 'string', 0, 0, '2022-12-17 03:51:12', 1);
INSERT INTO `tb_bucket` VALUES (5, 'bucket3', 111111, NULL, '2022-12-17 12:05:25', 1);
INSERT INTO `tb_bucket` VALUES (6, 'bucket44', 6666666, 0, '2022-12-17 12:10:37', 1);

-- ----------------------------
-- Table structure for tb_files
-- ----------------------------
DROP TABLE IF EXISTS `tb_files`;
CREATE TABLE `tb_files`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `file_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `file_size` double NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `md5` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `bid` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `bid`(`bid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
