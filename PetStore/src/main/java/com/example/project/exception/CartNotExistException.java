package com.example.project.exception;

public class CartNotExistException extends IllegalArgumentException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8986835313446925117L;

	public CartNotExistException(String msg) {
		super(msg);
	}
}
