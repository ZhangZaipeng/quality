-- 检查项表
DROP TABLE IF EXISTS `tb_check_item`
CREATE TABLE `tb_check_item` (
  `item_id` BIGINT(11) NOT NULL AUTO_INCREMENT COMMENT '自增长主键',

  `item_name` BIGINT(11) NOT NULL COMMENT '检查项 名称',

	`status` SMALLINT(1) DEFAULT '0' COMMENT '状态 0 正常 1 失效 ' ,

	`created_at` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	`updated_at` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',

	PRIMARY KEY (`item_id`)
)
COLLATE='utf8_unicode_ci'
ENGINE=INNODB
ROW_FORMAT=DEFAULT
DEFAULT CHARSET=utf8
COMMENT='检查工程 申请表'
AUTO_INCREMENT=1
;

insert into tb_check_item (`item_name`) VALUES ('钢筋检查记录');
insert into tb_check_item (`item_name`) VALUES ('钻孔桩成孔交验');
insert into tb_check_item (`item_name`) VALUES ('环框梁钢筋工程关键工序验收');

