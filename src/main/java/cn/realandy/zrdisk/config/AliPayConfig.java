package cn.realandy.zrdisk.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "alipay")
public class AliPayConfig {

    private String appid;
    private String url;
    private String privateKey;
    private String publicKey;
    private String notifyUrl;
    private String returnUrl;

}
