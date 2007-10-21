package sf.net.mlmechtrade.c2ati.tofile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.apache.commons.httpclient.HttpException;
import org.xml.sax.SAXException;

import junit.framework.TestCase;
import sf.net.mlmechtrade.c2ati.C2ATIAPI;
import sf.net.mlmechtrade.c2ati.C2ATIError;
import sf.net.mlmechtrade.c2ati.C2ATIMockImpl;
import sf.net.mlmechtrade.c2ati.C2ATITest;
import sf.net.mlmechtrade.c2ati.domain.ActionEnum;
import sf.net.mlmechtrade.c2ati.domain.OrderEnum;
import sf.net.mlmechtrade.c2ati.domain.Signal;

public class C2SignalsToFileTest extends TestCase {
	C2SignalsToFile fixture;

	@Override
	public void setUp() {
		fixture = new C2SignalsToFile("C:\\TEMP\\", "XXX", false);
	}

	public void testCurrentSystemSignals() {
		List<Signal> inputSignls = new ArrayList<Signal>();
		// Current signal
		Signal xxx = new Signal();
		xxx.setSystemName("xxx");
		inputSignls.add(xxx);
		// Signal from another system
		Signal yyy = new Signal();
		yyy.setSystemName("yyy");
		inputSignls.add(yyy);

		// Invocation
		List<Signal> filteredResult = fixture.currentSytemSignals(inputSignls);

		// Test
		assertEquals(1, filteredResult.size());
		assertEquals("xxx", filteredResult.get(0).getSystemName());
	}

	public void testCancel() throws HttpException, XPathExpressionException, IOException, ParserConfigurationException, SAXException, C2ATIError {
		C2ATIAPI c2 = new C2ATIMockImpl() {
			int countConfirm = 0;

			@Override
			public synchronized void cancelConfirmSigId(long sigId)
					throws HttpException, IOException,
					ParserConfigurationException, SAXException,
					XPathExpressionException, C2ATIError {
				countConfirm++;
				assertTrue(countConfirm >= 1 && countConfirm <= 2);
				assertTrue(sigId == 1 || sigId == 2);

			}
		};
		List<Long> cancelSignals = new ArrayList<Long>();
		cancelSignals.add(1L);
		cancelSignals.add(2L);
		String result = fixture.outputCancel(cancelSignals, c2);
		assertEquals("1\n2\n", result);
	}

	public void testOutputBuy() throws HttpException, XPathExpressionException,
			IOException, ParserConfigurationException, SAXException, C2ATIError {
		C2ATIAPI c2 = new C2ATIMockImpl() {
			int countConfirm = 0;

			@Override
			public synchronized void confirmSig(long quantity, long sigId,
					String permId) throws HttpException, IOException,
					ParserConfigurationException, SAXException,
					XPathExpressionException, C2ATIError {
				assertEquals(0, countConfirm++);
				assertEquals(1, sigId);
			}
		};
		// 2 Ipnut signals
		List<Signal> signals = getTestTrades();
		// Ececution
		String result = fixture.outputBuy(signals, c2);
		// Test
		assertTrue(result.endsWith("\n"));
		result = result.trim();
		assertEquals(-1, result.indexOf("\n"));
		StringTokenizer tokenizer = new StringTokenizer(result, "\t");
		assertEquals(4, tokenizer.countTokens());
		assertEquals("HPI", tokenizer.nextToken());
		assertEquals("10", tokenizer.nextToken());
		assertEquals("Limit", tokenizer.nextToken());
		assertEquals("30.0200", tokenizer.nextToken());
	}

	public void testOutputSell() throws HttpException,
			XPathExpressionException, IOException,
			ParserConfigurationException, SAXException, C2ATIError {

		// Check confirm signal back to C2ATI
		C2ATIAPI c2 = new C2ATIMockImpl() {
			int countConfirm = 0;

			@Override
			public synchronized void confirmSig(long quantity, long sigId,
					String permId) throws HttpException, IOException,
					ParserConfigurationException, SAXException,
					XPathExpressionException, C2ATIError {
				assertEquals(0, countConfirm++);
				assertEquals(2, sigId);
			}
		};
		// 2 Ipnut signals
		List<Signal> signals = getTestTrades();
		// Ececution
		String result = fixture.outputSell(signals, c2);
		// Test
		assertTrue(result.endsWith("\n"));
		result = result.trim();
		assertEquals(-1, result.indexOf("\n"));
		StringTokenizer tokenizer = new StringTokenizer(result, "\t");
		assertEquals(4, tokenizer.countTokens());
		assertEquals("HPI", tokenizer.nextToken());
		assertEquals("10", tokenizer.nextToken());
		assertEquals("Limit", tokenizer.nextToken());
		assertEquals("50.0100", tokenizer.nextToken());
	}

	private List<Signal> getTestTrades() {
		List<Signal> signals = new ArrayList<Signal>();
		Signal sig1 = new Signal();
		sig1.setSignalid(1);
		sig1.setAction(ActionEnum.BTO);
		sig1.setOrderType(OrderEnum.LIMIT);
		sig1.setSymbol("HPI");
		sig1.setLimit(30.02);
		sig1.setScaledQuant(10);
		signals.add(sig1);
		Signal sig2 = new Signal();
		sig2.setSignalid(2);
		sig2.setAction(ActionEnum.STC);
		sig2.setOrderType(OrderEnum.LIMIT);
		sig2.setSymbol("HPI");
		sig2.setLimit(50.01);
		sig2.setScaledQuant(10);
		signals.add(sig2);
		return signals;
	}

}
