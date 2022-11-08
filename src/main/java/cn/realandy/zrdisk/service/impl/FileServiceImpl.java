package cn.realandy.zrdisk.service.impl;

import cn.realandy.zrdisk.config.TencentCosConfig;
import cn.realandy.zrdisk.dao.FileDao;
import cn.realandy.zrdisk.dao.UserDao;
import cn.realandy.zrdisk.dto.FileDto;
import cn.realandy.zrdisk.enmus.FileStatus;
import cn.realandy.zrdisk.enmus.Storage;
import cn.realandy.zrdisk.entity.File;
import cn.realandy.zrdisk.entity.TencentCos;
import cn.realandy.zrdisk.entity.User;
import cn.realandy.zrdisk.exception.BizException;
import cn.realandy.zrdisk.exception.ExceptionType;
import cn.realandy.zrdisk.mapper.FileMapper;
import cn.realandy.zrdisk.service.FileService;
import cn.realandy.zrdisk.service.UserService;
import cn.realandy.zrdisk.utils.CosUploadUtil;
import cn.realandy.zrdisk.utils.FileTypeTransformer;
import cn.realandy.zrdisk.utils.KsuidIdentifierGenerator;
import cn.realandy.zrdisk.vo.ResponseResult;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;

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
@Slf4j
@Service
public class FileServiceImpl extends ServiceImpl<FileDao, File> implements FileService {

    private UserService userService;
    private FileMapper fileMapper;
    private KsuidIdentifierGenerator ksuidIdentifierGenerator;
    private CosUploadUtil cosUploadUtil;
    private TencentCos tencentCos;

    /**
     * 保存用户头像
     *
     * @param file          文件对象
     * @param multipartFile 文件
     * @return 保存后完整的文件对象
     */
    @Override
    @Transactional
    @CacheEvict(cacheNames = {"userInfo"}, key = "target.getCurrentUser.phone")
    public FileDto saveUserHeadImage(File file, MultipartFile multipartFile) {
        try {
            String[] split = file.getName().split("\\.");
            String ext = split[split.length - 1];
            file.setExt(ext);
            file.setHash(String.valueOf(multipartFile.hashCode()));
            User currentUser = this.userService.getCurrentUser();
            if (currentUser == null) {
                throw new BizException(ExceptionType.USER_NOT_FOUND);
            }
            file.setUploaderId(currentUser.getId());
            file.setDelete(false);
            file.setLocked(false);
            file.setParentPath("/userHeadImage");
            file.setName("userHeadImage_" + file.getName());
            String parentPath = "/UserHeadImage";
            file.setStorage(Storage.COS);
            file.setStatus(FileStatus.UPLOADED);
            String fileNameUUID = this.ksuidIdentifierGenerator.nextUUID(multipartFile);
            String originalFilename = multipartFile.getOriginalFilename();
            java.io.File localFile = java.io.File.createTempFile(split[split.length - 2], split[split.length - 1]);
            multipartFile.transferTo(localFile);
            parentPath = "/" + TencentCosConfig.COS_ATTACHMENT + "/" + currentUser.getId() + parentPath + "/" + fileNameUUID + "." + ext;
            ResponseResult<HashMap<String, Object>> upload = this.cosUploadUtil.upload(this.tencentCos.getBucketName(), parentPath, localFile, originalFilename);
            file.setParentPath(parentPath);
            file.setType(FileTypeTransformer.getFileTypeFromExt(ext));
            file.setId(fileNameUUID);
            HashMap<String, Object> data = upload.getData();
            data.put("fileId", fileNameUUID);
            file.setDownloadUrl(parentPath);
            int insertResult = this.baseMapper.insert(file);
            if (insertResult != 1) {
                throw new BizException(ExceptionType.FILE_UPLOAD_ERROR);
            }
            int result = ((UserDao) this.userService.getBaseMapper()).updateHeadImgId(currentUser, file.getId());
            if (result != 1) {
                throw new BizException(ExceptionType.USER_UPDATE_ERROR);
            }

            return this.fileMapper.entity2Dto(this.baseMapper.selectById(file.getId()));
        } catch (Exception e) {
            e.printStackTrace();
            log.info("MultipartFile transfer file IOException ={}", e.getMessage());
            //文件上传失败就返回错误响应
            throw new BizException(ExceptionType.FILE_UPLOAD_ERROR);
        }
    }

    /**
     * 获取当前登录用户
     *
     * @return 返回当前登录用户
     */
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (User) this.userService.loadUserByUsername(authentication.getName());
    }


    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setKsuidIdentifierGenerator(KsuidIdentifierGenerator ksuidIdentifierGenerator) {
        this.ksuidIdentifierGenerator = ksuidIdentifierGenerator;
    }

    @Autowired
    public void setFileMapper(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }

    @Autowired
    public void setCosUploadUtil(CosUploadUtil cosUploadUtil) {
        this.cosUploadUtil = cosUploadUtil;
    }

    @Autowired
    public void setTencentCos(TencentCos tencentCos) {
        this.tencentCos = tencentCos;
    }
}
