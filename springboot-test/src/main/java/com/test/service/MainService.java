package com.test.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author: laizc
 * @date: created in 2022/1/13
 * @desc:
 **/
@Service
public class MainService {

    @Autowired
    private AService aService;

    @Autowired
    private BService bService;

    @Autowired
    private ChildService childService;

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void mainTest1(String name) throws Exception {
        aService.A(name);
        childService.childTest1(name);
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void mainTest1_1(String name) throws Exception {
        aService.A(name);
        try {
            childService.childTest1_1(name);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void mainTest1_2(String name) throws Exception {
        aService.A(name);
        childService.childTest1_2(name);
    }


    public void mainTest2(String support) throws Exception {
        aService.A(support);
        childService.childTest2(support);
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void mainTest2_1(String support_1) throws Exception {
        aService.A(support_1);
        childService.childTest2_1(support_1);
    }

    public void mainTest3(String name) throws Exception {
        aService.A(name);
        childService.childTest3(name);
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void mainTest3_1(String name) throws Exception {
        aService.A(name);
        childService.childTest3_1(name);
    }

    public void mainTest4(String name) throws Exception{
        aService.A(name);
        childService.childTest4(name);
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void mainTest4_1(String name) throws Exception {
        aService.A(name);
        childService.childTest4_1(name);
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void mainTest4_2(String name) throws Exception {
        aService.A(name);
        childService.childTest4_2(name);
        throw new Exception();
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void mainTest4_3(String requires_new_2) {
        aService.A(requires_new_2);
        try {
            childService.childTest4_3(requires_new_2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void mainTest5(String not_supported) throws Exception {
        aService.A(not_supported);
        childService.childTest5(not_supported);
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void mainTest5_1(String not_supported_1) throws Exception {
        aService.A(not_supported_1);
        childService.childTest5_1(not_supported_1);
    }

    public void mainTest6(String never) throws Exception {
        aService.A(never);
        childService.childTest6(never);
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void mainTest6_1(String never) throws Exception {
        aService.A(never);
        childService.childTest6_1(never);
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void mainTest7(String nested) throws Exception {
        aService.A(nested);
        childService.childTest7(nested);
        throw new Exception();
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void mainTest7_1(String nested) {
        aService.A(nested);
        try {
            childService.childTest7_1(nested);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void mainTest7_2(String nested) throws Exception {
        aService.A(nested);
        childService.childTest7_2(nested);
    }
}
