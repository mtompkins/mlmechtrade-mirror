package sf.net.mlmechtrade.c2ati.test;

import java.io.IOException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.xml.sax.SAXException;

import sf.net.mlmechtrade.c2ati.C2ATI;
import sf.net.mlmechtrade.c2ati.C2ATIError;
import sf.net.mlmechtrade.c2ati.domain.TradingSystem;
import junit.framework.TestCase;

public class C2ATIGetAllSignalsTest extends TestCase {
	C2ATI fixture;

	public void setUp() throws IOException {
		fixture = new C2ATIMockImpl();
	}

	public void testGetAllSignals() throws XPathExpressionException,
			IOException, ParserConfigurationException, SAXException, C2ATIError {
		fixture.login();

		// 1
		List<TradingSystem> pendingSignals = fixture.getAllSignals();
		assertNotNull(pendingSignals);
		assertEquals(2, pendingSignals.size());
		TradingSystem pendingSignal = pendingSignals.get(0);
		assertEquals(15816213, pendingSignal.getSystemId());
		List<Long> pending = pendingSignal.getPendingBlock();
		assertEquals(2, pending.size());
		assertEquals(new Long(26869706), pending.get(0));
		assertEquals(new Long(26869709), pending.get(1));
		// 2
		pendingSignal = pendingSignals.get(1);
		assertEquals(2423423, pendingSignal.getSystemId());
		pending = pendingSignal.getPendingBlock();
		assertEquals(2, pending.size());
		assertEquals(new Long(26863333), pending.get(0));
		assertEquals(new Long(26844444), pending.get(1));

		// logoff
		fixture.logOff();
	}
}
