package cn.realandy.zrdisk.entity;

import cn.realandy.zrdisk.enmus.Gender;
import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
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
@JsonIgnoreProperties(ignoreUnknown = true)
@TableName(value = "user", resultMap = "userResultMap")
public class User implements UserDetails, Serializable {
    @TableId(type = IdType.ASSIGN_ID)
    private Integer id;
    @TableField
    private String phone;
    @TableField
    private String email;
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
    @TableField(value = "created_date_time", fill = FieldFill.INSERT)
    private Date createdDateTime;
    @TableField(value = "updated_date_time", fill = FieldFill.INSERT_UPDATE)
    private Date updatedDateTime;
    @TableField
    private Date birthday;
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

    //TODO VIP机制
//    @TableField
//    private boolean isVip;
    @TableField(exist = false)
    private File headImg;
    @TableField(exist = false)
    private List<Role> roles;


    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        this.roles.forEach(item -> authorities.add(new SimpleGrantedAuthority(item.getName())));
        return authorities;
    }

    @Override
    public String getUsername() {
        return this.phone;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.enabled;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !this.locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

}

