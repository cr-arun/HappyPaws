package com.example.project.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.project.dto.ProductDto;
import com.example.project.model.Product;
import com.example.project.model.ProductCategory;

@RunWith(SpringRunner.class)
@SpringBootTest
class ProductServiceTest {
	@Autowired
	private ProductService productService;

	@Test
	void addProductTest() {
		ProductCategory categoryTest = new ProductCategory();
		categoryTest.setCategoryId(1);
		categoryTest.setCategoryImageUrl("someurl");
		categoryTest.setCategoryName("Dogs");
		ProductDto product = new ProductDto();
		product.setProductid(1);
		product.setProductName("Labrador");
		product.setPrice(3000);
		product.setQuantity(50);
		product.setImageUrl("Some url");

		Product productResp = (Product) productService.addProduct(product, categoryTest).getBody();
		assertEquals(product.getProductName(), productResp.getProductName());
	}

	@Test
	void getOneProductTest() {
		long id = 1;
		Product product = productService.getOneProduct(id);
		assertEquals("Labrador", product.getProductName());
	}

	@Test
	void searchProduct() {
		String search = "lab";
		Product product = productService.searchProduct(search).stream().findFirst().get();
		assertEquals("Labrador", product.getProductName());
	}

}
