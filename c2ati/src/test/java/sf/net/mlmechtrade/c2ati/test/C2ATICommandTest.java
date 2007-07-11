package sf.net.mlmechtrade.c2ati.test;

import sf.net.mlmechtrade.c2ati.C2ATICommand;
import sf.net.mlmechtrade.c2ati.C2ATICommandEnum;
import junit.framework.TestCase;

public class C2ATICommandTest extends TestCase {
	
	public void test() {
		
		final C2ATICommand cmd = 
			C2ATICommand.create(C2ATICommandEnum.ackc2fill)
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
