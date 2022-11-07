package cn.realandy.zrdisk.service;

import cn.realandy.zrdisk.dto.UserDto;
import cn.realandy.zrdisk.entity.User;
import cn.realandy.zrdisk.vo.UserCreateRequest;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * <img src="http://blog.gnaixeuy.cn/wp-content/uploads/2022/09/倒闭.png"/>
 *
 * <p>项目： zr-disk </p>
 * 创建日期： 2022/11/5
 *
 * @author GnaixEuy
 * @version 1.0.0
 * @see <a href="https://github.com/GnaixEuy"> GnaixEuy的GitHub </a>
 */
public interface UserService extends IService<User>, UserDetailsService {

    /**
     * 新增用户业务
     *
     * @param userCreateRequest 新增用户传入参数
     * @return 返回是否新增数据对象vo
     */
    UserDto addUser(UserCreateRequest userCreateRequest);

    /**
     * 获取当前登录用户
     *
     * @return 返回当前登录用户
     */
    User getCurrentUser();

    /**
     * 更新用户密码
     *
     * @param password 密码
     * @return 是否成功
     */
    boolean updateUserPassword(String password);


}
