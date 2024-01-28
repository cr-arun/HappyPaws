package com.example.project.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class WishList {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long wishid;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<WishListItem> items = new ArrayList<>();

	@JsonIgnore
	@OneToOne(targetEntity = UserModel.class, fetch = FetchType.EAGER)
	@JoinColumn(nullable = false, name = "user_id")
	private UserModel user;

	public Long getWishid() {
		return wishid;
	}

	public void setWishid(Long wishid) {
		this.wishid = wishid;
	}

	public List<WishListItem> getItems() {
		return items;
	}

	public void setItems(List<WishListItem> items) {
		this.items = items;
	}

	public UserModel getUser() {
		return user;
	}

	public void setUser(UserModel user) {
		this.user = user;
	}
}
