package cn.realandy.zrdisk.service;

import cn.realandy.zrdisk.entity.UserVipAssociated;

public interface UserVipAssociatedService {

    UserVipAssociated selectVip(Integer userId);

    void VIPRecharge(Integer userId, Integer vipId, int month);
}
