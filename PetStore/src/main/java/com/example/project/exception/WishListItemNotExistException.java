package com.example.project.exception;

public class WishListItemNotExistException extends IllegalArgumentException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2615792603702364207L;

	public WishListItemNotExistException(String msg) {
		super(msg);
	}
}