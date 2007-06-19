package sf.net.mlmechtrade.c2ati.test;

import junit.framework.TestCase;

public class TestUtilTest extends TestCase {
	public void testGetCmd() {
		String url = "http://XX.XX.XXX.X:7878?cmd=login&e=";
		assertEquals("login", TestUtil.getCmd(url));
	}
}
