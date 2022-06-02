-- 商品表
create table t_product(
`id` bigint(20) not null auto_increment,
`name` varchar(64) not null comment "商品名",
`store` int default 0 comment "库存",
primary key(`id`)
)