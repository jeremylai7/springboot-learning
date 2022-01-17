create table t_user (
    `id` bigint not null auto_increment,
    `name` varchar(64) comment "姓名",
    `age` int comment "年龄",
    `submit_time` timestamp not null default current_timestamp comment "提交时间",
    primary key(`id`)
)