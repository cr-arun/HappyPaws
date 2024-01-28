package com.example.project.service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.project.dto.AddtoWishListDto;
import com.example.project.exception.WishListItemNotExistException;
import com.example.project.exception.WishListNotExistException;
import com.example.project.model.Product;
import com.example.project.model.UserModel;
import com.example.project.model.WishList;
import com.example.project.model.WishListItem;
import com.example.project.repository.WishListItemRepo;
import com.example.project.repository.WishListRepo;
import com.example.project.responseentity.ResponseHandler;

@Service
public class WishListService {

	@Autowired
	private ProductService productService;
	@Autowired
	private WishListRepo wishRepo;
	@Autowired
	private WishListItemRepo wishItemRepo;
	@Autowired
	private UserService userService;

	public WishList getWishListUser(UserModel user) {

		return wishRepo.findByUser(user).orElse(null);
	}

	public ResponseEntity<Object> addToExistWishList(AddtoWishListDto addwish, UserModel user) {

		Optional<WishList> wishlist = wishRepo.findByUser(user);
		if (wishlist.isPresent()) {
			Product productexist = productService.getOneProduct(addwish.getProductId());
			boolean productInWishList = false;
			if (wishlist.isPresent()) {

				List<WishListItem> items = wishlist.get().getItems();
				for (WishListItem item : items) {
					if (item.getProduct().equals(productexist)) {
						productInWishList = true;
						wishlist.get().setItems(items);
						wishRepo.save(wishlist.get());
						return ResponseHandler.generateResponse(wishlist, HttpStatus.OK);
					}
				}
			}
			if ((wishlist.isPresent()) && !productInWishList) {
				WishListItem wishItem1 = new WishListItem();
				wishItem1.setCreatedDate(new Date(System.currentTimeMillis()));
				wishItem1.setProduct(productexist);
				wishlist.get().getItems().add(wishItem1);
				wishRepo.save(wishlist.get());
				return ResponseHandler.generateResponse(wishlist, HttpStatus.OK);
			}

			return this.addToWishListFirstTime(addwish, user);
		} else {
			throw new WishListNotExistException("WishList Not Present");
		}

	}

	public ResponseEntity<Object> addToWishListFirstTime(AddtoWishListDto addwish, UserModel user) {
		Product product = productService.getOneProduct(addwish.getProductId());
		WishList wishList = new WishList();
		WishListItem wishItem = new WishListItem();
		wishItem.setProduct(product);
		wishItem.setCreatedDate(new Date(System.currentTimeMillis()));
		wishList.getItems().add(wishItem);
		wishList.setUser(user);
		wishRepo.save(wishList);
		return ResponseHandler.generateResponse(wishList, HttpStatus.OK);
	}

	public ResponseEntity<Object> deleteItemWishList(long wishListItemId, long userId) {
		UserModel user = userService.getOneUser(userId);
		Optional<WishList> wishList = wishRepo.findByUser(user);

		if (wishList.isPresent()) {
			List<WishListItem> wishlistItems = wishList.get().getItems();
			WishListItem wishListItem = null;
			for (WishListItem item : wishlistItems) {
				if (item.getWishItemid() == wishListItemId) {
					wishListItem = item;

				}
			}
			if (wishListItem != null) {
				wishlistItems.remove(wishListItem);
				wishItemRepo.delete(wishListItem);
			}
			return ResponseHandler.generateResponse(wishRepo.findByUser(user), HttpStatus.OK);
		} else {
			throw new WishListItemNotExistException("Wish List item does not exist" + wishListItemId);
		}
	}

	public ResponseEntity<Object> removeFromWishList(long productId, long userId) {
		Product product = productService.getOneProduct(productId);
		UserModel user = userService.getOneUser(userId);
		Optional<WishList> wishList = wishRepo.findByUser(user);
		if (wishList.isPresent()) {
			List<WishListItem> wishlistItems = wishList.get().getItems();
			WishListItem wishListItem = null;
			for (WishListItem item : wishlistItems) {
				if (item.getProduct() == product) {
					wishListItem = item;
				}
			}
			if (wishListItem != null) {
				wishlistItems.remove(wishListItem);
				wishItemRepo.delete(wishListItem);
			}
			return ResponseHandler.generateResponse(wishRepo.findByUser(user), HttpStatus.OK);
		} else {
			throw new WishListNotExistException("Not in wishList found for user");
		}
	}

}
