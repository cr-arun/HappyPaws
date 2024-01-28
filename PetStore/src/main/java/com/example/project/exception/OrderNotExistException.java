package com.example.project.exception;

public class OrderNotExistException extends IllegalArgumentException{
	  /**
	 * 
	 */
	private static final long serialVersionUID = 6770344515472441856L;

	public OrderNotExistException(String msg) {
	        super(msg);
	    }
}
