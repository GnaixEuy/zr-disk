package cn.realandy.zrdisk.config;

import cn.realandy.zrdisk.entity.TencentCos;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.region.Region;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

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
@Component
public class TencentCosConfig {

    public static final String COS_ATTACHMENT = "attachment";

    private TencentCos tencentCos;

    @Bean
    @Qualifier(COS_ATTACHMENT)
    @Primary
    public COSClient getCoSClient() {
        //初始化用户身份信息
        BasicCOSCredentials cosCredentials = new BasicCOSCredentials(tencentCos.getSecretId(), tencentCos.getSecretKey());
        //设置bucket区域
        //clientConfig中包含了设置region
        ClientConfig clientConfig = new ClientConfig(new Region(tencentCos.getRegionId()));
        //生成cos客户端
        return new COSClient(cosCredentials, clientConfig);
    }

    @Autowired
    public void setTencentCos(TencentCos tencentCos) {
        this.tencentCos = tencentCos;
    }
}
