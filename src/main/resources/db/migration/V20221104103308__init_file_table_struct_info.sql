CREATE TABLE `disk`.`file`
(
    `id`                varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin  NOT NULL COMMENT '文件ID',
    `name`              varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin  NOT NULL COMMENT '文件名',
    `hash`              varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin  NOT NULL COMMENT '文件hash值',
    `ext`               varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin  NOT NULL COMMENT '文件后缀名',
    `size`              int                                                    NOT NULL DEFAULT 0 COMMENT '文件大小；单位byte',
    `type`              int                                                    NOT NULL DEFAULT 0 COMMENT '文件类型，1-AUDIO-音频，2-IMAGE-图片，3-VIDEO-视频,4-压缩文件，0-OTHER-其他',
    `parent_file_id`    varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL     DEFAULT NULL,
    `parent_folder`     varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL     DEFAULT NULL,
    `download_url`      varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL     DEFAULT NULL,
    `uploader_id`       int                                                    NOT NULL DEFAULT '1' COMMENT '创建者用户ID',
    `created_date_time` datetime(6)                                            NOT NULL COMMENT '创建时间',
    `updated_date_time` datetime(6)                                            NOT NULL COMMENT '更新时间',
    `is_delete`         tinyint(1)                                             NOT NULL DEFAULT 0 COMMENT '是否删除',
    `parent_path`       varchar(255)                                           NOT NULL DEFAULT '/' COMMENT '文件前缀',
    `locked`            tinyint(1)                                             NOT NULL DEFAULT 0 COMMENT '文件是否封禁',
    `cover_url`         varchar(255)                                           NULL COMMENT '文件缩略图',
    `storage`           int                                                    NULL     DEFAULT 1 COMMENT '存储供应商，1-COS-腾讯云存储，2-OSS-阿里云存储',
    `status`            int                                                    NULL     DEFAULT 1 COMMENT '文件状态，1-UPLOADING-上传中，2-UPLOADED-已上传，3-CANCEL-已取消',
    PRIMARY KEY (`id`),
    CONSTRAINT `file_uploader_id` FOREIGN KEY (uploader_id) REFERENCES `disk`.`user` (`id`)
) COMMENT = '文件表';