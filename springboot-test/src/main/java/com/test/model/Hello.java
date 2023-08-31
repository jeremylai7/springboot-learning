package com.test.model;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @author: laizc
 * @date: created in 2023/7/28
 * @desc:
 */
@Component
@Scope("prototype")
public class Hello {

    private int name = 4;

}
