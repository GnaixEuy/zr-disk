package cn.realandy.zrdisk.controller.admin;

import cn.realandy.zrdisk.entity.User;
import cn.realandy.zrdisk.mapper.UserMapper;
import cn.realandy.zrdisk.service.UserService;
import cn.realandy.zrdisk.vo.ResponseResult;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.Map;

/**
 * <img src="http://blog.gnaixeuy.cn/wp-content/uploads/2022/09/倒闭.png"/>
 *
 * <p>项目： zr-disk </p>
 * 创建日期： 2022/11/11
 *
 * @author GnaixEuy
 * @version 1.0.0
 * @see <a href="https://github.com/GnaixEuy"> GnaixEuy的GitHub </a>
 */
@RestController
@RequestMapping(value = {"/admin/user"})
public class AdminUserController {

    private UserService userService;
    private UserMapper userMapper;

    //获取全部用户接口
    @GetMapping(value = {"/getAll"})
    @RolesAllowed(value = {"ROLE_ADMIN"})
    public ResponseResult<Page<User>> getAllUsers(Page<User> page) {

        return ResponseResult.success(this.userService.page(page));
    }

    //根据电话号码获取用户接口
    @RolesAllowed(value = {"ROLE_ADMIN"})
    @GetMapping(value = {"/getByPhone"})
    public ResponseResult<Page<User>> getUsersByPhone(Page<User> page, String phone) {
        System.out.println(phone);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("phone", phone);

        return ResponseResult.success(this.userService.page(page, queryWrapper));
    }

    //修改用户信息
    @RolesAllowed(value = {"ROLE_ADMIN"})
    @PostMapping(value = {"/updateUser"})
    public ResponseResult<String> updateUser(@RequestBody User user) {
        System.out.println(user.getBirthday());
        int result = this.userService.updateUser(user);
        if (result == 1) {
            return ResponseResult.success("用户修改成功");
        } else if (result == -1){
            return ResponseResult.error("该手机号已经被他人使用");
        } else if (result == -2){
            return ResponseResult.error("可用空间 不得小于 已使用空间！");
        } else {
            return ResponseResult.error("用户修改失败了");
        }



    }


    @RequestMapping(value = {"/logout"})
    public ResponseResult<String> logout() {
        return null;
    }


    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }
}
