CREATE TABLE `disk`.`user`
(
    `id`                int          NOT NULL COMMENT '用户id',
    `phone`             varchar(32)  NOT NULL COMMENT '手机号',
    `passworld`         varchar(64)  NOT NULL COMMENT '用户密码',
    `nickname`          varchar(32)  NOT NULL COMMENT '昵称',
    `gender`            int          NULL COMMENT '用户性别',
    `headImg_id`        varchar(255) NULL COMMENT '头像文件关联',
    `enable`            tinyint(1)   NOT NULL DEFAULT 1 COMMENT '账户是否启用',
    `locked`            tinyint(1)   NOT NULL DEFAULT 0 COMMENT '账户是否封禁',
    `created_date_time` datetime     NULL COMMENT '创建时间',
    `drive_size`        double       NULL COMMENT '用户存储空间',
    `drive_used`        double       NULL COMMENT '用户已使用空间',
    `last_login_ip`     varchar(64)  NULL COMMENT '最后登陆ip',
    `last_login_time`   datetime(6)  NULL COMMENT '最后登陆时间',
    `open_id`           varchar(255) NULL COMMENT '第三方open id',
    `vip`               tinyint(1)   NOT NULL DEFAULT 0 COMMENT '是否为vip',
    PRIMARY KEY (`id`)
) COMMENT = '基本用户结构';