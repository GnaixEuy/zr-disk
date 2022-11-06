package cn.realandy.zrdisk.dto;

import cn.realandy.zrdisk.enmus.Gender;
import cn.realandy.zrdisk.entity.File;
import cn.realandy.zrdisk.entity.Role;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

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
public class UserDto {

    private Integer id;
    private String phone;
    private String email;
    private String password;
    private String nickname;
    private Gender gender;
    private boolean enabled;
    private boolean locked;
    private Date createdDateTime;
    private Date updatedDateTime;
    private Date birthday;
    private BigDecimal driveSize;
    private BigDecimal driveUsed;
    private String lastLoginIp;
    private Date lastLoginTime;
    private String openId;
    //TODO VIP机制
//    private boolean isVip;
    private File headImg;
    private List<Role> roles;

}
