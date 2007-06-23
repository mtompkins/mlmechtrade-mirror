package sf.net.mlmechtrade.c2ati.test;

import java.io.IOException;

import sf.net.mlmechtrade.c2ati.C2ATI;
import junit.framework.TestCase;

public class C2ATISyncTest extends TestCase {
	C2ATI fixture;

	public void setUp() throws IOException {
		fixture = new C2ATIMockImpl();
	}

	public void testRequestSystemSync() {
	}
}
