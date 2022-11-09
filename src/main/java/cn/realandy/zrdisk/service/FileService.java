package cn.realandy.zrdisk.service;

import cn.realandy.zrdisk.dto.FileDto;
import cn.realandy.zrdisk.enmus.FileType;
import cn.realandy.zrdisk.entity.File;
import cn.realandy.zrdisk.vo.FileMergeRequest;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

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

    /**
     * 获取当前登录用户的所有文件信息
     *
     * @return list <filedto></>
     */
    Page<FileDto> getUserFilesPage(Page page);


    /**
     * 获取当前用户文件数量
     *
     * @return 文件数量
     */
    Integer getUserFilesTotal();

    /**
     * 指定文件类型获取当前用户的文件
     *
     * @param fileType 文件类型
     */
    List<FileDto> getUserFilesByType(FileType fileType);

    /**
     * 切片上传上传部分
     *
     * @param request
     * @param response
     * @param md5
     * @param chunk
     * @param file
     * @param chunks
     * @return jaava.io.file对象
     */
    boolean uploadPart(HttpServletRequest request, HttpServletResponse response, String md5, Integer chunk, MultipartFile file, Integer chunks);

    /**
     * 返回一个merge好的大文件对象
     *
     * @param fileMergeRequest
     * @return merge好的大文件对象
     */
    java.io.File mergeFile(FileMergeRequest fileMergeRequest);

    /**
     * 对合并好的file进行持久化
     *
     * @param file
     * @param fileMergeRequest
     * @return fileDto
     */
    FileDto bigFileUpload(java.io.File file, FileMergeRequest fileMergeRequest);

    /**
     * 获取模糊查询List列表
     *
     * @param searchWord 模糊关键字
     * @return list fileDto
     */
    List<FileDto> listLikeSearchWord(String searchWord);


    /**
     * 获取当前用户的收藏文件信息
     *
     * @return list fileDto
     */
    List<FileDto> getCollection();

}
