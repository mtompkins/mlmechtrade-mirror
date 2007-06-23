package sf.net.mlmechtrade.c2ati.test;

public class TestUtil {
	public static String getCmd(String url) {
		int cmdStart = url.indexOf("?cmd=") + 5;
		return url.substring(cmdStart, url.indexOf('&', cmdStart));
	}
}
