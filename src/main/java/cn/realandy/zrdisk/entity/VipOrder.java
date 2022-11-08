package cn.realandy.zrdisk.entity;


import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "vip_order")
public class VipOrder {

  @TableId(value = "order_id",type = IdType.AUTO)
  private Integer orderId;
  @TableField(value = "user_id")
  private Integer userId;
  @TableField(value = "vip_id")
  private Integer vipId;
  @TableField(value = "order_name")
  private String orderName;
  @TableField(value = "order_quantity")
  private int orderQuantity;
  @TableField(value = "order_price")
  private double orderPrice;
  @TableField(value = "order_status")
  private Integer orderStatus;
  @TableField(value = "order_trade_no")
  private String orderTradeNo;
  @TableField(value = "order_created_date", fill = FieldFill.INSERT)
  private Date createdDateTime;
  @TableField(value = "order_pay_date")
  private Date orderPayDate;


}
