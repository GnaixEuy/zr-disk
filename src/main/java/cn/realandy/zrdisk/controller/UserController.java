package cn.realandy.zrdisk.controller;

import cn.hutool.json.JSONUtil;
import cn.realandy.zrdisk.config.RedisConfig;
import cn.realandy.zrdisk.dto.UserDto;
import cn.realandy.zrdisk.entity.File;
import cn.realandy.zrdisk.entity.TencentCos;
import cn.realandy.zrdisk.entity.User;
import cn.realandy.zrdisk.exception.BizException;
import cn.realandy.zrdisk.exception.ExceptionType;
import cn.realandy.zrdisk.mapper.UserMapper;
import cn.realandy.zrdisk.service.UserService;
import cn.realandy.zrdisk.vo.FollowRequest;
import cn.realandy.zrdisk.vo.ResponseResult;
import cn.realandy.zrdisk.vo.UpdateUserBasicInfoRequest;
import cn.realandy.zrdisk.vo.UserVo;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    //前台获取全部用户接口
    @GetMapping(value = {"/getAllUserInfo"})
    public ResponseResult<List> getAllUserInfo() {
        return ResponseResult.success(this.userService.getAllUserInfo());
    }

    //修改用户封禁状态
    @PutMapping(value = "/updateLocked")
    public ResponseResult<String> updateLocked(@RequestBody Map<String, Object> map) {
        System.out.println(map);
        boolean isOk = userService.updateLocked((Integer) (map.get("userid")));
        return isOk ? ResponseResult.success("修改成功") : ResponseResult.error("修改失败了！");
    }

    @PutMapping(value = {"/updateUserInfo"})
    public ResponseResult<String> updateUserInfo(@RequestBody UpdateUserBasicInfoRequest updateUserBasicInfoRequest) {
        System.out.println(updateUserBasicInfoRequest);
        if (!this.userService.updateUserBasicInfo(updateUserBasicInfoRequest)) {
            throw new BizException(ExceptionType.USER_UPDATE_ERROR);
        }
        return ResponseResult.success("更新成功");
    }

    @GetMapping(value = {"/searchUser/{phone}"})
    public ResponseResult<UserVo> searchUser(@PathVariable String phone) {
        User one = this.userService.getOne(Wrappers.<User>lambdaQuery().eq(User::getPhone, phone));
        if (one == null) {
            throw new BizException(ExceptionType.USER_NOT_FOUND);
        }
        UserDto userDto = this.userMapper.entity2Dto(one);
        if (userDto.getHeadImg() != null) {
            userDto.getHeadImg().setDownloadUrl(this.tencentCos.getBaseUrl() + userDto.getHeadImg().getDownloadUrl());
        }
        return ResponseResult.success(this.userMapper.dto2Vo(userDto));
    }

    @GetMapping(value = {"/isFollow/{id}"})
    public ResponseResult<Boolean> isFollower(@PathVariable Integer id) {
        return ResponseResult.success(this.userService.isFollow(id));
    }

    @PostMapping(value = {"/follow"})
    public ResponseResult<String> follow(@RequestBody FollowRequest followRequest) {
        System.out.println(followRequest);
        if (!this.userService.follow(followRequest)) {
            return ResponseResult.error("关注失败");
        }
        return ResponseResult.success("关注成功");
    }

    @GetMapping(value = {"/getFollowers"})
    public ResponseResult<List<UserVo>> getFollowers() {
        return ResponseResult.success(this.userService.getFollowers().stream().map(this.userMapper::dto2Vo).collect(Collectors.toList()));
    }

    @GetMapping(value = {"/getFans"})
    public ResponseResult<List<UserVo>> getFans() {
        return ResponseResult.success(this.userService.getFans().stream().map(this.userMapper::dto2Vo).collect(Collectors.toList()));
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
