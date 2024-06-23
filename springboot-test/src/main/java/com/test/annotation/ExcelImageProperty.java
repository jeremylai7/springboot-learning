package com.test.annotation;

import java.lang.annotation.*;

/**
 * @author: laizc
 * @date: created in 2024/6/23
 * @desc:
 **/
@Inherited
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelImageProperty {

    String[] value() default {""};

    /**
     * 图片在第几列 1开始
     * @return
     */
    int index() default -1;
}
