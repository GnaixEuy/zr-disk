package cn.realandy.zrdisk.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

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
public class RegisteredUserByPhoneRequest {

    @NotNull
    @NotEmpty
    private String phone;
    @NotNull
    @NotEmpty
    private String password;
    @NotNull
    @NotEmpty
    private String nickname;
    @NotNull
    @NotEmpty
    private String realVerificationCode;

}
