package sf.net.mlmechtrade.c2ati.test;

import java.io.IOException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import junit.framework.TestCase;

import org.apache.commons.httpclient.HttpException;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import sf.net.mlmechtrade.c2ati.C2ATI;
import sf.net.mlmechtrade.c2ati.C2ATIError;
import sf.net.mlmechtrade.c2ati.domain.ActionEnum;
import sf.net.mlmechtrade.c2ati.domain.AssetEnum;
import sf.net.mlmechtrade.c2ati.domain.DurationEnum;
import sf.net.mlmechtrade.c2ati.domain.LatestSignals;
import sf.net.mlmechtrade.c2ati.domain.OrderEnum;
import sf.net.mlmechtrade.c2ati.domain.Signal;

public class C2ATITest extends TestCase {
	C2ATI fixture;

	public void setUp() throws IOException {
		fixture = new C2ATIMockImpl();
	}

	public void testConnectToCollective() throws XPathExpressionException,
			IOException, ParserConfigurationException, SAXException, C2ATIError {
		fixture.login();
		fixture.logOff();
	}
	
	public void testLatestSignals() throws HttpException, XPathExpressionException, IOException, ParserConfigurationException, SAXException, C2ATIError {
		LatestSignals signals = fixture.latestSignals();
		assertNotNull(signals);
		// canselListIds
		List<Long> canselListIds = signals.getCanselListIds();
		assertEquals(2, canselListIds.size());
		assertEquals(new Long(111L), canselListIds.get(0));
		assertEquals(new Long(222L), canselListIds.get(1));
		// canselListPermIds
		List<String> canselListPermIds = signals.getCanselListPermIds();
		assertEquals(2, canselListPermIds.size());
		assertEquals("AAA", canselListPermIds.get(0));
		assertEquals("BBB", canselListPermIds.get(1));
		// signals
		List<Signal> sSignals = signals.getSignals();
		assertEquals(4, sSignals.size());
		testSignal(sSignals);
	}

	private void testSignal(List<Signal> signals) {
		Signal signal = signals.get(0);
		assertEquals("XXX", signal.getSystemName());
		assertEquals(2272, signal.getSystemIdNum());
//		assertEquals(267066, signal.getSignalid());
//		assertEquals(1181568629, signal.getPostedWhen());
//		assertEquals("2007-06-11 09:30:29", signal.getPostedHumanTime());
//		assertEquals(ActionEnum.SSHORT, signal.getAction());
//		assertEquals(33, signal.getScaledQuant());
//		private long scaledQuant;
//
//		private long originalQuant;
//
//		private String symbol;
//
//		private AssetEnum assetType;
//
//		private boolean mutualFund;
//
//		private OrderEnum orderType;
//
//		private double stop;
//
//		private double limit;
//
//		private DurationEnum tif;
//
//		private String underlying;
//
//		private String right;
//
//		private String strike;
//
//		private String expir;
//
//		private String exchange;
//
//		private String marketcode;
//
//		private String ocagroup;
//
//		private Node conditionalUpon;
//
//		private String commentary;
//
//		private String[] matchingOpenSigsSigId;
//
//		private String[] matchingOpenSigsPermId;		
		
	}

}



// InputStream is = getClass().getClassLoader().getResourceAsStream(
// "login.properties");
// Properties prop = new Properties();
// prop.load(is);
// Load live props
// String eMail = prop.getProperty("eMail");
// String password = prop.getProperty("password");
// String host = prop.getProperty("host", "host");
// String isLifeAccountStr = prop.getProperty("liveType", "0");
// boolean isLifeAccount = isLifeAccountStr.equals("1");
// Create C2ATI API Class
