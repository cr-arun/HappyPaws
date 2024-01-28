package com.example.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.project.dto.AddtoWishListDto;
import com.example.project.model.UserModel;
import com.example.project.model.WishList;
import com.example.project.responseentity.ResponseHandler;
import com.example.project.service.UserService;
import com.example.project.service.WishListService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class WishListController {
	@Autowired
	private WishListService wishlistService;
	@Autowired
	private UserService userService;

	@PostMapping("/addtowish")
	public ResponseEntity<Object> addtoWishList(@RequestBody AddtoWishListDto addwish) {
		UserModel user = userService.getOneUser(addwish.getUserid());
		WishList wishList = wishlistService.getWishListUser(user);
		if (wishList != null) {
			return wishlistService.addToExistWishList(addwish, user);
		} else {
			return wishlistService.addToWishListFirstTime(addwish, user);
		}
	}

	@GetMapping("/getwishlist/{id}")
	public ResponseEntity<Object> getWishList(@PathVariable(value = "id") long id) {
		UserModel user = userService.getOneUser(id);
		return ResponseHandler.generateResponse(wishlistService.getWishListUser(user), HttpStatus.OK);
	}

	@GetMapping("/deletewishlistitem/{wishlistitem}/{id}")
	public ResponseEntity<Object> deleteWishListItem(@PathVariable(value = "wishlistitem") long wishlistitem,
			@PathVariable(value = "id") long id) {
		return wishlistService.deleteItemWishList(wishlistitem, id);
	}

	@GetMapping("/removewishlistitem/{pid}/{id}")
	public ResponseEntity<Object> removeFromWishList(@PathVariable(value = "pid") long wishlistitem,
			@PathVariable(value = "id") long id) {
		return wishlistService.removeFromWishList(wishlistitem, id);
	}

}
