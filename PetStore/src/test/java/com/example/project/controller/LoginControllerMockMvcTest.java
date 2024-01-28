package com.example.project.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

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

import com.example.project.dto.AuthResponse;
import com.example.project.dto.LoginDto;
import com.example.project.dto.UserDto;
import com.example.project.model.UserModel;
import com.example.project.repository.UserRepo;
import com.example.project.service.LoginService;
import com.example.project.util.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@WebMvcTest(value = LoginController.class)
@AutoConfigureMockMvc(addFilters = false)
class LoginControllerMockMvcTest {

	@Autowired
	MockMvc mockMvc;
	@Autowired
	ObjectMapper mapper;
	@MockBean
	private UserRepo userRepo;
	@MockBean
	private LoginService loginService;;
	@MockBean
	private JwtUtil util;
	private UserDto userDto = new UserDto(1l, "arun@gmail.com", "encoded password", "Arun", false, "9487550114",
			"User");

	@Test
	void loginTest() throws Exception {
		LoginDto login = new LoginDto();
		login.setEmail("arun@gmail.com");
		login.setPassword("arun2000");
		AuthResponse responseService = new AuthResponse(userDto, "token");
		ResponseEntity<Object> resp = new ResponseEntity<Object>(responseService, HttpStatus.OK);
		when(loginService.loginUser(login)).thenReturn(resp);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/login")
				.content(this.mapper.writeValueAsString(login)).contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(200, response.getStatus());

	}

	@Test
	void signupTest() throws Exception {
		UserModel user = new UserModel(userDto.getId(), userDto.getEmail(), userDto.getPassword(), userDto.getName(),
				userDto.getPhoneNumber(), false, userDto.getRole());
		ResponseEntity<Object> response = new ResponseEntity<Object>(user, HttpStatus.OK);
		when(loginService.signupUser(user)).thenReturn(response);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/login").accept(MediaType.APPLICATION_JSON)
				.content(this.mapper.writeValueAsString(user)).contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse responseMock = result.getResponse();

		assertEquals(200, responseMock.getStatus());

	}

}
