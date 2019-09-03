package com.tavisca.cache.exception;

/**
 * Custom Exception for Element Not Found in Cache
 * @author Naresh Mahajan
 *
 */
public class ElementNotFoundException extends Exception{

	/**
	 * Default COnstrutor 
	 * @param message
	 */
	public ElementNotFoundException(String message) {
		super(message);
	}

}
