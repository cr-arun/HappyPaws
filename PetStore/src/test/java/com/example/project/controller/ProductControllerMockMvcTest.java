package com.example.project.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.example.project.dto.ProductDto;
import com.example.project.model.Product;
import com.example.project.model.ProductCategory;
import com.example.project.model.UserModel;
import com.example.project.repository.ProductRepository;
import com.example.project.repository.UserRepo;
import com.example.project.service.ProductCategoryService;
import com.example.project.service.ProductService;
import com.example.project.service.UserService;
import com.example.project.service.WishListService;
import com.example.project.util.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@WebMvcTest(value = ProductController.class)
@AutoConfigureMockMvc(addFilters = false)
class ProductControllerMockMvcTest {
	@Autowired
	MockMvc mockMvc;
	@Autowired
	ObjectMapper mapper;
	@MockBean
	private ProductService productService;
	@MockBean
	private ProductCategoryService categoryService;
	@MockBean
	private UserRepo userRepo;
	@MockBean
	private JwtUtil jwt;
	@MockBean
	private UserService userService;
	@MockBean
	private WishListService wishlistService;
	@MockBean
	private ProductRepository productRepo;

	Optional<ProductCategory> category = Optional.ofNullable(new ProductCategory(1, "Dog", "Some Url"));
	UserModel user1 = new UserModel(1l, "arun@gmail.com", "encoded password", "Arun", "9487550114", false, "User");
	ProductDto productDto = new ProductDto();
	Product product = new Product();

	@BeforeEach
	void setup() {
		// Dto
		productDto.setProductid(1);
		productDto.setProductName("Labrador");
		productDto.setDescription("Desciption");
		productDto.setImageUrl("url");
		productDto.setPrice(2000);
		productDto.setQuantity(30);
		productDto.setCategoryId(1l);

		// product entity
		product.setProductid(1);
		product.setProductName("Labrador");
		product.setDescription("Desciption");
		product.setImageUrl("url");
		product.setPrice(2000);
		product.setQuantity(30);
		product.setProductCategory(category.get());
	}

	@Test
	void addProductTest() throws Exception {

		ResponseEntity<Object> response = new ResponseEntity<Object>(productDto, HttpStatus.OK);
		when(categoryService.checkCategory(productDto.getCategoryId())).thenReturn(category);
		when(productService.addProduct(productDto, category.get())).thenReturn(response);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/addproduct").accept(MediaType.APPLICATION_JSON)
				.content(this.mapper.writeValueAsString(productDto)).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse responseServlet = result.getResponse();

		assertEquals(200, responseServlet.getStatus());
	}

	@Test
	void editProductTest() throws Exception {

		ResponseEntity<Object> response = new ResponseEntity<Object>(product, HttpStatus.OK);
		when(categoryService.checkCategory(1l)).thenReturn(category);
		when(productService.getOneProduct(1)).thenReturn(product);
		when(productService.editProduct(product, category.get(), product)).thenReturn(response);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/editproduct/1").accept(MediaType.APPLICATION_JSON)
				.content(this.mapper.writeValueAsString(product)).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse responseServlet = result.getResponse();

		assertEquals(200, responseServlet.getStatus());

	}

	@Test
	void listAllProducts() throws Exception {
		List<Product> products = new ArrayList<>();
		products.add(product);
		ResponseEntity<List<Product>> response = new ResponseEntity<List<Product>>(products, HttpStatus.OK);
		when(productService.getAllProducts()).thenReturn(response);
		when(categoryService.checkCategory(1l)).thenReturn(category);
		when(userService.getOneUser(1l)).thenReturn(user1);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/allprod/1/1").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse responseServlet = result.getResponse();

		assertEquals(200, responseServlet.getStatus());

	}
}
