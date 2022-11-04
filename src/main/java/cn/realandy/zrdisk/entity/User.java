package cn.realandy.zrdisk.entity;

import cn.realandy.zrdisk.enmus.Gender;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

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
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "user", resultMap = "userResultMap")
public class User {
    @TableId
    private Integer id;
    @TableField
    private String phone;
    @TableField
    private String password;
    @TableField
    private String nickname;
    @TableField
    private Gender gender;

    @TableField
    private boolean enabled;
    @TableField
    private boolean locked;
    @TableField
    private Date createdDateTime;
    @TableField
    private Date updatedDateTime;
    @TableField
    private BigDecimal driveSize;
    @TableField
    private BigDecimal driveUsed;
    @TableField
    private String lastLoginIp;
    @TableField
    private Date lastLoginTime;
    @TableField
    private String openId;
    @TableField
    private boolean isVip;
    @TableField(exist = false)
    private File headImg;
    @TableField(exist = false)
    private List<Role> roles;


}

