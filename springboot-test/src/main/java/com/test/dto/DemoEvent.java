package com.test.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author: laizc
 * @date: created in 2024/12/4
 * @desc:
 **/
@Getter
@Setter
public class DemoEvent extends ApplicationEvent {

    private String name;


    public DemoEvent(Object source) {
        super(source);
    }
}
