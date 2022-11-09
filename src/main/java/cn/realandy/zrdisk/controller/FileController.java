package cn.realandy.zrdisk.controller;

import cn.realandy.zrdisk.dto.FileDto;
import cn.realandy.zrdisk.enmus.FileType;
import cn.realandy.zrdisk.entity.File;
import cn.realandy.zrdisk.entity.TencentCos;
import cn.realandy.zrdisk.exception.BizException;
import cn.realandy.zrdisk.exception.ExceptionType;
import cn.realandy.zrdisk.service.FileService;
import cn.realandy.zrdisk.vo.FileAttributeUpdateRequest;
import cn.realandy.zrdisk.vo.FileMergeRequest;
import cn.realandy.zrdisk.vo.ResponseResult;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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
@Slf4j
@RestController
@RequestMapping(value = {"/file"})
public class FileController {

    private FileService fileService;

    private TencentCos tencentCos;

    @GetMapping(value = {"/getFile"})
    public ResponseResult<Page<FileDto>> getFileTotalsByUser(Page<FileDto> page) {
        return ResponseResult.success(this.fileService.getUserFilesPage(page));
    }

    @GetMapping(value = {"/getFileTotal"})
    public ResponseResult<Integer> getFileTotal() {
        return ResponseResult.success(this.fileService.getUserFilesTotal());
    }

    @GetMapping(value = {"/getPhoto"})
    public ResponseResult<List<FileDto>> getUserFilesPhoto() {
        List<FileDto> userFilesByType = this.fileService.getUserFilesByType(FileType.IMAGE);
        return ResponseResult.success(userFilesByType);
    }

    @DeleteMapping(value = {"/delFile"})
    public ResponseResult<String> deleteFileById(String id) {
        try {
            this.fileService.removeById(id);
        } catch (Exception e) {
            throw new BizException(ExceptionType.FILE_DELETE_ERROR);
        }
        //TODO 修改用户使用size空间
        return ResponseResult.success("删除成功");
    }

    @PutMapping(value = {"/modify"})
    @RolesAllowed(value = {"ROLE_USER", "ROLE_ADMIN"})
    public ResponseResult<String> modify(@RequestBody FileAttributeUpdateRequest fileAttributeUpdateRequest) {
        File byId = this.fileService.getById(fileAttributeUpdateRequest.getId());
        byId.setName(fileAttributeUpdateRequest.getFileName());
        byId.setType(fileAttributeUpdateRequest.getType());
        boolean reuslt = this.fileService.updateById(byId);
        if (!reuslt) {
            throw new BizException(ExceptionType.FILE_UPDATE_ERROR);
        }
        return ResponseResult.success("更新成功");
    }

    @PostMapping(value = {"/uploadHeadImg"})
    public ResponseResult<FileDto> userUploadHeadImage(MultipartFile multipartFile, String fileName, long size) throws IOException {
        File file = new File();
        file.setName(fileName);
        file.setSize(size);
        FileDto fileDto = this.fileService.saveUserHeadImage(file, multipartFile);
        fileDto.setDownloadUrl(this.tencentCos.getBaseUrl() + fileDto.getDownloadUrl());
        return ResponseResult.success(fileDto);
    }

    @GetMapping(value = {"/getFileDownloadUrl/{id}"})
    public ResponseResult<String> getFileDownloadUrl(@PathVariable String id) {
        File result = this.fileService.getOne(Wrappers
                .<File>lambdaQuery()
                .select(File::getDownloadUrl)
                .eq(File::getId, id)
        );
        result.setDownloadUrl(this.tencentCos.getBaseUrl() + result.getDownloadUrl());
        return ResponseResult.success(result.getDownloadUrl());
    }

    //切片上传业务
    @PostMapping(value = {"/upload"})
    public ResponseResult<String> bigFile(HttpServletRequest request, HttpServletResponse response, String md5, Integer chunk, MultipartFile file, Integer chunks) {
        try {
            this.fileService.uploadPart(request, response, md5, chunk, file, chunks);
        } catch (Exception e) {
            return ResponseResult.error("上传失败");
        }
        return ResponseResult.success("上传成功");
    }

    @PostMapping(value = {"/merge"})
    public ResponseResult<String> mergeFileAndUpload(@RequestBody FileMergeRequest fileMergeRequest) {
        java.io.File file = this.fileService.mergeFile(fileMergeRequest);
        this.fileService.bigFileUpload(file, fileMergeRequest);
        try {
            FileUtils.delete(file);
        } catch (IOException e) {
            log.error("临时文件清理异常");
        }
        return ResponseResult.success("上传成功");
    }
//切片上传业务

    @GetMapping(value = {"/search/{searchWord}"})
    public ResponseResult<List<FileDto>> search(@PathVariable String searchWord) {
        return ResponseResult.success(this.fileService.listLikeSearchWord(searchWord));
    }

    @Autowired
    public void setFileService(FileService fileService) {
        this.fileService = fileService;
    }

    @Autowired
    public void setTencentCos(TencentCos tencentCos) {
        this.tencentCos = tencentCos;
    }
}
