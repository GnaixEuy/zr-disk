package cn.realandy.zrdisk.vo;

import cn.realandy.zrdisk.enmus.Gender;
import cn.realandy.zrdisk.entity.File;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <img src="http://blog.gnaixeuy.cn/wp-content/uploads/2022/09/倒闭.png"/>
 *
 * <p>项目： zr-disk </p>
 * 创建日期： 2022/11/6
 *
 * @author GnaixEuy
 * @version 1.0.0
 * @see <a href="https://github.com/GnaixEuy"> GnaixEuy的GitHub </a>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserVo {

    private Integer id;
    private String phone;
    private String email;
    private String nickname;
    private Gender gender;
    private Date birthday;
    private BigDecimal driveSize;
    private BigDecimal driveUsed;
    private String lastLoginIp;
    private Date lastLoginTime;
    //TODO VIP机制
//    private boolean isVip;
    private File headImg;
}
