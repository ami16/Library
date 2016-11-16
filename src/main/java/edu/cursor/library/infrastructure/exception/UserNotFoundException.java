package edu.cursor.library.infrastructure.exception;

public class UserNotFoundException extends Exception {
	
	private static final long serialVersionUID = 1L;
	
	@Override
	public String getMessage() {
		System.out.println("User not found");
		return super.getMessage();
	}

}
