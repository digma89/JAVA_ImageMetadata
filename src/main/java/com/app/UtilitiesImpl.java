package com.app;


public class UtilitiesImpl implements UtilitiesServcie {

	public double getStringNumberWhenParenthesis(String mapExif) {	
		String s = mapExif.substring(0, mapExif.indexOf("(")).trim();
		double one, two;
		one =  Double.parseDouble(s.substring(0, s.indexOf("/")));
		two =  Double.parseDouble(s.substring(s.indexOf("/")+1,s.length()));
		return one/two; 
	}

}
