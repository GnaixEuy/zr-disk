CREATE TABLE `disk`.`user_vip_associated`  (
    `user_id` int(0) NOT NULL COMMENT '用户ID',
    `vip_id` int(0) NOT NULL COMMENT 'vipID',
    `expired_date` datetime(0) NOT NULL COMMENT 'VIP过期时间',
    PRIMARY KEY (`user_id`) USING BTREE,
    INDEX `uservip_vip_id`(`vip_id`) USING BTREE,
    CONSTRAINT `uservip_user_id` FOREIGN KEY (`user_id`) REFERENCES `disk`.`user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
    CONSTRAINT `uservip_vip_id` FOREIGN KEY (`vip_id`) REFERENCES `disk`.`vip` (`vip_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) COMMENT = '用户和VIP多对多关系表'