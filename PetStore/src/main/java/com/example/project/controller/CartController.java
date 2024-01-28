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

import com.example.project.dto.AddtoCart;
import com.example.project.dto.CartItemDto;
import com.example.project.exception.CartItemNotExistException;
import com.example.project.model.Cart;
import com.example.project.model.Product;
import com.example.project.model.UserModel;
import com.example.project.service.CartService;
import com.example.project.service.ProductService;
import com.example.project.service.UserService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")

public class CartController {
	@Autowired
	CartService cartService;
	@Autowired
	ProductService productService;
	@Autowired
	UserService userService;

	@PostMapping("/cartadd/{id}")
	public Cart addtoCart(@RequestBody AddtoCart addDto, @PathVariable(value = "id") long id) {
		Product product = productService.getOneProduct(addDto.getProductId());
		UserModel user = userService.getOneUser(id);
		Cart cart = cartService.getCartByUser(id);
		if (cart != null) {
			return cartService.addToExistCart(addDto, product, user);
		} else {
			return cartService.addtoCartFirstTime(addDto, product, user);

		}

	}

	@GetMapping("/listcart/{id}")
	public ResponseEntity<Cart> list(@PathVariable(value = "id") Long id) {
			return new ResponseEntity<>(cartService.getCartByUser(id), HttpStatus.OK);
	}

	@PostMapping("/editcart/{id}")
	public ResponseEntity<Cart> updateCart(@RequestBody CartItemDto cartItem, @PathVariable(value = "id") long id) {
		Cart cart = cartService.editCartItem(cartItem, id);
		if (cart != null) {
			return new ResponseEntity<>(cart, HttpStatus.OK);
		} else {
			throw  new CartItemNotExistException("Cart item does not exist");
		}
	}

	@GetMapping("/deletecartitem/{cartItemid}/{id}")
	public Cart deleteCartItem(@PathVariable(value = "cartItemid") long cartItemid,
			@PathVariable(value = "id") long id) {
		return cartService.deleteCartItem(cartItemid, id);
	}

	@GetMapping("/deletecart/{id}")
	public String deleteCart(@PathVariable(value = "id") long id) {
		return cartService.deleteCart(id);
	}

}
