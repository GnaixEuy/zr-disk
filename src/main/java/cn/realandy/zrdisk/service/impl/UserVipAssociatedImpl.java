package cn.realandy.zrdisk.service.impl;

import cn.realandy.zrdisk.dao.UserVipAssociatedDao;
import cn.realandy.zrdisk.entity.UserVipAssociated;
import cn.realandy.zrdisk.service.UserVipAssociatedService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

@Service
public class UserVipAssociatedImpl implements UserVipAssociatedService {

    @Autowired
    private UserVipAssociatedDao userVipAssociatedDao;


    @Override
    //通过userid查询UserVipAssociated表中相关充值记录
    public UserVipAssociated selectVip(Integer userId) {
        QueryWrapper<UserVipAssociated> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        return userVipAssociatedDao.selectOne(queryWrapper);
    }

    @Override
    //vip充值
    public void VIPRecharge(Integer userId, Integer vipId, int month) {

        UserVipAssociated userVipAssociated = selectVip(userId);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);


        //不存在vip记录
        if (userVipAssociated == null) {

            calendar.add(Calendar.DATE, month * 30 + 1);

            userVipAssociatedDao.insert(new UserVipAssociated(userId, vipId, calendar.getTime()));
        }
        //vip已经过期
        else if (userVipAssociated.getExpiredDate().compareTo(calendar.getTime()) < 0 ){

            calendar.add(Calendar.DATE, month * 30 + 1);
            userVipAssociated.setExpiredDate(calendar.getTime());
            userVipAssociated.setVipId(vipId);
            userVipAssociatedDao.updateById(userVipAssociated);
        //vip未过期
        } else {
            //充值同种会员
            if (userVipAssociated.getVipId() == vipId) {
                calendar.setTime(userVipAssociated.getExpiredDate());
                calendar.add(Calendar.DATE, month * 30 + 1);
                userVipAssociated.setExpiredDate(calendar.getTime());
                userVipAssociatedDao.updateById(userVipAssociated);
            }
            //TODO 充值不同种会员
            else {
                System.err.println("用户充值了不同会员");
            }
        }
    }
}
