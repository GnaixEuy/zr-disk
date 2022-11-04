-- ----------------------------
-- Records of role
-- ----------------------------
BEGIN;
INSERT INTO `role` (`id`, `name`, `title`, `created_time`, `updated_time`)
VALUES (1, 'ROLE_USER', '普通用户', '2021-07-21 09:27:12.260000', '2021-07-21 09:27:12.260000');
INSERT INTO `role` (`id`, `name`, `title`, `created_time`, `updated_time`)
VALUES (2, 'ROLE_ADMIN', '超级管理员', '2021-07-21 09:27:12.260000', '2021-07-21 09:27:12.260000');
INSERT INTO `role` (`id`, `name`, `title`, `created_time`, `updated_time`)
VALUES (3, 'ROLE_USER_VIP', 'VIP用户', '2021-07-21 09:27:12.260000', '2021-07-21 09:27:12.260000');
COMMIT;

-- ----------------------------
-- Records of user
-- ----------------------------
BEGIN;
INSERT INTO `user` (`id`, `phone`, `passworld`, `nickname`, `gender`, `head_img_id`, `enable`, `locked`,
                    `created_date_time`, `updated_date_time`, `drive_size`, `drive_used`, `last_login_ip`,
                    `last_login_time`, `open_id`, `is_vip`)
VALUES (1001, '10-516-6295', 'mHqGRdH58i', '范岚', 1, NULL, 1, 0, '2020-07-02 09:55:10', '2006-12-07 15:15:17',
        573480.22, 54939.43, '251.49.243.32', '2017-05-07 16:52:25.000000', NULL, 0);
INSERT INTO `user` (`id`, `phone`, `passworld`, `nickname`, `gender`, `head_img_id`, `enable`, `locked`,
                    `created_date_time`, `updated_date_time`, `drive_size`, `drive_used`, `last_login_ip`,
                    `last_login_time`, `open_id`, `is_vip`)
VALUES (1002, '769-010-9738', 'Vnwc9OxQfp', '许安琪', 1, NULL, 1, 0, '2019-10-22 16:49:46', '2021-05-29 09:44:31',
        52858.14, 129167.51, '254.240.177.252', '2017-09-14 13:21:13.000000', NULL, 0);
INSERT INTO `user` (`id`, `phone`, `passworld`, `nickname`, `gender`, `head_img_id`, `enable`, `locked`,
                    `created_date_time`, `updated_date_time`, `drive_size`, `drive_used`, `last_login_ip`,
                    `last_login_time`, `open_id`, `is_vip`)
VALUES (1003, '175-0083-2795', 'yUK8RYb5JO', '郭嘉伦', 2, NULL, 1, 0, '2001-06-04 16:03:46', '2001-03-11 16:26:23',
        31990.07, 781685.2, '251.78.236.251', '2009-06-30 13:03:42.000000', NULL, 0);
INSERT INTO `user` (`id`, `phone`, `passworld`, `nickname`, `gender`, `head_img_id`, `enable`, `locked`,
                    `created_date_time`, `updated_date_time`, `drive_size`, `drive_used`, `last_login_ip`,
                    `last_login_time`, `open_id`, `is_vip`)
VALUES (1004, '146-8543-7155', '2iUBl0WAAR', '尹致远', 1, NULL, 1, 0, '2016-03-01 10:05:34', '2010-10-03 12:55:13',
        739073.63, 567785.5, '210.63.117.64', '2013-09-26 13:16:24.000000', NULL, 0);
INSERT INTO `user` (`id`, `phone`, `passworld`, `nickname`, `gender`, `head_img_id`, `enable`, `locked`,
                    `created_date_time`, `updated_date_time`, `drive_size`, `drive_used`, `last_login_ip`,
                    `last_login_time`, `open_id`, `is_vip`)
VALUES (1005, '188-9927-9847', 'K0QqYks2yC', '卢璐', 0, NULL, 1, 0, '2021-03-24 15:02:46', '2013-12-14 12:45:10',
        98461.59, 699664.02, '234.92.75.80', '2009-09-23 14:09:38.000000', NULL, 0);
