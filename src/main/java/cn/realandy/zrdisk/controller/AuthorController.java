package cn.realandy.zrdisk.controller;

import cn.realandy.zrdisk.service.LoginService;
import cn.realandy.zrdisk.service.UserService;
import cn.realandy.zrdisk.vo.RegisteredUserByPhoneRequest;
import cn.realandy.zrdisk.vo.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
@RestController
@RequestMapping(value = {"/author"})
public class AuthorController {

    private UserService userService;
    private LoginService loginService;

    @GetMapping(value = {"/getPhoneVerificationCode/{phoneNumber}"})
    public ResponseResult<String> getVerificationCode(@PathVariable String phoneNumber) {
        this.loginService.getPhoneVerificationCode(phoneNumber);
        return ResponseResult.success("短信发送成功");
    }

    @PostMapping(value = {"/registerByPhone"})
    public ResponseResult<String> registerByPhone(@RequestBody RegisteredUserByPhoneRequest registeredUserByPhoneRequest) {
        String tokenByPhone = this.loginService.createTokenByPhone(registeredUserByPhoneRequest);
        return ResponseResult.success(tokenByPhone);
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setLoginService(LoginService loginService) {
        this.loginService = loginService;
    }

}
