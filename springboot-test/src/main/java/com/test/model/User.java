package com.test.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author: laizc
 * @date: created in 2022/1/13
 * @desc:
 **/
@Getter
@Setter
public class User {

    private Long id;

    private String name;

    private Integer age;

    private Date submitTime;

}
