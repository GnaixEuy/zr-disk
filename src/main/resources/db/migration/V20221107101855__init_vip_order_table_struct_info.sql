CREATE TABLE `disk`.`vip_order`  (
  `order_id` int(0) NOT NULL COMMENT '订单ID主键',
  `user_id` int(0) NOT NULL COMMENT '用户ID',
  `vip_id` int(0) NOT NULL COMMENT 'vipID主键',
  `order_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '订单名称',
  `order_quantity` int(0) NOT NULL COMMENT '充值月数',
  `order_price` double(10, 2) NOT NULL COMMENT '订单价格',
  `order_status` tinyint(1) NOT NULL DEFAULT 1 COMMENT '订单状态（0已取消/1正在处理/2已完成）',
  `order_trade_no` varchar(0) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '订单支付宝交易凭证号',
  `order_created_date` datetime(6) NOT NULL COMMENT '订单创建时间',
  `order_pay_date` datetime(6) NOT NULL COMMENT '订单支付时间',
  PRIMARY KEY (`order_id`) USING BTREE,
  INDEX `user_order_id`(`user_id`) USING BTREE,
  INDEX `vip_order_id`(`vip_id`) USING BTREE,
  CONSTRAINT `user_order_id` FOREIGN KEY (`user_id`) REFERENCES `disk`.`user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `vip_order_id` FOREIGN KEY (`vip_id`) REFERENCES `disk`.`vip` (`vip_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) COMMENT = 'VIP充值订单表'