package cn.realandy.zrdisk.controller;

import cn.realandy.zrdisk.vo.ResponseResult;
import cn.realandy.zrdisk.vo.YiYanVo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

/**
 * <img src="http://blog.gnaixeuy.cn/wp-content/uploads/2022/09/倒闭.png"/>
 *
 * <p>项目： zr-disk </p>
 * 创建日期： 2022/11/12
 *
 * @author GnaixEuy
 * @version 1.0.0
 * @see <a href="https://github.com/GnaixEuy"> GnaixEuy的GitHub </a>
 */
@RestController
@RequestMapping(value = {"/yiyan"})
public class YiYanController {

    @GetMapping(value = {"/getYiyan"})
    public ResponseResult<YiYanVo> getYiyan() {
        YiYanVo[] list = {
                new YiYanVo("人活着嘛,哪有不发疯的,写个勾八，开摆", "卡布达"),
                new YiYanVo("你什么勾八？你什么勾八？你什么勾八？", "蜻蜓队长"),
                new YiYanVo("你他妈真该死!你他妈真该死!你他妈真该死!", "鲨鱼辣椒")
        };
        return ResponseResult.success(list[new Random().nextInt(3)]);
    }

}
