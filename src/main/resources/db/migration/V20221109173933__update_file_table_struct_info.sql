ALTER TABLE `disk`.`file`
    ADD COLUMN `is_collection` tinyint(1) NULL DEFAULT 0 COMMENT '是否收藏' AFTER `status`;