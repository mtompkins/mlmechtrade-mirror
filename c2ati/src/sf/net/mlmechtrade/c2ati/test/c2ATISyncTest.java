package sf.net.mlmechtrade.c2ati.test;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.xml.sax.SAXException;

import sf.net.mlmechtrade.c2ati.C2ATI;
import sf.net.mlmechtrade.c2ati.C2ATIError;
import junit.framework.TestCase;

public class c2ATISyncTest extends TestCase {
	C2ATI fixture;
	
	public void setUp() throws IOException {
		fixture = new C2ATIMockImpl();
	}

	public void testConfirmSig() throws XPathExpressionException,
			IOException, ParserConfigurationException, SAXException, C2ATIError {
		fixture.login();
		fixture.confirmSig(1, 1111, "abc");
		fixture.logOff();
	}
}
