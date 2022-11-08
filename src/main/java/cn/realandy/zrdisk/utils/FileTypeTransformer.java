package cn.realandy.zrdisk.utils;

import cn.realandy.zrdisk.enmus.FileType;

/**
 * <img src="http://blog.gnaixeuy.cn/wp-content/uploads/2022/09/倒闭.png"/>
 *
 * <p>项目： zr-disk </p>
 * 创建日期： 2022/11/8
 *
 * @author GnaixEuy
 * @version 1.0.0
 * @see <a href="https://github.com/GnaixEuy"> GnaixEuy的GitHub </a>
 */
public class FileTypeTransformer {

    public static FileType getFileTypeFromExt(String ext) {
        if (isAudio(ext)) {
            return FileType.AUDIO;
        }

        if (isImage(ext)) {
            return FileType.IMAGE;
        }

        if (isVideo(ext)) {
            return FileType.VIDEO;
        }

        if (isZip(ext)) {
            return FileType.ZIP;
        }

        return FileType.OTHER;
    }

    private static Boolean isVideo(String ext) {
        String[] videoExt = {"vob", "mp4", "avi",
                "flv", "f4v", "wmv", "mov", "rmvb",
                "mkv", "mpg", "m4v", "webm", "rm",
                "mpeg", "asf", "ts", "mts"};
        for (String perExt : videoExt) {
            if (perExt.equals(ext)) {
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }

    private static Boolean isAudio(String ext) {
        String[] videoExt = {"mp3", "wav"};
        for (String perExt : videoExt) {
            if (perExt.equals(ext)) {
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }


    private static Boolean isImage(String ext) {
        String[] videoExt = {"png", "jpg", "jpeg"};
        for (String perExt : videoExt) {
            if (perExt.equals(ext)) {
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }

    private static Boolean isZip(String ext) {
        String[] videoExt = {"zip", "rar", "7z"};
        for (String perExt : videoExt) {
            if (perExt.equals(ext)) {
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }

}
