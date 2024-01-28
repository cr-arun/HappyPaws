package com.example.project.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class WishListItem {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long wishItemid;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "product_id", referencedColumnName = "productid")
	private Product product;
	@Column(name = "created_date")
	private Date createdDate;

	public long getWishItemid() {
		return wishItemid;
	}

	public void setWishItemid(long wishItemid) {
		this.wishItemid = wishItemid;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

}
