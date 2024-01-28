package com.example.project.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.project.dto.ReviewDto;
import com.example.project.model.Product;
import com.example.project.model.Review;
import com.example.project.model.UserModel;
import com.example.project.service.ProductService;
import com.example.project.service.ReviewService;
import com.example.project.service.UserService;

@SpringBootTest(classes = { ReviewControllerTest.class })
class ReviewControllerTest {
	@Mock
	ReviewService reviewService;
	@Mock
	UserService userService;
	@Mock
	ProductService productService;
	@InjectMocks
	ReviewController reviewController;

	ReviewDto reviewDto;
	UserModel user;
	Product product;
	Review review;
	List<Review> listreview = new ArrayList<>();

	@BeforeEach
	void setup() {
		user = new UserModel((long) 1, "arun@gmail.com", "encryped password", "Arun", "9487550114", false, "User");

		product = new Product();
		product.setProductid(1);
		product.setProductName("Labrador");
		product.setDescription("Desciption");
		product.setImageUrl("url");
		product.setPrice(2000);
		product.setQuantity(30);

		reviewDto = new ReviewDto();
		reviewDto.setId(1);
		reviewDto.setComment("some comment");
		reviewDto.setProductid(1);

		review = new Review();
		review.setId(1);
		review.setProduct(product);
		review.setUser(user);
		review.setComment("some comment");

	}

	@Test
	void addReviewTest() {
		when(userService.getOneUser(reviewDto.getUserid())).thenReturn(user);
		when(productService.getOneProduct(reviewDto.getProductid())).thenReturn(product);
		ResponseEntity<Review> resp = new ResponseEntity<Review>(review, HttpStatus.OK);
		when(reviewService.addReview(reviewDto, user, product)).thenReturn(resp);

		assertEquals(resp, reviewController.addReview(reviewDto));
		assertEquals(200, reviewController.addReview(reviewDto).getStatusCodeValue());

	}

	@Test
	void listReviewTest() {
		when(productService.getOneProduct(1)).thenReturn(product);
		listreview.add(review);
		ResponseEntity<List<Review>> resp = new ResponseEntity<List<Review>>(listreview, HttpStatus.OK);

		when(reviewService.showReview(product)).thenReturn(resp);
		assertEquals(listreview, reviewController.listReview(1).getBody());
		assertEquals(200, reviewController.listReview(1).getStatusCodeValue());
	}

	@Test
	void getReviewTest() {
		ResponseEntity<Review> resp = new ResponseEntity<Review>(review, HttpStatus.OK);
		when(reviewService.getOneReview(1)).thenReturn(resp);
		assertEquals(review, reviewController.getReview(1).getBody());
		assertEquals(200, reviewController.getReview(1).getStatusCodeValue());
	}

	@Test
	void editReviewTest() {
		ResponseEntity<Review> resp = new ResponseEntity<Review>(review, HttpStatus.OK);
		when(reviewService.editReview(reviewDto)).thenReturn(resp);
		assertEquals(review, reviewController.editReview(reviewDto).getBody());
		assertEquals(200, reviewController.editReview(reviewDto).getStatusCodeValue());
	}

}
