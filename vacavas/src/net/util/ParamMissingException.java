package net.util;

public class ParamMissingException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ParamMissingException(String error){
		super(error);
	}
}
