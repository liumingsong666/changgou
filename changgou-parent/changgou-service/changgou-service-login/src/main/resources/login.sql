CREATE TABLE `login_log` (
  `login_id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '登陆日志ID',
  `customer_id` int(10) unsigned NOT NULL COMMENT '登陆用户ID',
  `login_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '用户登陆时间',
  `login_ip` varchar(15) NOT NULL COMMENT '登陆IP',
  `login_type` tinyint(1) NOT NULL DEFAULT '1' COMMENT '登陆类型：1手机号，2用户名 ，3微信',
  `login_flag` tinyint(1) NOT NULL DEFAULT '1' COMMENT '登录状态：0失败 1成功',
  PRIMARY KEY (`login_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='用户登陆日志表';


CREATE TABLE `tb_role` (
  `id` int(3) NOT NULL COMMENT '角色id',
  `name` varchar(6) NOT NULL COMMENT '角色名',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色表';