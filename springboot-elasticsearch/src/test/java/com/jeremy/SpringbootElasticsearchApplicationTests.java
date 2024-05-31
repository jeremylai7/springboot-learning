package com.jeremy;


import com.jeremy.model.ProductTest;
import com.jeremy.service.ProductService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class SpringbootElasticsearchApplicationTests {

	@Autowired
	private ProductService productService;

	@Test
	public void add() {
		ProductTest product = new ProductTest();
		product.setId("3");
		product.setName("11");
		product.setPrice(12);
		product.setDescription("hahah");
		productService.saveProduct(product);
	}

	@Test
	public void delete() {
		productService.deleteProduct("2");
	}

}
