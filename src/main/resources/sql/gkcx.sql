/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MariaDB
 Source Server Version : 100408
 Source Host           : localhost:3306
 Source Schema         : gkcx

 Target Server Type    : MariaDB
 Target Server Version : 100408
 File Encoding         : 65001

 Date: 10/07/2020 17:39:09
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for lp_menu
-- ----------------------------
DROP TABLE IF EXISTS `lp_menu`;
CREATE TABLE `lp_menu` (
  `id` varchar(50) NOT NULL COMMENT '菜单编号',
  `pid` varchar(50) DEFAULT NULL COMMENT '父节点ID',
  `menu_name` varchar(50) DEFAULT NULL COMMENT '菜单名称',
  `menu_url` varchar(100) DEFAULT NULL COMMENT '菜单连接地址',
  `clazz` varchar(100) DEFAULT 'layui-icon' COMMENT '菜单样式',
  `icon` varchar(20) DEFAULT NULL COMMENT '菜单图标',
  `is_using` char(1) DEFAULT '1' COMMENT '菜单状态（0:未启用；1:启用）',
  `ranking` int(11) DEFAULT NULL COMMENT '菜单显示顺序',
  `lev` int(11) DEFAULT 1 COMMENT '菜单级别',
  `update_time` datetime DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `is_leaf` int(5) DEFAULT 1 COMMENT '是否是叶子结点',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='菜单信息';

-- ----------------------------
-- Records of lp_menu
-- ----------------------------
BEGIN;
INSERT INTO `lp_menu` VALUES ('7DC3FE6BE11511E9873D54759506BA70', NULL, '系统管理', NULL, 'layui-icon layui-icon-set', NULL, '1', 3000, 1, '2019-10-17 09:46:32', 0);
INSERT INTO `lp_menu` VALUES ('7DE5420CE11511E9873D54759506BA70', '7DC3FE6BE11511E9873D54759506BA70', '用户管理', '/user/toList', 'layui-icon layui-icon-username', '', '1', 3001, 2, '2019-10-07 15:23:50', 1);
INSERT INTO `lp_menu` VALUES ('7E07971DE11511E9873D54759506BA70', '7DC3FE6BE11511E9873D54759506BA70', '权限管理', '/role/toList', 'layui-icon layui-icon-auz', '', '1', 3002, 2, '2019-10-16 06:52:16', 1);
COMMIT;

-- ----------------------------
-- Table structure for lp_org_info
-- ----------------------------
DROP TABLE IF EXISTS `lp_org_info`;
CREATE TABLE `lp_org_info` (
  `id` varchar(50) NOT NULL,
  `org_code` varchar(50) DEFAULT NULL COMMENT '组织机构代码',
  `org_name` varchar(50) DEFAULT NULL COMMENT '组织机构名称',
  `org_lev` char(1) DEFAULT '2' COMMENT '级别(1:总公司；2分公司)',
  `key_word` varchar(50) DEFAULT NULL COMMENT '关键字',
  `ranking` int(11) DEFAULT NULL COMMENT '排序',
  `update_time` datetime DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='组织机构信息';

-- ----------------------------
-- Records of lp_org_info
-- ----------------------------
BEGIN;
INSERT INTO `lp_org_info` VALUES ('1', 'BM0001', 'XXX公司', '1', 'XXX公司', 0, '2020-07-10 16:28:51');
INSERT INTO `lp_org_info` VALUES ('2', 'BM0002', '办公司', '2', '办公司', 1, '2020-07-10 16:30:03');
INSERT INTO `lp_org_info` VALUES ('3', 'BM0003', '产品部', '2', '产品部', 2, '2020-07-10 16:30:06');
COMMIT;

-- ----------------------------
-- Table structure for lp_role
-- ----------------------------
DROP TABLE IF EXISTS `lp_role`;
CREATE TABLE `lp_role` (
  `id` varchar(50) NOT NULL COMMENT '角色ID',
  `role_name` varchar(100) DEFAULT NULL COMMENT '角色名称',
  `data_authority` int(1) DEFAULT 3 COMMENT '数据权限（1:全部；2部门；3个人）',
  `is_using` char(1) DEFAULT '0' COMMENT '是否启动（1:是；0否）',
  `ranking` int(11) DEFAULT 1 COMMENT '排序',
  `update_time` datetime DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='角色信息';

-- ----------------------------
-- Records of lp_role
-- ----------------------------
BEGIN;
INSERT INTO `lp_role` VALUES ('GUANLIYUAN', '管理员', 1, '1', 1, '2020-07-10 16:31:16');
COMMIT;

-- ----------------------------
-- Table structure for lp_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `lp_role_menu`;
CREATE TABLE `lp_role_menu` (
  `role_id` varchar(50) NOT NULL COMMENT '角色ID',
  `menu_id` varchar(50) NOT NULL COMMENT '菜单ID',
  `data_authority` int(1) DEFAULT NULL COMMENT '菜单数据权限（1:全部；2部门；3个人）',
  `update_time` datetime DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='角色菜单中间表';

-- ----------------------------
-- Records of lp_role_menu
-- ----------------------------
BEGIN;
INSERT INTO `lp_role_menu` VALUES ('GUANLIYUAN', '7DC3FE6BE11511E9873D54759506BA70', 1, '2020-07-10 16:45:11');
INSERT INTO `lp_role_menu` VALUES ('GUANLIYUAN', '7DE5420CE11511E9873D54759506BA70', 1, '2020-07-10 16:45:11');
INSERT INTO `lp_role_menu` VALUES ('GUANLIYUAN', '7E07971DE11511E9873D54759506BA70', 1, '2020-07-10 16:45:11');
COMMIT;

-- ----------------------------
-- Table structure for lp_role_user
-- ----------------------------
DROP TABLE IF EXISTS `lp_role_user`;
CREATE TABLE `lp_role_user` (
  `role_id` varchar(50) NOT NULL COMMENT '角色ID',
  `user_id` varchar(50) NOT NULL COMMENT '用户ID',
  `update_time` datetime DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='角色用户中间表';

-- ----------------------------
-- Records of lp_role_user
-- ----------------------------
BEGIN;
INSERT INTO `lp_role_user` VALUES ('GUANLIYUAN', 'D8AB0A79C28811EAA67C02857F4BDD06', '2020-07-10 16:45:19');
COMMIT;

-- ----------------------------
-- Table structure for lp_score_info
-- ----------------------------
DROP TABLE IF EXISTS `lp_score_info`;
CREATE TABLE `lp_score_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `years` varchar(4) DEFAULT NULL COMMENT '年份',
  `ranking` int(11) DEFAULT NULL COMMENT '名次',
  `names` varchar(20) DEFAULT NULL COMMENT '姓名',
  `gender` varchar(10) DEFAULT NULL COMMENT '性别',
  `scores` varchar(10) DEFAULT NULL COMMENT '分数',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of lp_score_info
-- ----------------------------
BEGIN;
INSERT INTO `lp_score_info` VALUES (1, '2020', 1, '张三', '男', '702');
INSERT INTO `lp_score_info` VALUES (2, '2020', 2, '李四', '女', '699');
INSERT INTO `lp_score_info` VALUES (3, '2020', 3, '王伟', '男', '698');
INSERT INTO `lp_score_info` VALUES (4, '2019', 1, '小明', '男', '665');
INSERT INTO `lp_score_info` VALUES (5, '2019', 2, '郭晓', '男', '453');
COMMIT;

-- ----------------------------
-- Table structure for lp_user_info
-- ----------------------------
DROP TABLE IF EXISTS `lp_user_info`;
CREATE TABLE `lp_user_info` (
  `user_id` varchar(32) NOT NULL COMMENT '用户ID',
  `user_account` varchar(50) NOT NULL COMMENT '用户账号',
  `user_pwd` varchar(50) DEFAULT NULL COMMENT '用户密码',
  `user_name` varchar(100) DEFAULT NULL COMMENT '用户名称',
  `area_code` varchar(20) DEFAULT NULL COMMENT '行政区划代码',
  `area_name` varchar(100) DEFAULT NULL COMMENT '行政区划名称',
  `user_level` varchar(10) DEFAULT NULL COMMENT '用户级别1厅级，2地州级，3县市级',
  `org_id` varchar(32) DEFAULT NULL COMMENT '组织机构ID',
  `is_using` char(1) DEFAULT '0' COMMENT '是否启动（1:是；0否）',
  `roles` varchar(1000) DEFAULT NULL COMMENT '拥有的角色，冗余字段用作展示',
  `update_time` datetime DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `dept_id` varchar(50) DEFAULT NULL COMMENT '部门ID',
  `dept_name` varchar(100) DEFAULT NULL COMMENT '部门名称',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='用户信息';

-- ----------------------------
-- Records of lp_user_info
-- ----------------------------
BEGIN;
INSERT INTO `lp_user_info` VALUES ('D8AB0A79C28811EAA67C02857F4BDD06', 'zhangsan', 'e10adc3949ba59abbe56e057f20f883e', '张三', 'BM0002', '办公司', '2', '2', '1', '管理员', '2020-07-10 16:45:19', NULL, NULL);
COMMIT;

-- ----------------------------
-- View structure for lp_contract_info_view
-- ----------------------------
DROP VIEW IF EXISTS `lp_contract_info_view`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `lp_contract_info_view` AS select `t`.`uuid` AS `uuid`,`t`.`id` AS `id`,`t`.`data_from` AS `data_from`,`t`.`prefecture` AS `prefecture`,`t`.`county` AS `county`,`t`.`org` AS `org`,`t`.`project_id` AS `project_id`,`t`.`project_name` AS `project_name`,`t`.`is_sign` AS `is_sign`,`t`.`names` AS `names`,`t`.`total_fund` AS `total_fund`,`t`.`sign_date` AS `sign_date`,`t`.`file_date` AS `file_date`,`t`.`year` AS `year`,`t`.`remark` AS `remark`,`t`.`is_bidding` AS `is_bidding`,`t`.`bidding_date` AS `bidding_date`,`t`.`is_add` AS `is_add`,`t`.`add_id` AS `add_id`,`t`.`add_name` AS `add_name`,`t`.`changed` AS `changed`,`t`.`changed_info` AS `changed_info`,`t`.`consumer` AS `consumer`,`t`.`clients` AS `clients`,`t`.`liaison` AS `liaison`,`t`.`liaison_call` AS `liaison_call`,`t`.`customer` AS `customer`,`t`.`income_fund` AS `income_fund`,`t`.`billed_fund` AS `billed_fund`,`t`.`billed_unincome` AS `billed_unincome`,`t`.`income_unbill` AS `income_unbill`,`t`.`status` AS `status`,`t`.`is_inspected` AS `is_inspected`,`t`.`inspect_date` AS `inspect_date`,`t`.`is_checked` AS `is_checked`,`t`.`checked_fund` AS `checked_fund`,`t`.`gross_rate` AS `gross_rate`,`t`.`gluings` AS `gluings`,`t`.`materials` AS `materials`,`t`.`is_sealed` AS `is_sealed`,`t`.`origin_num` AS `origin_num`,`t`.`copy_num` AS `copy_num`,`t`.`is_turned` AS `is_turned`,`t`.`turned_num` AS `turned_num`,`t`.`is_confirm` AS `is_confirm`,`t`.`is_oam` AS `is_oam`,`t`.`update_time` AS `update_time`,`i`.`contract_id` AS `contract_id`,`i`.`income_progress` AS `income_progress`,`i`.`is_funds_source` AS `is_funds_source`,`i`.`funds_source` AS `funds_source`,`i`.`no_funds_desc` AS `no_funds_desc`,`i`.`pay_way` AS `pay_way`,`i`.`problems` AS `problems`,`i`.`plan_income` AS `plan_income`,`i`.`plan_date` AS `plan_date`,`i`.`provideds` AS `provideds` from (`lp_contract_info` `t` left join `lp_contract_income` `i` on(`t`.`id` = `i`.`contract_id`));

-- ----------------------------
-- View structure for lp_plan_income_view
-- ----------------------------
DROP VIEW IF EXISTS `lp_plan_income_view`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `lp_plan_income_view` AS select `a`.`id` AS `id`,`a`.`contract_id` AS `contract_id`,`a`.`contract_uuid` AS `contract_uuid`,`a`.`contract_name` AS `contract_name`,`a`.`total_fund` AS `total_fund`,`a`.`outer_fund` AS `outer_fund`,`a`.`estimate_fund` AS `estimate_fund`,`a`.`prefecture` AS `prefecture`,`a`.`county` AS `county`,`a`.`org` AS `org`,`a`.`year` AS `year`,`a`.`month` AS `month`,`a`.`ymonth` AS `ymonth`,`a`.`inspect` AS `inspect`,`a`.`details_one` AS `details_one`,`a`.`details_two` AS `details_two`,`a`.`details_three` AS `details_three`,`a`.`details_four` AS `details_four`,`a`.`remark` AS `remark`,`a`.`user_name` AS `user_name`,`a`.`update_time` AS `update_time`,`a`.`user_id` AS `user_id`,`a`.`estimate_billed_fund` AS `estimate_billed_fund`,`a`.`estimate_special_invoice` AS `estimate_special_invoice`,`a`.`estimate_plain_invoice` AS `estimate_plain_invoice`,`b`.`income_progress` AS `income_progress`,`b`.`is_funds_source` AS `is_funds_source`,`b`.`funds_source` AS `funds_source`,`b`.`no_funds_desc` AS `no_funds_desc`,`b`.`pay_way` AS `pay_way`,`b`.`problems` AS `problems`,`b`.`plan_income` AS `plan_income`,`b`.`plan_date` AS `plan_date`,`b`.`provideds` AS `provideds` from (`lp_plan_income` `a` left join `lp_contract_income` `b` on(`a`.`contract_id` = `b`.`contract_id`));

SET FOREIGN_KEY_CHECKS = 1;
