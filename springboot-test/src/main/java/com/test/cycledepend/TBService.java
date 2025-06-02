package com.test.cycledepend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: laizc
 * @date: 2025/6/2 15:46
 * @desc:
 */
@Service
public class TBService {

    @Autowired
    private TCService cService;
}
