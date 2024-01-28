package com.example.project.exception;

public class CartItemNotExistException extends IllegalArgumentException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7543592383033041745L;

	public CartItemNotExistException(String msg) {
		super(msg);
	}
}