package cn.realandy.zrdisk.service.impl;

import cn.realandy.zrdisk.dao.OrderDao;
import cn.realandy.zrdisk.entity.VipOrder;
import cn.realandy.zrdisk.service.OrderService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;

    @Override
    public VipOrder selectOrderByID(Integer orderId) {
        QueryWrapper<VipOrder> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("order_id", orderId);
        return orderDao.selectOne(queryWrapper);
    }

    @Override
    public int insertOrder(VipOrder order) {
        return orderDao.insert(order);
    }

    @Override
    public int updateOrder(VipOrder order) {
        return orderDao.updateById(order);
    }
}
