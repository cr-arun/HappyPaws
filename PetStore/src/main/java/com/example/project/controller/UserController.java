package com.example.project.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.project.dto.UserDto;
import com.example.project.model.UserModel;
import com.example.project.service.UserService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {
	@Autowired
	private UserService userService;

	@GetMapping("/allusers")
	public ResponseEntity<List<UserModel>> listAllUsers() {
		return userService.listAllUsers();
	}

	@GetMapping("/getuser/{id}")
	public UserModel forAdmin(@PathVariable(value = "id") Long id) {
		return userService.getOneUser(id);
	}

	@PostMapping("/edituser/{id}")
	public ResponseEntity<Object> editUser(@RequestBody UserDto userDto, @PathVariable(value = "id") Long id) {
		UserModel user = new UserModel();
		BeanUtils.copyProperties(userDto, user);
		return userService.editUser(user, id);
	}

	@GetMapping("/deleteuser/{id}")
	public ResponseEntity<HttpStatus> deleteUser(@PathVariable(value = "id") Long id) {
		return userService.deleteUser(id);
	}

}
