package com.jeremy.controller;

import com.jeremy.dao.ProductDao;
import com.jeremy.model.Product;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: laizc
 * @Date: Created in  2022-06-02
 * @desc: 获取商品
 */
@RestController
@RequestMapping("/product")
public class ProductController {

	@Resource
	private ProductDao productDao;

	@GetMapping("/list")
	public List<Product> list() {
		List<Product> list = productDao.selectAll();
		return list;
	}

}
