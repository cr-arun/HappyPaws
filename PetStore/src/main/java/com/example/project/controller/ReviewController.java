package com.example.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.project.dto.ReviewDto;
import com.example.project.model.Product;
import com.example.project.model.Review;
import com.example.project.model.UserModel;
import com.example.project.service.ProductService;
import com.example.project.service.ReviewService;
import com.example.project.service.UserService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class ReviewController {
	@Autowired
	private ReviewService reviewService;
	@Autowired
	private UserService userService;
	@Autowired
	private ProductService productService;

	public ReviewController(ReviewService reviewService, UserService userService, ProductService productService) {
		super();
		this.reviewService = reviewService;
		this.userService = userService;
		this.productService = productService;
	}

	@PostMapping("/addreview")
	public ResponseEntity<Review> addReview(@RequestBody ReviewDto reviewDto) {
		UserModel user = userService.getOneUser(reviewDto.getUserid());
		Product product = productService.getOneProduct(reviewDto.getProductid());
		return reviewService.addReview(reviewDto, user, product);
	}

	@GetMapping("/listreview/{pid}")
	public ResponseEntity<List<Review>> listReview(@PathVariable long pid) {
		Product product = productService.getOneProduct(pid);
		return reviewService.showReview(product);
	}

	@GetMapping("/getonereview/{id}")
	public ResponseEntity<Review> getReview(@PathVariable long id) {
		return reviewService.getOneReview(id);
	}

	@PostMapping("/editreview")
	public ResponseEntity<Review> editReview(@RequestBody ReviewDto reviewDto) {
		return reviewService.editReview(reviewDto);
	}

	@GetMapping("/deletereview/{id}")
	public void deleteReview(@PathVariable long id) {
		reviewService.deleteReview(id);
	}
}
