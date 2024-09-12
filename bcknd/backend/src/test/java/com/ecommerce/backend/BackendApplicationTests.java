package com.ecommerce.backend;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@SpringBootTest
@RestController
class BackendApplicationTests {

	@GetMapping("/Test")
	public String test( ) {
		return "test";
	}
	

}
