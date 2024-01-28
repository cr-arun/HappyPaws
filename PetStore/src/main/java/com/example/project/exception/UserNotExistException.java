package com.example.project.exception;

public class UserNotExistException extends IllegalArgumentException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 225705644688779849L;

	public UserNotExistException(String msg) {
        super(msg);
    }
}
