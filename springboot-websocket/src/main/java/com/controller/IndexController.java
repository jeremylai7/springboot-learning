package com.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Author: laizc
 * @Date: Created in  2021-10-08
 * @desc:
 */
@Controller
public class IndexController {

	@GetMapping({"","index.html"})
	public ModelAndView index() {
		ModelAndView view = new ModelAndView("index");
		return view;
	}

}
