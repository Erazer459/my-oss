/*
 Navicat Premium Data Transfer

 Source Server         : tencent
 Source Server Type    : MySQL
 Source Server Version : 50718 (5.7.18-cynos-log)
 Source Host           : gz-cynosdbmysql-grp-hv910w7l.sql.tencentcdb.com:29773
 Source Schema         : turingoss

 Target Server Type    : MySQL
 Target Server Version : 50718 (5.7.18-cynos-log)
 File Encoding         : 65001

 Date: 05/04/2023 13:45:32
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for tb_access_referer
-- ----------------------------
DROP TABLE IF EXISTS `tb_access_referer`;
CREATE TABLE `tb_access_referer`  (
  `id` int(11) NOT NULL,
  `bid` int(11) NULL DEFAULT NULL COMMENT '用户id',
  `referer` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '允许访问链接',
  `is_delete` tinyint(4) NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_access_referer
-- ----------------------------
INSERT INTO `tb_access_referer` VALUES (1, 2, 'http://baidu.com', 0, '2023-03-23 21:03:59', '2023-03-23 21:04:02');
INSERT INTO `tb_access_referer` VALUES (2, 2, 'http://localhost', 0, '2023-03-23 21:03:59', '2023-03-23 21:04:02');

-- ----------------------------
-- Table structure for tb_auth_keys
-- ----------------------------
DROP TABLE IF EXISTS `tb_auth_keys`;
CREATE TABLE `tb_auth_keys`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `accesskey` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `secretkey` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `uid` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_auth_keys
-- ----------------------------
INSERT INTO `tb_auth_keys` VALUES (5, '11856212-1498-448f-afff-56c619205d4f', 'c70e0352-04dc-4da4-b74d-fff2a92bd0e8', 20);
INSERT INTO `tb_auth_keys` VALUES (6, '8b7d1ea9-3026-49be-92a1-5efa73ac00c0', '7878c4bb-ef2c-41f3-ba06-098ae7ccc68c', 28);
INSERT INTO `tb_auth_keys` VALUES (7, '1cf3041a-7fcf-4780-a799-794e1d5dbb72', '0968a566-1282-47eb-be27-d9e787a67195', 29);
INSERT INTO `tb_auth_keys` VALUES (8, 'f28bc97b-de96-427a-964e-2e5efcf5940a', '5aa92534-d822-4f6f-ba35-ad0ee836d33c', 30);

-- ----------------------------
-- Table structure for tb_bucket
-- ----------------------------
DROP TABLE IF EXISTS `tb_bucket`;
CREATE TABLE `tb_bucket`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `total_size` double NULL DEFAULT NULL,
  `used_size` double UNSIGNED ZEROFILL NULL DEFAULT 0000000000000000000000,
  `create_time` datetime NULL DEFAULT NULL,
  `uid` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 54 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_bucket
-- ----------------------------
INSERT INTO `tb_bucket` VALUES (1, 'string', 1, 0000000000000000000000, '2023-03-27 12:35:47', 12);
INSERT INTO `tb_bucket` VALUES (2, 'bucket2', 22122, 0000000000000000022222, '2022-12-08 11:14:44', 1);
INSERT INTO `tb_bucket` VALUES (3, 'string', 0, 0000000000000000000000, '2022-12-17 03:51:12', 1);
INSERT INTO `tb_bucket` VALUES (4, 'string', 0, 0000000000000000000000, '2022-12-17 03:51:12', 1);
INSERT INTO `tb_bucket` VALUES (5, 'bucket3', 111111, 0000000000000000000000, '2022-12-17 12:05:25', 1);
INSERT INTO `tb_bucket` VALUES (6, 'bucket44', 6666666, 0000000000000000000000, '2022-12-17 12:10:37', 1);
INSERT INTO `tb_bucket` VALUES (7, 'oss-test', 1024000000000, 0000000000000000167668, '2022-12-23 16:17:48', 1);
INSERT INTO `tb_bucket` VALUES (9, 'NSFW-bucket', 999999999999999, 0000000000000999305149, '2023-01-13 17:10:39', 1);
INSERT INTO `tb_bucket` VALUES (13, 'yybucket', 0, 0000000000000000000000, '2023-01-16 18:29:38', 8);
INSERT INTO `tb_bucket` VALUES (14, 'kojibucket', 0, 0000000000000000000000, '2023-01-16 21:25:06', 9);
INSERT INTO `tb_bucket` VALUES (15, 'string', 0, 0000000000000000000000, '2023-03-27 12:34:37', 12);
INSERT INTO `tb_bucket` VALUES (16, 'string', 1, 0000000000000000000000, '2023-03-27 12:35:16', 12);
INSERT INTO `tb_bucket` VALUES (17, 'string', 1, 0000000000000000000000, '2023-03-27 12:35:50', 12);
INSERT INTO `tb_bucket` VALUES (18, 'string', 1, 0000000000000000000000, '2023-03-27 12:36:44', 12);
INSERT INTO `tb_bucket` VALUES (19, 'string', 1, 0000000000000000000000, '2023-03-27 12:38:05', 12);
INSERT INTO `tb_bucket` VALUES (20, 'string', 1, 0000000000000000000000, '2023-03-27 12:38:21', 12);
INSERT INTO `tb_bucket` VALUES (21, 'yyybucket', 114514, 0000000000000000000000, '2023-03-27 13:22:13', 20);
INSERT INTO `tb_bucket` VALUES (26, 'yybukk', 123, 0000000000000000000000, '2023-03-28 18:01:40', 27);
INSERT INTO `tb_bucket` VALUES (29, '1', 323232, 0000000000000000000000, '2023-03-28 23:30:29', 22);
INSERT INTO `tb_bucket` VALUES (30, '1', 323232, 0000000000000000000000, '2023-03-28 23:30:30', 22);
INSERT INTO `tb_bucket` VALUES (31, '1', 323232, 0000000000000000000000, '2023-03-28 23:30:30', 22);
INSERT INTO `tb_bucket` VALUES (32, '1', 323232, 0000000000000000000000, '2023-03-28 23:30:31', 22);
INSERT INTO `tb_bucket` VALUES (33, '1', 323232, 0000000000000000000000, '2023-03-28 23:30:31', 22);
INSERT INTO `tb_bucket` VALUES (34, '1', 323232, 0000000000000000000000, '2023-03-28 23:30:32', 22);
INSERT INTO `tb_bucket` VALUES (35, '1', 323232, 0000000000000000000000, '2023-03-28 23:30:33', 22);
INSERT INTO `tb_bucket` VALUES (36, '1', 323232, 0000000000000000000000, '2023-03-28 23:30:33', 22);
INSERT INTO `tb_bucket` VALUES (37, '1', 323232, 0000000000000000000000, '2023-03-28 23:30:34', 22);
INSERT INTO `tb_bucket` VALUES (38, '1', 323232, 0000000000000000000000, '2023-03-28 23:30:34', 22);
INSERT INTO `tb_bucket` VALUES (39, '1', 323232, 0000000000000000000000, '2023-03-28 23:30:36', 22);
INSERT INTO `tb_bucket` VALUES (40, 'yyy1', 111, 0000000000000000000000, '2023-03-29 13:56:24', 20);
INSERT INTO `tb_bucket` VALUES (41, 'yyy2', 111, 0000000000000000000000, '2023-03-29 13:56:29', 20);
INSERT INTO `tb_bucket` VALUES (42, 'yyy3', 111, 0000000000000000000000, '2023-03-29 13:56:33', 20);
INSERT INTO `tb_bucket` VALUES (43, 'yyy4', 111, 0000000000000000000000, '2023-03-29 13:56:37', 20);
INSERT INTO `tb_bucket` VALUES (44, 'toby', 123, 0000000000000000000000, '2023-03-29 22:27:40', 22);
INSERT INTO `tb_bucket` VALUES (45, 'yy', 114, 0000000000000000000000, '2023-03-29 22:29:38', 22);
INSERT INTO `tb_bucket` VALUES (46, 'cxx', 114, 0000000000000000000000, '2023-03-31 00:06:05', 22);
INSERT INTO `tb_bucket` VALUES (50, 'test-bucket1', 10000000000000, 0000000000000000000000, '2023-03-31 13:14:38', 22);
INSERT INTO `tb_bucket` VALUES (51, '123213', 12312312, 0000000000000000000000, '2023-03-31 18:01:31', 22);
INSERT INTO `tb_bucket` VALUES (52, '123123', 1231231, 0000000000000000000000, '2023-03-31 19:08:29', 22);
INSERT INTO `tb_bucket` VALUES (53, 'blog-img', 1e20, 0000000000000000000000, '2023-04-05 13:41:46', 30);

-- ----------------------------
-- Table structure for tb_bucket_privilege
-- ----------------------------
DROP TABLE IF EXISTS `tb_bucket_privilege`;
CREATE TABLE `tb_bucket_privilege`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uid` int(11) NULL DEFAULT NULL,
  `bid` int(11) NULL DEFAULT NULL,
  `privilege` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL,
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 66 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_bucket_privilege
-- ----------------------------
INSERT INTO `tb_bucket_privilege` VALUES (6, 8, 13, 'rw', '2023-01-16 18:29:38', '2023-01-16 18:29:38', NULL);
INSERT INTO `tb_bucket_privilege` VALUES (7, 9, 13, 'rw', '2023-01-16 19:35:29', '2023-01-16 19:35:29', NULL);
INSERT INTO `tb_bucket_privilege` VALUES (8, 9, 14, 'rw', '2023-01-16 21:25:06', '2023-01-16 21:25:06', NULL);
INSERT INTO `tb_bucket_privilege` VALUES (10, 12, 15, 'rw', '2023-03-27 12:34:37', '2023-03-27 12:34:37', NULL);
INSERT INTO `tb_bucket_privilege` VALUES (11, 12, 16, 'rw', '2023-03-27 12:35:16', '2023-03-27 12:35:16', NULL);
INSERT INTO `tb_bucket_privilege` VALUES (12, 12, 1, 'rw', '2023-03-27 12:35:48', '2023-03-27 12:35:48', NULL);
INSERT INTO `tb_bucket_privilege` VALUES (13, 12, 17, 'rw', '2023-03-27 12:35:50', '2023-03-27 12:35:50', NULL);
INSERT INTO `tb_bucket_privilege` VALUES (14, 12, 18, 'rw', '2023-03-27 12:36:44', '2023-03-27 12:36:44', NULL);
INSERT INTO `tb_bucket_privilege` VALUES (15, 12, 19, 'rw', '2023-03-27 12:38:05', '2023-03-27 12:38:05', NULL);
INSERT INTO `tb_bucket_privilege` VALUES (16, 12, 20, 'rw', '2023-03-27 12:38:21', '2023-03-27 12:38:21', NULL);
INSERT INTO `tb_bucket_privilege` VALUES (17, 20, 21, 'rw', '2023-03-27 13:22:13', '2023-03-27 13:22:13', NULL);
INSERT INTO `tb_bucket_privilege` VALUES (22, 27, 26, 'rw', '2023-03-28 18:01:40', '2023-03-28 18:01:40', NULL);
INSERT INTO `tb_bucket_privilege` VALUES (25, 22, 29, 'rw', '2023-03-28 23:30:29', '2023-03-28 23:30:29', NULL);
INSERT INTO `tb_bucket_privilege` VALUES (26, 22, 30, 'rw', '2023-03-28 23:30:30', '2023-03-28 23:30:30', NULL);
INSERT INTO `tb_bucket_privilege` VALUES (27, 22, 31, 'rw', '2023-03-28 23:30:30', '2023-03-28 23:30:30', NULL);
INSERT INTO `tb_bucket_privilege` VALUES (28, 22, 32, 'rw', '2023-03-28 23:30:31', '2023-03-28 23:30:31', NULL);
INSERT INTO `tb_bucket_privilege` VALUES (29, 22, 33, 'rw', '2023-03-28 23:30:31', '2023-03-28 23:30:31', NULL);
INSERT INTO `tb_bucket_privilege` VALUES (30, 22, 34, 'rw', '2023-03-28 23:30:32', '2023-03-28 23:30:32', NULL);
INSERT INTO `tb_bucket_privilege` VALUES (31, 22, 35, 'rw', '2023-03-28 23:30:33', '2023-03-28 23:30:33', NULL);
INSERT INTO `tb_bucket_privilege` VALUES (32, 22, 36, 'rw', '2023-03-28 23:30:33', '2023-03-28 23:30:33', NULL);
INSERT INTO `tb_bucket_privilege` VALUES (33, 22, 37, 'rw', '2023-03-28 23:30:34', '2023-03-28 23:30:34', NULL);
INSERT INTO `tb_bucket_privilege` VALUES (34, 22, 38, 'rw', '2023-03-28 23:30:34', '2023-03-28 23:30:34', NULL);
INSERT INTO `tb_bucket_privilege` VALUES (35, 22, 39, 'rw', '2023-03-28 23:30:36', '2023-03-28 23:30:36', NULL);
INSERT INTO `tb_bucket_privilege` VALUES (36, 20, 40, 'rw', '2023-03-29 13:56:24', '2023-03-29 13:56:24', NULL);
INSERT INTO `tb_bucket_privilege` VALUES (37, 20, 41, 'rw', '2023-03-29 13:56:29', '2023-03-29 13:56:29', NULL);
INSERT INTO `tb_bucket_privilege` VALUES (38, 20, 42, 'rw', '2023-03-29 13:56:33', '2023-03-29 13:56:33', NULL);
INSERT INTO `tb_bucket_privilege` VALUES (39, 20, 43, 'rw', '2023-03-29 13:56:37', '2023-03-29 13:56:37', NULL);
INSERT INTO `tb_bucket_privilege` VALUES (40, 22, 40, 'rw', '2023-03-29 14:09:58', '2023-03-29 14:09:58', NULL);
INSERT INTO `tb_bucket_privilege` VALUES (41, 22, 41, 'rw', '2023-03-29 14:10:33', '2023-03-29 14:10:33', NULL);
INSERT INTO `tb_bucket_privilege` VALUES (42, 22, 42, 'rw', '2023-03-29 14:10:36', '2023-03-29 14:10:36', NULL);
INSERT INTO `tb_bucket_privilege` VALUES (43, 22, 43, 'rw', '2023-03-29 14:10:47', '2023-03-29 14:10:47', NULL);
INSERT INTO `tb_bucket_privilege` VALUES (44, 27, 40, 'rw', '2023-03-29 14:22:02', '2023-03-29 14:22:02', NULL);
INSERT INTO `tb_bucket_privilege` VALUES (45, 13, 40, 'rw', '2023-03-29 14:22:20', '2023-03-29 14:22:20', NULL);
INSERT INTO `tb_bucket_privilege` VALUES (46, 9, 40, 'rw', '2023-03-29 14:22:26', '2023-03-29 14:22:26', NULL);
INSERT INTO `tb_bucket_privilege` VALUES (47, 8, 40, 'rw', '2023-03-29 14:22:30', '2023-03-29 14:22:30', NULL);
INSERT INTO `tb_bucket_privilege` VALUES (48, 10, 40, 'rw', '2023-03-29 14:22:40', '2023-03-29 14:22:40', NULL);
INSERT INTO `tb_bucket_privilege` VALUES (49, 22, 44, 'rw', '2023-03-29 22:27:40', '2023-03-29 22:27:40', NULL);
INSERT INTO `tb_bucket_privilege` VALUES (50, 22, 45, 'rw', '2023-03-29 22:29:39', '2023-03-29 22:29:39', NULL);
INSERT INTO `tb_bucket_privilege` VALUES (51, 13, 43, 'r', '2023-03-29 22:30:36', '2023-03-29 22:30:36', NULL);
INSERT INTO `tb_bucket_privilege` VALUES (52, 22, 46, 'rw', '2023-03-31 00:06:05', '2023-03-31 00:06:05', NULL);
INSERT INTO `tb_bucket_privilege` VALUES (56, 22, 50, 'rw', '2023-03-31 13:14:38', '2023-03-31 13:14:38', NULL);
INSERT INTO `tb_bucket_privilege` VALUES (57, 22, 51, 'rw', '2023-03-31 18:01:31', '2023-03-31 18:01:31', NULL);
INSERT INTO `tb_bucket_privilege` VALUES (58, 22, 52, 'rw', '2023-03-31 19:08:29', '2023-03-31 19:08:29', NULL);
INSERT INTO `tb_bucket_privilege` VALUES (59, 10, 33, 'rw', '2023-04-02 13:11:26', '2023-04-02 13:11:26', NULL);
INSERT INTO `tb_bucket_privilege` VALUES (60, 10, 29, 'rw', '2023-04-04 18:48:07', '2023-04-04 18:48:07', NULL);
INSERT INTO `tb_bucket_privilege` VALUES (61, 10, 32, 'rw', '2023-04-04 19:11:30', '2023-04-04 19:11:30', NULL);
INSERT INTO `tb_bucket_privilege` VALUES (62, 8, 29, 'rw', '2023-04-04 19:27:58', '2023-04-04 19:27:58', NULL);
INSERT INTO `tb_bucket_privilege` VALUES (63, 12, 29, 'rw', '2023-04-04 19:32:57', '2023-04-04 19:32:57', NULL);
INSERT INTO `tb_bucket_privilege` VALUES (64, 15, 29, 'rw', '2023-04-04 19:35:03', '2023-04-04 19:35:03', NULL);
INSERT INTO `tb_bucket_privilege` VALUES (65, 30, 53, 'rw', '2023-04-05 13:41:46', '2023-04-05 13:41:46', NULL);

-- ----------------------------
-- Table structure for tb_files
-- ----------------------------
DROP TABLE IF EXISTS `tb_files`;
CREATE TABLE `tb_files`  (
  `id` bigint(50) NOT NULL AUTO_INCREMENT,
  `file_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `file_size` double NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  `md5` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `bid` int(11) NULL DEFAULT NULL,
  `file_type` tinyint(1) NULL DEFAULT NULL COMMENT '1:视频，2:图片,3:ot',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `bid`(`bid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1614215405479497729 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_files
-- ----------------------------
INSERT INTO `tb_files` VALUES (1614215329424183296, 'test1.txt', 31020, '2023-01-14 18:58:20', '83461ce6c6725590699ed27098532306', '7/test1.txt', 40, 3);
INSERT INTO `tb_files` VALUES (1614215405479497728, 'test2.jpg', 73944, '2023-01-14 18:58:38', '3966e8647386bb29505a0e1a9af1e86c', '7/test2.jpg', 7, 2);

-- ----------------------------
-- Table structure for tb_login_record
-- ----------------------------
DROP TABLE IF EXISTS `tb_login_record`;
CREATE TABLE `tb_login_record`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ip` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `time` datetime NULL DEFAULT NULL,
  `uid` int(11) NULL DEFAULT NULL,
  `device` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `city` json NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 186 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_login_record
-- ----------------------------
INSERT INTO `tb_login_record` VALUES (33, '0:0:0:0:0:0:0:1', '2023-03-22 15:32:01', 8, 'Windows 10', '{\"result\": {\"ip\": \"120.235.249.125\", \"ad_info\": {\"city\": \"湛江市\", \"adcode\": 440811, \"nation\": \"中国\", \"district\": \"麻章区\", \"province\": \"广东省\", \"nation_code\": 156}, \"location\": {\"lat\": 21.26331, \"lng\": 110.33427}}, \"status\": 0, \"message\": \"Success\", \"request_id\": \"5a6e665a0bf64c519fc945dcb31b9fc4\"}');
INSERT INTO `tb_login_record` VALUES (34, '120.235.249.125', '2023-03-22 17:11:19', 10, 'Windows 10', '{\"result\": {\"ip\": \"120.235.249.125\", \"ad_info\": {\"city\": \"湛江市\", \"adcode\": 440811, \"nation\": \"中国\", \"district\": \"麻章区\", \"province\": \"广东省\", \"nation_code\": 156}, \"location\": {\"lat\": 21.26331, \"lng\": 110.33427}}, \"status\": 0, \"message\": \"Success\", \"request_id\": \"38922c744c874c1b93817e63a583812b\"}');
INSERT INTO `tb_login_record` VALUES (35, '120.235.249.125', '2023-03-22 17:11:45', 10, 'Windows 10', '{\"result\": {\"ip\": \"120.235.249.125\", \"ad_info\": {\"city\": \"湛江市\", \"adcode\": 440811, \"nation\": \"中国\", \"district\": \"麻章区\", \"province\": \"广东省\", \"nation_code\": 156}, \"location\": {\"lat\": 21.26331, \"lng\": 110.33427}}, \"status\": 0, \"message\": \"Success\", \"request_id\": \"925be9e89bd64d749665cd90e9c09e55\"}');
INSERT INTO `tb_login_record` VALUES (36, '120.235.249.68', '2023-03-25 21:20:28', 15, 'Windows 10', '{\"result\": {\"ip\": \"120.235.249.68\", \"ad_info\": {\"city\": \"湛江市\", \"adcode\": 440811, \"nation\": \"中国\", \"district\": \"麻章区\", \"province\": \"广东省\", \"nation_code\": 156}, \"location\": {\"lat\": 21.26331, \"lng\": 110.33427}}, \"status\": 0, \"message\": \"Success\", \"request_id\": \"caa7ffb5477243c3b2b186f725519c75\"}');
INSERT INTO `tb_login_record` VALUES (37, '154.22.57.45', '2023-03-25 21:33:27', 12, 'Windows 10', '{\"result\": {\"ip\": \"154.22.57.45\", \"ad_info\": {\"city\": \"\", \"adcode\": -1, \"nation\": \"美国\", \"district\": \"\", \"province\": \"\", \"nation_code\": 840}, \"location\": {\"lat\": 38.8833, \"lng\": -77}}, \"status\": 0, \"message\": \"Success\", \"request_id\": \"b73925fc34c6425e944370a2e0e69814\"}');
INSERT INTO `tb_login_record` VALUES (38, '154.22.57.45', '2023-03-25 21:35:27', 11, 'Windows 10', '{\"result\": {\"ip\": \"154.22.57.45\", \"ad_info\": {\"city\": \"\", \"adcode\": -1, \"nation\": \"美国\", \"district\": \"\", \"province\": \"\", \"nation_code\": 840}, \"location\": {\"lat\": 38.8833, \"lng\": -77}}, \"status\": 0, \"message\": \"Success\", \"request_id\": \"623d7040dc9c492ebb55603dd4447866\"}');
INSERT INTO `tb_login_record` VALUES (39, '154.22.57.45', '2023-03-25 21:37:10', 12, 'Windows 10', '{\"result\": {\"ip\": \"154.22.57.45\", \"ad_info\": {\"city\": \"\", \"adcode\": -1, \"nation\": \"美国\", \"district\": \"\", \"province\": \"\", \"nation_code\": 840}, \"location\": {\"lat\": 38.8833, \"lng\": -77}}, \"status\": 0, \"message\": \"Success\", \"request_id\": \"a5d8be6336174b2987bd04f25fa112d5\"}');
INSERT INTO `tb_login_record` VALUES (40, '154.22.57.45', '2023-03-25 21:55:54', 12, 'Windows 10', '{\"result\": {\"ip\": \"154.22.57.45\", \"ad_info\": {\"city\": \"\", \"adcode\": -1, \"nation\": \"美国\", \"district\": \"\", \"province\": \"\", \"nation_code\": 840}, \"location\": {\"lat\": 38.8833, \"lng\": -77}}, \"status\": 0, \"message\": \"Success\", \"request_id\": \"936be2241c5e4a36b27d8ed7ad62a1f0\"}');
INSERT INTO `tb_login_record` VALUES (41, '120.235.249.68', '2023-03-25 21:56:04', 12, 'Windows 10', '{\"result\": {\"ip\": \"120.235.249.68\", \"ad_info\": {\"city\": \"湛江市\", \"adcode\": 440811, \"nation\": \"中国\", \"district\": \"麻章区\", \"province\": \"广东省\", \"nation_code\": 156}, \"location\": {\"lat\": 21.26331, \"lng\": 110.33427}}, \"status\": 0, \"message\": \"Success\", \"request_id\": \"0253c3c04fd946b582204e1b58542a91\"}');
INSERT INTO `tb_login_record` VALUES (42, '120.235.249.68', '2023-03-25 21:56:18', 12, 'Windows 10', '{\"result\": {\"ip\": \"120.235.249.68\", \"ad_info\": {\"city\": \"湛江市\", \"adcode\": 440811, \"nation\": \"中国\", \"district\": \"麻章区\", \"province\": \"广东省\", \"nation_code\": 156}, \"location\": {\"lat\": 21.26331, \"lng\": 110.33427}}, \"status\": 0, \"message\": \"Success\", \"request_id\": \"1355b6a88d214b11a14b9f9544a5ce14\"}');
INSERT INTO `tb_login_record` VALUES (43, '120.235.249.68', '2023-03-25 21:56:28', 12, 'Windows 10', '{\"result\": {\"ip\": \"120.235.249.68\", \"ad_info\": {\"city\": \"湛江市\", \"adcode\": 440811, \"nation\": \"中国\", \"district\": \"麻章区\", \"province\": \"广东省\", \"nation_code\": 156}, \"location\": {\"lat\": 21.26331, \"lng\": 110.33427}}, \"status\": 0, \"message\": \"Success\", \"request_id\": \"46ae4c4da26747dda0713166eb19281b\"}');
INSERT INTO `tb_login_record` VALUES (44, '120.235.249.68', '2023-03-25 21:57:16', 12, 'Windows 10', '{\"result\": {\"ip\": \"120.235.249.68\", \"ad_info\": {\"city\": \"湛江市\", \"adcode\": 440811, \"nation\": \"中国\", \"district\": \"麻章区\", \"province\": \"广东省\", \"nation_code\": 156}, \"location\": {\"lat\": 21.26331, \"lng\": 110.33427}}, \"status\": 0, \"message\": \"Success\", \"request_id\": \"ca777c9734bc41b5bce64a9edb7bfc4a\"}');
INSERT INTO `tb_login_record` VALUES (45, '120.235.249.68', '2023-03-25 22:05:25', 12, 'Windows 10', '{\"result\": {\"ip\": \"120.235.249.68\", \"ad_info\": {\"city\": \"湛江市\", \"adcode\": 440811, \"nation\": \"中国\", \"district\": \"麻章区\", \"province\": \"广东省\", \"nation_code\": 156}, \"location\": {\"lat\": 21.26331, \"lng\": 110.33427}}, \"status\": 0, \"message\": \"Success\", \"request_id\": \"5b9c47ffd6fe4db9a7b7ef20b2fd1fb2\"}');
INSERT INTO `tb_login_record` VALUES (46, '120.235.249.68', '2023-03-25 22:10:00', 12, 'Windows 10', '{\"result\": {\"ip\": \"120.235.249.68\", \"ad_info\": {\"city\": \"湛江市\", \"adcode\": 440811, \"nation\": \"中国\", \"district\": \"麻章区\", \"province\": \"广东省\", \"nation_code\": 156}, \"location\": {\"lat\": 21.26331, \"lng\": 110.33427}}, \"status\": 0, \"message\": \"Success\", \"request_id\": \"6b948265a9c54dadaf541865dd95c594\"}');
INSERT INTO `tb_login_record` VALUES (47, '120.235.249.68', '2023-03-25 22:18:13', 12, 'Windows 10', '{\"result\": {\"ip\": \"120.235.249.68\", \"ad_info\": {\"city\": \"湛江市\", \"adcode\": 440811, \"nation\": \"中国\", \"district\": \"麻章区\", \"province\": \"广东省\", \"nation_code\": 156}, \"location\": {\"lat\": 21.26331, \"lng\": 110.33427}}, \"status\": 0, \"message\": \"Success\", \"request_id\": \"bc3e2db541454ea6845198f1cee65152\"}');
INSERT INTO `tb_login_record` VALUES (48, '120.235.249.68', '2023-03-25 22:42:42', 12, 'Windows 10', '{\"result\": {\"ip\": \"120.235.249.68\", \"ad_info\": {\"city\": \"湛江市\", \"adcode\": 440811, \"nation\": \"中国\", \"district\": \"麻章区\", \"province\": \"广东省\", \"nation_code\": 156}, \"location\": {\"lat\": 21.26331, \"lng\": 110.33427}}, \"status\": 0, \"message\": \"Success\", \"request_id\": \"46485f1705334272bc74e7baa31a726c\"}');
INSERT INTO `tb_login_record` VALUES (49, '120.235.249.68', '2023-03-25 22:45:59', 12, 'Windows 10', '{\"result\": {\"ip\": \"120.235.249.68\", \"ad_info\": {\"city\": \"湛江市\", \"adcode\": 440811, \"nation\": \"中国\", \"district\": \"麻章区\", \"province\": \"广东省\", \"nation_code\": 156}, \"location\": {\"lat\": 21.26331, \"lng\": 110.33427}}, \"status\": 0, \"message\": \"Success\", \"request_id\": \"0edac092bf0f4e7580e447f753553b81\"}');
INSERT INTO `tb_login_record` VALUES (50, '120.235.249.68', '2023-03-25 22:52:09', 12, 'Windows 10', '{\"result\": {\"ip\": \"120.235.249.68\", \"ad_info\": {\"city\": \"湛江市\", \"adcode\": 440811, \"nation\": \"中国\", \"district\": \"麻章区\", \"province\": \"广东省\", \"nation_code\": 156}, \"location\": {\"lat\": 21.26331, \"lng\": 110.33427}}, \"status\": 0, \"message\": \"Success\", \"request_id\": \"afa1757c7a9b412c8a56f451f70c18bc\"}');
INSERT INTO `tb_login_record` VALUES (51, '120.235.249.68', '2023-03-25 22:53:26', 12, 'Windows 10', '{\"result\": {\"ip\": \"120.235.249.68\", \"ad_info\": {\"city\": \"湛江市\", \"adcode\": 440811, \"nation\": \"中国\", \"district\": \"麻章区\", \"province\": \"广东省\", \"nation_code\": 156}, \"location\": {\"lat\": 21.26331, \"lng\": 110.33427}}, \"status\": 0, \"message\": \"Success\", \"request_id\": \"b1ad85187d044e25afa141609ae217d1\"}');
INSERT INTO `tb_login_record` VALUES (52, '120.235.249.68', '2023-03-25 22:56:46', 12, 'Windows 10', '{\"result\": {\"ip\": \"120.235.249.68\", \"ad_info\": {\"city\": \"湛江市\", \"adcode\": 440811, \"nation\": \"中国\", \"district\": \"麻章区\", \"province\": \"广东省\", \"nation_code\": 156}, \"location\": {\"lat\": 21.26331, \"lng\": 110.33427}}, \"status\": 0, \"message\": \"Success\", \"request_id\": \"2c8e360962e84248a218486f4e2c4c6a\"}');
INSERT INTO `tb_login_record` VALUES (53, '120.235.249.68', '2023-03-25 22:57:01', 12, 'Windows 10', '{\"result\": {\"ip\": \"120.235.249.68\", \"ad_info\": {\"city\": \"湛江市\", \"adcode\": 440811, \"nation\": \"中国\", \"district\": \"麻章区\", \"province\": \"广东省\", \"nation_code\": 156}, \"location\": {\"lat\": 21.26331, \"lng\": 110.33427}}, \"status\": 0, \"message\": \"Success\", \"request_id\": \"a60f5dccadad4f9fbf07f09df39db823\"}');
INSERT INTO `tb_login_record` VALUES (54, '120.235.249.68', '2023-03-25 23:00:22', 8, 'Windows 10', '{\"result\": {\"ip\": \"120.235.249.68\", \"ad_info\": {\"city\": \"湛江市\", \"adcode\": 440811, \"nation\": \"中国\", \"district\": \"麻章区\", \"province\": \"广东省\", \"nation_code\": 156}, \"location\": {\"lat\": 21.26331, \"lng\": 110.33427}}, \"status\": 0, \"message\": \"Success\", \"request_id\": \"065c6e2337ab4675ae6c5d3d671ecd3b\"}');
INSERT INTO `tb_login_record` VALUES (55, '120.235.249.68', '2023-03-25 23:04:20', 12, 'Windows 10', '{\"result\": {\"ip\": \"120.235.249.68\", \"ad_info\": {\"city\": \"湛江市\", \"adcode\": 440811, \"nation\": \"中国\", \"district\": \"麻章区\", \"province\": \"广东省\", \"nation_code\": 156}, \"location\": {\"lat\": 21.26331, \"lng\": 110.33427}}, \"status\": 0, \"message\": \"Success\", \"request_id\": \"be8c40234b1048b69a15300ec7a65bb2\"}');
INSERT INTO `tb_login_record` VALUES (56, '183.46.240.151', '2023-03-27 11:33:14', 10, 'Windows 10', '{\"result\": {\"ip\": \"183.46.240.151\", \"ad_info\": {\"city\": \"广州市\", \"adcode\": 440100, \"nation\": \"中国\", \"district\": \"\", \"province\": \"广东省\", \"nation_code\": 156}, \"location\": {\"lat\": 23.12908, \"lng\": 113.26436}}, \"status\": 0, \"message\": \"Success\", \"request_id\": \"e0aff24304dc4b8797baaf73cff9aef0\"}');
INSERT INTO `tb_login_record` VALUES (57, '183.46.240.151', '2023-03-27 11:48:04', 10, 'Windows 10', '{\"result\": {\"ip\": \"183.46.240.151\", \"ad_info\": {\"city\": \"广州市\", \"adcode\": 440100, \"nation\": \"中国\", \"district\": \"\", \"province\": \"广东省\", \"nation_code\": 156}, \"location\": {\"lat\": 23.12908, \"lng\": 113.26436}}, \"status\": 0, \"message\": \"Success\", \"request_id\": \"b217be68f65a4624a919ba29ec886ede\"}');
INSERT INTO `tb_login_record` VALUES (58, '183.46.240.151', '2023-03-27 11:48:52', 10, 'Windows 10', '{\"result\": {\"ip\": \"183.46.240.151\", \"ad_info\": {\"city\": \"广州市\", \"adcode\": 440100, \"nation\": \"中国\", \"district\": \"\", \"province\": \"广东省\", \"nation_code\": 156}, \"location\": {\"lat\": 23.12908, \"lng\": 113.26436}}, \"status\": 0, \"message\": \"Success\", \"request_id\": \"2262f88139e44c2c92bddbb3b4b63a7e\"}');
INSERT INTO `tb_login_record` VALUES (59, '183.46.240.151', '2023-03-27 12:00:41', 10, 'Windows 10', '{\"result\": {\"ip\": \"183.46.240.151\", \"ad_info\": {\"city\": \"广州市\", \"adcode\": 440100, \"nation\": \"中国\", \"district\": \"\", \"province\": \"广东省\", \"nation_code\": 156}, \"location\": {\"lat\": 23.12908, \"lng\": 113.26436}}, \"status\": 0, \"message\": \"Success\", \"request_id\": \"a0668d0a4e8d4cbfa069bc139f6997a6\"}');
INSERT INTO `tb_login_record` VALUES (60, '183.46.240.151', '2023-03-27 12:02:14', 10, 'Windows 10', '{\"result\": {\"ip\": \"183.46.240.151\", \"ad_info\": {\"city\": \"广州市\", \"adcode\": 440100, \"nation\": \"中国\", \"district\": \"\", \"province\": \"广东省\", \"nation_code\": 156}, \"location\": {\"lat\": 23.12908, \"lng\": 113.26436}}, \"status\": 0, \"message\": \"Success\", \"request_id\": \"2fc9ec96602c48f9a079427695ef7a10\"}');
INSERT INTO `tb_login_record` VALUES (61, '120.235.249.34', '2023-03-27 12:24:15', 12, 'Windows 10', '{\"result\": {\"ip\": \"120.235.249.34\", \"ad_info\": {\"city\": \"湛江市\", \"adcode\": 440811, \"nation\": \"中国\", \"district\": \"麻章区\", \"province\": \"广东省\", \"nation_code\": 156}, \"location\": {\"lat\": 21.26331, \"lng\": 110.33427}}, \"status\": 0, \"message\": \"Success\", \"request_id\": \"d43e0a19d3294400b19771ffaad431a7\"}');
INSERT INTO `tb_login_record` VALUES (62, '120.235.249.34', '2023-03-27 12:25:24', 12, 'Windows 10', '{\"result\": {\"ip\": \"120.235.249.34\", \"ad_info\": {\"city\": \"湛江市\", \"adcode\": 440811, \"nation\": \"中国\", \"district\": \"麻章区\", \"province\": \"广东省\", \"nation_code\": 156}, \"location\": {\"lat\": 21.26331, \"lng\": 110.33427}}, \"status\": 0, \"message\": \"Success\", \"request_id\": \"ceedc0e2f6b34b2bae2e2b75272dac6b\"}');
INSERT INTO `tb_login_record` VALUES (63, '120.235.249.34', '2023-03-27 12:25:27', 12, 'Windows 10', '{\"result\": {\"ip\": \"120.235.249.34\", \"ad_info\": {\"city\": \"湛江市\", \"adcode\": 440811, \"nation\": \"中国\", \"district\": \"麻章区\", \"province\": \"广东省\", \"nation_code\": 156}, \"location\": {\"lat\": 21.26331, \"lng\": 110.33427}}, \"status\": 0, \"message\": \"Success\", \"request_id\": \"a39df54126bd4db9afc7133e139e8b90\"}');
INSERT INTO `tb_login_record` VALUES (64, '120.235.249.34', '2023-03-27 12:26:21', 12, 'Windows 10', '{\"result\": {\"ip\": \"120.235.249.34\", \"ad_info\": {\"city\": \"湛江市\", \"adcode\": 440811, \"nation\": \"中国\", \"district\": \"麻章区\", \"province\": \"广东省\", \"nation_code\": 156}, \"location\": {\"lat\": 21.26331, \"lng\": 110.33427}}, \"status\": 0, \"message\": \"Success\", \"request_id\": \"c574a42f83504a1794b80756ffe65ec4\"}');
INSERT INTO `tb_login_record` VALUES (65, '120.235.249.34', '2023-03-27 12:27:14', 12, 'Windows 10', '{\"result\": {\"ip\": \"120.235.249.34\", \"ad_info\": {\"city\": \"湛江市\", \"adcode\": 440811, \"nation\": \"中国\", \"district\": \"麻章区\", \"province\": \"广东省\", \"nation_code\": 156}, \"location\": {\"lat\": 21.26331, \"lng\": 110.33427}}, \"status\": 0, \"message\": \"Success\", \"request_id\": \"c7d86bdfe2a44e4d8efff57752a8b217\"}');
INSERT INTO `tb_login_record` VALUES (66, '120.235.249.34', '2023-03-27 12:34:12', 12, 'Windows 10', '{\"result\": {\"ip\": \"120.235.249.34\", \"ad_info\": {\"city\": \"湛江市\", \"adcode\": 440811, \"nation\": \"中国\", \"district\": \"麻章区\", \"province\": \"广东省\", \"nation_code\": 156}, \"location\": {\"lat\": 21.26331, \"lng\": 110.33427}}, \"status\": 0, \"message\": \"Success\", \"request_id\": \"54383ba390814798ae6ce2a65135eb58\"}');
INSERT INTO `tb_login_record` VALUES (67, '0:0:0:0:0:0:0:1', '2023-03-27 13:06:40', 20, 'Windows 10', '{\"status\": 375, \"message\": \"局域网IP\", \"request_id\": \"caea633f8f6c47d8ae13533a1f3010e9\"}');
INSERT INTO `tb_login_record` VALUES (68, '0:0:0:0:0:0:0:1', '2023-03-27 13:21:29', 20, 'Windows 10', '{\"status\": 375, \"message\": \"局域网IP\", \"request_id\": \"0fb12c5fe9cb4a3f94051ab18bbb6733\"}');
INSERT INTO `tb_login_record` VALUES (69, '119.143.111.226', '2023-03-27 13:46:08', 20, 'Mac OS X (iPhone)', '{\"result\": {\"ip\": \"119.143.111.226\", \"ad_info\": {\"city\": \"\", \"adcode\": -1, \"nation\": \"中国\", \"district\": \"\", \"province\": \"\", \"nation_code\": 156}, \"location\": {\"lat\": 39.9167, \"lng\": 116.3833}}, \"status\": 0, \"message\": \"Success\", \"request_id\": \"925ffbbbd846491f8285fde0368b161d\"}');
INSERT INTO `tb_login_record` VALUES (70, '183.46.240.151', '2023-03-27 22:21:42', 22, 'Windows 10', '{\"result\": {\"ip\": \"183.46.240.151\", \"ad_info\": {\"city\": \"广州市\", \"adcode\": 440100, \"nation\": \"中国\", \"district\": \"\", \"province\": \"广东省\", \"nation_code\": 156}, \"location\": {\"lat\": 23.12908, \"lng\": 113.26436}}, \"status\": 0, \"message\": \"Success\", \"request_id\": \"d6344ac20eb64105a386738f14464bf2\"}');
INSERT INTO `tb_login_record` VALUES (71, '183.46.240.151', '2023-03-27 22:22:02', 22, 'Windows 10', '{\"result\": {\"ip\": \"183.46.240.151\", \"ad_info\": {\"city\": \"广州市\", \"adcode\": 440100, \"nation\": \"中国\", \"district\": \"\", \"province\": \"广东省\", \"nation_code\": 156}, \"location\": {\"lat\": 23.12908, \"lng\": 113.26436}}, \"status\": 0, \"message\": \"Success\", \"request_id\": \"2fd3a708e2db44c58f9b8d374cb761ae\"}');
INSERT INTO `tb_login_record` VALUES (72, '183.46.240.151', '2023-03-27 22:31:13', 22, 'Windows 10', '{\"result\": {\"ip\": \"183.46.240.151\", \"ad_info\": {\"city\": \"广州市\", \"adcode\": 440100, \"nation\": \"中国\", \"district\": \"\", \"province\": \"广东省\", \"nation_code\": 156}, \"location\": {\"lat\": 23.12908, \"lng\": 113.26436}}, \"status\": 0, \"message\": \"Success\", \"request_id\": \"eeed3a400cfb439aaed68534f92b7cf8\"}');
INSERT INTO `tb_login_record` VALUES (73, '0:0:0:0:0:0:0:1', '2023-03-27 23:05:44', 24, 'Windows 10', '{\"status\": 375, \"message\": \"局域网IP\", \"request_id\": \"d5aed22940da43efbde530cf7e316e56\"}');
INSERT INTO `tb_login_record` VALUES (74, '119.143.107.198', '2023-03-27 23:18:34', 22, 'Windows 10', '{\"result\": {\"ip\": \"119.143.107.198\", \"ad_info\": {\"city\": \"深圳市\", \"adcode\": 440306, \"nation\": \"中国\", \"district\": \"宝安区\", \"province\": \"广东省\", \"nation_code\": 156}, \"location\": {\"lat\": 22.55329, \"lng\": 113.88308}}, \"status\": 0, \"message\": \"Success\", \"request_id\": \"a49aa8d2b7dc4b65b981c014975166f2\"}');
INSERT INTO `tb_login_record` VALUES (75, '119.143.107.198', '2023-03-27 23:34:50', 22, 'Windows 10', '{\"result\": {\"ip\": \"119.143.107.198\", \"ad_info\": {\"city\": \"深圳市\", \"adcode\": 440306, \"nation\": \"中国\", \"district\": \"宝安区\", \"province\": \"广东省\", \"nation_code\": 156}, \"location\": {\"lat\": 22.55329, \"lng\": 113.88308}}, \"status\": 0, \"message\": \"Success\", \"request_id\": \"21e1eab2650e44378e4d9b788f6bb9a8\"}');
INSERT INTO `tb_login_record` VALUES (76, '0:0:0:0:0:0:0:1', '2023-03-28 17:00:59', 26, 'Windows 10', '{\"status\": 375, \"message\": \"局域网IP\", \"request_id\": \"778d4cc2a80e4d42985dcf18732a4396\"}');
INSERT INTO `tb_login_record` VALUES (77, '0:0:0:0:0:0:0:1', '2023-03-28 17:08:02', 27, 'Windows 10', '{\"status\": 375, \"message\": \"局域网IP\", \"request_id\": \"ccd6a27e244c4d6aa0ee5ad5bdeb7396\"}');
INSERT INTO `tb_login_record` VALUES (78, '0:0:0:0:0:0:0:1', '2023-03-28 18:01:09', 27, 'Windows 10', '{\"status\": 375, \"message\": \"局域网IP\", \"request_id\": \"09ad2d60031a48c588fcda5bdb83663b\"}');
INSERT INTO `tb_login_record` VALUES (79, '119.143.107.198', '2023-03-28 18:46:54', 22, 'Windows 10', '{\"result\": {\"ip\": \"119.143.107.198\", \"ad_info\": {\"city\": \"广州市\", \"adcode\": 440100, \"nation\": \"中国\", \"district\": \"\", \"province\": \"广东省\", \"nation_code\": 156}, \"location\": {\"lat\": 23.12908, \"lng\": 113.26436}}, \"status\": 0, \"message\": \"Success\", \"request_id\": \"e8b7832582bc479a8e686b295fb1b438\"}');
INSERT INTO `tb_login_record` VALUES (80, '183.46.122.222', '2023-03-28 18:56:21', 22, 'Mac OS X (iPhone)', '{\"result\": {\"ip\": \"183.46.122.222\", \"ad_info\": {\"city\": \"广州市\", \"adcode\": 440100, \"nation\": \"中国\", \"district\": \"\", \"province\": \"广东省\", \"nation_code\": 156}, \"location\": {\"lat\": 23.12908, \"lng\": 113.26436}}, \"status\": 0, \"message\": \"Success\", \"request_id\": \"ded6bbba7d744e28892eba0a66fc81e2\"}');
INSERT INTO `tb_login_record` VALUES (81, '119.143.107.198', '2023-03-28 18:58:20', 22, 'Windows 10', '{\"result\": {\"ip\": \"119.143.107.198\", \"ad_info\": {\"city\": \"广州市\", \"adcode\": 440100, \"nation\": \"中国\", \"district\": \"\", \"province\": \"广东省\", \"nation_code\": 156}, \"location\": {\"lat\": 23.12908, \"lng\": 113.26436}}, \"status\": 0, \"message\": \"Success\", \"request_id\": \"f47d061ae9c140b79693b65d67072694\"}');
INSERT INTO `tb_login_record` VALUES (82, '183.46.122.222', '2023-03-28 18:59:42', 22, 'Mac OS X (iPhone)', '{\"result\": {\"ip\": \"183.46.122.222\", \"ad_info\": {\"city\": \"广州市\", \"adcode\": 440100, \"nation\": \"中国\", \"district\": \"\", \"province\": \"广东省\", \"nation_code\": 156}, \"location\": {\"lat\": 23.12908, \"lng\": 113.26436}}, \"status\": 0, \"message\": \"Success\", \"request_id\": \"333bef75751f4d2bbe93c81bf2c8403a\"}');
INSERT INTO `tb_login_record` VALUES (83, '119.143.107.198', '2023-03-28 19:01:10', 22, 'Windows 10', '{\"result\": {\"ip\": \"119.143.107.198\", \"ad_info\": {\"city\": \"广州市\", \"adcode\": 440100, \"nation\": \"中国\", \"district\": \"\", \"province\": \"广东省\", \"nation_code\": 156}, \"location\": {\"lat\": 23.12908, \"lng\": 113.26436}}, \"status\": 0, \"message\": \"Success\", \"request_id\": \"0b38129442df4e92954b5e99f50760aa\"}');
INSERT INTO `tb_login_record` VALUES (84, '0:0:0:0:0:0:0:1', '2023-03-28 20:29:28', 27, 'Windows 10', '{\"status\": 375, \"message\": \"局域网IP\", \"request_id\": \"312837a64901492e92d447021c5ebe9e\"}');
INSERT INTO `tb_login_record` VALUES (85, '183.46.122.222', '2023-03-28 20:44:00', 20, 'Mac OS X (iPhone)', '{\"result\": {\"ip\": \"183.46.122.222\", \"ad_info\": {\"city\": \"广州市\", \"adcode\": 440100, \"nation\": \"中国\", \"district\": \"\", \"province\": \"广东省\", \"nation_code\": 156}, \"location\": {\"lat\": 23.12908, \"lng\": 113.26436}}, \"status\": 0, \"message\": \"Success\", \"request_id\": \"0491ff0df2c149608d3700640e578dba\"}');
INSERT INTO `tb_login_record` VALUES (86, '0:0:0:0:0:0:0:1', '2023-03-28 20:48:47', 20, 'Windows 10', '{\"status\": 375, \"message\": \"局域网IP\", \"request_id\": \"69b6d5af1eb549fc98481ac5c856b11b\"}');
INSERT INTO `tb_login_record` VALUES (87, '0:0:0:0:0:0:0:1', '2023-03-28 20:57:32', 20, 'Windows 10', '{\"status\": 375, \"message\": \"局域网IP\", \"request_id\": \"c0d4ff5c2c0a4142bdce4fcdc520b4c3\"}');
INSERT INTO `tb_login_record` VALUES (88, '0:0:0:0:0:0:0:1', '2023-03-28 21:09:07', 20, 'Windows 10', '{\"status\": 375, \"message\": \"局域网IP\", \"request_id\": \"af109914d2884360b3dd83b2bb14561b\"}');
INSERT INTO `tb_login_record` VALUES (89, '0:0:0:0:0:0:0:1', '2023-03-28 21:55:24', 20, 'Windows 10', '{\"status\": 375, \"message\": \"局域网IP\", \"request_id\": \"1656825c66d24f168842ff8d681a8d69\"}');
INSERT INTO `tb_login_record` VALUES (90, '119.143.107.198', '2023-03-28 22:50:46', 22, 'Windows 10', '{\"result\": {\"ip\": \"119.143.107.198\", \"ad_info\": {\"city\": \"广州市\", \"adcode\": 440100, \"nation\": \"中国\", \"district\": \"\", \"province\": \"广东省\", \"nation_code\": 156}, \"location\": {\"lat\": 23.12908, \"lng\": 113.26436}}, \"status\": 0, \"message\": \"Success\", \"request_id\": \"99659eee6b984536855fcf86c22c2a2a\"}');
INSERT INTO `tb_login_record` VALUES (91, '119.143.107.198', '2023-03-28 23:28:35', 22, 'Windows 10', '{\"result\": {\"ip\": \"119.143.107.198\", \"ad_info\": {\"city\": \"广州市\", \"adcode\": 440100, \"nation\": \"中国\", \"district\": \"\", \"province\": \"广东省\", \"nation_code\": 156}, \"location\": {\"lat\": 23.12908, \"lng\": 113.26436}}, \"status\": 0, \"message\": \"Success\", \"request_id\": \"3f6c67285c7a4af784e74069091dac1a\"}');
INSERT INTO `tb_login_record` VALUES (92, '119.143.107.198', '2023-03-28 23:29:57', 22, 'Windows 10', '{\"result\": {\"ip\": \"119.143.107.198\", \"ad_info\": {\"city\": \"广州市\", \"adcode\": 440100, \"nation\": \"中国\", \"district\": \"\", \"province\": \"广东省\", \"nation_code\": 156}, \"location\": {\"lat\": 23.12908, \"lng\": 113.26436}}, \"status\": 0, \"message\": \"Success\", \"request_id\": \"61a8cb4b8d2541109b5ad440315edfb9\"}');
INSERT INTO `tb_login_record` VALUES (93, '119.143.107.198', '2023-03-28 23:30:53', 22, 'Windows 10', '{\"result\": {\"ip\": \"119.143.107.198\", \"ad_info\": {\"city\": \"广州市\", \"adcode\": 440100, \"nation\": \"中国\", \"district\": \"\", \"province\": \"广东省\", \"nation_code\": 156}, \"location\": {\"lat\": 23.12908, \"lng\": 113.26436}}, \"status\": 0, \"message\": \"Success\", \"request_id\": \"3dc45276804540c5a0bd228d5c563ddb\"}');
INSERT INTO `tb_login_record` VALUES (94, '14.150.145.235', '2023-03-28 23:42:59', 22, 'Mac OS X (iPhone)', '{\"result\": {\"ip\": \"14.150.145.235\", \"ad_info\": {\"city\": \"广州市\", \"adcode\": 440100, \"nation\": \"中国\", \"district\": \"\", \"province\": \"广东省\", \"nation_code\": 156}, \"location\": {\"lat\": 23.12908, \"lng\": 113.26436}}, \"status\": 0, \"message\": \"Success\", \"request_id\": \"6e58d198806543d38abe15df5d8660d9\"}');
INSERT INTO `tb_login_record` VALUES (95, '119.143.107.198', '2023-03-28 23:46:58', 22, 'Windows 10', '{\"result\": {\"ip\": \"119.143.107.198\", \"ad_info\": {\"city\": \"广州市\", \"adcode\": 440100, \"nation\": \"中国\", \"district\": \"\", \"province\": \"广东省\", \"nation_code\": 156}, \"location\": {\"lat\": 23.12908, \"lng\": 113.26436}}, \"status\": 0, \"message\": \"Success\", \"request_id\": \"81eab4ca18cc400d8a89f5dfea964d28\"}');
INSERT INTO `tb_login_record` VALUES (96, '223.104.73.44', '2023-03-28 23:52:24', 22, 'Linux', '{\"result\": {\"ip\": \"223.104.73.44\", \"ad_info\": {\"city\": \"湛江市\", \"adcode\": 440800, \"nation\": \"中国\", \"district\": \"\", \"province\": \"广东省\", \"nation_code\": 156}, \"location\": {\"lat\": 21.27134, \"lng\": 110.35894}}, \"status\": 0, \"message\": \"Success\", \"request_id\": \"793331837df44268b3f4bd3c469908a4\"}');
INSERT INTO `tb_login_record` VALUES (97, '119.143.107.198', '2023-03-28 23:59:46', 22, 'Windows 10', '{\"result\": {\"ip\": \"119.143.107.198\", \"ad_info\": {\"city\": \"广州市\", \"adcode\": 440100, \"nation\": \"中国\", \"district\": \"\", \"province\": \"广东省\", \"nation_code\": 156}, \"location\": {\"lat\": 23.12908, \"lng\": 113.26436}}, \"status\": 0, \"message\": \"Success\", \"request_id\": \"b687792f34bd41d49e7a117be3b0029d\"}');
INSERT INTO `tb_login_record` VALUES (98, '223.104.73.44', '2023-03-29 00:03:11', 22, 'Linux', '{\"result\": {\"ip\": \"223.104.73.44\", \"ad_info\": {\"city\": \"湛江市\", \"adcode\": 440800, \"nation\": \"中国\", \"district\": \"\", \"province\": \"广东省\", \"nation_code\": 156}, \"location\": {\"lat\": 21.27134, \"lng\": 110.35894}}, \"status\": 0, \"message\": \"Success\", \"request_id\": \"0af0d9f6a45e476d9dd11e5981a5e7cc\"}');
INSERT INTO `tb_login_record` VALUES (99, '223.104.73.44', '2023-03-29 00:15:52', 22, 'Linux', '{\"result\": {\"ip\": \"223.104.73.44\", \"ad_info\": {\"city\": \"湛江市\", \"adcode\": 440800, \"nation\": \"中国\", \"district\": \"\", \"province\": \"广东省\", \"nation_code\": 156}, \"location\": {\"lat\": 21.27134, \"lng\": 110.35894}}, \"status\": 0, \"message\": \"Success\", \"request_id\": \"fd7b065ff51d46bb8cbdeb5147e18260\"}');
INSERT INTO `tb_login_record` VALUES (100, '14.150.145.235', '2023-03-29 00:17:53', 22, 'Mac OS X (iPhone)', '{\"result\": {\"ip\": \"14.150.145.235\", \"ad_info\": {\"city\": \"广州市\", \"adcode\": 440100, \"nation\": \"中国\", \"district\": \"\", \"province\": \"广东省\", \"nation_code\": 156}, \"location\": {\"lat\": 23.12908, \"lng\": 113.26436}}, \"status\": 0, \"message\": \"Success\", \"request_id\": \"058639845c1d4230b65fafeb78b25089\"}');
INSERT INTO `tb_login_record` VALUES (101, '14.150.251.210', '2023-03-29 00:25:14', 22, 'Mac OS X (iPhone)', '{\"result\": {\"ip\": \"14.150.251.210\", \"ad_info\": {\"city\": \"深圳市\", \"adcode\": 440300, \"nation\": \"中国\", \"district\": \"\", \"province\": \"广东省\", \"nation_code\": 156}, \"location\": {\"lat\": 22.54286, \"lng\": 114.05956}}, \"status\": 0, \"message\": \"Success\", \"request_id\": \"efb0fa9e6a5b46a19e802682a3c1a464\"}');
INSERT INTO `tb_login_record` VALUES (102, '223.104.73.44', '2023-03-29 00:33:13', 22, 'Linux', '{\"result\": {\"ip\": \"223.104.73.44\", \"ad_info\": {\"city\": \"湛江市\", \"adcode\": 440800, \"nation\": \"中国\", \"district\": \"\", \"province\": \"广东省\", \"nation_code\": 156}, \"location\": {\"lat\": 21.27134, \"lng\": 110.35894}}, \"status\": 0, \"message\": \"Success\", \"request_id\": \"e868bd5760e6421fa06f18e21c9127d3\"}');
INSERT INTO `tb_login_record` VALUES (103, '14.150.145.235', '2023-03-29 00:34:49', 22, 'Mac OS X (iPhone)', '{\"result\": {\"ip\": \"14.150.145.235\", \"ad_info\": {\"city\": \"广州市\", \"adcode\": 440100, \"nation\": \"中国\", \"district\": \"\", \"province\": \"广东省\", \"nation_code\": 156}, \"location\": {\"lat\": 23.12908, \"lng\": 113.26436}}, \"status\": 0, \"message\": \"Success\", \"request_id\": \"99ab321269834063b1585a807ed0fd37\"}');
INSERT INTO `tb_login_record` VALUES (104, '14.150.145.235', '2023-03-29 00:35:51', 22, 'Mac OS X (iPhone)', '{\"result\": {\"ip\": \"14.150.145.235\", \"ad_info\": {\"city\": \"广州市\", \"adcode\": 440100, \"nation\": \"中国\", \"district\": \"\", \"province\": \"广东省\", \"nation_code\": 156}, \"location\": {\"lat\": 23.12908, \"lng\": 113.26436}}, \"status\": 0, \"message\": \"Success\", \"request_id\": \"f2ef6efa046744d8a2680cf3716a0713\"}');
INSERT INTO `tb_login_record` VALUES (105, '14.150.251.210', '2023-03-29 00:36:10', 22, 'Mac OS X (iPhone)', '{\"result\": {\"ip\": \"14.150.251.210\", \"ad_info\": {\"city\": \"深圳市\", \"adcode\": 440300, \"nation\": \"中国\", \"district\": \"\", \"province\": \"广东省\", \"nation_code\": 156}, \"location\": {\"lat\": 22.54286, \"lng\": 114.05956}}, \"status\": 0, \"message\": \"Success\", \"request_id\": \"1122e0dd45ba446dab0179af5efd7dc9\"}');
INSERT INTO `tb_login_record` VALUES (106, '14.150.145.235', '2023-03-29 00:36:36', 22, 'Mac OS X (iPhone)', '{\"result\": {\"ip\": \"14.150.145.235\", \"ad_info\": {\"city\": \"广州市\", \"adcode\": 440100, \"nation\": \"中国\", \"district\": \"\", \"province\": \"广东省\", \"nation_code\": 156}, \"location\": {\"lat\": 23.12908, \"lng\": 113.26436}}, \"status\": 0, \"message\": \"Success\", \"request_id\": \"c18d68b6fee849e4bc0c0bf80157ccc5\"}');
INSERT INTO `tb_login_record` VALUES (107, '14.150.251.210', '2023-03-29 00:37:04', 22, 'Mac OS X (iPhone)', '{\"result\": {\"ip\": \"14.150.251.210\", \"ad_info\": {\"city\": \"深圳市\", \"adcode\": 440300, \"nation\": \"中国\", \"district\": \"\", \"province\": \"广东省\", \"nation_code\": 156}, \"location\": {\"lat\": 22.54286, \"lng\": 114.05956}}, \"status\": 0, \"message\": \"Success\", \"request_id\": \"c9c7e8b759a94290afeb9f7f53f334ca\"}');
INSERT INTO `tb_login_record` VALUES (108, '14.150.145.235', '2023-03-29 00:37:43', 22, 'Mac OS X (iPhone)', '{\"result\": {\"ip\": \"14.150.145.235\", \"ad_info\": {\"city\": \"广州市\", \"adcode\": 440100, \"nation\": \"中国\", \"district\": \"\", \"province\": \"广东省\", \"nation_code\": 156}, \"location\": {\"lat\": 23.12908, \"lng\": 113.26436}}, \"status\": 0, \"message\": \"Success\", \"request_id\": \"1ef6e56fbf9140a1a446eaf6667e10d5\"}');
INSERT INTO `tb_login_record` VALUES (109, '14.150.251.210', '2023-03-29 00:40:06', 22, 'Mac OS X (iPhone)', '{\"result\": {\"ip\": \"14.150.251.210\", \"ad_info\": {\"city\": \"深圳市\", \"adcode\": 440300, \"nation\": \"中国\", \"district\": \"\", \"province\": \"广东省\", \"nation_code\": 156}, \"location\": {\"lat\": 22.54286, \"lng\": 114.05956}}, \"status\": 0, \"message\": \"Success\", \"request_id\": \"5e5466b3a1314ca7a922907936c648f4\"}');
INSERT INTO `tb_login_record` VALUES (110, '14.150.145.235', '2023-03-29 00:43:40', 22, 'Mac OS X (iPhone)', '{\"result\": {\"ip\": \"14.150.145.235\", \"ad_info\": {\"city\": \"广州市\", \"adcode\": 440100, \"nation\": \"中国\", \"district\": \"\", \"province\": \"广东省\", \"nation_code\": 156}, \"location\": {\"lat\": 23.12908, \"lng\": 113.26436}}, \"status\": 0, \"message\": \"Success\", \"request_id\": \"9777332ebe414d01bbc30593d9897042\"}');
INSERT INTO `tb_login_record` VALUES (111, '0:0:0:0:0:0:0:1', '2023-03-29 13:55:42', 20, 'Windows 10', '{\"status\": 375, \"message\": \"局域网IP\", \"request_id\": \"3a1bad9f71aa4a9c956f6df21d6a48d2\"}');
INSERT INTO `tb_login_record` VALUES (112, '120.235.248.108', '2023-03-29 14:44:23', 22, 'Windows 10', '{\"result\": {\"ip\": \"120.235.248.108\", \"ad_info\": {\"city\": \"湛江市\", \"adcode\": 440811, \"nation\": \"中国\", \"district\": \"麻章区\", \"province\": \"广东省\", \"nation_code\": 156}, \"location\": {\"lat\": 21.26331, \"lng\": 110.33427}}, \"status\": 0, \"message\": \"Success\", \"request_id\": \"806a2d876bcd4bcda68436a4534e2a1b\"}');
INSERT INTO `tb_login_record` VALUES (113, '120.235.248.108', '2023-03-29 14:45:05', 22, 'Windows 10', '{\"result\": {\"ip\": \"120.235.248.108\", \"ad_info\": {\"city\": \"湛江市\", \"adcode\": 440811, \"nation\": \"中国\", \"district\": \"麻章区\", \"province\": \"广东省\", \"nation_code\": 156}, \"location\": {\"lat\": 21.26331, \"lng\": 110.33427}}, \"status\": 0, \"message\": \"Success\", \"request_id\": \"b664685304bd4c8e9430983f4da8db06\"}');
INSERT INTO `tb_login_record` VALUES (114, '14.150.251.210', '2023-03-29 15:54:13', 22, 'Windows 10', '{\"result\": {\"ip\": \"14.150.251.210\", \"ad_info\": {\"city\": \"深圳市\", \"adcode\": 440300, \"nation\": \"中国\", \"district\": \"\", \"province\": \"广东省\", \"nation_code\": 156}, \"location\": {\"lat\": 22.54286, \"lng\": 114.05956}}, \"status\": 0, \"message\": \"Success\", \"request_id\": \"29693c3d3b084a1cb33ef878d5812f04\"}');
INSERT INTO `tb_login_record` VALUES (115, '0:0:0:0:0:0:0:1', '2023-03-29 17:33:23', 20, 'Windows 10', '{\"status\": 375, \"message\": \"局域网IP\", \"request_id\": \"5d5a587bef3c4deeae3557e77ec031a4\"}');
INSERT INTO `tb_login_record` VALUES (116, '0:0:0:0:0:0:0:1', '2023-03-29 20:46:56', 20, 'Windows 10', '{\"status\": 375, \"message\": \"局域网IP\", \"request_id\": \"056f29af98d142109e06e10740c591dd\"}');
INSERT INTO `tb_login_record` VALUES (117, '119.143.234.231', '2023-03-29 22:07:49', 22, 'Windows 10', '{\"result\": {\"ip\": \"119.143.234.231\", \"ad_info\": {\"city\": \"广州市\", \"adcode\": 440100, \"nation\": \"中国\", \"district\": \"\", \"province\": \"广东省\", \"nation_code\": 156}, \"location\": {\"lat\": 23.12908, \"lng\": 113.26436}}, \"status\": 0, \"message\": \"Success\", \"request_id\": \"a2b98dd213d34052832df406019b90e5\"}');
INSERT INTO `tb_login_record` VALUES (118, '119.143.234.231', '2023-03-29 22:07:50', 22, 'Windows 10', '{\"result\": {\"ip\": \"119.143.234.231\", \"ad_info\": {\"city\": \"广州市\", \"adcode\": 440100, \"nation\": \"中国\", \"district\": \"\", \"province\": \"广东省\", \"nation_code\": 156}, \"location\": {\"lat\": 23.12908, \"lng\": 113.26436}}, \"status\": 0, \"message\": \"Success\", \"request_id\": \"6ebcd4410a08466ba3d2b9a1c9bbb566\"}');
INSERT INTO `tb_login_record` VALUES (119, '0:0:0:0:0:0:0:1', '2023-03-30 20:03:03', 20, 'Windows 10', '{\"status\": 375, \"message\": \"局域网IP\", \"request_id\": \"a6447a6a9b694454b64ad1914f848f04\"}');
INSERT INTO `tb_login_record` VALUES (120, '0:0:0:0:0:0:0:1', '2023-03-30 21:05:12', 20, 'Windows 10', '{\"status\": 375, \"message\": \"局域网IP\", \"request_id\": \"42971d59232a4070b83daaa29f395f25\"}');
INSERT INTO `tb_login_record` VALUES (121, '0:0:0:0:0:0:0:1', '2023-03-30 22:25:35', 20, 'Windows 10', '{\"status\": 375, \"message\": \"局域网IP\", \"request_id\": \"7d6cf322e14d4cffbfcb4706777b8046\"}');
INSERT INTO `tb_login_record` VALUES (122, '183.46.72.59', '2023-03-30 22:59:25', 22, 'Windows 10', '{\"result\": {\"ip\": \"183.46.72.59\", \"ad_info\": {\"city\": \"珠海市\", \"adcode\": 440400, \"nation\": \"中国\", \"district\": \"\", \"province\": \"广东省\", \"nation_code\": 156}, \"location\": {\"lat\": 22.27073, \"lng\": 113.57668}}, \"status\": 0, \"message\": \"Success\", \"request_id\": \"0e5feb9798fb425a82c4a7ebe12929b3\"}');
INSERT INTO `tb_login_record` VALUES (123, '183.46.72.59', '2023-03-30 23:15:04', 22, 'Windows 10', '{\"result\": {\"ip\": \"183.46.72.59\", \"ad_info\": {\"city\": \"珠海市\", \"adcode\": 440400, \"nation\": \"中国\", \"district\": \"\", \"province\": \"广东省\", \"nation_code\": 156}, \"location\": {\"lat\": 22.27073, \"lng\": 113.57668}}, \"status\": 0, \"message\": \"Success\", \"request_id\": \"33b807034f3d42d38866d8404c9171b8\"}');
INSERT INTO `tb_login_record` VALUES (124, '0:0:0:0:0:0:0:1', '2023-03-31 10:29:25', 22, 'Windows 10', '{\"status\": 375, \"message\": \"局域网IP\", \"request_id\": \"fb2aee6cc53948d4b83fb1073d687941\"}');
INSERT INTO `tb_login_record` VALUES (125, '0:0:0:0:0:0:0:1', '2023-03-31 10:53:00', 22, 'Windows 10', '{\"status\": 375, \"message\": \"局域网IP\", \"request_id\": \"4035888f6f9c4fdba95cc3a7eb03d9cd\"}');
INSERT INTO `tb_login_record` VALUES (126, '0:0:0:0:0:0:0:1', '2023-03-31 11:00:36', 22, 'Windows 10', '{\"status\": 375, \"message\": \"局域网IP\", \"request_id\": \"0ca56fb260e0448baaed54c4c9caaa7f\"}');
INSERT INTO `tb_login_record` VALUES (127, '0:0:0:0:0:0:0:1', '2023-03-31 11:04:09', 22, 'Windows 10', '{\"status\": 375, \"message\": \"局域网IP\", \"request_id\": \"5115b2b3eeeb4e58b76328f2c0c06f15\"}');
INSERT INTO `tb_login_record` VALUES (128, '0:0:0:0:0:0:0:1', '2023-03-31 12:32:47', 22, 'Windows 10', '{\"status\": 375, \"message\": \"局域网IP\", \"request_id\": \"8b4f3df6ae5743abbbb2199c5b83d186\"}');
INSERT INTO `tb_login_record` VALUES (129, '120.235.248.108', '2023-03-31 14:21:34', 22, 'Windows 10', '{\"result\": {\"ip\": \"120.235.248.108\", \"ad_info\": {\"city\": \"湛江市\", \"adcode\": 440811, \"nation\": \"中国\", \"district\": \"麻章区\", \"province\": \"广东省\", \"nation_code\": 156}, \"location\": {\"lat\": 21.26331, \"lng\": 110.33427}}, \"status\": 0, \"message\": \"Success\", \"request_id\": \"faa976e09a1d4338b59b74ad3ed34fb0\"}');
INSERT INTO `tb_login_record` VALUES (130, '14.150.107.174', '2023-03-31 17:29:19', 22, 'Windows 10', '{\"result\": {\"ip\": \"14.150.107.174\", \"ad_info\": {\"city\": \"东莞市\", \"adcode\": 441900, \"nation\": \"中国\", \"district\": \"\", \"province\": \"广东省\", \"nation_code\": 156}, \"location\": {\"lat\": 23.02067, \"lng\": 113.75179}}, \"status\": 0, \"message\": \"Success\", \"request_id\": \"f9b44a5069454ed7be25759344fd6114\"}');
INSERT INTO `tb_login_record` VALUES (131, '14.150.107.174', '2023-03-31 17:37:22', 22, 'Windows 10', '{\"result\": {\"ip\": \"14.150.107.174\", \"ad_info\": {\"city\": \"东莞市\", \"adcode\": 441900, \"nation\": \"中国\", \"district\": \"\", \"province\": \"广东省\", \"nation_code\": 156}, \"location\": {\"lat\": 23.02067, \"lng\": 113.75179}}, \"status\": 0, \"message\": \"Success\", \"request_id\": \"2bf6f12ab1104fd78364ab9fffa22dbc\"}');
INSERT INTO `tb_login_record` VALUES (132, '14.150.107.174', '2023-03-31 17:40:16', 22, 'Windows 10', '{\"result\": {\"ip\": \"14.150.107.174\", \"ad_info\": {\"city\": \"东莞市\", \"adcode\": 441900, \"nation\": \"中国\", \"district\": \"\", \"province\": \"广东省\", \"nation_code\": 156}, \"location\": {\"lat\": 23.02067, \"lng\": 113.75179}}, \"status\": 0, \"message\": \"Success\", \"request_id\": \"2d1d8a3d37234b7b96188a2a8642c447\"}');
INSERT INTO `tb_login_record` VALUES (133, '14.150.107.174', '2023-03-31 17:47:05', 22, 'Windows 10', '{\"result\": {\"ip\": \"14.150.107.174\", \"ad_info\": {\"city\": \"东莞市\", \"adcode\": 441900, \"nation\": \"中国\", \"district\": \"\", \"province\": \"广东省\", \"nation_code\": 156}, \"location\": {\"lat\": 23.02067, \"lng\": 113.75179}}, \"status\": 0, \"message\": \"Success\", \"request_id\": \"9b3f34aea66143dea662baba7d68e0af\"}');
INSERT INTO `tb_login_record` VALUES (134, '14.150.107.174', '2023-03-31 17:55:33', 22, 'Windows 10', '{\"result\": {\"ip\": \"14.150.107.174\", \"ad_info\": {\"city\": \"东莞市\", \"adcode\": 441900, \"nation\": \"中国\", \"district\": \"\", \"province\": \"广东省\", \"nation_code\": 156}, \"location\": {\"lat\": 23.02067, \"lng\": 113.75179}}, \"status\": 0, \"message\": \"Success\", \"request_id\": \"451b422966b04e1c89a2f9d87b3fb153\"}');
INSERT INTO `tb_login_record` VALUES (135, '14.150.107.174', '2023-03-31 18:20:20', 22, 'Windows 10', '{\"result\": {\"ip\": \"14.150.107.174\", \"ad_info\": {\"city\": \"东莞市\", \"adcode\": 441900, \"nation\": \"中国\", \"district\": \"\", \"province\": \"广东省\", \"nation_code\": 156}, \"location\": {\"lat\": 23.02067, \"lng\": 113.75179}}, \"status\": 0, \"message\": \"Success\", \"request_id\": \"5f84f75226334cfcaffe0637de39187b\"}');
INSERT INTO `tb_login_record` VALUES (136, '14.150.107.174', '2023-03-31 18:21:18', 22, 'Windows 10', '{\"result\": {\"ip\": \"14.150.107.174\", \"ad_info\": {\"city\": \"东莞市\", \"adcode\": 441900, \"nation\": \"中国\", \"district\": \"\", \"province\": \"广东省\", \"nation_code\": 156}, \"location\": {\"lat\": 23.02067, \"lng\": 113.75179}}, \"status\": 0, \"message\": \"Success\", \"request_id\": \"0c1f8957bd2c4fd9ade774b0a7948ff5\"}');
INSERT INTO `tb_login_record` VALUES (137, '14.150.107.174', '2023-03-31 18:23:51', 22, 'Windows 10', '{\"result\": {\"ip\": \"14.150.107.174\", \"ad_info\": {\"city\": \"东莞市\", \"adcode\": 441900, \"nation\": \"中国\", \"district\": \"\", \"province\": \"广东省\", \"nation_code\": 156}, \"location\": {\"lat\": 23.02067, \"lng\": 113.75179}}, \"status\": 0, \"message\": \"Success\", \"request_id\": \"bed86399bde449c0a542cc78f03d65cf\"}');
INSERT INTO `tb_login_record` VALUES (138, '14.150.107.174', '2023-03-31 18:29:23', 22, 'Windows 10', '{\"result\": {\"ip\": \"14.150.107.174\", \"ad_info\": {\"city\": \"东莞市\", \"adcode\": 441900, \"nation\": \"中国\", \"district\": \"\", \"province\": \"广东省\", \"nation_code\": 156}, \"location\": {\"lat\": 23.02067, \"lng\": 113.75179}}, \"status\": 0, \"message\": \"Success\", \"request_id\": \"d54a5432ea1d412fa1e67d26afe08123\"}');
INSERT INTO `tb_login_record` VALUES (139, '14.150.107.174', '2023-03-31 18:40:06', 22, 'Windows 10', '{\"result\": {\"ip\": \"14.150.107.174\", \"ad_info\": {\"city\": \"东莞市\", \"adcode\": 441900, \"nation\": \"中国\", \"district\": \"\", \"province\": \"广东省\", \"nation_code\": 156}, \"location\": {\"lat\": 23.02067, \"lng\": 113.75179}}, \"status\": 0, \"message\": \"Success\", \"request_id\": \"5fb0cc6baa5b4d17af0ed7bd7c15bb2f\"}');
INSERT INTO `tb_login_record` VALUES (140, '14.150.107.174', '2023-03-31 18:44:58', 22, 'Windows 10', '{\"result\": {\"ip\": \"14.150.107.174\", \"ad_info\": {\"city\": \"东莞市\", \"adcode\": 441900, \"nation\": \"中国\", \"district\": \"\", \"province\": \"广东省\", \"nation_code\": 156}, \"location\": {\"lat\": 23.02067, \"lng\": 113.75179}}, \"status\": 0, \"message\": \"Success\", \"request_id\": \"507762b86ad24fccbfa751b33679154f\"}');
INSERT INTO `tb_login_record` VALUES (141, '0:0:0:0:0:0:0:1', '2023-03-31 19:01:29', 20, 'Windows 10', '{\"status\": 375, \"message\": \"局域网IP\", \"request_id\": \"170ae71d6d644ce1b7438a55e7d35f64\"}');
INSERT INTO `tb_login_record` VALUES (142, '14.150.107.174', '2023-03-31 19:16:49', 22, 'Windows 10', '{\"result\": {\"ip\": \"14.150.107.174\", \"ad_info\": {\"city\": \"东莞市\", \"adcode\": 441900, \"nation\": \"中国\", \"district\": \"\", \"province\": \"广东省\", \"nation_code\": 156}, \"location\": {\"lat\": 23.02067, \"lng\": 113.75179}}, \"status\": 0, \"message\": \"Success\", \"request_id\": \"f8061201c14d4a3c91ce4bf32f1c6618\"}');
INSERT INTO `tb_login_record` VALUES (143, '14.150.107.174', '2023-03-31 19:20:16', 22, 'Windows 10', '{\"result\": {\"ip\": \"14.150.107.174\", \"ad_info\": {\"city\": \"东莞市\", \"adcode\": 441900, \"nation\": \"中国\", \"district\": \"\", \"province\": \"广东省\", \"nation_code\": 156}, \"location\": {\"lat\": 23.02067, \"lng\": 113.75179}}, \"status\": 0, \"message\": \"Success\", \"request_id\": \"f5d9a4472c584eeabdc387944d3881f0\"}');
INSERT INTO `tb_login_record` VALUES (144, '0:0:0:0:0:0:0:1', '2023-03-31 19:46:29', 20, 'Windows 10', '{\"status\": 375, \"message\": \"局域网IP\", \"request_id\": \"c9084c5f7b5e432592bd448c2ae23e0a\"}');
INSERT INTO `tb_login_record` VALUES (145, '0:0:0:0:0:0:0:1', '2023-03-31 20:47:20', 20, 'Windows 10', '{\"status\": 375, \"message\": \"局域网IP\", \"request_id\": \"d3188b2849044918b71046b1a08b1bdb\"}');
INSERT INTO `tb_login_record` VALUES (146, '0:0:0:0:0:0:0:1', '2023-03-31 20:47:24', 20, 'Windows 10', '{\"status\": 375, \"message\": \"局域网IP\", \"request_id\": \"56b69b8d646542fa88ac3a21a0f7a4da\"}');
INSERT INTO `tb_login_record` VALUES (147, '0:0:0:0:0:0:0:1', '2023-03-31 20:47:28', 20, 'Windows 10', '{\"status\": 375, \"message\": \"局域网IP\", \"request_id\": \"a0470f7ee8d243e8bf827399c2b31108\"}');
INSERT INTO `tb_login_record` VALUES (148, '0:0:0:0:0:0:0:1', '2023-03-31 20:47:36', 20, 'Windows 10', '{\"status\": 375, \"message\": \"局域网IP\", \"request_id\": \"97fc663f34bf43d48ee4d59f9d67ce92\"}');
INSERT INTO `tb_login_record` VALUES (149, '0:0:0:0:0:0:0:1', '2023-03-31 20:51:47', 20, 'Windows 10', '{\"status\": 375, \"message\": \"局域网IP\", \"request_id\": \"9967c98298aa4bf0bc418d7afb95d5e9\"}');
INSERT INTO `tb_login_record` VALUES (150, '0:0:0:0:0:0:0:1', '2023-03-31 20:59:22', 20, 'Windows 10', '{\"status\": 375, \"message\": \"局域网IP\", \"request_id\": \"789510cd6f304be6accac5fe848b3f04\"}');
INSERT INTO `tb_login_record` VALUES (151, '0:0:0:0:0:0:0:1', '2023-03-31 20:59:28', 20, 'Windows 10', '{\"status\": 375, \"message\": \"局域网IP\", \"request_id\": \"d734b90a220049b1b7bd800004bd9594\"}');
INSERT INTO `tb_login_record` VALUES (152, '0:0:0:0:0:0:0:1', '2023-03-31 20:59:45', 20, 'Windows 10', '{\"status\": 375, \"message\": \"局域网IP\", \"request_id\": \"305518508070451f86d1ca17d17880bc\"}');
INSERT INTO `tb_login_record` VALUES (153, '0:0:0:0:0:0:0:1', '2023-03-31 20:59:47', 20, 'Windows 10', '{\"status\": 375, \"message\": \"局域网IP\", \"request_id\": \"ffada07bcde54e1cabee44100a8d0073\"}');
INSERT INTO `tb_login_record` VALUES (154, '0:0:0:0:0:0:0:1', '2023-03-31 20:59:51', 20, 'Windows 10', '{\"status\": 375, \"message\": \"局域网IP\", \"request_id\": \"7ef34af3dcff4026830f062db968b760\"}');
INSERT INTO `tb_login_record` VALUES (155, '0:0:0:0:0:0:0:1', '2023-03-31 21:10:03', 20, 'Windows 10', '{\"status\": 375, \"message\": \"局域网IP\", \"request_id\": \"0bd98ac7ffbd46fdbc28c61cd4b7fe29\"}');
INSERT INTO `tb_login_record` VALUES (156, '0:0:0:0:0:0:0:1', '2023-03-31 21:22:41', 20, 'Unknown', '{\"status\": 375, \"message\": \"局域网IP\", \"request_id\": \"5b67cc3cc0e8452aa7f5a5cb59f3365c\"}');
INSERT INTO `tb_login_record` VALUES (157, '0:0:0:0:0:0:0:1', '2023-03-31 21:22:47', 20, 'Windows 10', '{\"status\": 375, \"message\": \"局域网IP\", \"request_id\": \"43f2f646a96d4c35bc14288b90652ec2\"}');
INSERT INTO `tb_login_record` VALUES (158, '0:0:0:0:0:0:0:1', '2023-03-31 21:23:36', 20, 'Windows 10', '{\"status\": 375, \"message\": \"局域网IP\", \"request_id\": \"21845c575b964fcab07b8ab589f9e21a\"}');
INSERT INTO `tb_login_record` VALUES (159, '0:0:0:0:0:0:0:1', '2023-03-31 21:23:44', 20, 'Windows 10', '{\"status\": 375, \"message\": \"局域网IP\", \"request_id\": \"f2e0732d47064d6692a840170bf3a562\"}');
INSERT INTO `tb_login_record` VALUES (160, '0:0:0:0:0:0:0:1', '2023-03-31 21:23:47', 20, 'Windows 10', '{\"status\": 375, \"message\": \"局域网IP\", \"request_id\": \"dcedcc0a2047451fbb11d4dee9d4d37d\"}');
INSERT INTO `tb_login_record` VALUES (161, '0:0:0:0:0:0:0:1', '2023-04-01 15:23:38', 20, 'Windows 10', '{\"status\": 375, \"message\": \"局域网IP\", \"request_id\": \"bd730959806e46249abb2f996a520ee5\"}');
INSERT INTO `tb_login_record` VALUES (162, '0:0:0:0:0:0:0:1', '2023-04-01 16:55:40', 20, 'Windows 10', '{\"status\": 375, \"message\": \"局域网IP\", \"request_id\": \"3c27e2ff2e584934aa16186671e3734b\"}');
INSERT INTO `tb_login_record` VALUES (163, '0:0:0:0:0:0:0:1', '2023-04-01 17:04:12', 20, 'Unknown', '{\"status\": 375, \"message\": \"局域网IP\", \"request_id\": \"562f13e01c37490c8eb18dd3263479d6\"}');
INSERT INTO `tb_login_record` VALUES (164, '0:0:0:0:0:0:0:1', '2023-04-01 17:04:24', 20, 'Windows 10', '{\"status\": 375, \"message\": \"局域网IP\", \"request_id\": \"5a98f405779e45d1a60f1de0b05a7ade\"}');
INSERT INTO `tb_login_record` VALUES (165, '0:0:0:0:0:0:0:1', '2023-04-01 17:21:26', 20, 'Windows 10', '{\"status\": 375, \"message\": \"局域网IP\", \"request_id\": \"23ffe687508a4fdd86f90e52924455af\"}');
INSERT INTO `tb_login_record` VALUES (166, '183.46.33.39', '2023-04-01 22:00:08', 22, 'Windows 10', '{\"result\": {\"ip\": \"183.46.33.39\", \"ad_info\": {\"city\": \"云浮市\", \"adcode\": 445300, \"nation\": \"中国\", \"district\": \"\", \"province\": \"广东省\", \"nation_code\": 156}, \"location\": {\"lat\": 22.91525, \"lng\": 112.04453}}, \"status\": 0, \"message\": \"Success\", \"request_id\": \"60d91eca327b4871a38663b5b9f5dd63\"}');
INSERT INTO `tb_login_record` VALUES (167, '119.143.230.43', '2023-04-02 00:51:39', 22, 'Windows 10', '{\"result\": {\"ip\": \"119.143.230.43\", \"ad_info\": {\"city\": \"深圳市\", \"adcode\": 440300, \"nation\": \"中国\", \"district\": \"\", \"province\": \"广东省\", \"nation_code\": 156}, \"location\": {\"lat\": 22.54286, \"lng\": 114.05956}}, \"status\": 0, \"message\": \"Success\", \"request_id\": \"da0ef853225f4d8fb4e732f4fab73954\"}');
INSERT INTO `tb_login_record` VALUES (168, '120.235.248.108', '2023-04-02 11:55:38', 22, 'Windows 10', '{\"result\": {\"ip\": \"120.235.248.108\", \"ad_info\": {\"city\": \"湛江市\", \"adcode\": 440811, \"nation\": \"中国\", \"district\": \"麻章区\", \"province\": \"广东省\", \"nation_code\": 156}, \"location\": {\"lat\": 21.26331, \"lng\": 110.33427}}, \"status\": 0, \"message\": \"Success\", \"request_id\": \"1df0addfbc934653895ef15544e6f45f\"}');
INSERT INTO `tb_login_record` VALUES (169, '119.143.230.43', '2023-04-02 12:54:32', 22, 'Windows 10', '{\"result\": {\"ip\": \"119.143.230.43\", \"ad_info\": {\"city\": \"广州市\", \"adcode\": 440100, \"nation\": \"中国\", \"district\": \"\", \"province\": \"广东省\", \"nation_code\": 156}, \"location\": {\"lat\": 23.12908, \"lng\": 113.26436}}, \"status\": 0, \"message\": \"Success\", \"request_id\": \"3a1b9baea39640fd8a3793eb91010b09\"}');
INSERT INTO `tb_login_record` VALUES (170, '0:0:0:0:0:0:0:1', '2023-04-02 15:28:23', 20, 'Unknown', '{\"status\": 375, \"message\": \"局域网IP\", \"request_id\": \"4a583ce608bc491fb96af1690b1e74bf\"}');
INSERT INTO `tb_login_record` VALUES (171, '0:0:0:0:0:0:0:1', '2023-04-02 16:13:31', 20, 'Windows 10', '{\"status\": 375, \"message\": \"局域网IP\", \"request_id\": \"2d430293c1614e66a7606a888e289d52\"}');
INSERT INTO `tb_login_record` VALUES (172, '120.235.248.108, 172.18.0.4', '2023-04-02 18:42:29', 22, 'Windows 10', '{\"status\": 348, \"message\": \"参数错误，以下参数不合法：ip\", \"request_id\": \"0c611194f95049ea89c4d9e41e397bac\"}');
INSERT INTO `tb_login_record` VALUES (173, '120.235.248.108, 172.18.0.4', '2023-04-03 17:56:10', 22, 'Windows 10', '{\"status\": 348, \"message\": \"参数错误，以下参数不合法：ip\", \"request_id\": \"f8f046909e2b4883974dd9f968a5c7ea\"}');
INSERT INTO `tb_login_record` VALUES (174, '183.46.171.180, 172.18.0.4', '2023-04-04 17:41:52', 22, 'Windows 10', '{\"status\": 348, \"message\": \"参数错误，以下参数不合法：ip\", \"request_id\": \"b7ae2c30c78142ce89659344d6e1d074\"}');
INSERT INTO `tb_login_record` VALUES (175, '183.46.171.180', '2023-04-04 18:34:05', 22, 'Windows 10', '{\"result\": {\"ip\": \"183.46.171.180\", \"ad_info\": {\"city\": \"东莞市\", \"adcode\": 441900, \"nation\": \"中国\", \"district\": \"\", \"province\": \"广东省\", \"nation_code\": 156}, \"location\": {\"lat\": 23.02067, \"lng\": 113.75179}}, \"status\": 0, \"message\": \"Success\", \"request_id\": \"5130b628b49e4203af90344dd4bec73b\"}');
INSERT INTO `tb_login_record` VALUES (176, '183.46.171.180', '2023-04-04 18:39:09', 22, 'Windows 10', '{\"result\": {\"ip\": \"183.46.171.180\", \"ad_info\": {\"city\": \"东莞市\", \"adcode\": 441900, \"nation\": \"中国\", \"district\": \"\", \"province\": \"广东省\", \"nation_code\": 156}, \"location\": {\"lat\": 23.02067, \"lng\": 113.75179}}, \"status\": 0, \"message\": \"Success\", \"request_id\": \"422b98912a9444d69d074269e37189a4\"}');
INSERT INTO `tb_login_record` VALUES (177, '183.46.171.180', '2023-04-04 18:43:05', 22, 'Windows 10', '{\"result\": {\"ip\": \"183.46.171.180\", \"ad_info\": {\"city\": \"东莞市\", \"adcode\": 441900, \"nation\": \"中国\", \"district\": \"\", \"province\": \"广东省\", \"nation_code\": 156}, \"location\": {\"lat\": 23.02067, \"lng\": 113.75179}}, \"status\": 0, \"message\": \"Success\", \"request_id\": \"f26dfd960b51417ea65b3d23e9e0f47b\"}');
INSERT INTO `tb_login_record` VALUES (178, '183.46.171.180', '2023-04-04 18:46:43', 22, 'Windows 10', '{\"result\": {\"ip\": \"183.46.171.180\", \"ad_info\": {\"city\": \"东莞市\", \"adcode\": 441900, \"nation\": \"中国\", \"district\": \"\", \"province\": \"广东省\", \"nation_code\": 156}, \"location\": {\"lat\": 23.02067, \"lng\": 113.75179}}, \"status\": 0, \"message\": \"Success\", \"request_id\": \"5a2296d22aaa4596825c410e3c899c8f\"}');
INSERT INTO `tb_login_record` VALUES (179, '183.46.171.180', '2023-04-04 18:55:09', 22, 'Windows 10', '{\"result\": {\"ip\": \"183.46.171.180\", \"ad_info\": {\"city\": \"东莞市\", \"adcode\": 441900, \"nation\": \"中国\", \"district\": \"\", \"province\": \"广东省\", \"nation_code\": 156}, \"location\": {\"lat\": 23.02067, \"lng\": 113.75179}}, \"status\": 0, \"message\": \"Success\", \"request_id\": \"abec1cabed924b1c863ff39eebb4e598\"}');
INSERT INTO `tb_login_record` VALUES (180, '183.46.171.180', '2023-04-04 19:01:07', 22, 'Windows 10', '{\"result\": {\"ip\": \"183.46.171.180\", \"ad_info\": {\"city\": \"东莞市\", \"adcode\": 441900, \"nation\": \"中国\", \"district\": \"\", \"province\": \"广东省\", \"nation_code\": 156}, \"location\": {\"lat\": 23.02067, \"lng\": 113.75179}}, \"status\": 0, \"message\": \"Success\", \"request_id\": \"4a44164aeee94fb0b984db14d83f6562\"}');
INSERT INTO `tb_login_record` VALUES (181, '183.46.171.180', '2023-04-04 19:03:02', 22, 'Windows 10', '{\"result\": {\"ip\": \"183.46.171.180\", \"ad_info\": {\"city\": \"东莞市\", \"adcode\": 441900, \"nation\": \"中国\", \"district\": \"\", \"province\": \"广东省\", \"nation_code\": 156}, \"location\": {\"lat\": 23.02067, \"lng\": 113.75179}}, \"status\": 0, \"message\": \"Success\", \"request_id\": \"9b59b2f5f3764c67950458af208a6dd6\"}');
INSERT INTO `tb_login_record` VALUES (182, '183.46.171.180', '2023-04-04 19:07:18', 22, 'Windows 10', '{\"result\": {\"ip\": \"183.46.171.180\", \"ad_info\": {\"city\": \"东莞市\", \"adcode\": 441900, \"nation\": \"中国\", \"district\": \"\", \"province\": \"广东省\", \"nation_code\": 156}, \"location\": {\"lat\": 23.02067, \"lng\": 113.75179}}, \"status\": 0, \"message\": \"Success\", \"request_id\": \"f91bd4ea8cc74c51a763de93bd08bf67\"}');
INSERT INTO `tb_login_record` VALUES (183, '183.46.171.180', '2023-04-04 19:14:21', 22, 'Windows 10', '{\"result\": {\"ip\": \"183.46.171.180\", \"ad_info\": {\"city\": \"东莞市\", \"adcode\": 441900, \"nation\": \"中国\", \"district\": \"\", \"province\": \"广东省\", \"nation_code\": 156}, \"location\": {\"lat\": 23.02067, \"lng\": 113.75179}}, \"status\": 0, \"message\": \"Success\", \"request_id\": \"0545bf3930cf403c8597e6d8b7bdfa69\"}');
INSERT INTO `tb_login_record` VALUES (184, '120.235.249.146, 172.18.0.4', '2023-04-04 21:49:38', 22, 'Windows 10', '{\"status\": 348, \"message\": \"参数错误，以下参数不合法：ip\", \"request_id\": \"b9c7afc375e5495d863e820224a9dfa3\"}');
INSERT INTO `tb_login_record` VALUES (185, '120.235.249.146, 172.18.0.4', '2023-04-05 13:41:06', 30, 'Windows 10', '{\"status\": 348, \"message\": \"参数错误，以下参数不合法：ip\", \"request_id\": \"58cab648e8824e4a8dd3bae30f7cafea\"}');

-- ----------------------------
-- Table structure for tb_respheader_ctrl
-- ----------------------------
DROP TABLE IF EXISTS `tb_respheader_ctrl`;
CREATE TABLE `tb_respheader_ctrl`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uid` int(11) NULL DEFAULT NULL,
  `respheader` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `value` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_respheader_ctrl
-- ----------------------------
INSERT INTO `tb_respheader_ctrl` VALUES (2, 20, 'Content-Type', 'text/html');
INSERT INTO `tb_respheader_ctrl` VALUES (3, 20, 'Cotent-Ba', 'sbxll');
INSERT INTO `tb_respheader_ctrl` VALUES (4, 20, 'Cotent-Ba', '12345');

-- ----------------------------
-- Table structure for tb_sysuser
-- ----------------------------
DROP TABLE IF EXISTS `tb_sysuser`;
CREATE TABLE `tb_sysuser`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `salt` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `email` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 31 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_sysuser
-- ----------------------------
INSERT INTO `tb_sysuser` VALUES (6, 'xcc', 'd72e6232a17628f1', '7cd1f34ccaa7ef14d7813618a474cf1d', NULL);
INSERT INTO `tb_sysuser` VALUES (7, 'xc', '23a63571aa21b860', '4cfe9e1abea78fcfc4a9f683e9116bed', NULL);
INSERT INTO `tb_sysuser` VALUES (8, 'yy', 'dcd46c66368f48a6', '90d38d6e1d0c9639f924d39dde1b0846', '123@qq.com');
INSERT INTO `tb_sysuser` VALUES (9, 'koji', '9f8e255f7afe1381', '3e9c1fd92c168aad26b68fa8ebb36f94', '321@qq.com');
INSERT INTO `tb_sysuser` VALUES (10, 'TOBY', '2dd1f79f7cf932f1', '20eb6616e593477870506b2384e1b459', '1505994122@qq.com');
INSERT INTO `tb_sysuser` VALUES (11, '', 'd2b8bfb2736b0d70', '3b2e9f3255831af1bbbc2a98fd6cbc64', '');
INSERT INTO `tb_sysuser` VALUES (12, '1', 'e9492a649919fc46', '28dce353e481d2b251e44922a29cf592', '1');
INSERT INTO `tb_sysuser` VALUES (13, 'cxx', '49d033c73b9b42e9', '497d8211960467ca0d9c564c2a7842d1', '14356456789@qq.com');
INSERT INTO `tb_sysuser` VALUES (14, 'asd', 'f354b0cb0a80cca4', 'ff24b3b5e63ca2d4a02cab94a0872c41', '2');
INSERT INTO `tb_sysuser` VALUES (15, '123', '5c4583a2d901e72a', 'b5fcefda9749a8e023fb15d0e4840840', '123');
INSERT INTO `tb_sysuser` VALUES (16, '12345', '4a4ea01e093c5dfa', 'b8355751349dfd186fa5734c0ce3425b', '1211');
INSERT INTO `tb_sysuser` VALUES (17, '14444', 'e8110b8d409d2217', '8e6b90e8597b05290149632c557e7abe', '444');
INSERT INTO `tb_sysuser` VALUES (18, '777', '65d411ddc9bdd915', '9a01eaab01996d50c3ab396ac9d6c02e', '777');
INSERT INTO `tb_sysuser` VALUES (20, 'yyy', '909dfb6e3237936b247ca4a542b814e3072a52be', 'turing_oss', 'string');
INSERT INTO `tb_sysuser` VALUES (21, '3', '46451cb51a8425b8857803f2b2cfa588f81d3c9d', 'turing_oss', '3');
INSERT INTO `tb_sysuser` VALUES (22, '1212', '892dcc25bcc58d0ba07d36078158e3b2731f27bf', 'turing_oss', '1212');
INSERT INTO `tb_sysuser` VALUES (27, 'zzy', '909dfb6e3237936b247ca4a542b814e3072a52be', 'turing_oss', '123123');
INSERT INTO `tb_sysuser` VALUES (28, 'franzli', '691e9ea34894ef4813216de6707d4d5c20c25fb8', 'turing_oss', 'franz_li@yeah.net');
INSERT INTO `tb_sysuser` VALUES (29, 'franzli', '691e9ea34894ef4813216de6707d4d5c20c25fb8', 'turing_oss', 'franz_li@yeah.net');
INSERT INTO `tb_sysuser` VALUES (30, 'franzli11', '427a0e6a3d90b0fab1fad27ee386d4abe5f8ec7e', 'turing_oss', 'kiyoingachi@gmail.com');

SET FOREIGN_KEY_CHECKS = 1;
