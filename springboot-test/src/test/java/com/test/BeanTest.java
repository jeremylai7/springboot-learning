package com.test;

import com.test.model.Hello;
import com.test.util.SpringContextUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author: laizc
 * @date: created in 2023/8/31
 * @desc:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class BeanTest {

    @Test
    public void test() {
        Hello hello = (Hello) SpringContextUtil.getBean("hello");
        System.out.println(hello);
        Hello hello2 = (Hello) SpringContextUtil.getBean("hello");
        System.out.println(hello2);
    }
}
