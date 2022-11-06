package cn.realandy.zrdisk.mapper;

import cn.realandy.zrdisk.dto.UserDto;
import cn.realandy.zrdisk.entity.User;
import cn.realandy.zrdisk.vo.UserCreateRequest;
import cn.realandy.zrdisk.vo.UserVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

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
@Mapper(componentModel = "spring")
public interface UserMapper {

    /**
     * @param user 实体层对象
     * @return userVo vo对象
     */
    @Mappings(value = {
            @Mapping(target = "birthday", source = "birthday", dateFormat = "yyyy-MM-dd HH:mm:ss"),
    })
    UserDto entity2Dto(User user);

    @Mappings(value = {
            @Mapping(target = "birthday", source = "birthday", dateFormat = "yyyy-MM-dd HH:mm:ss"),
            @Mapping(target = "headImg", source = "headImg"),
    })
    UserVo dto2Vo(UserDto userDto);

    @Mappings(value = {
            @Mapping(target = "birthday", source = "birthday", dateFormat = "yyyy-MM-dd HH:mm:ss"),
            @Mapping(target = "enabled", source = "enabled"),
            @Mapping(target = "locked", source = "locked")
    })
    User dto2Entity(UserDto userDto);


    User userCreateRequst2Entity(UserCreateRequest userCreateRequest);

}