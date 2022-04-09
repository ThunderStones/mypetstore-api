package org.csu.mypetstore.api.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("cartitem")
public class CartItem {
    @TableId(value = "id", type = IdType.AUTO)
    private int id;
    @TableField(value = "username")
    private String username;
    @TableField(value = "itemid")
    private String itemId;
    @TableField("quantity")
    private int quantity;
}
