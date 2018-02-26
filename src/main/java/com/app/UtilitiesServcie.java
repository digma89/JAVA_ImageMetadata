package com.app;


public interface UtilitiesServcie {
	
	/**
	 * Method that gets the String value when it has parentesis
	 * ex: 10/1600 (0.006) --> 10/1600
	 * 
	 * @param Map<Integer,Object> exifMap)
	 * @return String
	 */
	public double getStringNumberWhenParenthesis(String mapExif);

}
