package com.example.project.service;

import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.stereotype.Service;

import com.example.project.dto.AuthResponse;
import com.example.project.dto.LoginDto;
import com.example.project.dto.UserDto;
import com.example.project.model.UserModel;
import com.example.project.repository.UserRepo;
import com.example.project.responseentity.ResponseHandler;
import com.example.project.util.JwtUtil;

@Service
public class LoginService {
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private AuthenticationManager authManager;
	@Autowired
	private JwtUtil jwtUtil;
	private static final String ROLE = "User";
	Logger logger = LogManager.getLogger(LoginService.class);

	public ResponseEntity<Object> signupUser(UserModel user) {

		Optional<UserModel> userExist = userRepo.findByEmail(user.getEmail());
		if (userExist.isEmpty()) {
			if ( user.getRole() == null) {
				user.setRole(ROLE);
			}
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			userRepo.save(user);
			return ResponseHandler.generateResponse(user, HttpStatus.OK);
		} else {
			return ResponseHandler.generateResponse("Email already Exist", HttpStatus.BAD_REQUEST);
		}
	}

	public ResponseEntity<Object> loginUser(LoginDto login) {

		try {
			Authentication authentication = authManager
					.authenticate(new UsernamePasswordAuthenticationToken(login.getEmail(), login.getPassword()));
			UserModel userModel = (UserModel) authentication.getPrincipal();
			UserDto userDto = new UserDto(userModel.getId(), userModel.getEmail(), userModel.getName(),
					userModel.getPhoneNumber(), userModel.isActive(), userModel.getRole(),userModel.getPassword());
			String accessToken = jwtUtil.generateAccessToken(userModel);
			AuthResponse response = new AuthResponse(userDto, accessToken);
			logger.info("Email LoggedIn {}" , login.getEmail());
			return ResponseEntity.ok(response);
		} catch (BadCredentialsException ex) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

		}

	}

}
