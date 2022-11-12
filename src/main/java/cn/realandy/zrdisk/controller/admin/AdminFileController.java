package cn.realandy.zrdisk.controller.admin;

import cn.realandy.zrdisk.entity.File;

import cn.realandy.zrdisk.vo.ResponseResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import cn.realandy.zrdisk.service.FileService;
import cn.realandy.zrdisk.mapper.FileMapper;

import javax.annotation.security.RolesAllowed;

@RestController
@RequestMapping(value = {"/admin/file"})
public class AdminFileController {

    private FileService fileService;
    private FileMapper fileMapper;

    @GetMapping(value = {"/getAllFiles"})
    @RolesAllowed(value = {"ROLE_ADMIN"})
    public ResponseResult<Page<File>> getAllFiles(Page<File> page) {

        return ResponseResult.success(this.fileService.page(page));
    }


    @Autowired
    public void setFileService(FileService fileService) {
        this.fileService = fileService;
    }
    @Autowired
    public void setFileMapper(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }
}
