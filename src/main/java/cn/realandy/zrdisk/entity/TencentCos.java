package cn.realandy.zrdisk.entity;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.Serializable;

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
@Data
@Component
public class TencentCos implements Serializable {

    @Value("${tencent.cos.app-id}")
    private String appId;
    @Value("${tencent.cos.secret-id}")
    private String secretId;
    @Value("${tencent.cos.secret-key}")
    private String secretKey;
    @Value("${tencent.cos.bucket-name}")
    private String bucketName;
    @Value("${tencent.cos.region-id}")
    private String regionId;
    @Value("${tencent.cos.base-url}")
    private String baseUrl;

}

