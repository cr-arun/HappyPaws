package com.example.project.exception;

public class ReviewNotExistException extends IllegalArgumentException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4865253150591707765L;

	public ReviewNotExistException (String msg) {
        super(msg);
    }
}
