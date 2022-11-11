# -- ----------------------------
# -- Records of role
# -- ----------------------------
BEGIN;
INSERT INTO `role` (`id`, `name`, `title`, `created_date_time`, `updated_date_time`)
VALUES (1, 'ROLE_USER', '普通用户', '2021-07-21 09:27:12.260000', '2021-07-21 09:27:12.260000');
INSERT INTO `role` (`id`, `name`, `title`, `created_date_time`, `updated_date_time`)
VALUES (2, 'ROLE_ADMIN', '超级管理员', '2021-07-21 09:27:12.260000', '2021-07-21 09:27:12.260000');
INSERT INTO `role` (`id`, `name`, `title`, `created_date_time`, `updated_date_time`)
VALUES (3, 'ROLE_VIP', 'VIP', '2021-07-21 09:27:12.260000', '2021-07-21 09:27:12.260000');
COMMIT;
#
#
# -- ----------------------------
# -- Records of user
# -- ----------------------------
BEGIN;
INSERT INTO `user` (`id`, `phone`, `email`, `password`, `nickname`, `gender`, `head_img_id`, `enabled`, `locked`,
                    `created_date_time`, `updated_date_time`, `birthday`, `drive_size`, `drive_used`, `last_login_ip`,
                    `last_login_time`, `open_id`)
VALUES (-1769983999, '13365917711', NULL, '$2a$10$AFb3cPcxETmcIJXv5y1B..oj.AgGP0BjHiqjx4Ty6QUvUoWjXZ8ZS', '小破盘test',
        NULL, '2HFw0E94l9TJp8iqpMxtSyK8LBn', 1, 0, '2022-11-08 14:54:41', '2022-11-09 17:32:09', NULL, 10737418240,
        7268756, NULL, NULL, NULL);
INSERT INTO `user` (`id`, `phone`, `email`, `password`, `nickname`, `gender`, `head_img_id`, `enabled`, `locked`,
                    `created_date_time`, `updated_date_time`, `birthday`, `drive_size`, `drive_used`, `last_login_ip`,
                    `last_login_time`, `open_id`)
VALUES (1, '145-0101-0039', 'houy96@gmail.com', 'rlIGMfzMKr', '侯云熙', 1, NULL, 1, 0, '2015-05-12 10:58:58',
        '2016-12-21 17:53:30', '2002-01-27 13:10:10', 10737418240, 26329.05, '232.2.63.134',
        '2022-02-28 09:58:34.000000',
        '2VE2ATpUX4');
INSERT INTO `user` (`id`, `phone`, `email`, `password`, `nickname`, `gender`, `head_img_id`, `enabled`, `locked`,
                    `created_date_time`, `updated_date_time`, `birthday`, `drive_size`, `drive_used`, `last_login_ip`,
                    `last_login_time`, `open_id`)
VALUES (2, '21-8590-3029', 'anqitang1976@gmail.com', 'DsiRUqEnuT', '汤安琪', 1, NULL, 1, 0, '2016-10-11 11:55:36',
        '2007-11-03 09:32:58', '2002-03-03 09:17:36', 10737418240, 24615.79, '213.251.250.253',
        '2019-09-27 17:35:17.000000',
        'rNIIJDlO81');
INSERT INTO `user` (`id`, `phone`, `email`, `password`, `nickname`, `gender`, `head_img_id`, `enabled`, `locked`,
                    `created_date_time`, `updated_date_time`, `birthday`, `drive_size`, `drive_used`, `last_login_ip`,
                    `last_login_time`, `open_id`)
VALUES (3, '20-984-7976', 'hyunxi4@gmail.com', 'TQTgsfHP9U', '胡云熙', 2, NULL, 1, 0, '2017-04-02 14:13:53',
        '2022-06-18 11:31:57', '2006-06-26 13:53:13', 10737418240, 46259.17, '15.200.147.5',
        '2014-07-01 12:14:13.000000',
        '6nFxWILYfc');
INSERT INTO `user` (`id`, `phone`, `email`, `password`, `nickname`, `gender`, `head_img_id`, `enabled`, `locked`,
                    `created_date_time`, `updated_date_time`, `birthday`, `drive_size`, `drive_used`, `last_login_ip`,
                    `last_login_time`, `open_id`)
VALUES (4, '198-4389-8023', 'fan1@mail.com', 'YGlPbTjsTD', '方安琪', 0, NULL, 1, 0, '2012-06-27 14:20:10',
        '2021-06-21 11:21:21', '2007-10-15 11:53:49', 10737418240, 16955.42, '5.183.121.7',
        '2000-06-01 12:56:43.000000',
        'EvaoMRXV5j');
INSERT INTO `user` (`id`, `phone`, `email`, `password`, `nickname`, `gender`, `head_img_id`, `enabled`, `locked`,
                    `created_date_time`, `updated_date_time`, `birthday`, `drive_size`, `drive_used`, `last_login_ip`,
                    `last_login_time`, `open_id`)
