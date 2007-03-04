package net.sf.mlmechtrade.tafunc.generator;

import java.util.StringTokenizer;

public class Util {
	
	// Translate names of parameters to Java like style. Sorry not in C ;-)
	static String camelCase(String input) {
		StringTokenizer st = new StringTokenizer(input);
		StringBuffer output = new StringBuffer();
		if (st.hasMoreTokens()) {
			String token = st.nextToken();
			output.append(firstCharLower(token));
		}
		while (st.hasMoreTokens()) {
			output.append(st.nextToken());
		}
		return output.toString();
	}
	
	// Provide word with first char in lower case
	static String firstCharLower(String input) {
		StringBuffer buf = new StringBuffer(input);
		if (input != null && input.length() > 0) {
			buf.setCharAt(0, Character.toLowerCase(input.charAt(0)));
		}
		return buf.toString();
	}
	
	static String concat(String input) {
		StringTokenizer st = new StringTokenizer(input);
		StringBuffer output = new StringBuffer();
		while (st.hasMoreTokens()) {
			output.append(st.nextToken());
		}
		return output.toString();
	}
}
