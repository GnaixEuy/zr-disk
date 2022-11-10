package cn.realandy.zrdisk.service.impl;

import cn.realandy.zrdisk.config.TencentCosConfig;
import cn.realandy.zrdisk.dao.FileDao;
import cn.realandy.zrdisk.dao.UserDao;
import cn.realandy.zrdisk.dto.FileDto;
import cn.realandy.zrdisk.dto.UserDto;
import cn.realandy.zrdisk.enmus.FileStatus;
import cn.realandy.zrdisk.enmus.FileType;
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
import cn.realandy.zrdisk.utils.VideoUtils;
import cn.realandy.zrdisk.vo.FileMergeRequest;
import cn.realandy.zrdisk.vo.UserMkdirRequest;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
            this.cosUploadUtil.upload(this.tencentCos.getBucketName(), parentPath, localFile, originalFilename);
            file.setParentPath("root");
            file.setType(FileTypeTransformer.getFileTypeFromExt(ext));
            file.setId(fileNameUUID);
            file.setDownloadUrl(parentPath);
            file.setCoverUrl(file.getDownloadUrl());
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
     * 获取当前登录用户的所有文件信息
     *
     * @return list <filedto></>
     */
    @Override
    public Page<FileDto> getUserFilesPage(Page page, String parentFileId) {
        UserDto currentUserDto = this.userService.getCurrentUserDto();
        QueryWrapper<File> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("uploader_id", currentUserDto.getId());
        queryWrapper.eq("parent_file_id", parentFileId);

        Page<File> resultPage = this.baseMapper.selectPage(page, queryWrapper);
        List<FileDto> collect = resultPage.getRecords()
                .stream()
                .map(this.fileMapper::entity2Dto)
                .collect(Collectors.toList());
        collect.forEach(item -> {
            item.setUploader(currentUserDto);
            if (item.getUploader().isLocked()) {
                item.setDownloadUrl("违规锁定图片地址");
                item.setCoverUrl("违规锁定图片地址");
            } else if (item.getUploader().isEnabled()) {
                item.setDownloadUrl("私密图片地址");
                item.setCoverUrl("私密图片地址");
            } else {
                item.setDownloadUrl(this.tencentCos.getBaseUrl() + item.getDownloadUrl());
                item.setCoverUrl(this.tencentCos.getBaseUrl() + item.getCoverUrl());
            }
        });
        page.setRecords(collect);
        page.setPages(resultPage.getPages());
        page.setTotal(resultPage.getTotal());
        page.setSize(resultPage.getSize());
        return page;
    }

    /**
     * 获取当前用户文件数量
     *
     * @return 文件数量
     */
    @Override
    public Integer getUserFilesTotal() {
        return this.baseMapper.getUserFilesTotal(this.userService.getCurrentUserDto().getId());
    }

    /**
     * 指定文件类型获取当前用户的文件
     *
     * @param fileType 文件类型
     */
    @Override
    public List<FileDto> getUserFilesByType(FileType fileType) {
        UserDto currentUserDto = this.userService.getCurrentUserDto();
        List<FileDto> collect = this.baseMapper.selectList(Wrappers
                        .<File>lambdaQuery()
                        .eq(File::getUploaderId, currentUserDto.getId())
                        .eq(File::getType, fileType)
                )
                .stream()
                .map(this.fileMapper::entity2Dto)
                .collect(Collectors.toList());
        collect.forEach(item -> {
            item.setUploader(currentUserDto);
            item.setDownloadUrl(this.tencentCos.getBaseUrl() + item.getDownloadUrl());
            item.setCoverUrl(item.getDownloadUrl());
        });
        return collect;
    }

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
    @Override
    public boolean uploadPart(HttpServletRequest request, HttpServletResponse response, String md5, Integer chunk, MultipartFile file, Integer chunks) {
        try {
            String projectUrl = System.getProperty("user.dir").replaceAll("\\\\", "/");
            boolean isMultipart = ServletFileUpload.isMultipartContent(request);
            if (isMultipart) {
                if (chunk == null) {
                    chunk = 0;
                }
                // 临时目录用来存放所有分片文件
                String tempFileDir = projectUrl + "/upload/" + md5;
                java.io.File parentFileDir = new java.io.File(tempFileDir);
                if (!parentFileDir.exists()) {
                    parentFileDir.mkdirs();
                }
                // 分片处理时，前台会多次调用上传接口，每次都会上传文件的一部分到后台
                java.io.File tempPartFile = new java.io.File(parentFileDir, md5 + "_" + chunk + ".part");
                org.apache.commons.io.FileUtils.copyInputStreamToFile(file.getInputStream(), tempPartFile);
                return true;
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new BizException(ExceptionType.FILE_UPLOAD_ERROR);
        }
        return false;
    }

    /**
     * 返回一个merge好的大文件对象
     *
     * @param fileMergeRequest
     * @return merge好的大文件对象
     */
    @Override
    public java.io.File mergeFile(FileMergeRequest fileMergeRequest) {
        String md5 = fileMergeRequest.getMd5();
        // 得到 destTempFile 就是最终的文件
        String projectUrl = System.getProperty("user.dir").replaceAll("\\\\", "/");
        try {
            String sName = fileMergeRequest.getFileName().substring(fileMergeRequest.getFileName().lastIndexOf("."));
            //时间格式化格式
            Date currentTime = new Date();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
            //获取当前时间并作为时间戳
            String timeStamp = simpleDateFormat.format(currentTime);
            //拼接新的文件名
            String newName = timeStamp + sName;
            simpleDateFormat = new SimpleDateFormat("yyyyMM");
            String path = projectUrl + "/upload/";
            String tmp = simpleDateFormat.format(currentTime);
            java.io.File parentFileDir = new java.io.File(path + md5);
            if (parentFileDir.isDirectory()) {
                java.io.File destTempFile = new java.io.File(path + tmp, newName);
                if (!destTempFile.exists()) {
                    //先得到文件的上级目录，并创建上级目录，在创建文件
                    destTempFile.getParentFile().mkdir();
                    try {
                        destTempFile.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                for (int i = 0; i < parentFileDir.listFiles().length; i++) {
                    java.io.File partFile = new java.io.File(parentFileDir, md5 + "_" + i + ".part");
                    FileOutputStream destTempfos = new FileOutputStream(destTempFile, true);
                    //遍历"所有分片文件"到"最终文件"中
                    FileUtils.copyFile(partFile, destTempfos);
                    destTempfos.close();
                }
                // 删除临时目录中的分片文件
                FileUtils.deleteDirectory(parentFileDir);
                return destTempFile;
            } else {
                throw new BizException(ExceptionType.FILE_MERGE_ERROR);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new BizException(ExceptionType.FILE_MERGE_ERROR);
        }
    }

    /**
     * 对合并好的file进行持久化
     *
     * @param file
     * @param fileMergeRequest
     * @return fileDto
     */
    @Override
    @Transactional
    @CacheEvict(cacheNames = {"userInfo"}, key = "target.getCurrentUser.phone")
    public FileDto bigFileUpload(java.io.File file, FileMergeRequest fileMergeRequest) {
        UserDto currentUserDto = this.userService.getCurrentUserDto();
        String[] split = fileMergeRequest.getFileName().split("\\.");
        File fileInfo = new File();
        fileInfo.setName(fileMergeRequest.getFileName());
        fileInfo.setHash(fileMergeRequest.getMd5());
        fileInfo.setStorage(Storage.COS);
        fileInfo.setParentPath(fileMergeRequest.getParentPath());
        fileInfo.setParentFolder(fileMergeRequest.getParentFolder());
        fileInfo.setSize(BigDecimal.valueOf(file.length()));
        fileInfo.setUploaderId(currentUserDto.getId());
        fileInfo.setExt(split[split.length - 1]);
        fileInfo.setType(FileTypeTransformer.getFileTypeFromExt(split[split.length - 1]));
        String fileNameUUID = this.ksuidIdentifierGenerator.nextUUID(fileInfo);
        String filePath = "/" + TencentCosConfig.COS_ATTACHMENT + "/" + currentUserDto.getId() + "/" + fileInfo.getParentPath() + "/" + fileNameUUID + "." + fileInfo.getExt();
        this.cosUploadUtil.upload(this.tencentCos.getBucketName(), filePath, file, fileInfo.getName());
        fileInfo.setDownloadUrl(filePath);
        fileInfo.setParentFileId(fileMergeRequest.getParentFileId());
        if (fileInfo.getType() == FileType.IMAGE || fileInfo.getType() == FileType.AUDIO) {
            fileInfo.setCoverUrl(filePath);
        }
        if (fileInfo.getType() == FileType.VIDEO) {
            System.out.println(file.getPath());

            String projectUrl = System.getProperty("user.dir").replaceAll("\\\\", "/");
            // 临时目录用来存放所有分片文件
            String tempFileDir = projectUrl + "/tmpCoverImage/";
            java.io.File parentFileDir = new java.io.File(tempFileDir);
            if (!parentFileDir.exists()) {
                parentFileDir.mkdirs();
            }
            String coverUploadPath = tempFileDir + fileNameUUID + "_cover.jpg";
            try {
                Objects.requireNonNull(VideoUtils.base64ToMultipart(
                        VideoUtils.fetchFrame(file.getPath())
                )).transferTo(new java.io.File(coverUploadPath));
                java.io.File cover = new java.io.File(coverUploadPath);
                String coverUlr = "/" + TencentCosConfig.COS_ATTACHMENT + "/" + currentUserDto.getId() + "/" + fileInfo.getParentPath() + "/" + fileNameUUID + "_cover.jpg";
                this.cosUploadUtil.upload(
                        this.tencentCos.getBucketName(),
                        coverUlr,
                        cover,
                        coverUploadPath);
                fileInfo.setCoverUrl(coverUlr);
                FileUtils.delete(cover);
            } catch (IOException e) {
                throw new RuntimeException("缩略图生成异常");
            }
        }
        int insertResult = this.baseMapper.insert(fileInfo);
        if (insertResult != 1) {
            throw new BizException(ExceptionType.FILE_UPLOAD_ERROR);
        }
        FileDto fileDto = this.fileMapper.entity2Dto(this.baseMapper.selectById(fileInfo.getId()));
        fileDto.setUploader(currentUserDto);
        this.updateUserDriveUsed(file);
        return fileDto;
    }

    /**
     * 获取模糊查询List列表
     *
     * @param searchWord 模糊关键字
     * @return list fileDto
     */
    @Override
    public List<FileDto> listLikeSearchWord(String searchWord) {
        UserDto currentUserDto = this.userService.getCurrentUserDto();
        List<FileDto> collect = this.baseMapper.selectList(
                Wrappers.<File>lambdaQuery()
                        .eq(File::getUploaderId, currentUserDto.getId())
                        .like(File::getName, searchWord)
        ).stream().map(this.fileMapper::entity2Dto).collect(Collectors.toList());
        collect.forEach(item -> {
            item.setUploader(currentUserDto);
            item.setDownloadUrl(this.tencentCos.getBaseUrl() + item.getDownloadUrl());
            if (item.getType() == FileType.IMAGE || item.getType() == FileType.VIDEO || item.getType() == FileType.AUDIO) {
                item.setCoverUrl(item.getDownloadUrl());
            }
        });
        return collect;
    }

    /**
     * 获取当前用户的收藏文件信息
     *
     * @return list fileDto
     */
    @Override
    public List<FileDto> getCollection() {
        UserDto currentUserDto = this.userService.getCurrentUserDto();
        List<FileDto> collect = this.baseMapper.selectList(
                Wrappers.<File>lambdaQuery()
                        .eq(File::getUploaderId, currentUserDto.getId())
                        .eq(File::isCollection, true)
                        .orderByAsc(File::getCreatedDateTime)
        ).stream().map(this.fileMapper::entity2Dto).collect(Collectors.toList());
        collect.forEach(item -> {
            item.setUploader(currentUserDto);
            item.setCoverUrl(this.tencentCos.getBaseUrl() + item.getCoverUrl());
            item.setDownloadUrl(this.tencentCos.getBaseUrl() + item.getDownloadUrl());
        });
        return collect;
    }

    /**
     * 用户新建文件夹记录
     *
     * @param userMkdirRequest 新建文件夹请求
     * @return 是否成功
     */
    @Override
    public boolean mkdir(UserMkdirRequest userMkdirRequest) {
        User currentUser = this.userService.getCurrentUser();
        File file = new File();
        file.setExt("");
        file.setSize(BigDecimal.ZERO);
        file.setType(FileType.DIR);
        file.setName(userMkdirRequest.getName());
        file.setParentFileId(userMkdirRequest.getParentFileId());
        file.setHash(String.valueOf(file.getName().hashCode()));
        file.setUploaderId(currentUser.getId());
        File parentFile = this.baseMapper.selectById(userMkdirRequest.getParentFileId());
        if (parentFile != null) {
            file.setParentPath(parentFile.getParentPath());
        } else {
            file.setParentPath("/root");
        }
        return 1 == this.baseMapper.insert(file);
    }

    /**
     * 删除用户文件业务
     *
     * @param id 文件id
     */
    @Override
    @Transactional
    @CacheEvict(cacheNames = {"userInfo"}, key = "target.getCurrentUser.phone")
    public boolean deleteFileById(String id) {
        BigDecimal size = this.getById(id).getSize();
        try {
            this.removeById(id);
        } catch (Exception e) {
            throw new BizException(ExceptionType.FILE_DELETE_ERROR);
        }
        User currentUser;
        try {
            currentUser = this.userService.getCurrentUser();
            currentUser.setDriveUsed(currentUser.getDriveUsed().subtract(size));
            this.userService.updateById(currentUser);
        } catch (Exception e) {
            throw new BizException(ExceptionType.USER_UPDATE_ERROR);
        }
        return true;
    }


    private boolean updateUserDriveUsed(java.io.File file) {
        User currentUser = this.userService.getCurrentUser();
        currentUser.setDriveUsed(currentUser.getDriveUsed().add(BigDecimal.valueOf(file.length())));
        return this.userService.updateById(currentUser);
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
