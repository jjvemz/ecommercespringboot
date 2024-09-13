package com.ecommerce.backend;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;


@SpringBootTest
@RestController
class BackendApplicationTests {

	@GetMapping("/Test")
	public String test( ) {
		return "test";
	}
	

}
