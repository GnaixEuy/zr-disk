ALTER TABLE `disk`.`user`
    MODIFY COLUMN `gender` int NOT NULL DEFAULT 0 COMMENT '用户性别' AFTER `nickname`;