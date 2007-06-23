package sf.net.mlmechtrade.c2ati.test;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.xml.sax.SAXException;

import sf.net.mlmechtrade.c2ati.C2ATI;
import sf.net.mlmechtrade.c2ati.C2ATIError;
import junit.framework.TestCase;

public class C2ATIGetAllSignalsTest extends TestCase {
	C2ATI fixture;

	public void setUp() throws IOException {
		fixture = new C2ATIMockImpl();
	}

	public void testGetAllSignals() throws XPathExpressionException,
			IOException, ParserConfigurationException, SAXException, C2ATIError {
		fixture.login();
		fixture.getAllSignals();
		fixture.logOff();
	}
}
