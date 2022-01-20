package com.test.controller;

import com.test.service.MySqlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: laizc
 * @Date: Created in  2022-01-20
 * @desc:
 */
@RestController
@RequestMapping("/mysql")
public class MySqlController {

	@Autowired
	private MySqlService mySqlService;

	@GetMapping("/test1")
	public String test1() {
		long id = 73;
		String name = "tomcat1";
		mySqlService.test1(id,name);
		return "ok";
	}

	@GetMapping("/test2")
	public String test2(){
		long id = 72;
		String name = "tomcat2";
		mySqlService.test2(id,name);
		return "ok";
	}

	@GetMapping("/test3")
	public String test3() {
		String name = "tomcat1";
		mySqlService.test3(name);
		return "ok";
	}

	@GetMapping("/test4")
	public String test4(){
		String name = "tomcat2";
		mySqlService.test4(name);
		return "ok";
	}


}
