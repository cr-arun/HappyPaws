package com.example.project.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.project.model.ProductCategory;

@RunWith(SpringRunner.class)
@SpringBootTest
class ProductCategoryServiceTest {

	@Autowired
	private ProductCategoryService productCategoryService;

	@Test
	void addCategoryTest() {
		ProductCategory categoryTest = new ProductCategory();
		categoryTest.setCategoryId(1);
		categoryTest.setCategoryImageUrl("someurl");
		categoryTest.setCategoryName("Dogs");
		ProductCategory category = (ProductCategory) productCategoryService.addCategory(categoryTest).getBody();
		assertEquals(categoryTest.getCategoryName(), category.getCategoryName());
	}

	@Test
	void checkCategoryTest() {
		long id = 1;
		ProductCategory category = productCategoryService.checkCategory(id).get();
		assertEquals("Dogs", category.getCategoryName());
	}

}
