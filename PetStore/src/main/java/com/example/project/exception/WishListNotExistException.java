package com.example.project.exception;

public class WishListNotExistException  extends IllegalArgumentException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 225705644688779849L;

	public WishListNotExistException(String msg) {
        super(msg);
    }
}