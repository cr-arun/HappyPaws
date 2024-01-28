package com.example.project.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.project.dto.AddtoCart;
import com.example.project.dto.CartItemDto;
import com.example.project.model.Cart;
import com.example.project.model.CartItem;
import com.example.project.model.Product;
import com.example.project.model.UserModel;
import com.example.project.service.CartService;
import com.example.project.service.ProductService;
import com.example.project.service.UserService;

@SpringBootTest(classes = { CartControllerTest.class })
class CartControllerTest {
	@Mock
	CartService cartService;
	@Mock
	ProductService productService;
	@Mock
	UserService userService;
	@InjectMocks
	CartController cartController;
	UserModel user;
	Product product;
	Cart cart;
	CartItem cartItem;

	@BeforeEach
	void setUp() {
		// user
		user = new UserModel((long) 1, "arun@gmail.com", "encryped password", "Arun", "9487550114", false, "User");
		// product
		product = new Product();
		product.setProductid(1);
		product.setProductName("Labrador");
		product.setDescription("Desciption");
		product.setImageUrl("url");
		product.setPrice(2000);
		product.setQuantity(30);
		// cartItem
		cartItem = new CartItem(1, product, new Date(System.currentTimeMillis()), 2);
		List<CartItem> cartItems = new ArrayList<>();
		cartItems.add(cartItem);
		// cart
		cart = new Cart();
		cart.setCartid((long) 1);
		cart.setItems(cartItems);
		cart.setUser(user);

	}

	@Test
	void addToCartTest() {
		AddtoCart addToCart = new AddtoCart((long) 1, (long) 1, 2);
		when(productService.getOneProduct((long) 1)).thenReturn(product);
		when(userService.getOneUser(1)).thenReturn(user);
		when(cartService.addtoCartFirstTime(addToCart, product, user)).thenReturn(cart);
		assertEquals(cart, cartController.addtoCart(addToCart, 1));

	}

	@Test
	void getCart() {
		when(cartService.getCartByUser(1)).thenReturn(cart);
		assertEquals(cart, cartController.list((long) 1).getBody());
	}

	@Test
	void editCartItem() {
		CartItemDto cartItem = new CartItemDto();
		cartItem.setCartItemId(1);
		cartItem.setQuantity(2);
		when(cartService.editCartItem(cartItem, 1)).thenReturn(cart);
		assertEquals(cart, cartController.updateCart(cartItem, 1).getBody());
	}

}
