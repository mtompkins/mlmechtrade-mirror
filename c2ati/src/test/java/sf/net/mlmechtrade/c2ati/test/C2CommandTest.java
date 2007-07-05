package sf.net.mlmechtrade.c2ati.test;

import sf.net.mlmechtrade.c2ati.C2Command;
import sf.net.mlmechtrade.c2ati.C2CommandEnum;
import junit.framework.TestCase;

public class C2CommandTest extends TestCase {
	
	public void test() {
		
		final C2Command cmd = 
			C2Command.create(C2CommandEnum.ackc2fill)
				.setParam("x", "X")
				.setParam("y", "Y")
				.setParam("z", "Z")
				.setParam("tmp", "tmp")
				.setParam("tmp", null)
				;
		
		final String httpRequestString = cmd.toString();
		//System.out.println(httpRequestString);
		assertTrue(httpRequestString.contains("cmd=ackc2fill&"));
		assertTrue(httpRequestString.contains("&x=X"));
		assertTrue(httpRequestString.contains("&y=Y"));
		assertTrue(httpRequestString.contains("&z=Z"));
		assertFalse(httpRequestString.contains("tmp"));
		assertEquals("cmd=ackc2fill&z=Z&y=Y&x=X".length(), httpRequestString.length());
	}

}
