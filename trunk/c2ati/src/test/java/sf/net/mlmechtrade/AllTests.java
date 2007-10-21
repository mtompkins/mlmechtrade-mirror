package sf.net.mlmechtrade;

import sf.net.mlmechtrade.c2api.C2SignalEntryCommandTest;
import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("Test for sf.net.mlmechtrade");
		//$JUnit-BEGIN$
		suite.addTestSuite(C2CommandTest.class);
		suite.addTestSuite(C2AnnotatedCommandTest.class);
		//$JUnit-END$
		suite.addTestSuite(C2SignalEntryCommandTest.class);
		suite.addTest(sf.net.mlmechtrade.c2ati.AllTests.suite());
		return suite;
	}

}
