package com.example.project.responseentity;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseHandler {
	public static ResponseEntity<Object> generateResponse(Object responseObj, HttpStatus status) {

		return new ResponseEntity<>(responseObj, status);
	}

	public static ResponseEntity<HttpStatus> generateReponseStatus(HttpStatus status) {
		return new ResponseEntity<>(status);
	}

}