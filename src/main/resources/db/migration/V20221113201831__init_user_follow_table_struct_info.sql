CREATE TABLE `disk`.`user_follow`
(
    `user_id`     int NULL COMMENT '关注者id',
    `follower_id` int NULL COMMENT '被关注者id',
    CONSTRAINT `follow_user_id` FOREIGN KEY (`user_id`) REFERENCES `disk`.`user` (`id`),
    CONSTRAINT `follow_follower_id` FOREIGN KEY (`user_id`) REFERENCES `disk`.`user` (`id`)
) COMMENT = '用户关注表';