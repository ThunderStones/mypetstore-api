package org.csu.mypetstore.api.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@TableName("sequence")
public class Sequence {
    @TableId(value = "name", type = IdType.INPUT)
    private String name;
    @TableField("nextid")
    private int nextId;
}
