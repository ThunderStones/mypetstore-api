package org.csu.mypetstore.api.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("orderstatus")
public class OrderStatus {
    @TableId(value = "orderid", type = IdType.AUTO)
    private int orderId;
    @TableField("linenum")
    private int lineNum;
    @TableField("timestamp")
    private Date timestamp;
    @TableField("status")
    private String status;
}
