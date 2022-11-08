package cn.realandy.zrdisk.dao;

import cn.realandy.zrdisk.entity.VipOrder;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderDao extends BaseMapper<VipOrder> {
}
