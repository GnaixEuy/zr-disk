package cn.realandy.zrdisk.utils;

import cn.realandy.zrdisk.config.TencentCosConfig;
import cn.realandy.zrdisk.entity.TencentCos;
import cn.realandy.zrdisk.vo.ResponseResult;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.exception.CosServiceException;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.model.StorageClass;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.File;
import java.util.HashMap;

/**
 * <img src="http://blog.gnaixeuy.cn/wp-content/uploads/2022/09/倒闭.png"/>
 *
 * <p>项目： zr-disk </p>
 * 创建日期： 2022/11/8
 *
 * @author GnaixEuy
 * @version 1.0.0
 * @see <a href="https://github.com/GnaixEuy"> GnaixEuy的GitHub </a>
 */
@Log4j2
@Component
public class CosUploadUtil {

    private TencentCos tencentCos;

    @Resource
    private TencentCosConfig tencentCosConfig;

    private COSClient CoSClient;


    public ResponseResult<HashMap<String, Object>> upload(String bucketName, String filePath, File localFile, String originalFilename) {
        //2.【将文件上传到腾讯云】
        // PutObjectRequest(参数1,参数2,参数3)参数1:存储桶,参数2:指定腾讯云的上传文件路径,参数3:要上传的文件
        //String filePath = baseUrl + "/" + originalFilename;
        log.info("key = {}", filePath);
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, filePath, localFile);
        //设置存储类型 默认标准型
        putObjectRequest.setStorageClass(StorageClass.Standard);
        //获得到客户端
        COSClient client = this.CoSClient;
        try {
            PutObjectResult putObjectResult = client.putObject(putObjectRequest);
            //putObjectResult 会返回etag
            String eTag = putObjectResult.getETag();
            log.info("eTag = {}", eTag);
        } catch (CosServiceException e) {
            log.error("CosServiceException ={}", e.getMessage());
            throw new CosServiceException(e.getMessage());
        } catch (CosClientException e) {
            log.error("CosClientException ={}", e.getMessage());
            throw new CosClientException(e.getMessage());
        }
        String url = tencentCos.getBaseUrl() + "/" + filePath;
        log.info("上传路径:" + url);
        HashMap<String, Object> map = new HashMap<>();
        map.put("originFileName", originalFilename);
        String[] newFileName = filePath.split("/");
        map.put("newFileName", newFileName[1]);
        map.put("filePath", url);
        return ResponseResult.success(map);
    }

    @Autowired
    public void setTencentCosConfig(TencentCosConfig tencentCosConfig) {
        this.tencentCosConfig = tencentCosConfig;
    }

    @Autowired
    public void setTencentCos(TencentCos tencentCos) {
        this.tencentCos = tencentCos;
    }

    @Autowired
    public void setCoSClient(COSClient coSClient) {
        CoSClient = coSClient;
    }
}

