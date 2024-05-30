package com.jeremy.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jeremy.handler.StringListTypeHandler;
import com.jeremy.handler.TypeListHandler;
import lombok.Data;

import java.util.List;

/**
 * @author: laizc
 * @date: created in 2024/5/27
 * @desc:
 **/
@Data
@TableName(value = "type",autoResultMap = true)
public class Type {

    private Integer typeId;

    private String typeName;

    @TableField(typeHandler = StringListTypeHandler.class)
    private List<String> typeList;
}
