package com.example.project.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.project.dto.ProductDto;
import com.example.project.exception.ProductNotExistException;
import com.example.project.model.Product;
import com.example.project.model.ProductCategory;
import com.example.project.model.UserModel;
import com.example.project.model.WishList;
import com.example.project.model.WishListItem;
import com.example.project.repository.ProductRepository;
import com.example.project.repository.WishListRepo;
import com.example.project.responseentity.ResponseHandler;

@Service
public class ProductService {
	@Autowired
	private ProductRepository productRepo;
	@Autowired
	private WishListRepo wishRepo;

	public ResponseEntity<Object> addProduct(ProductDto productDto, ProductCategory category) {
		try {
			Product product = new Product();
			product.setProductName(productDto.getProductName());
			product.setDescription(productDto.getDescription());
			product.setImageUrl(productDto.getImageUrl());
			product.setPrice(productDto.getPrice());
			product.setQuantity(productDto.getQuantity());
			product.setProductCategory(category);
			productRepo.save(product);
			return ResponseHandler.generateResponse(product, HttpStatus.ACCEPTED);
		} catch (Exception e) {
			return ResponseHandler.generateResponse("Not able to add product", HttpStatus.BAD_REQUEST);
		}

	}

	public ResponseEntity<List<Product>> getAllProductsforCategory(ProductCategory category) {

		List<Product> products = productRepo.findAllByProductCategoryOrderByProductName(category);
		return new ResponseEntity<>(products, HttpStatus.OK);
	}

	public ResponseEntity<Object> editProduct(Product productEdit, ProductCategory category, Product productExist) {

		productExist.setProductName(productEdit.getProductName());
		productExist.setDescription(productEdit.getDescription());
		productExist.setImageUrl(productEdit.getImageUrl());
		productExist.setPrice(productEdit.getPrice());
		productExist.setQuantity(productEdit.getQuantity());
		productExist.setProductCategory(category);
		return ResponseHandler.generateResponse(productRepo.save(productExist), HttpStatus.OK);

	}

	public Product getOneProduct(long id) {
		Optional<Product> productExist = productRepo.findById(id);
		if (!productExist.isPresent()) {
			throw new ProductNotExistException("Product id is invalid " + id);
		}
		return productExist.get();
	}

	public ResponseEntity<List<Product>> getAllProducts() {
		List<Product> products = productRepo.findAllByOrderByProductName();
		return new ResponseEntity<>(products, HttpStatus.OK);

	}

	public void deleteProductById(long pid) {
		productRepo.deleteById(pid);

	}

	public void changeQuantity(Long productid, int quantity) {
		Optional<Product> product = productRepo.findById(productid);
		if (!product.isPresent()) {
			throw new ProductNotExistException("Product id is invalid " + productid);
		}
		int changedQuantity = product.get().getQuantity() - quantity;
		product.get().setQuantity(changedQuantity);
		productRepo.save(product.get());
	}

	public List<Product> searchProduct(String productname) {
		return productRepo.findByProductNameContaining(productname);

	}

	

	public ResponseEntity<List<Product>> getAllProductsforCategory(ProductCategory category, UserModel user) {

		List<Product> products = productRepo.findAllByProductCategoryOrderByProductName(category);
		Optional<WishList> wish = wishRepo.findByUser(user);
		if(wish.isPresent()) {
		List<WishListItem> wishItems = wish.get().getItems();
		for (Product product : products) {
			for (WishListItem wishItem : wishItems) {
				if (wishItem.getProduct().equals(product)) {
					product.setWishlist(true);
				}
			}

		}}
		return new ResponseEntity<>(products, HttpStatus.OK);
	}

}