VALUES (5, '769-9854-0412', 'tlu@mail.com', 'HB7VgE6QOA', '汤璐', 1, NULL, 1, 0, '2004-01-26 14:47:51',
        '2017-02-04 10:37:39', '2002-02-02 15:52:39', 10737418240, 2021.47, '207.35.93.128',
        '2006-05-16 14:46:27.000000',
        'gYAHU5MjEX');
INSERT INTO `user` (`id`, `phone`, `email`, `password`, `nickname`, `gender`, `head_img_id`, `enabled`, `locked`,
                    `created_date_time`, `updated_date_time`, `birthday`, `drive_size`, `drive_used`, `last_login_ip`,
                    `last_login_time`, `open_id`)
VALUES (6, '142-0465-2549', 'pzhe4@icloud.com', 'OUrDEaspdT', '潘震南', 1, NULL, 1, 0, '2003-02-22 12:25:53',
        '2000-11-11 14:37:53', '2001-10-11 11:24:46', 10737418240, 83771.4, '250.168.122.9',
        '2001-12-29 15:15:33.000000',
        'NmTx2RfVAN');
INSERT INTO `user` (`id`, `phone`, `email`, `password`, `nickname`, `gender`, `head_img_id`, `enabled`, `locked`,
                    `created_date_time`, `updated_date_time`, `birthday`, `drive_size`, `drive_used`, `last_login_ip`,
                    `last_login_time`, `open_id`)
VALUES (7, '153-4719-5661', 'yunxiwei@outlook.com', 'YgO5uc59FC', '韦云熙', 0, NULL, 1, 0, '2000-09-08 14:37:13',
        '2001-01-17 17:09:57', '2020-11-26 10:01:26', 10737418240, 48310.94, '153.81.254.37',
        '2021-12-27 09:53:01.000000',
        'JWTW4JHvIn');
INSERT INTO `user` (`id`, `phone`, `email`, `password`, `nickname`, `gender`, `head_img_id`, `enabled`, `locked`,
                    `created_date_time`, `updated_date_time`, `birthday`, `drive_size`, `drive_used`, `last_login_ip`,
                    `last_login_time`, `open_id`)
VALUES (8, '181-7020-4202', 'anqi62@gmail.com', 'xqzGgBCVfH', '于安琪', 0, NULL, 1, 0, '2012-09-20 12:43:38',
        '2000-02-25 17:28:02', '2003-08-09 17:46:13', 10737418240, 30307.08, '106.1.135.255',
        '2013-04-25 14:32:56.000000',
        'KTItyGM0ve');
INSERT INTO `user` (`id`, `phone`, `email`, `password`, `nickname`, `gender`, `head_img_id`, `enabled`, `locked`,
                    `created_date_time`, `updated_date_time`, `birthday`, `drive_size`, `drive_used`, `last_login_ip`,
                    `last_login_time`, `open_id`)
VALUES (9, '10-742-9898', 'lluo@outlook.com', '3yDhV6Y5NJ', '罗岚', 1, NULL, 1, 0, '2008-04-10 12:41:52',
        '2016-03-07 14:09:21', '2018-07-09 12:55:32', 10737418240, 48267.48, '145.188.9.218',
        '2017-09-28 11:38:57.000000',
        '23sAznmf8d');
INSERT INTO `user` (`id`, `phone`, `email`, `password`, `nickname`, `gender`, `head_img_id`, `enabled`, `locked`,
                    `created_date_time`, `updated_date_time`, `birthday`, `drive_size`, `drive_used`, `last_login_ip`,
                    `last_login_time`, `open_id`)
VALUES (10, '769-496-2416', 'wujie@yahoo.com', 'tFN4LVR6U8', '吴杰宏', 1, NULL, 1, 0, '2020-08-20 12:09:25',
        '2001-10-19 12:59:43', '2006-06-05 16:09:53', 10737418240, 89131.22, '32.120.39.220',
        '2010-04-30 15:03:59.000000',
        'qBVsHqUs4S');
COMMIT;
#
# -- ----------------------------
# -- Records of user_role_associated
# -- ----------------------------
BEGIN;
INSERT INTO `user_role_associated` (`user_id`, `role_id`)
VALUES (-1769983999, 1);
INSERT INTO `user_role_associated` (`user_id`, `role_id`)
VALUES (1, 1);
INSERT INTO `user_role_associated` (`user_id`, `role_id`)
VALUES (2, 1);
INSERT INTO `user_role_associated` (`user_id`, `role_id`)
VALUES (3, 1);
INSERT INTO `user_role_associated` (`user_id`, `role_id`)
VALUES (4, 1);
INSERT INTO `user_role_associated` (`user_id`, `role_id`)
VALUES (5, 1);
INSERT INTO `user_role_associated` (`user_id`, `role_id`)
VALUES (6, 1);
INSERT INTO `user_role_associated` (`user_id`, `role_id`)
VALUES (7, 1);
INSERT INTO `user_role_associated` (`user_id`, `role_id`)
VALUES (8, 1);
INSERT INTO `user_role_associated` (`user_id`, `role_id`)
VALUES (9, 1);
INSERT INTO `user_role_associated` (`user_id`, `role_id`)
VALUES (10, 1);
COMMIT;