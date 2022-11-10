package cn.realandy.zrdisk.mapper;

import cn.realandy.zrdisk.dto.FileDto;
import cn.realandy.zrdisk.entity.File;
import cn.realandy.zrdisk.entity.FileParentChildDto;
import org.mapstruct.Mapper;

/**
 * <img src="http://blog.gnaixeuy.cn/wp-content/uploads/2022/09/倒闭.png"/>
 *
 * <p>项目： zr-disk </p>
 * 创建日期： 2022/11/7
 *
 * @author GnaixEuy
 * @version 1.0.0
 * @see <a href="https://github.com/GnaixEuy"> GnaixEuy的GitHub </a>
 */
@Mapper(componentModel = "spring")
public interface FileMapper {

    FileDto entity2Dto(File file);

    FileParentChildDto entity2FileParentChildDto(File file);

}
