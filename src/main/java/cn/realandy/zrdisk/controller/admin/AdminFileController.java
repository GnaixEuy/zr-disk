package cn.realandy.zrdisk.controller.admin;

import cn.realandy.zrdisk.entity.File;

import cn.realandy.zrdisk.vo.ResponseResult;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import cn.realandy.zrdisk.service.FileService;
import cn.realandy.zrdisk.mapper.FileMapper;

import javax.annotation.security.RolesAllowed;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = {"/admin/file"})
public class AdminFileController {

    private FileService fileService;
    private FileMapper fileMapper;

    @GetMapping(value = {"/getAllFiles"})
    @RolesAllowed(value = {"ROLE_ADMIN"})
    public ResponseResult<Page<File>> getAllFiles(Page<File> page, String name) {
        System.out.println(name);
        QueryWrapper<File> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("name", name);


        return ResponseResult.success(this.fileService.page(page, queryWrapper));
    }

    @PutMapping(value = "/updateLocked")
    @RolesAllowed(value = {"ROLE_ADMIN"})
    public ResponseResult<String> updateLocked(@RequestBody Map<String, Object> map) {

        boolean isOk = fileService.updateLocked((String) map.get("id"));
        return isOk? ResponseResult.success("修改成功"): ResponseResult.error("修改失败了！");
    }

    @PutMapping(value = "/updataDeleted")
    @RolesAllowed(value = {"ROLE_ADMIN"})
    public ResponseResult<String> updataDeleted(@RequestBody Map<String, Object> map) {

        boolean isOk = fileService.deleteFileById((String) map.get("id"));
        return isOk? ResponseResult.success("删除成功"): ResponseResult.error("删除失败了！");
    }

    @GetMapping(value = "/getTypePieInfo")
    @RolesAllowed(value = {"ROLE_ADMIN"})
    public ResponseResult<List<Map<String, Object>>> getTypePieInfo(){
        return ResponseResult.success(this.fileService.getTypePieInfo());

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
