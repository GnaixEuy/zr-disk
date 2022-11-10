package cn.realandy.zrdisk.entity;

import cn.realandy.zrdisk.enmus.FileType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * <img src="http://blog.gnaixeuy.cn/wp-content/uploads/2022/09/倒闭.png"/>
 *
 * <p>项目： zr-disk </p>
 * 创建日期： 2022/11/10
 *
 * @author GnaixEuy
 * @version 1.0.0
 * @see <a href="https://github.com/GnaixEuy"> GnaixEuy的GitHub </a>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileParentChildDto {
    private String id;
    private String name;
    private List<FileParentChildDto> children;
    private String hash;
    private String ext;
    private FileType type;
    private BigDecimal size;
    private String parentFileId;
    private String parentFolder;
    private String downloadUrl;
    private Date createdDateTime;
    private Date updatedDateTime;
    private String parentPath;
    private String coverUrl;
    private boolean collection;

}
