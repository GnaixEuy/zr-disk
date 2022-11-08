package cn.realandy.zrdisk.service;

import cn.realandy.zrdisk.dto.FileDto;
import cn.realandy.zrdisk.entity.File;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

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
public interface FileService extends IService<File> {

    /**
     * 保存用户头像
     *
     * @param file          文件对象
     * @param multipartFile 文件
     * @return 保存后完整的文件对象
     */
    FileDto saveUserHeadImage(File file, MultipartFile multipartFile);

}
