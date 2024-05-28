package com.jeremy.handler;

import com.fasterxml.jackson.core.type.TypeReference;

import java.util.List;

/**
 * @author laizc
 * @description stringList类型处理器
 * @date 2023/12/15 13:51
 */
public class StringListTypeHandler extends ListTypeHandler<String> {

    @Override
    protected TypeReference<List<String>> specificType() {
        return new TypeReference<List<String>>() {};
    }

}
