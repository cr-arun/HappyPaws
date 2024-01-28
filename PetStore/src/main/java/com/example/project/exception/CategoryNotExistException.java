package com.example.project.exception;


public class CategoryNotExistException extends IllegalArgumentException{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2943079653662757597L;

	public CategoryNotExistException(String msg) {
        super(msg);
    }
}
