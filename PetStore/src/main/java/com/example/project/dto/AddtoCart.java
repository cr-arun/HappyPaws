package com.example.project.dto;

public class AddtoCart {
	private long id;
	private long productId;
	private int quantity;

	public AddtoCart() {
		super();
	}

	public AddtoCart(long id, long productId, int quantity) {
		super();
		this.id = id;
		this.productId = productId;
		this.quantity = quantity;
	}

	@Override
	public String toString() {
		return "AddtoCart [id=" + id + ", productId=" + productId + ", quantity=" + quantity + "]";
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getProductId() {
		return productId;
	}

	public void setProductId(long productId) {
		this.productId = productId;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

}
