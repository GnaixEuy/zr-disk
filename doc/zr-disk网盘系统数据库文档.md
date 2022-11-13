# 数据库设计文档

**数据库名：** disk

**文档版本：** 1.0.0

**文档描述：** zr-disk数据库设计

| 表名                  | 说明       |
| :---: | :---: |
| [user](#user) | 基本用户结构 |
| [role](#role) | 角色权限表 |
| [file](#file) | 文件表 |
| [vip](#vip) | vip表 |
| [user_follow](#user_follow) | 用户关注表 |
| [user_role_associated](#user_role_associated) | 用户角色多对多关系表 |
| [user_vip_associated](#user_vip_associated) | 用户和VIP多对多关系表 |
| [vip_order](#vip_order) | VIP充值订单表 |

**表名：** <a id="user">user</a>

**说明：** 基本用户结构

**数据列：**

| 序号 | 名称 | 数据类型 |  长度  | 小数位 | 允许空值 | 主键 | 默认值 | 说明 |
| :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: |
|  1   | id |   int   | 10 |   0    |    N     |  Y   |       | 用户id  |
|  2   | phone |   varchar   | 32 |   0    |    N     |  N   |       | 手机号（必须绑定）  |
|  3   | email |   varchar   | 32 |   0    |    Y     |  N   |       | 用户邮箱  |
|  4   | password |   varchar   | 64 |   0    |    N     |  N   |       | 用户密码  |
|  5   | nickname |   varchar   | 32 |   0    |    N     |  N   |       | 昵称  |
|  6   | gender |   int   | 10 |   0    |    N     |  N   |   0    | 用户性别  |
|  7   | head_img_id |   varchar   | 32 |   0    |    Y     |  N   |       | 头像文件关联  |
|  8   | enabled |   bit   | 1 |   0    |    N     |  N   |   1    | 账户是否启用  |
|  9   | locked |   bit   | 1 |   0    |    N     |  N   |   0    | 账户是否封禁  |
|  10   | created_date_time |   datetime   | 19 |   0    |    Y     |  N   |       | 创建时间  |
|  11   | updated_date_time |   datetime   | 19 |   0    |    Y     |  N   |       | 更新时间  |
|  12   | birthday |   datetime   | 19 |   0    |    Y     |  N   |       | 用户生日  |
|  13   | drive_size |   double   | 23 |   0    |    N     |  N   |   10737418240    | 用户存储空间  |
|  14   | drive_used |   double   | 23 |   0    |    N     |  N   |   0    | 用户已使用空间  |
|  15   | last_login_ip |   varchar   | 64 |   0    |    Y     |  N   |       | 最后登陆ip  |
|  16   | last_login_time |   datetime   | 26 |   0    |    Y     |  N   |       | 最后登陆时间  |
|  17   | open_id |   varchar   | 255 |   0    |    Y     |  N   |       | 第三方openid  |

**表名：** <a id="role">role</a>

**说明：** 角色权限表

**数据列：**

| 序号 | 名称 | 数据类型 |  长度  | 小数位 | 允许空值 | 主键 | 默认值 | 说明 |
| :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: |
|  1   | id |   int   | 10 |   0    |    N     |  Y   |       | 角色ID  |
|  2   | name |   varchar   | 128 |   0    |    Y     |  N   |       | 角色名称  |
|  3   | title |   varchar   | 128 |   0    |    Y     |  N   |       | 角色标识  |
|  4   | created_date_time |   datetime   | 26 |   0    |    N     |  N   |       | 创建时间  |
|  5   | updated_date_time |   datetime   | 26 |   0    |    N     |  N   |       | 更新时间  |

**表名：** <a id="file">file</a>

**说明：** 文件表

**数据列：**

| 序号 | 名称 | 数据类型 |  长度  | 小数位 | 允许空值 | 主键 | 默认值 | 说明 |
| :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: |
|  1   | id |   varchar   | 32 |   0    |    N     |  Y   |       | 文件ID  |
|  2   | name |   varchar   | 64 |   0    |    N     |  N   |       | 文件名  |
|  3   | hash |   varchar   | 64 |   0    |    N     |  N   |       | 文件hash值  |
|  4   | ext |   varchar   | 12 |   0    |    N     |  N   |       | 文件后缀名  |
|  5   | size |   int   | 10 |   0    |    N     |  N   |   0    | 文件大小；单位byte  |
|  6   | type |   int   | 10 |   0    |    N     |  N   |   0    | 文件类型，1-AUDIO-音频，2-IMAGE-图片，3-VIDEO-视频,4-压缩文件，0-OTHER-其他  |
|  7   | parent_file_id |   varchar   | 255 |   0    |    Y     |  N   |       |   |
|  8   | parent_folder |   varchar   | 255 |   0    |    Y     |  N   |       |   |
|  9   | download_url |   varchar   | 255 |   0    |    Y     |  N   |       |   |
|  10   | uploader_id |   int   | 10 |   0    |    N     |  N   |   1    | 创建者用户ID  |
|  11   | created_date_time |   datetime   | 26 |   0    |    N     |  N   |       | 创建时间  |
|  12   | updated_date_time |   datetime   | 26 |   0    |    N     |  N   |       | 更新时间  |
|  13   | is_delete |   bit   | 1 |   0    |    N     |  N   |   0    | 是否删除  |
|  14   | parent_path |   varchar   | 255 |   0    |    N     |  N   |   /    | 文件前缀  |
|  15   | locked |   bit   | 1 |   0    |    N     |  N   |   0    | 文件是否封禁  |
|  16   | cover_url |   varchar   | 255 |   0    |    Y     |  N   |       | 文件缩略图  |
|  17   | storage |   int   | 10 |   0    |    Y     |  N   |   1    | 存储供应商，1-COS-腾讯云存储，2-OSS-阿里云存储  |
|  18   | status |   int   | 10 |   0    |    Y     |  N   |   1    | 文件状态，1-UPLOADING-上传中，2-UPLOADED-已上传，3-CANCEL-已取消  |
|  19   | is_collection |   bit   | 1 |   0    |    Y     |  N   |   0    | 是否收藏  |

**表名：** <a id="vip">vip</a>

**说明：** vip表

**数据列：**

| 序号 | 名称 | 数据类型 |  长度  | 小数位 | 允许空值 | 主键 | 默认值 | 说明 |
| :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: |
|  1   | vip_id |   int   | 10 |   0    |    N     |  Y   |       | vipID主键  |
|  2   | vip_name |   varchar   | 255 |   0    |    N     |  N   |       | vip名称  |
|  3   | increase_drive_size |   double   | 23 |   0    |    N     |  N   |       | vip增加的存储空间  |
|  4   | vip_month_price |   double   | 11 |   2    |    N     |  N   |       | vip月单价  |

**表名：** <a id="user_follow">user_follow</a>

**说明：** 用户关注表

**数据列：**

| 序号 | 名称 | 数据类型 |  长度  | 小数位 | 允许空值 | 主键 | 默认值 | 说明 |
| :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: |
|  1   | user_id |   int   | 10 |   0    |    Y     |  N   |       | 关注者id  |
|  2   | follower_id |   int   | 10 |   0    |    Y     |  N   |       | 被关注者id  |

**表名：** <a id="user_role_associated">user_role_associated</a>

**说明：** 用户角色多对多关系表

**数据列：**

| 序号 | 名称 | 数据类型 |  长度  | 小数位 | 允许空值 | 主键 | 默认值 | 说明 |
| :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: |
|  1   | user_id |   int   | 10 |   0    |    N     |  N   |       | 用户ID  |
|  2   | role_id |   int   | 10 |   0    |    N     |  N   |   1    | 角色ID  |

**表名：** <a id="user_vip_associated">user_vip_associated</a>

**说明：** 用户和VIP多对多关系表

**数据列：**

| 序号 | 名称 | 数据类型 |  长度  | 小数位 | 允许空值 | 主键 | 默认值 | 说明 |
| :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: |
|  1   | user_id |   int   | 10 |   0    |    N     |  Y   |       | 用户ID  |
|  2   | vip_id |   int   | 10 |   0    |    N     |  N   |       | vipID  |
|  3   | expired_date |   datetime   | 19 |   0    |    N     |  N   |       | VIP过期时间  |

**表名：** <a id="vip_order">vip_order</a>

**说明：** VIP充值订单表

**数据列：**

| 序号 | 名称 | 数据类型 |  长度  | 小数位 | 允许空值 | 主键 | 默认值 | 说明 |
| :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: |
|  1   | order_id |   int   | 10 |   0    |    N     |  Y   |       | 订单ID主键  |
|  2   | user_id |   int   | 10 |   0    |    N     |  N   |       | 用户ID  |
|  3   | vip_id |   int   | 10 |   0    |    N     |  N   |       | vipID主键  |
|  4   | order_name |   varchar   | 255 |   0    |    N     |  N   |       | 订单名称  |
|  5   | order_quantity |   int   | 10 |   0    |    N     |  N   |       | 充值月数  |
|  6   | order_price |   double   | 11 |   2    |    N     |  N   |       | 订单价格  |
|  7   | order_status |   bit   | 1 |   0    |    N     |  N   |   1    | 订单状态（0已取消/1正在处理/2已完成）  |
|  8   | order_trade_no |   varchar   | 255 |   0    |    Y     |  N   |       | 订单支付宝交易凭证号  |
|  9   | order_created_date |   datetime   | 26 |   0    |    N     |  N   |       | 订单创建时间  |
|  10   | order_pay_date |   datetime   | 26 |   0    |    Y     |  N   |       | 订单支付时间  |
