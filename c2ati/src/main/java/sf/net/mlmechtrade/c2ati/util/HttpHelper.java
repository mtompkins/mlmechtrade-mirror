package sf.net.mlmechtrade.c2ati.util;

/**
 * Helper to format HTTP requests, parse HTTP responses.
 *
 */
public class HttpHelper {

	/**
	 * Similar to JavaScript htmlEncode function.
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

	/**
	 * HTML-encodes a command
	 * @param command The command to encode (for HTTP request)
	 * @return command.name()
	 */
	public static final String htmlEncodeCommand(Enum command) {
		return command.name();
	}

	/**
	 * HTML-encodes a command parameter
	 * @param command The parameter to encode (for HTTP request)
	 * @return htmlEncode(param.toString())
	 */
	public static final String htmlEncodeParameter(Object param) {
		
		if (param == null) {
			return "";
		}
		
		return htmlEncode(param.toString());
	}

	 /** @return "command=param" */
	public static final String htmlEncodeCommand(Enum command, Object param) {
		return htmlEncodeCommand(command) + '=' + HttpHelper.htmlEncodeParameter(param);
	}
}
