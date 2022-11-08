package cn.realandy.zrdisk.utils;

import com.baomidou.mybatisplus.core.incrementer.DefaultIdentifierGenerator;
import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import com.github.ksuid.KsuidGenerator;
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
public class KsuidIdentifierGenerator implements IdentifierGenerator {

    @Override
    public Number nextId(Object entity) {
        return new DefaultIdentifierGenerator().nextId(entity);
    }

    @Override
    public String nextUUID(Object entity) {
        return KsuidGenerator.generate();
    }
}
