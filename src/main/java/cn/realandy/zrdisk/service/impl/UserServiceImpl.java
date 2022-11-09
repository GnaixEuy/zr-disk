package cn.realandy.zrdisk.service.impl;

import cn.realandy.zrdisk.config.RedisConfig;
import cn.realandy.zrdisk.dao.RoleDao;
import cn.realandy.zrdisk.dao.UserDao;
import cn.realandy.zrdisk.dao.relation.UserRoleAssociatedDao;
import cn.realandy.zrdisk.dto.UserDto;
import cn.realandy.zrdisk.entity.Role;
import cn.realandy.zrdisk.entity.User;
import cn.realandy.zrdisk.entity.relation.UserRoleAssociated;
import cn.realandy.zrdisk.exception.BizException;
import cn.realandy.zrdisk.exception.ExceptionType;
import cn.realandy.zrdisk.mapper.UserMapper;
import cn.realandy.zrdisk.service.UserService;
import cn.realandy.zrdisk.vo.UserCreateRequest;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

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
@Service
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements UserService {

    /**
     * 映射转换对象
     */
    private UserMapper userMapper;
    private RoleDao roleDao;
    private UserRoleAssociatedDao userRoleAssociateDao;
    private PasswordEncoder passwordEncoder;
    private RedisConfig redisConfig;

    /**
     * @param phone 手机号码作为用户名，手机必须绑定才能使用
     * @return Userdetails
     * @throws UsernameNotFoundException
     */
    @Override
    @Cacheable(value = {"userInfo"}, key = "#phone")
    public UserDetails loadUserByUsername(String phone) throws UsernameNotFoundException {
        return this.baseMapper.selectOne(Wrappers.<User>lambdaQuery().eq(User::getPhone, phone));
    }

    /**
     * 新增用户业务
     *
     * @param userCreateRequest 新增用户传入参数
     * @return 返回是否新增数据对象vo
     */
    @Override
    @Transactional
    public UserDto addUser(UserCreateRequest userCreateRequest) {
        User user = this.userMapper.userCreateRequst2Entity(userCreateRequest);
        user.setCreatedDateTime(new Date());
        user.setPassword(this.passwordEncoder.encode(userCreateRequest.getPassword()));
        //TODO 改为配置
        user.setDriveSize(new BigDecimal("5368709120"));
        int result = this.baseMapper.insert(user);
        if (result != 1) {
            throw new BizException(ExceptionType.USER_CREATE_EXCEPTION);
        }
        user = this.baseMapper
                .selectOne(Wrappers
                        .<User>lambdaQuery()
                        .eq(User::getId, user.getId()));
        if (user == null) {
            throw new BizException(ExceptionType.USER_NOT_FOUND);
        }
        //默认新用户赋予用户权限
        int authorizeResult = this.userRoleAssociateDao.insert(new UserRoleAssociated(1, user.getId()));
        if (authorizeResult != 1) {
            throw new BizException(ExceptionType.AUTHORIZATION_EXCEPTION);
        }
        //填充默认用户权限信息
        Role role = this.roleDao.selectOne(Wrappers.<Role>lambdaQuery().eq(Role::getId, 1));
        ArrayList<Role> roles = new ArrayList<>();
        roles.add(role);
        user.setRoles(roles);
        return this.userMapper.entity2Dto(user);
    }


    /**
     * 获取当前登录用户
     *
     * @return 返回当前登录用户
     */
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (User) this.loadUserByUsername(authentication.getName());
    }


    /**
     * 获取当前登录用户dto
     *
     * @return 返回当前登录用户
     */
    @Override
    public UserDto getCurrentUserDto() {
        return this.userMapper.entity2Dto(this.getCurrentUser());
    }

    /**
     * 更新用户密码
     *
     * @param password 密码
     * @return 是否成功
     */
    @Override
    @CacheEvict(cacheNames = {"userInfo"}, key = "target.getCurrentUser.phone")
    public boolean updateUserPassword(String password) {
        String encode = this.passwordEncoder.encode(password);
        User currentUser = this.getCurrentUser();
        currentUser.setPassword(encode);
        if (1 != this.baseMapper.updateById(currentUser)) {
            throw new BizException(ExceptionType.USER_UPDATE_ERROR);
        }
        return true;
    }

    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Autowired
    public void setRoleDao(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Autowired
    public void setUserRoleAssociateDao(UserRoleAssociatedDao userRoleAssociateDao) {
        this.userRoleAssociateDao = userRoleAssociateDao;
    }

    @Lazy
    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public void setRedisConfig(RedisConfig redisConfig) {
        this.redisConfig = redisConfig;
    }
}
