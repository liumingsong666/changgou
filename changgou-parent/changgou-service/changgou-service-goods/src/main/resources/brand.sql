CREATE TABLE `tb_brand` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `name` varchar(4) NOT NULL COMMENT '品牌名字',
  `image` varchar(50) DEFAULT NULL COMMENT '品牌图片',
  `letter` varchar(1) NOT NULL COMMENT '首字母',
  `seq` int(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='品牌表';