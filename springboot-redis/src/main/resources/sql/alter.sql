-- 商品表
create table t_product(
    `id` bigint(20) not null auto_increment,
    `name` varchar(64) not null comment "商品名",
    `store` int default 0 comment "库存",
    primary key(`id`)
)



create table t_order(
  `id` bigint(20) not null auto_increment,
  `sn` varchar(64) not null comment '订单号',
  `num` int default null comment '数量',
  `price` int default null comment '单价',
  `product_id` bigint default null comment '商品id',
  `create_time` timestamp not null default CURRENT_TIMESTAMP comment '创建时间',
  primary key(`id`)
)





