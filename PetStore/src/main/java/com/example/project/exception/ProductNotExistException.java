package com.example.project.exception;

public class ProductNotExistException extends IllegalArgumentException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 4210176792875698159L;

	public ProductNotExistException(String msg) {
        super(msg);
    }
}
