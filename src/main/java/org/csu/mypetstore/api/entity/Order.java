package org.csu.mypetstore.api.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;


@Data
@TableName("orders")
public class Order {
    @TableId(value = "orderid", type = IdType.INPUT)
    private int orderId;
    @TableField("userid")
    private String userId;
    @TableField("orderdate")
    private Date orderDate;
    @TableField("courier")
    private String courier;
    @TableField("totalprice")
    private BigDecimal totalPrice;
    @TableField("name")
    private String name;
    @TableField("address_detail")
    private String addressDetail;
    @TableField("phone")
    private String phone;
    @TableField("address_data_id")
    private String addressDataId;
    @TableField("address_id")
    private int addressId;
}
