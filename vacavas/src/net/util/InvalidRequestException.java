package net.util;

public class InvalidRequestException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidRequestException(String error){
		super(error);
	}
}
