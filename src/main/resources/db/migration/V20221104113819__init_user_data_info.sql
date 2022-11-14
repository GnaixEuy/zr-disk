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
# INSERT INTO `user` (`id`, `phone`, `email`, `password`, `nickname`, `gender`, `head_img_id`, `enabled`, `locked`,
#                     `created_date_time`, `updated_date_time`, `birthday`, `drive_size`, `drive_used`, `last_login_ip`,
#                     `last_login_time`, `open_id`)
# VALUES (-1769983999, '13365917711', NULL, '$2a$10$AFb3cPcxETmcIJXv5y1B..oj.AgGP0BjHiqjx4Ty6QUvUoWjXZ8ZS', '小破盘test',
#         1, '2HFw0E94l9TJp8iqpMxtSyK8LBn', 1, 0, '2022-11-08 14:54:41', '2022-11-09 17:32:09', NULL, 10737418240,
#         7268756, NULL, NULL, NULL);
INSERT INTO `user` (`id`, `phone`, `email`, `password`, `nickname`, `gender`, `head_img_id`, `enabled`, `locked`,
                    `created_date_time`, `updated_date_time`, `birthday`, `drive_size`, `drive_used`, `last_login_ip`,
                    `last_login_time`, `open_id`)
VALUES (1, '145-0101-0039', 'houy96@gmail.com', 'rlIGMfzMKr', '侯云熙', 1, '2HFw0E94l9TJp8iqpMxtSyK8LBn', 1, 0,
        '2015-05-12 10:58:58',
        '2016-12-21 17:53:30', '2002-01-27 13:10:10', 10737418240, 26329.05, '232.2.63.134',
        '2022-02-28 09:58:34.000000',
        '2VE2ATpUX4');
INSERT INTO `user` (`id`, `phone`, `email`, `password`, `nickname`, `gender`, `head_img_id`, `enabled`, `locked`,
                    `created_date_time`, `updated_date_time`, `birthday`, `drive_size`, `drive_used`, `last_login_ip`,
                    `last_login_time`, `open_id`)
VALUES (2, '21-8590-3029', 'anqitang1976@gmail.com', 'DsiRUqEnuT', '汤安琪', 1, '2HFw0E94l9TJp8iqpMxtSyK8LBn', 1, 0,
        '2016-10-11 11:55:36',
        '2007-11-03 09:32:58', '2002-03-03 09:17:36', 10737418240, 24615.79, '213.251.250.253',
        '2019-09-27 17:35:17.000000',
        'rNIIJDlO81');

COMMIT;
#
# -- ----------------------------
# -- Records of user_role_associated
# -- ----------------------------
BEGIN;
# INSERT INTO `user_role_associated` (`user_id`, `role_id`)
# VALUES (-1769983999, 1);
INSERT INTO `user_role_associated` (`user_id`, `role_id`)
VALUES (1, 1);
INSERT INTO `user_role_associated` (`user_id`, `role_id`)
VALUES (2, 1);
COMMIT;