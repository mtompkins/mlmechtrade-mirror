package sf.net.mlmechtrade.c2ati.util;

public class HttpHelper {


	/**
	 * Similar to JavaScript htmlDecode function.
	 * @param s String to encode (for HTTP request)
	 * @return s, because C2 8.2 uses plain US-ASCII
	 */
	public static final String htmlEncode(String s) {
		return s;
		
	}

	/**
	 * Similar to JavaScript htmlDecode function.
	 * @param s String to decode (from HTTP request)
	 * @return s, because C2 8.2 uses plain US-ASCII
	 */
	public static final String htmlDecode(String s) {
		return s;
	}
}
