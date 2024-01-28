package com.example.project.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.project.model.ProductCategory;
import com.example.project.repository.ProductCategoryRepository;

@Service
public class ProductCategoryService {
	@Autowired
	private ProductCategoryRepository productCat;

	public ProductCategoryService(ProductCategoryRepository productCat) {
		super();
		this.productCat = productCat;
	}

	public ResponseEntity<Object> addCategory(ProductCategory category) {
		return new ResponseEntity<>(productCat.save(category), HttpStatus.OK);
	}

	public Optional<ProductCategory> checkCategory(long catId) {
		return productCat.findById(catId);
	}

	public List<ProductCategory> listCategory() {

		List<ProductCategory> categorys = productCat.findAll();
		Collections.sort(categorys, (obj1, obj2) -> obj1.getCategoryName().compareToIgnoreCase(obj2.getCategoryName()));
		return categorys;
	}

	public ProductCategory editCategory(ProductCategory categoryEdit, ProductCategory categoryExist) {
		categoryExist.setCategoryName(categoryEdit.getCategoryName());
		categoryExist.setCategoryImageUrl(categoryEdit.getCategoryImageUrl());

		return productCat.save(categoryExist);
	}

	public void deleteCategory(long categoryId) {

		productCat.deleteById(categoryId);
	}

}
