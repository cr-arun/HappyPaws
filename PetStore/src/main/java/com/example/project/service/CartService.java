package com.example.project.service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.project.dto.AddtoCart;
import com.example.project.dto.CartItemDto;
import com.example.project.exception.CartItemNotExistException;
import com.example.project.model.Cart;
import com.example.project.model.CartItem;
import com.example.project.model.Product;
import com.example.project.model.UserModel;
import com.example.project.repository.CartItemRepo;
import com.example.project.repository.CartRepo;

@Service
public class CartService {
	@Autowired
	CartRepo cartRepo;
	@Autowired
	CartItemRepo cartItemRepo;
	@Autowired
	ProductService productService;
	@Autowired
	UserService userService;

	public CartService(CartRepo cartRepo) {
		super();
		this.cartRepo = cartRepo;
	}

	public Cart addtoCartFirstTime(AddtoCart addDto, Product product, UserModel user) {
		Cart cartlocal = new Cart();
		CartItem cartItem = new CartItem();
		cartItem.setQuantity(addDto.getQuantity());
		cartItem.setCreatedDate(new Date(System.currentTimeMillis()));
		cartItem.setProduct(product);
		cartlocal.setUser(user);
		cartlocal.getItems().add(cartItem);
		return cartRepo.save(cartlocal);
	}

	public Cart addToExistCart(AddtoCart addDto, Product product, UserModel user) {

		Cart cart = cartRepo.findByUser(user);
		Product productexist = productService.getOneProduct(product.getProductid());
		boolean productInCart = false;
		if (cart != null) {

			List<CartItem> items = cart.getItems();
			for (CartItem item : items) {
				if (item.getProduct().equals(productexist)) {
					productInCart = true;
					item.setQuantity(item.getQuantity() + addDto.getQuantity());
					cart.setItems(items);
					return cartRepo.save(cart);
				}
			}
		}
		if ((cart != null) && !productInCart) {
			CartItem cartItem1 = new CartItem();
			cartItem1.setCreatedDate(new Date(System.currentTimeMillis()));
			cartItem1.setProduct(product);
			cartItem1.setQuantity(addDto.getQuantity());
			cart.getItems().add(cartItem1);
			return cartRepo.save(cart);
		}

		return this.addtoCartFirstTime(addDto, productexist, user);

	}

	public Cart getCartByUser(long id) {
		UserModel user = userService.getOneUser(id);
		return cartRepo.findByUser(user);
	}

	public Cart editCartItem(CartItemDto cartItem, long userid) {
		UserModel user = userService.getOneUser(userid);
		Optional<CartItem> cartItemlocal = cartItemRepo.findById(cartItem.getCartItemId());
		if (cartItemlocal.isPresent()) {
			cartItemlocal.get().setQuantity(cartItem.getQuantity());
			cartItemRepo.save(cartItemlocal.get());
			return cartRepo.findByUser(user);
		} else {
			throw new CartItemNotExistException("No cart item found");
		}
	}

	public Cart deleteCartItem(long cartItemId, long userid) {
		UserModel user = userService.getOneUser(userid);
		Cart cart = cartRepo.findByUser(user);
		List<CartItem> items = cart.getItems();
		CartItem cartItemlocal = null;
		for (CartItem item : items) {
			if (item.getCartItemid() == cartItemId) {
				cartItemlocal = item;
			}
		}
		if (cartItemlocal != null) {
			items.remove(cartItemlocal);
			cartItemRepo.delete(cartItemlocal);
		}
		return cartRepo.findByUser(user);
	}

	public String deleteCart(long id) {
		UserModel user = userService.getOneUser(id);
		Cart cart = cartRepo.findByUser(user);
		if (cart != null) {
			cartRepo.delete(cart);
			return "Success";
		} else {
			return "User Not Found";
		}
	}
}
