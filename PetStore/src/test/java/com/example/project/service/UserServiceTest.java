package com.example.project.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.project.model.UserModel;

@RunWith(SpringRunner.class)
@SpringBootTest
class UserServiceTest {

	@Autowired
	private UserService userService;

	@Test
	void getOneUserTest() {
		long id = 1;
		UserModel user = userService.getOneUser(id);
		assertEquals("Arun", user.getName());
	}

	@Test
	void listAllUsersTest() {
		HttpStatus statusTest = HttpStatus.OK;
		HttpStatus status = userService.listAllUsers().getStatusCode();
		assertEquals(statusTest, status);
	}

	@Test
	void editUserTest() {
		UserModel userEdit = new UserModel((long) 1, "arun@gmail.com", "arun", "Arun", "123457890", false, "User");
		long id = 1;
		UserModel user = (UserModel) userService.editUser(userEdit, id).getBody();
		assertEquals(userEdit.getName(), user.getName());
	}

}
