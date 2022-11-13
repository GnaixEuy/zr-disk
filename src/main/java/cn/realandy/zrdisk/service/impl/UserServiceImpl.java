package cn.realandy.zrdisk.service.impl;

import cn.realandy.zrdisk.config.RedisConfig;
import cn.realandy.zrdisk.dao.RoleDao;
import cn.realandy.zrdisk.dao.UserDao;
import cn.realandy.zrdisk.dao.relation.UserFollowDao;
import cn.realandy.zrdisk.dao.relation.UserRoleAssociatedDao;
import cn.realandy.zrdisk.dto.UserDto;
import cn.realandy.zrdisk.enmus.RedisDbType;
import cn.realandy.zrdisk.entity.File;
import cn.realandy.zrdisk.entity.Role;
import cn.realandy.zrdisk.entity.TencentCos;
import cn.realandy.zrdisk.entity.User;
import cn.realandy.zrdisk.entity.relation.UserFollow;
import cn.realandy.zrdisk.entity.relation.UserRoleAssociated;
import cn.realandy.zrdisk.exception.BizException;
import cn.realandy.zrdisk.exception.ExceptionType;
import cn.realandy.zrdisk.mapper.UserMapper;
import cn.realandy.zrdisk.service.UserService;
import cn.realandy.zrdisk.vo.FindPassRequest;
import cn.realandy.zrdisk.vo.FollowRequest;
import cn.realandy.zrdisk.vo.UpdateUserBasicInfoRequest;
import cn.realandy.zrdisk.vo.UserCreateRequest;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.core.RedisTemplate;
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
import java.util.List;
import java.util.stream.Collectors;

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
    private UserDao userDao;
    private UserFollowDao userFollowDao;

    private TencentCos tencentCos;


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
        user.setPassword(userCreateRequest.getPassword());
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
        this.baseMapper.updateHeadImgId(user, "1");
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
     * 用户重制密码业务
     *
     * @param findPassRequest
     */
    @Override
    @CacheEvict(cacheNames = {"userInfo"}, key = "#findPassRequest.username")
    public boolean findPass(FindPassRequest findPassRequest) {
        RedisTemplate<String, Object> redisTemplateByDb = this.redisConfig.getRedisTemplateByDb(RedisDbType.PHONE_VERIFICATION_CODE.getCode());
        Integer code = (Integer) redisTemplateByDb
                .opsForValue()
                .get(findPassRequest.getUsername());
        if (code == null || !code.equals(findPassRequest.getVerificationCode())) {
            throw new BizException(ExceptionType.PHONE_VERIFICATION_CODE_ERROR);
        }
        redisTemplateByDb.delete(findPassRequest.getUsername());
        User user = this.baseMapper.selectOne(
                Wrappers
                        .<User>lambdaQuery()
                        .eq(User::getPhone, findPassRequest.getUsername())
        );
        if (user == null) {
            throw new BizException(ExceptionType.USER_NOT_FOUND);
        }
        user.setPassword(this.passwordEncoder.encode(findPassRequest.getPassword()));
        return 1 == this.baseMapper.updateById(user);
    }

    /**
     * 用户登出
     */
    @Override
    @CacheEvict(cacheNames = {"userInfo"}, key = "target.getCurrentUser.phone")
    public void logout() {
    }

    /**
     * @return 返回用户全部数据
     */
    @Override
    public List<User> getAllUserInfo() {
        return this.userDao.selectList(null);
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

    /**
     * 后台用户封禁
     *
     * @param userid
     * @return
     */
    @Override
    public boolean updateLocked(Integer userid) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", userid);
        User user = userDao.selectOne(queryWrapper);
        user.setLocked(!user.isLocked());
        int i = userDao.updateById(user);
        return i == 1;
    }

    @Override
    public int updateUser(User user) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("phone", user.getPhone());
        User other = userDao.selectOne(queryWrapper);
        if (other != null) {

            if (!user.getId().equals(other.getId())) {
                return -1;
            }
        }

        if (user.getDriveSize().compareTo(user.getDriveUsed()) == -1) {
            return -2;
        }

        return userDao.updateById(user);
    }

    /**
     * 修改用户本身基本信息
     *
     * @param updateUserBasicInfoRequest
     */
    @Override
    @CacheEvict(cacheNames = {"userInfo"}, key = "target.getCurrentUser.phone", beforeInvocation = true)
    public boolean updateUserBasicInfo(UpdateUserBasicInfoRequest updateUserBasicInfoRequest) {
        User currentUser = this.getCurrentUser();
        if (currentUser == null) {
            throw new BizException(ExceptionType.USER_NOT_FOUND);
        }
        if (updateUserBasicInfoRequest.getBirthday() != null) {
            currentUser.setBirthday(updateUserBasicInfoRequest.getBirthday());
        }
        if (updateUserBasicInfoRequest.getGender() != null) {
            currentUser.setGender(updateUserBasicInfoRequest.getGender());
        }
        if (updateUserBasicInfoRequest.getPhone() != null) {
            currentUser.setPhone(updateUserBasicInfoRequest.getPhone());
        }
        if (updateUserBasicInfoRequest.getEmail() != null) {
            currentUser.setEmail(updateUserBasicInfoRequest.getEmail());
        }
        if (updateUserBasicInfoRequest.getNickname() != null) {
            currentUser.setNickname(updateUserBasicInfoRequest.getNickname());
        }
        return 1 == this.baseMapper.updateById(currentUser);
    }

    /**
     * 关注用户
     *
     * @return 是否成功
     */
    @Override
    public boolean follow(FollowRequest followRequest) {
        User currentUser = this.getCurrentUser();
        System.out.println(followRequest.getIsFollow());
        if ("关注".equals(followRequest.getIsFollow())) {
            User user = this.baseMapper.selectById(followRequest.getId());
            if (user == null) {
                throw new BizException(ExceptionType.USER_NOT_FOUND);
            }
            return 1 == this.userFollowDao.insert(new UserFollow(currentUser.getId(), followRequest.getId()));
        } else {
            return 1 == this.userFollowDao.delete(Wrappers.<UserFollow>lambdaUpdate().eq(UserFollow::getUserId, currentUser.getId()).eq(UserFollow::getFollowerId, followRequest.getId()));
        }
    }

    /**
     * 获取关注的人
     *
     * @return list
     */
    @Override
    public List<UserDto> getFollowers() {
        User currentUser = this.getCurrentUser();
        List<UserFollow> userFollows = this.userFollowDao.selectList(Wrappers.<UserFollow>lambdaQuery().eq(UserFollow::getUserId, currentUser.getId()));
        if (userFollows.size() == 0) {
            return new ArrayList<>();
        }
        List<Integer> integers = new ArrayList<>();
        userFollows.forEach(item -> {
            integers.add(item.getFollowerId());
        });
        List<UserDto> collect = this.listByIds(integers).stream().map(this.userMapper::entity2Dto).collect(Collectors.toList());
        collect.forEach(item -> {
            File headImg = item.getHeadImg();
            headImg.setDownloadUrl(this.tencentCos.getBaseUrl() + headImg.getDownloadUrl());
        });
        return collect;
    }

    /**
     * 是否关注
     *
     * @param id
     */
    @Override
    public boolean isFollow(Integer id) {
        User currentUser = this.getCurrentUser();
        if (this.userFollowDao.selectOne(Wrappers.<UserFollow>lambdaQuery().eq(UserFollow::getUserId, currentUser.getId()).eq(UserFollow::getFollowerId, id)) != null) {
            return true;
        }
        return false;

    }

    /**
     * 获取粉丝
     *
     * @return list
     */
    @Override
    public List<UserDto> getFans() {
        User currentUser = this.getCurrentUser();
        List<UserFollow> userFans = this.userFollowDao.selectList(Wrappers.<UserFollow>lambdaQuery().eq(UserFollow::getFollowerId, currentUser.getId()));
        if (userFans.size() == 0) {
            return new ArrayList<>();
        }
        List<Integer> integers = new ArrayList<>();
        userFans.forEach(item -> {
            System.out.println(item);
            integers.add(item.getUserId());
        });
        List<UserDto> collect = this.listByIds(integers).stream().map(this.userMapper::entity2Dto).collect(Collectors.toList());
        collect.forEach(item -> {
            File headImg = item.getHeadImg();
            headImg.setDownloadUrl(this.tencentCos.getBaseUrl() + headImg.getDownloadUrl());
        });
        return collect;
    }

    /**
     * 后台用户修改
     *
     * @param userMapper
     */


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

    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Autowired
    public void setUserFollowDao(UserFollowDao userFollowDao) {
        this.userFollowDao = userFollowDao;
    }

    @Autowired
    public void setTencentCos(TencentCos tencentCos) {
        this.tencentCos = tencentCos;
    }
}
