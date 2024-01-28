package com.example.project.controller;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

import com.example.project.model.ProductCategory;
import com.example.project.repository.ProductCategoryRepository;
import com.example.project.repository.UserRepo;
import com.example.project.service.ProductCategoryService;
import com.example.project.util.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@WebMvcTest(value = CategoryController.class)
@AutoConfigureMockMvc(addFilters = false)
class ProductCategoryControllerMockMvcTest {
	@Autowired
	MockMvc mockMvc;
	@Autowired
	ObjectMapper mapper;
	@MockBean
	private UserRepo userRepo;
	@MockBean
	private ProductCategoryService categoryService;
	@MockBean
	private ProductCategoryRepository catRepo;
	@MockBean
	private JwtUtil jwt;

	private List<ProductCategory> categorys;
	private ProductCategory category = new ProductCategory(1, "Birds", "Some Url");
	Optional<ProductCategory> categoryExist = Optional.ofNullable(new ProductCategory(1, "Birds", "Some Url"));

	@Test
	void allcategory() throws Exception {
		categorys = new ArrayList<ProductCategory>();
		categorys.add(new ProductCategory(1, "Birds", "Some Url"));
		categorys.add(new ProductCategory(2, "Dogs", "Some Url"));
		when(categoryService.listCategory()).thenReturn(categorys);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/allcategory").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();
		assertTrue(response.getContentAsString().contains("Dogs"));
		assertEquals(200, response.getStatus());

	}

	@Test
	void addCategoryTest() throws Exception {

		ResponseEntity<Object> resp = new ResponseEntity<>(category, HttpStatus.OK);
		when(categoryService.addCategory(category)).thenReturn(resp);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/addcategory").accept(MediaType.APPLICATION_JSON)
				.content(this.mapper.writeValueAsString(category)).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(200, response.getStatus());

	}

	@Test
	void editCategoryTest() throws Exception {

		when(categoryService.checkCategory(1)).thenReturn(categoryExist);
		when(categoryService.editCategory(category, categoryExist.get())).thenReturn(category);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/editcategory/1")
				.accept(MediaType.APPLICATION_JSON).content(this.mapper.writeValueAsString(category))
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(200, response.getStatus());

	}

	@Test
	void getCategoryTest() throws Exception {

		when(categoryService.checkCategory(1)).thenReturn(categoryExist);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/getcategory/1").accept(MediaType.APPLICATION_JSON)
				.content(this.mapper.writeValueAsString(category)).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();
		assertTrue(response.getContentAsString().contains("Birds"));
		assertEquals(200, response.getStatus());

	}

}
