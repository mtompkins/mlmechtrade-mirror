package net.sf.mlmechtrade.tafunc.generator;

import junit.framework.TestCase;

public class UtilTest extends TestCase {
	public void testCamelCase() throws Exception {
		assertEquals("abc", Util.camelCase("Abc"));
		assertEquals("abcDE", Util.camelCase("Abc DE"));
		assertEquals("", Util.camelCase(""));
	}
}
