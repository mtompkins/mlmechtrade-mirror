package sf.net.mlmechtrade.c22ib.test;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.xml.sax.SAXException;

import sf.net.mlmechtrade.c22ib.C2IBTrader;
import sf.net.mlmechtrade.c2ati.C2ATIError;
import junit.framework.TestCase;

public class C2IBTraderTest extends TestCase {

	C2IBTrader fixture;

	public void testC2IBTrader() throws XPathExpressionException, IOException,
			ParserConfigurationException, SAXException, C2ATIError {
		fixture = new C2IBTrader("login.properties");
		fixture.c2TradeCycle();
	}

}
