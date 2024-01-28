package com.example.project.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.project.dto.ProductDto;
import com.example.project.model.Product;
import com.example.project.model.ProductCategory;
import com.example.project.service.ProductCategoryService;
import com.example.project.service.ProductService;
import com.example.project.service.UserService;
import com.example.project.service.WishListService;

@SpringBootTest(classes = { ProductControllerTest.class })
class ProductControllerTest {
	@Mock
	ProductService productService;
	@Mock
	ProductCategoryService categoryService;
	@Mock
	UserService userService;
	@Mock
	WishListService wishlistService;
	@InjectMocks
	ProductController productController;
	Optional<ProductCategory> category = Optional.ofNullable(new ProductCategory(1, "Dog", "Some Url"));

	@Test
	void addProductTest() {
		ProductDto product = new ProductDto();
		product.setProductid(1);
		product.setProductName("Labrador");
		product.setDescription("Desciption");
		product.setImageUrl("url");
		product.setPrice(2000);
		product.setQuantity(30);
		product.setCategoryId(1);
		ResponseEntity<Object> response = new ResponseEntity<Object>(product, HttpStatus.OK);
		when(categoryService.checkCategory(product.getCategoryId())).thenReturn(category);
		when(productService.addProduct(product, category.get())).thenReturn(response);
		assertEquals(product, productController.addProduct(product).getBody());
	}

	@Test
	void editProductTest() {
		Product product = new Product();
		product.setProductid(1);
		product.setProductName("Labrador");
		product.setDescription("Desciption");
		product.setImageUrl("url");
		product.setPrice(2000);
		product.setQuantity(30);
		product.setProductCategory(category.get());
		ResponseEntity<Object> response = new ResponseEntity<Object>(product, HttpStatus.OK);
		when(categoryService.checkCategory(product.getProductCategory().getCategoryId())).thenReturn(category);
		when(productService.getOneProduct(1)).thenReturn(product);
		when(productService.editProduct(product, category.get(), product)).thenReturn(response);
		assertEquals(product, productController.editProduct(product, 1).getBody());
	}

	@Test
	void listAllProducts() {
		Product product = new Product();
		product.setProductid(1);
		product.setProductName("Labrador");
		product.setDescription("Desciption");
		product.setImageUrl("url");
		product.setPrice(2000);
		product.setQuantity(30);
		product.setProductCategory(category.get());
		List<Product> products = new ArrayList<>();
		products.add(product);
		ResponseEntity<List<Product>> response = new ResponseEntity<List<Product>>(products, HttpStatus.OK);
		when(productService.getAllProducts()).thenReturn(response);
		assertEquals(products, productController.allProducts().getBody());
	}
}
