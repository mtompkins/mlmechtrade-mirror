package sf.net.mlmechtrade.c2ati;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.xml.sax.SAXException;

import sf.net.mlmechtrade.c2ati.C2ATIAPI;
import sf.net.mlmechtrade.c2ati.C2ATIError;
import junit.framework.TestCase;

public class C2ATIRequestSystemListTest extends TestCase {
	C2ATIAPI fixture;

	public void setUp() throws IOException {
		fixture = new C2ATIMockImpl();
	}

	public void testRequestSystemList() throws XPathExpressionException,
			IOException, ParserConfigurationException, SAXException, C2ATIError {
		fixture.login();
		fixture.requestSystemList();
		fixture.logOff();
	}

}
