package sf.net.mlmechtrade.c2ati;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite(
				"Test for sf.net.mlmechtrade.c2ati.test");
		// $JUnit-BEGIN$
		suite.addTestSuite(C2ATIACKTest.class);
		suite.addTestSuite(C2ATIErrorTest.class);
		suite.addTestSuite(C2ATIGetAllSignalsTest.class);
		suite.addTestSuite(C2ATIRequestSystemListTest.class);
		suite.addTestSuite(C2ATISyncTest.class);
		suite.addTestSuite(C2ATITest.class);
		suite.addTestSuite(TestUtilTest.class);
		// $JUnit-END$
		return suite;
	}

}