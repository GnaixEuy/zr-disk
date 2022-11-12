package cn.realandy.zrdisk.controller;

import cn.hutool.json.JSONUtil;
import cn.realandy.zrdisk.config.RedisConfig;
import cn.realandy.zrdisk.entity.File;
import cn.realandy.zrdisk.entity.TencentCos;
import cn.realandy.zrdisk.entity.User;
import cn.realandy.zrdisk.exception.BizException;
import cn.realandy.zrdisk.exception.ExceptionType;
import cn.realandy.zrdisk.mapper.UserMapper;
import cn.realandy.zrdisk.service.UserService;
import cn.realandy.zrdisk.vo.ResponseResult;
import cn.realandy.zrdisk.vo.UserVo;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;

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
@Slf4j
@RequestMapping(value = {"/user"})
public class UserController {

    private UserService userService;
    private UserMapper userMapper;
    private RedisConfig redisConfig;

    private TencentCos tencentCos;

    @GetMapping(value = {"/getUserInfo"})
    @RolesAllowed(value = {"ROLE_USER"})
    @ApiOperation(value = "通过请求头保存的token，获取当前用户的信息", httpMethod = "GET")
    public ResponseResult<UserVo> me() {
        User currentUser = this.userService.getCurrentUser();
        File headImg = currentUser.getHeadImg();
        headImg.setDownloadUrl(this.tencentCos.getBaseUrl() + headImg.getDownloadUrl());
        return ResponseResult.success(this.userMapper.dto2Vo(
                        this.userMapper.entity2Dto(
                                currentUser
                        )
                )
        );
    }

    @PutMapping(value = {"/modifyNick"})
    public ResponseResult<String> modifyNick(@RequestBody String nickname) {
        nickname = JSONUtil.parseObj(nickname).getStr("nickname");
        if (nickname == null || "".equals(nickname)) {
            return ResponseResult.error("昵称不能为空");
        }
        User currentUser = this.userService.getCurrentUser();
        currentUser.setNickname(nickname);
        boolean result = this.userService.updateById(currentUser);
        if (!result) {
            throw new BizException(ExceptionType.USER_UPDATE_ERROR);
        }
        return ResponseResult.success("用户名更新成功");
    }

    @PutMapping(value = {"/modifyCipher"})
    public ResponseResult<String> modifyCipher(@RequestBody String password) {
        password = JSONUtil.parseObj(password).getStr("password");
        if (password == null || "".equals(password)) {
            return ResponseResult.error("密码不能为空");
        }
        this.userService.updateUserPassword(password);
        return ResponseResult.success("密码更新成功");
    }

    @GetMapping(value = {"/getUsedDrive"})
    public ResponseResult<Long> getUsedDrive() {
        return ResponseResult.success(this.userService.getCurrentUserDto().getDriveUsed().longValue());
    }

    @GetMapping(value = {"/logout"})
    public ResponseResult<String> userLogout() {
        this.userService.logout();
        return ResponseResult.success("登出成功");
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Autowired
    public void setRedisConfig(RedisConfig redisConfig) {
        this.redisConfig = redisConfig;
    }

    @Autowired
    public void setTencentCos(TencentCos tencentCos) {
        this.tencentCos = tencentCos;
    }
}
