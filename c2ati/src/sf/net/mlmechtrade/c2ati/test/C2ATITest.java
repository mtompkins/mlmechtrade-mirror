package sf.net.mlmechtrade.c2ati.test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.xml.sax.SAXException;

import sf.net.mlmechtrade.c2ati.C2ATI;
import sf.net.mlmechtrade.c2ati.C2ATIError;
import sf.net.mlmechtrade.c2ati.LatestSignals;
import junit.framework.TestCase;

public class C2ATITest extends TestCase {
	C2ATI fixture;

	public void setUp() throws IOException {
		InputStream is = getClass().getClassLoader().getResourceAsStream(
				"login.properties");
		Properties prop = new Properties();
		prop.load(is);
		// Load props
		String eMail = prop.getProperty("eMail");
		String password = prop.getProperty("password");
		String host = prop.getProperty("host", "host");
		String isLifeAccountStr = prop.getProperty("liveType", "0");
		boolean isLifeAccount = isLifeAccountStr.equals("1");
		// Create
		fixture = new C2ATI(eMail, password, isLifeAccount, host);
	}

	public void testConnectToCollective() throws XPathExpressionException,
			IOException, ParserConfigurationException, SAXException, C2ATIError {
		fixture.login();
		LatestSignals signals = fixture.latestSignals();
		fixture.logOff();
	}
}
