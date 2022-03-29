package org.csu.mypetstore.api.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;

@Getter
@TableName("sequence")
public class Sequence {
    @TableId(value = "name", type = IdType.INPUT)
    private String name;
    @TableField("nextid")
    private String nextId;
}
