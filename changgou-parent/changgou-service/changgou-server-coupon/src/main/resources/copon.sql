CREATE TABLE `coupon` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `temp_id` varchar(30) NOT NULL COMMENT '模板id',
  `coupon_id` varchar(30) NOT NULL COMMENT '优惠券id',
  `coupon_name` varchar(255) DEFAULT NULL COMMENT '优惠券名称',
  `coupon_desc` varchar(255) DEFAULT NULL COMMENT '优惠券描述',
  `coupon_status` tinyint(4) DEFAULT '1' COMMENT '优惠券状态 1：未领取 2：已领取,未使用  3：已使用 4：已过期 5：已冻结 6：已作废',
  `coupon_type` tinyint(4) DEFAULT NULL COMMENT '优惠券类型 1：满减卷 2: 折扣券 3：立减券',
  `coupon_money_min` decimal(11,2) DEFAULT NULL COMMENT '最少使用金额 0：不限制(单位：元,依赖coupon_type)',
  `coupon_money_max` decimal(11,2) DEFAULT NULL COMMENT '最大使用金额 0：不限制(单位：元,依赖coupon_type)',
  `coupon_value` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '优惠券面值,小于0为折扣',
  `coupon_start_time` datetime DEFAULT NULL COMMENT '优惠券开始时间',
  `coupon_end_time` datetime DEFAULT NULL COMMENT '优惠券结束时间',
  `draw_time` datetime DEFAULT NULL COMMENT '领取时间',
  `use_time` datetime DEFAULT NULL COMMENT '使用时间',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `unx_coupon_base_sn` (`coupon_id`),
  KEY `idx_act_sn_coupon_status` (`temp_id`,`coupon_status`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='优惠券主表';


CREATE TABLE `coupon_temp` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `temp_id` varchar(30) NOT NULL COMMENT '优惠券码',
  `coupon_name` varchar(255) DEFAULT NULL COMMENT '优惠券名称',
  `coupon_desc` varchar(255) DEFAULT NULL COMMENT '优惠券描述',
  `temp_status` tinyint(4) NOT NULL COMMENT '优惠券状态 1：未启用 2：已启用 3：已中止 4：已过期 5 生成中',
  `coupon_num` int(11) NOT NULL DEFAULT '0' COMMENT '优惠券数量',
  `limit_type` tinyint(4) NOT NULL DEFAULT '1' COMMENT '1 固定时间范围过期，2 领券后固定时间过期',
  `limit_day` tinyint(4) NOT NULL DEFAULT '0' COMMENT '优惠券限制使用天数:0不限制',
  `coupon_type` tinyint(4) DEFAULT NULL COMMENT '优惠券类型 1：满减卷 2: 折扣券 3:立减券',
  `coupon_money_min` decimal(11,2) DEFAULT NULL COMMENT '最少使用金额 0：不限制(单位：元,依赖coupon_type)',
  `coupon_money_max` decimal(11,2) DEFAULT NULL COMMENT '最大使用金额 0：不限制(单位：元,依赖coupon_type)',
  `coupon_value` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '优惠券面值,小于0为折扣',
  `coupon_start_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '优惠券开始时间',
  `coupon_end_time` datetime DEFAULT NULL COMMENT '优惠券结束时间',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `IDXU_coupon_activity_act_sn` (`temp_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='优惠券模板';

CREATE TABLE `coupon_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `coupon_id` varchar(30) NOT NULL COMMENT '优惠券id',
  `biz_code` varchar(50) DEFAULT NULL COMMENT '活动来源码',
  `biz_source` int(11) DEFAULT '1' COMMENT '业务来源：1活动领取,2手动赠送',
  `user_id` varchar(30) DEFAULT NULL COMMENT '用户ID',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_user_id` (`user_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户领卷表';