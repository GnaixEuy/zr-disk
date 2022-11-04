CREATE TABLE `disk`.`role`
(
    `id`           int                                                    NOT NULL COMMENT '角色ID',
    `name`         varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '角色名称',
    `title`        varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '角色标识',
    `created_time` datetime(6)                                            NOT NULL COMMENT '创建时间',
    `updated_time` datetime(6)                                            NOT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`)
) COMMENT = '角色权限表';