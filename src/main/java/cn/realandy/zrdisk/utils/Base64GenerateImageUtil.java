package cn.realandy.zrdisk.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Base64;


/**
 * @author GnaixEuy
 */
@Slf4j
public class Base64GenerateImageUtil {
    //base64字符串转化成图片
    public static InputStream GenerateImage(String imgStr) {//对字节数组字符串进行Base64解码并生成图片
        if (imgStr == null) {//图像数据为空
            log.error("图像数据为空");
            throw new RuntimeException("数据为空");
        }

        try {
            //Base64解码
            byte[] b = Base64.getDecoder().decode(imgStr);
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {//调整异常数据
                    b[i] += 256;
                }
            }
            return new ByteArrayInputStream(b);
        } catch (Exception e) {
            log.error("读取头像失败");
            throw new RuntimeException("读取头像失败");
        }
    }
}


