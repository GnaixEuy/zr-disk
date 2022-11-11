package cn.realandy.zrdisk.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.realandy.zrdisk.config.RedisConfig;
import cn.realandy.zrdisk.config.SecurityConfig;
import cn.realandy.zrdisk.dto.UserDto;
import cn.realandy.zrdisk.enmus.RedisDbType;
import cn.realandy.zrdisk.entity.User;
import cn.realandy.zrdisk.exception.BizException;
import cn.realandy.zrdisk.exception.ExceptionType;
import cn.realandy.zrdisk.mapper.UserMapper;
import cn.realandy.zrdisk.service.LoginService;
import cn.realandy.zrdisk.service.UserService;
import cn.realandy.zrdisk.utils.ValidateCodeUtils;
import cn.realandy.zrdisk.vo.LoginByPhoneAndPasswordRequest;
import cn.realandy.zrdisk.vo.RegisteredUserByPhoneRequest;
import cn.realandy.zrdisk.vo.UserCreateRequest;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.TimeUnit;

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
@Service
public class LoginServiceImpl implements LoginService {

    @Value("#{T(java.lang.Integer).parseInt('${phone.verification.length:6}')}")
    private Integer phoneVerificationCodeLength;
    @Value("#{T(java.lang.Integer).parseInt('${phone.verification.live:120}')}")
    private Integer phoneVerificationCodeLiveTime;
    private RedisConfig redisConfig;
    private UserService userService;
    private UserMapper userMapper;

    private PasswordEncoder passwordEncoder;

    /**
     * 请求手机验证码
     *
     * @param phoneNumber 手机号码
     * @return 业务是否成功
     */
    @Override
    public boolean getPhoneVerificationCode(String phoneNumber) {
        if (StrUtil.isBlank(phoneNumber)) {
            throw new BizException(ExceptionType.DATA_IS_EMPTY);
        }
        RedisTemplate<String, Object> redisTemplateByDb = this.redisConfig.getRedisTemplateByDb(RedisDbType.PHONE_VERIFICATION_CODE.getCode());
        if (ObjectUtil.isNotNull(redisTemplateByDb.opsForValue().get(phoneNumber))) {
            throw new BizException(ExceptionType.PHONE_VERIFICATION_EXIT);
        }
        Integer verificationCode = ValidateCodeUtils.generateValidateCode(this.phoneVerificationCodeLength);
        LoginServiceImpl.log.info(phoneNumber + " verificationCode: " + verificationCode);
        redisTemplateByDb.opsForValue().set(phoneNumber, verificationCode, this.phoneVerificationCodeLiveTime, TimeUnit.SECONDS);
        try {
            //TODO 发送功能已正常，模版未申请
//            SmsUtils.sendMessage("阿里云短信测试", "SMS_154950909", phoneNumber, String.valueOf(verificationCode));
        } catch (Exception e) {
            e.printStackTrace();
        }
        //TODO 返回机制待确认
        return true;
    }

    /**
     * 通过手机方式登录
     *
     * @param registeredUserByPhoneRequest 手机注册方式包装对象 手机号和验证码 昵称密码
     * @return token
     */
    @Override
    public String createTokenByPhone(RegisteredUserByPhoneRequest registeredUserByPhoneRequest) {
        String realVerificationCode = String.valueOf(this.redisConfig
                .getRedisTemplateByDb(RedisDbType.PHONE_VERIFICATION_CODE.getCode())
                .opsForValue()
                .getAndDelete(registeredUserByPhoneRequest.getPhone()));
        if (realVerificationCode == null || "null".equals(realVerificationCode)) {
            throw new BizException(ExceptionType.PHONE_VERIFICATION_EXPIRED);
        }
        if (!registeredUserByPhoneRequest.getRealVerificationCode().equals(realVerificationCode)) {
            throw new BizException(ExceptionType.PHONE_VERIFICATION_CODE_ERROR);
        }
        User user = this.userService.getOne(
                Wrappers.<User>lambdaQuery().eq(User::getPhone, registeredUserByPhoneRequest.getPhone())
        );
        if (ObjectUtil.isNull(user)) {
            String encodePassword = this.passwordEncoder.encode(registeredUserByPhoneRequest.getPassword());
            UserDto userDto = this.userService.addUser(new UserCreateRequest() {{
                this.setPhone(registeredUserByPhoneRequest.getPhone());
                this.setPassword(encodePassword);
                this.setNickname(registeredUserByPhoneRequest.getNickname());
                this.setEnabled(true);
                this.setLocked(false);
            }});
            user = this.userMapper.dto2Entity(userDto);
        } else {
            throw new BizException(ExceptionType.USER_NAME_DUPLICATE);
        }
        return tokenVerifyAndGenerated(user);
    }

    /**
     * 通过手机和密码登陆获取token
     *
     * @param loginByPhoneAndPasswordRequest 手机号和密码封包
     * @return token
     */
    @Override
    public String getTokenByPhoneAndPassword(LoginByPhoneAndPasswordRequest loginByPhoneAndPasswordRequest) {
        User user = this.userService.getOne(Wrappers
                .<User>lambdaQuery()
                .eq(User::getPhone, loginByPhoneAndPasswordRequest.getPhone())
        );
        if (user == null) {
            throw new BizException(ExceptionType.USER_NOT_FOUND);
        }
        if (!passwordEncoder.matches(loginByPhoneAndPasswordRequest.getPassword(), user.getPassword())) {
            throw new BizException(ExceptionType.USER_PASSWORD_NOT_MATCH);
        }
        return tokenVerifyAndGenerated(user);
    }

    private String tokenVerifyAndGenerated(User user) {
        if (!user.isEnabled()) {
            throw new BizException(ExceptionType.USER_NOT_ENABLED);
        }
        if (!user.isAccountNonLocked()) {
            throw new BizException(ExceptionType.USER_LOCKED);
        }
        return JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + SecurityConfig.EXPIRATION_TIME))
                .sign(Algorithm.HMAC512(SecurityConfig.SECRET.getBytes()));
    }

    @Autowired
    public void setRedisConfig(RedisConfig redisConfig) {
        this.redisConfig = redisConfig;
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
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }
}