package com.test;

import com.test.service.MainService;
import com.test.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringbootTestApplicationTests {

    @Autowired
    private UserService userService;

    @Autowired
    private MainService mainService;

    @Test
    void contextLoads() {
        userService.insertName("json");
    }


    /**
     * 以下是 required
     * @throws Exception
     */
    @Test
    void required() throws Exception {
        mainService.mainTest1("required");
    }

    @Test
    void required_1() throws Exception {
        mainService.mainTest1_1("required_1");
    }

    @Test
    void required_2() throws Exception {
        mainService.mainTest1_2("required_2");
    }

    /**
     * support
     */
    @Test
    void support() throws Exception {
        mainService.mainTest2("support");
    }

    @Test
    void support_1() throws Exception {
        mainService.mainTest2_1("support_1");
    }

    /**
     * mandatory 强制
     */
    @Test
    void mandatory() throws Exception {
        mainService.mainTest3("mandatory");
    }

    @Test
    void mandatory_1() throws Exception {
        mainService.mainTest3_1("mandatory_1");
    }

    /**
     * requires_new
     */
    @Test
    void requiresNew() throws Exception {
        mainService.mainTest4("requires_new");
    }

    @Test
    void requiresNew_1() throws Exception {
        mainService.mainTest4_1("requires_new_1");
    }

    @Test
    void requiresNew_2() throws Exception {
        mainService.mainTest4_2("requires_new_2");
    }

    @Test
    void requiresNew_3() {
        mainService.mainTest4_3("requires_new_3");
    }

    /**
     * not supported
     */
    @Test
    void notSupported() throws Exception {
        mainService.mainTest5("not_supported");
    }

    @Test
    void notSupported_1() throws Exception {
        mainService.mainTest5_1("not_supported_1");
    }

    /**
     * never
     */
    @Test
    void never() throws Exception {
        mainService.mainTest6("never");
    }

    @Test
    void never_1() throws Exception {
        mainService.mainTest6_1("never_1");
    }

    /**
     * NESTED nested
     */
    @Test
    void nested() throws Exception {
        mainService.mainTest7("nested");
    }

    @Test
    void nested_1() {
        mainService.mainTest7_1("nested-1");
    }

    @Test
    void nested_2() throws Exception {
        mainService.mainTest7_2("nested-2");
    }

}
