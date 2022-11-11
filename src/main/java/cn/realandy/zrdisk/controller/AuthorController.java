package cn.realandy.zrdisk.controller;

import cn.realandy.zrdisk.service.LoginService;
import cn.realandy.zrdisk.service.UserService;
import cn.realandy.zrdisk.vo.FindPassRequest;
import cn.realandy.zrdisk.vo.LoginByPhoneAndPasswordRequest;
import cn.realandy.zrdisk.vo.RegisteredUserByPhoneRequest;
import cn.realandy.zrdisk.vo.ResponseResult;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
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

    @PostMapping(value = {"/login"})
    public ResponseResult<String> loginByPhone(@RequestBody LoginByPhoneAndPasswordRequest loginByPhoneAndPasswordRequest) {
        return ResponseResult.success(this.loginService.getTokenByPhoneAndPassword(loginByPhoneAndPasswordRequest));
    }

    @PutMapping(value = {"/findPass"})
    public ResponseResult<String> findPass(@RequestBody FindPassRequest findPassRequest) {
        try {
            this.userService.findPass(findPassRequest);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseResult.error("密码重制失败");
        }
        return ResponseResult.success("密码重制成功");
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
