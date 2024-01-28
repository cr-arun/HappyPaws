package com.example.project.controller;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

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

import com.example.project.model.UserModel;
import com.example.project.repository.UserRepo;
import com.example.project.service.UserService;
import com.example.project.util.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@WebMvcTest(value = UserController.class)
@AutoConfigureMockMvc(addFilters = false)
class UserControllerMockMvcTest {

	@Autowired
	MockMvc mockMvc;
	@Autowired
	ObjectMapper mapper;
	@MockBean
	private UserRepo userRepo;
	@MockBean
	private UserService userService;
	@MockBean
	private JwtUtil util;

	UserModel user1 = new UserModel(1l, "arun@gmail.com", "encoded password", "Arun", "9487550114", false, "User");
	UserModel user2 = new UserModel(2l, "ajay@gmail.com", "encoded password", "Ajay", "9487550114", false, "User");

	@Test
	void listAllUser() throws Exception {
		List<UserModel> users = new ArrayList<>();
		users.add(user1);
		users.add(user2);

		ResponseEntity<List<UserModel>> responseService = new ResponseEntity<List<UserModel>>(users, HttpStatus.OK);

		when(userService.listAllUsers()).thenReturn(responseService);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/allusers").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();
		assertTrue(response.getContentAsString().contains("Arun"));
		assertEquals(200, response.getStatus());
	}

	@Test
	void getUserTest() throws Exception {
		when(userService.getOneUser(1l)).thenReturn(user1);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/getuser/1").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();
		assertTrue(response.getContentAsString().contains("Arun"));
		assertEquals(200, response.getStatus());
	}

	@Test
	void editUserTest() throws Exception {
		ResponseEntity<Object> responseService = new ResponseEntity<Object>(user1, HttpStatus.OK);
		when(userService.editUser(user1, 1l)).thenReturn(responseService);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/edituser/1").accept(MediaType.APPLICATION_JSON)
				.content(this.mapper.writeValueAsString(user1)).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(200, response.getStatus());

	}

	@Test
	void deleteUser() throws Exception {

		ResponseEntity<HttpStatus> responseService = new ResponseEntity<HttpStatus>(HttpStatus.OK);
		when(userService.deleteUser(1l)).thenReturn(responseService);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/deleteuser/1").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(200, response.getStatus());

	}

}
