package org.csu.mypetstore.api.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.Getter;

@Data
@TableName("address_data")
public class AddressData {
    @TableId(value = "id", type = IdType.INPUT)
    private String addressId;
    @TableField("name")
    private String name;
}
