create  TABLE order-record{
 id int not null auto_increment,
 order_no varchar(25) not null comment '订单编号',
 order_type varchar(255) default null comment '订单类型',
 create_time datetime DEFAULT CURRENT_TIMESTAMP comment '创建时间'  ,
 update_time timestamp null default null no update CURRENT_TIMESTAMP comment '更新时间',
 primary  key (id),
 unique key idx_order_no (order_no) using btree
} engine=innodb auth_increment=16 default charset=utf8 comment='订单记录表-业务级别';



CREATE TABLE `product_bak` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `stock` int(11) DEFAULT NULL COMMENT '库存量',
  `purchase_date` date DEFAULT  NULL COMMENT '采购是日期',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP  COMMENT '创建时间',
  `update_time` timestamp  NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP  COMMENT '更新时间',
  `is_active` int(11) DEFAULT '1' COMMENT '是否有效',
  PRIMARY KEY (`id`)
) ENGINE=INNODB AUTO_INCREMENT=26 DEFAULT CHARSET=UTF8 COMMENT='产品信息表';

CREATE TABLE `product`(
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `product_no` varchar(255) DEFAULT NULL COMMENT '商品编号',
  `total` int(255) DEFAULT NULL COMMENT '库存量',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP ,
  `update_time` timestamp  NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY ('id')
)ENGINE=INNODB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='商品信息表';

CREATE TABLE `product_robbing_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `mobile` varchar(255) DEFAULT NULL COMMENT '手机号',
  `product_id` int(11) DEFAULT NULL COMMENT '产品id',
  `robbing_time` datetime DEFAULT CURRENT_TIMESTAMP  COMMENT '抢单时间',
  `update_time` timestamp  NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=INNODB AUTO_INCREMENT=302 DEFAULT CHARSET=UTF8 COMMENT='抢单记录表';