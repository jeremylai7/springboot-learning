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
public class ChildService {

    @Autowired
    private BService bService;


    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void childTest1(String name) throws Exception {
        bService.B(name);
        if (true) {
            throw new Exception();
        }
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void childTest1_1(String name) throws Exception {
        bService.B(name);
        if (true) {
            throw new Exception();
        }
    }

    public void childTest1_2(String name) throws Exception {
        bService.B(name);
        if (true) {
            throw new Exception();
        }
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
    public void childTest2(String name) throws Exception {
        bService.B(name);
        if (true) {
            throw new Exception();
        }
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
    public void childTest2_1(String name) throws Exception {
        bService.B(name);
        if (true) {
            throw new RuntimeException();
        }
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.MANDATORY)
    public void childTest3(String name) throws Exception {
        bService.B(name);
        if (true) {
            throw new Exception();
        }
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.MANDATORY)
    public void childTest3_1(String name) throws Exception {
        bService.B(name);
        if (true) {
            throw new Exception();
        }
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public void childTest4(String name) throws Exception {
        bService.B(name);
        if (true) {
            throw new Exception();
        }
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public void childTest4_1(String name) throws Exception {
        bService.B(name);
        if (true) {
            throw new Exception();
        }
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public void childTest4_2(String name) {
        bService.B(name);
        bService.B2(name);
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public void childTest4_3(String requires_new_2) throws Exception {
        bService.B(requires_new_2);
        if (true) {
            throw new Exception();
        }
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.NOT_SUPPORTED)
    public void childTest5(String not_supported) throws Exception {
        bService.B(not_supported);
        if (true) {
            throw new Exception();
        }
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.NOT_SUPPORTED)
    public void childTest5_1(String not_supported_1) throws Exception {
        bService.B(not_supported_1);
        if (true) {
            throw new Exception();
        }
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.NEVER)
    public void childTest6(String never) throws Exception {
        bService.B(never);
        if (true) {
            throw new Exception();
        }
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.NEVER)
    public void childTest6_1(String never) throws Exception {
        bService.B(never);
        if (true) {
            throw new Exception();
        }
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.NESTED)
    public void childTest7(String nested) throws Exception {
        bService.B(nested);
        bService.B2(nested);
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.NESTED)
    public void childTest7_1(String nested) throws Exception {
        bService.B(nested);
        if (true) {
            throw new Exception();
        }
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.NESTED)
    public void childTest7_2(String nested) throws Exception {
        bService.B(nested);
        if (true) {
            throw new Exception();
        }
    }
}
