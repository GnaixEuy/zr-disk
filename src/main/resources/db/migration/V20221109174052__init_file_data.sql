-- ----------------------------
-- Records of file
-- ----------------------------
BEGIN;
# INSERT INTO `file` (`id`, `name`, `hash`, `ext`, `size`, `type`, `parent_file_id`, `parent_folder`, `download_url`,
#                     `uploader_id`, `created_date_time`, `updated_date_time`, `is_delete`, `parent_path`, `locked`,
#                     `cover_url`, `storage`, `status`)
# VALUES ('2HFp3kipsUsr6J1jhfug2xI5dzQ', 'userHeadImage_123.jpg', '282746570', 'jpg', 303231, 2, NULL, NULL,
#         '/attachment/-1769983999/UserHeadImage/2HFp3kipsUsr6J1jhfug2xI5dzQ.jpg', -1769983999,
#         '2022-11-08 14:54:49.819000', '2022-11-08 21:43:32.963000', 1,
#         '/attachment/-1769983999/UserHeadImage/2HFp3kipsUsr6J1jhfug2xI5dzQ.jpg', 0, NULL, 1, 2);
# INSERT INTO `file` (`id`, `name`, `hash`, `ext`, `size`, `type`, `parent_file_id`, `parent_folder`, `download_url`,
#                     `uploader_id`, `created_date_time`, `updated_date_time`, `is_delete`, `parent_path`, `locked`,
#                     `cover_url`, `storage`, `status`)
# VALUES ('2HFw0E94l9TJp8iqpMxtSyK8LBn', '刻晴福利姬.jpg', '405889508', 'jpg', 38659, 2, 'root', NULL,
#         '/attachment/-1769983999/UserHeadImage/2HFw0E94l9TJp8iqpMxtSyK8LBn.jpg', -1769983999,
#         '2022-11-08 15:51:55.235000', '2022-11-09 09:48:23.079000', 0,
#         '/attachment/-1769983999/UserHeadImage/2HFw0E94l9TJp8iqpMxtSyK8LBn.jpg', 0,
#         '/attachment/-1769983999/UserHeadImage/2HFw0E94l9TJp8iqpMxtSyK8LBn.jpg', 1, 2);
INSERT INTO `file` (`id`, `name`, `hash`, `ext`, `size`, `type`, `parent_file_id`, `parent_folder`, `download_url`,
                    `uploader_id`, `created_date_time`, `updated_date_time`, `is_delete`, `parent_path`, `locked`,
                    `cover_url`, `storage`, `status`)
VALUES ('1', '默认头像.jpeg', '21822aa8fb4047532e1f5709c47e0296', 'jpeg', 29.48242188, 2,
        'root', NULL, '/defaultHeadImg.jpeg', 1,
        '2022-11-09 17:26:31.065000', '2022-11-09 17:26:31.067000', 0, 'root', 0,
        '默认头像.jpeg', 1, 1);
COMMIT;