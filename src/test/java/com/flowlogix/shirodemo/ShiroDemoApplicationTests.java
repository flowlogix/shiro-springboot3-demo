package com.flowlogix.shirodemo;

import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = ShiroDemoApplication.class)
class ShiroDemoApplicationTests {
	@Autowired
	AbstractShiroFilter filter;

	@Test
	void contextLoads() {
		assertTrue(filter.getFilterChainResolver().getClass().getName().startsWith("org.apache.shiro"));
	}
}
