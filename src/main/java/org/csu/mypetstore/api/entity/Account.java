package org.csu.mypetstore.api.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("account")
public class Account {
    @TableId(value = "userid", type = IdType.INPUT)
    private String username;
    @TableField(value = "email")
    private String email;
    @TableField(value = "firstname")
    private String firstName;
    @TableField(value = "lastname")
    private String lastName;
    @TableField(value = "status")
    private String status;
    @TableField(value = "country")
    private String country;
    @TableField(value = "phone")
    private String phone;
}
