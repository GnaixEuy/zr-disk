package cn.realandy.zrdisk.service;

import cn.realandy.zrdisk.entity.VipOrder;

public interface OrderService {

    VipOrder selectOrderByID(Integer orderId);

    int insertOrder(VipOrder order);

    int updateOrder(VipOrder order);
}
