package com.example.project.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.project.model.UserModel;
import com.example.project.repository.UserRepo;
import com.example.project.responseentity.ResponseHandler;

@Service
public class UserService {
	@Autowired
	private UserRepo userRepo;

	public ResponseEntity<List<UserModel>> listAllUsers() {

		return ResponseEntity.ok(userRepo.findAll());
	}

	public UserModel getOneUser(long id) {

		Optional<UserModel> user = userRepo.findById(id);
		if (user.isEmpty()) {
			throw new UsernameNotFoundException("user not exist");
		}
		return user.get();

	}

	public ResponseEntity<Object> editUser(UserModel userEdit, Long id) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		UserModel userExist = this.getOneUser(id);
		userExist.setEmail(userEdit.getEmail());
		userExist.setName(userEdit.getName());
		if (!(userEdit.getPassword().equals(userExist.getPassword()))) {
			userExist.setPassword(passwordEncoder.encode(userEdit.getPassword()));
		}
		userExist.setPhoneNumber(userEdit.getPhoneNumber());
		userExist.setRole(userEdit.getRole());
		userRepo.save(userExist);
		return ResponseHandler.generateResponse(userExist, HttpStatus.OK);
	}

	public ResponseEntity<HttpStatus> deleteUser(Long id) {
		UserModel user = this.getOneUser(id);
		if (user != null) {
			userRepo.deleteById(id);
			return ResponseHandler.generateReponseStatus(HttpStatus.OK);
		}
		return ResponseHandler.generateReponseStatus(HttpStatus.BAD_REQUEST);
	}
}
