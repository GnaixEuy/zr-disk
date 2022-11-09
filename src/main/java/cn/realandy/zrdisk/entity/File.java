package cn.realandy.zrdisk.entity;

import cn.realandy.zrdisk.enmus.FileStatus;
import cn.realandy.zrdisk.enmus.FileType;
import cn.realandy.zrdisk.enmus.Storage;
import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * <img src="http://blog.gnaixeuy.cn/wp-content/uploads/2022/09/倒闭.png"/>
 * <p>
 * TODO 待完成
 *
 * <p>项目： zr-disk </p>
 *
 * @author GnaixEuy
 * @version 1.0.0
 * @date 2022/11/4
 * @see <a href="https://github.com/GnaixEuy"> GnaixEuy的GitHub </a>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "file")
public class File implements Serializable {

    @TableId(type = IdType.ASSIGN_UUID)
    private String id;
    @TableField
    private String name;
    @TableField
    private String hash;
    @TableField
    private String ext;
    @TableField
    private long size;
    @TableField
    private String parentFileId;
    @TableField
    private String parentFolder;
    @TableField
    private String downloadUrl;
    @TableField
    private FileType type;
    @TableField
    private Integer uploaderId;
    @TableField(value = "created_date_time", fill = FieldFill.INSERT)
    private Date createdDateTime;
    @TableField(value = "updated_date_time", fill = FieldFill.INSERT_UPDATE)
    private Date updatedDateTime;
    @TableLogic
    @TableField(select = false)
    private boolean isDelete;
    @TableField
    private String parentPath;
    @TableField
    private boolean locked;
    @TableField
    private String coverUrl;
    @TableField
    private Storage storage;
    @TableField
    private FileStatus status;
    @TableField(value = "is_collection")
    private boolean collection;

}
