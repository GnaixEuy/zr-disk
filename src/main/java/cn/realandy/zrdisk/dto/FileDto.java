package cn.realandy.zrdisk.dto;

import cn.realandy.zrdisk.enmus.FileStatus;
import cn.realandy.zrdisk.enmus.FileType;
import cn.realandy.zrdisk.enmus.Storage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

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
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileDto {

    private String id;
    private String name;
    private String hash;
    private String ext;
    private long size;
    private String parentFileId;
    private String parentFolder;
    private String downloadUrl;
    private FileType type;
    private UserDto uploader;
    private Date createdDateTime;
    private Date updatedDateTime;
    private boolean isDelete;
    private String parentPath;
    private boolean locked;
    private String coverUrl;
    //待使用
    private Storage storage;
    private FileStatus status;

}
