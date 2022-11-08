package cn.realandy.zrdisk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class ZrDiskApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZrDiskApplication.class, args);
    }

}
