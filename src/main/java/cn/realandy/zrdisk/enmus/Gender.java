package cn.realandy.zrdisk.enmus;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * <img src="http://blog.gnaixeuy.cn/wp-content/uploads/2022/09/倒闭.png"/>
 *
 *
 *
 * <p>项目： zr-disk </p>
 *
 * @author GnaixEuy
 * @version 1.0.0
 * @date 2022/11/4
 * @see <a href="https://github.com/GnaixEuy"> GnaixEuy的GitHub </a>
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum Gender {

    /**
     * unknown-0-未知 man-1-男 woman-2-女
     */
    UNKNOWN(0, "未知"),
    MAN(1, "男"),
    WOMAN(2, "女");

    @EnumValue
    private Integer key;

    @JsonValue
    private String display;

}
