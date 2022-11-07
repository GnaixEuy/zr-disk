CREATE TABLE `disk`.`vip`  (
    `vip_id` int(0) NOT NULL AUTO_INCREMENT COMMENT 'vipID主键',
    `vip_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT 'vip名称',
    `increase_drive_size` double NOT NULL COMMENT 'vip增加的存储空间',
    `vip_month_price` double(10, 2) NOT NULL COMMENT 'vip月单价',
    PRIMARY KEY (`vip_id`) USING BTREE
) COMMENT = 'vip表'