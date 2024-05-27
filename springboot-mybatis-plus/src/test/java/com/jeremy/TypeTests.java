package com.jeremy;


import com.jeremy.dao.TypeMapper;
import com.jeremy.model.Type;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TypeTests {

	@Autowired
	private TypeMapper typeMapper;

	@Test
	public void test() {
		System.out.println(("----- selectAll method test ------"));
		List<Type> userList = typeMapper.selectList(null);
		userList.forEach(System.out::println);
	}
}
