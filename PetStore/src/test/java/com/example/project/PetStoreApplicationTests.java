package com.example.project;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PetStoreApplicationTests {

	@Test
	void contextLoads() {
		int value=2;
		assertEquals(2, value);
	}

}
