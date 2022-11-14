package cn.realandy.zrdisk.enmus;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * <img src="http://blog.gnaixeuy.cn/wp-content/uploads/2022/09/倒闭.png"/>
 *
 * <p>项目： zr-disk </p>
 * 创建日期： 2022/11/7
 *
 * @author GnaixEuy
 * @version 1.0.0
 * @see <a href="https://github.com/GnaixEuy"> GnaixEuy的GitHub </a>
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum FileType {
    /**
     * 用于分辨文件类型的枚举值
     */
    OTHER(0, "other"),
    AUDIO(1, "audio"),
    IMAGE(2, "image"),
    VIDEO(3, "video"),
    ZIP(4, "zip"),
    DOC(5, "doc"),
    DIR(6, "folder"),
    EXE(7, "exe"),
    PPT(8, "ppt"),
    PDF(9, "pdf"),
    TXT(10, "txt");


    @EnumValue
    private Integer key;

    @JsonValue
    private String display;
}
