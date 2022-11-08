package cn.realandy.zrdisk.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "user_vip_associated")
public class UserVipAssociated {

  @TableId(value = "user_id")
  private long userId;
  @TableField(value = "vip_id")
  private long vipId;
  @TableField(value = "expired_date")
  private Date expiredDate;

}
