package com.example.project.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ProductCategory implements Serializable {
	/**
		 * 
		 */
	private static final long serialVersionUID = 1332498L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long categoryId;
	private String categoryName;
	private String categoryImageUrl;

	public ProductCategory() {
		super();
	}

	public ProductCategory(long categoryId, String categoryName, String categoryImageUrl) {
		super();
		this.categoryId = categoryId;
		this.categoryName = categoryName;
		this.categoryImageUrl = categoryImageUrl;
	}

	public String getCategoryImageUrl() {
		return categoryImageUrl;
	}

	public void setCategoryImageUrl(String categoryImageUrl) {
		this.categoryImageUrl = categoryImageUrl;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(long categoryId) {
		this.categoryId = categoryId;
	}

}
