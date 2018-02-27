package com.app;

public class UtilitiesImpl implements UtilitiesServcie {

	public double getStringNumberWhenParenthesis(String mapExif) {
		String s = null;
		if (mapExif.indexOf("(") > 0) {
			s = mapExif.substring(0, mapExif.indexOf("(")).trim();
		}
		double one = 0, two = 0, result = 0;
		if (s != null) {
			try {
				one = Double.parseDouble(s.substring(0, s.indexOf("/")));
				two = Double.parseDouble(s.substring(s.indexOf("/") + 1,
						s.length()));
			} catch (Exception e) {
				result = 0;
			}
			if (one <= 0 || two <= 0) {
				result = 0;
			} else {
				result = one / two;
			}
		} else {
			result = 0;
		}
		return result;
	}

}
