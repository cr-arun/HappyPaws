package com.example.project.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.project.dto.AuthResponse;
import com.example.project.dto.LoginDto;
import com.example.project.model.UserModel;

@RunWith(SpringRunner.class)
@SpringBootTest
class LoginServiceTest {

	@Autowired
	private LoginService loginService;

	@Test
	void SignUpUserTest() {
		UserModel user = new UserModel((long) 1, "arun@gmail.com", "arun", "Arun", "9487550114", false, null);
		UserModel userTest = (UserModel) loginService.signupUser(user).getBody();
		assertEquals("User", userTest.getRole());
	}

	@Test
	void loginUserTest() {
		LoginDto login = new LoginDto();
		login.setEmail("arun@gmail.com");
		login.setPassword("arun");
		AuthResponse auth = (AuthResponse) loginService.loginUser(login).getBody();
		assertEquals("Arun", auth.getUser().getName());

	}

}
