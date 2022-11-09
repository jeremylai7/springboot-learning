package com.controller;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
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

	@Autowired
	private RabbitTemplate rabbitTemplate;

	@GetMapping({"","index.html"})
	public ModelAndView index() {
		ModelAndView view = new ModelAndView("index");
		return view;
	}

	@GetMapping("/publish-sub-send")
	public String publishSubSend() {
		rabbitTemplate.convertAndSend("PUBLISH_SUBSCRIBE_EXCHANGE", null, "publish/subscribe hello");
		return "ok";
	}

}
