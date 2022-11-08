package cn.realandy.zrdisk.controller;

import cn.realandy.zrdisk.dto.FileDto;
import cn.realandy.zrdisk.entity.File;
import cn.realandy.zrdisk.entity.TencentCos;
import cn.realandy.zrdisk.service.FileService;
import cn.realandy.zrdisk.vo.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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
@RestController
@RequestMapping(value = {"/file"})
public class FileController {

    private FileService fileService;

    private TencentCos tencentCos;


    @PostMapping(value = {"/uploadHeadImg"})
    public ResponseResult<FileDto> userUploadHeadImage(MultipartFile multipartFile, String fileName, long size) throws IOException {
        File file = new File();
        file.setName(fileName);
        file.setSize(size);
        FileDto fileDto = this.fileService.saveUserHeadImage(file, multipartFile);
        fileDto.setDownloadUrl(this.tencentCos.getBaseUrl() + fileDto.getDownloadUrl());
        return ResponseResult.success(fileDto);
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
