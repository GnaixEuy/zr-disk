CREATE TABLE `disk`.`user_role_associated`
(
    `user_id` int COLLATE utf8mb4_bin NOT NULL COMMENT '用户ID',
    `role_id` int COLLATE utf8mb4_bin NOT NULL COMMENT '角色ID',
    CONSTRAINT `user_associated` FOREIGN KEY (`user_id`) REFERENCES `disk`.`user` (`id`),
    CONSTRAINT `role_associated` FOREIGN KEY (`role_id`) REFERENCES `disk`.`role` (`id`)
) COMMENT = '用户角色多对多关系表';