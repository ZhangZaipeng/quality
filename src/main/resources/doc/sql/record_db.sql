-- 工程申请表
DROP TABLE IF EXISTS `tb_project`;
CREATE TABLE `tb_project` (
  `project_id` BIGINT(11) NOT NULL AUTO_INCREMENT COMMENT '自增长主键',

  `group_id` BIGINT(11) NOT NULL COMMENT '小组 编号',

  `project_name` BIGINT(11) NOT NULL COMMENT '分项工程名称',

  `construction_date` TIMESTAMP DEFAULT NULL COMMENT '施工时间',

  `construction` varchar(100) DEFAULT NULL COMMENT '施工 单位',

  `supervision` varchar(100) DEFAULT NULL COMMENT '监理 单位',

	`status` SMALLINT(1) DEFAULT '0' COMMENT '状态 0 启动中 1 结项 ' ,

	`created_at` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	`updated_at` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',

	PRIMARY KEY (`project_id`),
	INDEX `idx_group_id` (`group_id`)
)
COLLATE='utf8_unicode_ci'
ENGINE=INNODB
ROW_FORMAT=DEFAULT
DEFAULT CHARSET=utf8
COMMENT='检查工程 申请表'
AUTO_INCREMENT=1
;

-- 工程 与 检查项 关联表
DROP TABLE IF EXISTS `tb_project_item`;
CREATE TABLE `tb_project_item` (
  `id` BIGINT(11) NOT NULL AUTO_INCREMENT COMMENT '关联ID',

  `project_id` BIGINT(11) NOT NULL COMMENT '工程 ID',

  `item_id` BIGINT(11) NOT NULL COMMENT '检查项 ID',

	`status` SMALLINT(1) DEFAULT '0' COMMENT '状态 0 正常 1 失效 ' ,

	`created_at` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	`updated_at` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',

	PRIMARY KEY (`id`),
	INDEX `idx_project_id` (`project_id`),
	INDEX `idx_item_id` (`item_id`)
)
COLLATE='utf8_unicode_ci'
ENGINE=INNODB
ROW_FORMAT=DEFAULT
DEFAULT CHARSET=utf8
COMMENT='工程 与 检查项 关联表'
AUTO_INCREMENT=1
;

-- 钢筋检查项 数据记录表
DROP TABLE IF EXISTS `tb_fixture_record`

-- 钢筋检查项 数据 详细 记录表

