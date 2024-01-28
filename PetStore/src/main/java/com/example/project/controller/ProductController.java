package com.example.project.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.project.dto.ProductDto;
import com.example.project.exception.CategoryNotExistException;
import com.example.project.exception.ProductNotExistException;
import com.example.project.model.Product;
import com.example.project.model.ProductCategory;
import com.example.project.model.UserModel;
import com.example.project.model.WishList;
import com.example.project.service.ProductCategoryService;
import com.example.project.service.ProductService;
import com.example.project.service.UserService;
import com.example.project.service.WishListService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class ProductController {
	@Autowired
	private ProductService productserv;
	@Autowired
	private ProductCategoryService categoryService;
	@Autowired
	private UserService userService;
	@Autowired
	private WishListService wishService;
	
	public ProductController(ProductService productService, ProductCategoryService categoryService) {
		super();
		this.productserv = productService;
		this.categoryService = categoryService;
	}
	@GetMapping("/allprod/{cid}/{id}")
	public ResponseEntity<List<Product>> productcatWish(@PathVariable (value="cid") long cid,@PathVariable (value = "id") long id){
		Optional<ProductCategory> category=categoryService.checkCategory(cid);
		UserModel user=userService.getOneUser(id);
		if(!category.isPresent()) {
			throw new CategoryNotExistException("Category is invalid ");
		}
		WishList wishList=wishService.getWishListUser(user);
		if(wishList==null) {
			return productserv.getAllProductsforCategory(category.get());}
		else {
			return productserv.getAllProductsforCategory(category.get(), user);
		}
	}
	@GetMapping("/allproducts")
	public ResponseEntity<List<Product>> allProducts() {
		return productserv.getAllProducts();
	}

	

	@PostMapping("/addproduct")
	public ResponseEntity<Object> addProduct(@RequestBody ProductDto product) {
		Optional<ProductCategory> category = categoryService.checkCategory(product.getCategoryId());
		if (!category.isPresent()) {
			throw new CategoryNotExistException("Category id is invalid " + product.getCategoryId());
		}
		return productserv.addProduct(product, category.get());
	}

	@PostMapping("/editproduct/{pid}")
	public ResponseEntity<Object> editProduct(@RequestBody Product productEdit, @PathVariable(value = "pid") long pid) {
		Optional<ProductCategory> category = categoryService
				.checkCategory(productEdit.getProductCategory().getCategoryId());
		if (!category.isPresent()) {
			throw new CategoryNotExistException(
					"Category id is invalid " + productEdit.getProductCategory().getCategoryId());
		}
		Product productExist = productserv.getOneProduct(pid);
		if (category.get() != null && productExist != null) {
			return productserv.editProduct(productEdit, category.get(), productExist);
		}
		else {
			throw new ProductNotExistException("Product Does not exist");
		}
		
	}

	@GetMapping("/getproduct/{pid}")
	public Product getProduct(@PathVariable(value = "pid") long pid) {
		return productserv.getOneProduct(pid);
	}

	@GetMapping("/deleteproduct/{pid}")
	public void deleteProduct(@PathVariable(value = "pid") long pid) {
		productserv.deleteProductById(pid);
	}

	@GetMapping("/search/{product_name}")
	public List<Product> searchProduct(@PathVariable(value = "product_name") String name) {
		return productserv.searchProduct(name);
	}

	

}
