package com.example.project.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.project.dto.AddtoCart;
import com.example.project.dto.CartItemDto;
import com.example.project.model.Cart;
import com.example.project.model.Product;
import com.example.project.model.UserModel;

@RunWith(SpringRunner.class)
@SpringBootTest
class CartServiceTest {
	@Autowired
	private CartService cartService;
	@Autowired
	private ProductService productService;
	@Autowired
	private UserService userService;

	UserModel user;
	Product product;

	@BeforeEach
	void setup() {
		user = userService.getOneUser(1);
		product = productService.getOneProduct(1);
	}

	@Test
	void addtoCartFirstTimeTest() {
		Product product = productService.getOneProduct(1);
		AddtoCart addCart = new AddtoCart();
		addCart.setQuantity(2);
		UserModel user = userService.getOneUser((long) 1);
		Cart cart = cartService.addtoCartFirstTime(addCart, product, user);
		assertEquals("Labrador", cart.getItems().stream().findFirst().get().getProduct().getProductName());

	}

	@Test
	void addtoExistCart() {
		AddtoCart addCart = new AddtoCart();
		addCart.setId(1);
		addCart.setProductId(1);
		addCart.setQuantity(2);
		Cart cart = cartService.addToExistCart(addCart, product, user);
		assertEquals("Labrador", cart.getItems().stream().findFirst().get().getProduct().getProductName());

	}

	@Test
	void editCartItemTest() {
		CartItemDto cartItem = new CartItemDto();
		cartItem.setCartItemId(1);
		cartItem.setQuantity(2);
		Cart cart = cartService.editCartItem(cartItem, 1);
		assertEquals(2, cart.getItems().stream().findFirst().get().getQuantity());

	}

	@Test
	void getCartByUserTest() {
		long id = 1;
		Cart cart = cartService.getCartByUser(id);
		assertEquals("Arun", cart.getUser().getName());

	}

}
