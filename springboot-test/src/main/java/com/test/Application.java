package com.test;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @Author: laizc
 * @Date: Created in  2022-01-20
 * @desc:
 */
@SpringBootApplication
@EnableAsync
@MapperScan("com.test.dao")
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class,args);
	}
}
