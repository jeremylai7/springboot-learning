package com.test.cycledepend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @author: laizc
 * @date: 2025/6/2 15:46
 * @desc:
 */
@Service
public class TAService {

    @Autowired
    private TBService bService;


    //@Async 加了就报错
    public void doSomethingAsync() {
        // do something
    }
}
