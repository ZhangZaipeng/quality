1.项目背景
	该系统为工程勘测人员而做，勘测人员会不定期去检查工程质量，然后将检查质量指标录入到系统中，会在每月末会生成月度报表。
	
	检查指标分为4大类：1.钢 筋 检 查 表，2.钻孔桩成孔交验表， 3.关键工序验收表，4.环框梁钢筋工程关键工序验收表

	月度报表 需求待确认中：
	
	系统用户角色：管理员（1.管理人员，2.分配任务），小组，小组组员（1.需填写或更改检查指标）

2.需要的资料
	1.小程序申请资料（邮箱，企业信息）
	2.阿里服务器（4核 8G 500G硬盘 10M 宽带），域名备案，服务器实名

3.实体之间的关联关系
     1.一个分项工程下面  有若干个检查指标表
     2.一个分工程  对应 一个小组
     3.一个小组 对应 若干个人, 一个人只对应一个组
     4.一个检查指标表 对应 若干个 检查项