INSERT INTO `user` (`id`, `phone`, `passworld`, `nickname`, `gender`, `head_img_id`, `enable`, `locked`,
                    `created_date_time`, `updated_date_time`, `drive_size`, `drive_used`, `last_login_ip`,
                    `last_login_time`, `open_id`, `is_vip`)
VALUES (1006, '28-9970-4241', 'seOE1CDoBi', '韩震南', 1, NULL, 1, 0, '2020-05-25 15:14:12', '2009-12-06 10:24:32',
        460616.31, 334359.77, '209.252.40.187', '2016-02-09 10:33:33.000000', NULL, 0);
INSERT INTO `user` (`id`, `phone`, `passworld`, `nickname`, `gender`, `head_img_id`, `enable`, `locked`,
                    `created_date_time`, `updated_date_time`, `drive_size`, `drive_used`, `last_login_ip`,
                    `last_login_time`, `open_id`, `is_vip`)
VALUES (1007, '165-2561-8815', 'H65osRpyEG', '郑睿', 0, NULL, 1, 0, '2000-07-28 14:55:25', '2020-12-24 14:06:01',
        123132.38, 4745.62, '248.245.251.3', '2018-02-15 13:33:06.000000', NULL, 0);
INSERT INTO `user` (`id`, `phone`, `passworld`, `nickname`, `gender`, `head_img_id`, `enable`, `locked`,
                    `created_date_time`, `updated_date_time`, `drive_size`, `drive_used`, `last_login_ip`,
                    `last_login_time`, `open_id`, `is_vip`)
VALUES (1008, '21-918-3988', 'dBASI9sr71', '杜宇宁', 0, NULL, 1, 0, '2021-01-07 15:06:14', '2010-05-31 11:59:28',
        930125.16, 353498.03, '250.242.223.155', '2022-02-05 10:46:03.000000', NULL, 0);
INSERT INTO `user` (`id`, `phone`, `passworld`, `nickname`, `gender`, `head_img_id`, `enable`, `locked`,
                    `created_date_time`, `updated_date_time`, `drive_size`, `drive_used`, `last_login_ip`,
                    `last_login_time`, `open_id`, `is_vip`)
VALUES (1009, '10-342-9002', 'jlGD2A6MSP', '何云熙', 0, NULL, 1, 0, '2014-07-05 09:09:52', '2012-02-15 15:32:47',
        588850.79, 896469.84, '1.196.109.3', '2004-01-29 10:08:54.000000', NULL, 0);
INSERT INTO `user` (`id`, `phone`, `passworld`, `nickname`, `gender`, `head_img_id`, `enable`, `locked`,
                    `created_date_time`, `updated_date_time`, `drive_size`, `drive_used`, `last_login_ip`,
                    `last_login_time`, `open_id`, `is_vip`)
VALUES (1010, '155-5788-1027', 'LOli6EeruI', '徐詩涵', 1, NULL, 1, 0, '2004-12-30 15:04:22', '2008-12-13 11:31:21',
        676044.84, 941855.74, '250.252.197.3', '2011-01-17 09:11:41.000000', NULL, 0);
COMMIT;

-- ----------------------------
-- Records of user_role_associated
-- ----------------------------
BEGIN;
INSERT INTO `user_role_associated` (`user_id`, `role_id`)
VALUES (1002, 1);
INSERT INTO `user_role_associated` (`user_id`, `role_id`)
VALUES (1003, 1);
INSERT INTO `user_role_associated` (`user_id`, `role_id`)
VALUES (1004, 1);
INSERT INTO `user_role_associated` (`user_id`, `role_id`)
VALUES (1005, 1);
INSERT INTO `user_role_associated` (`user_id`, `role_id`)
VALUES (1006, 1);
INSERT INTO `user_role_associated` (`user_id`, `role_id`)
VALUES (1007, 1);
INSERT INTO `user_role_associated` (`user_id`, `role_id`)
VALUES (1008, 1);
INSERT INTO `user_role_associated` (`user_id`, `role_id`)
VALUES (1009, 1);
INSERT INTO `user_role_associated` (`user_id`, `role_id`)
VALUES (1001, 1);
INSERT INTO `user_role_associated` (`user_id`, `role_id`)
VALUES (1010, 1);
COMMIT;