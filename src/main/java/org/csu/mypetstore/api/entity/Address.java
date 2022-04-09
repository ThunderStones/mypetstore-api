package org.csu.mypetstore.api.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName
public class Address {
    @TableId(value = "id", type = IdType.AUTO)
    private int id;
    @TableField("user_id")
    private String userId;
    @TableField("name")
    private String name;
    @TableField("address_id")
    private String addressId;
    @TableField("address_name")
    private String addressName;
    @TableField("address_detail")
    private String addressDetail;
    @TableField("phone")
    private String phone;
    @TableField("isdefault")
    private int isDefault;
}
