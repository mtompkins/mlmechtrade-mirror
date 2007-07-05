package sf.net.mlmechtrade.c2ati.test;

import java.io.IOException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import junit.framework.TestCase;

import org.apache.commons.httpclient.HttpException;
import org.xml.sax.SAXException;

import sf.net.mlmechtrade.c2ati.C2ATI;
import sf.net.mlmechtrade.c2ati.C2ATIError;
import sf.net.mlmechtrade.c2ati.domain.ActionEnum;
import sf.net.mlmechtrade.c2ati.domain.AssetEnum;
import sf.net.mlmechtrade.c2ati.domain.C2RecentFill;
import sf.net.mlmechtrade.c2ati.domain.DurationEnum;
import sf.net.mlmechtrade.c2ati.domain.FillAcknowledgment;
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

	public void testLatestSignals() throws HttpException,
			XPathExpressionException, IOException,
			ParserConfigurationException, SAXException, C2ATIError {
		LatestSignals signals = fixture.latestSignals();
		assertNotNull(signals);
		// canselListIds
		List<Long> cancelListIds = signals.getCancelListIds();
		assertEquals(2, cancelListIds.size());
		assertEquals(new Long(111L), cancelListIds.get(0));
		assertEquals(new Long(222L), cancelListIds.get(1));
		// canselListPermIds
		List<String> canselListPermIds = signals.getCancelListPermIds();
		assertEquals(2, canselListPermIds.size());
		assertEquals("AAA", canselListPermIds.get(0));
		assertEquals("BBB", canselListPermIds.get(1));
		// signals
		List<Signal> sSignals = signals.getSignals();
		assertEquals(4, sSignals.size());
		testSignal(sSignals);
		// fillInfoReceived
		List<FillAcknowledgment> fillInfoReceived = signals
				.getFillInfoReceived();
		assertEquals(2, fillInfoReceived.size());
		testFillInfoReceived(fillInfoReceived);
		// resentC2Fills
		List<C2RecentFill> recentC2Fills = signals.getResentC2Fills();
		assertEquals(2, recentC2Fills.size());
		testRecentC2Fills(recentC2Fills);
		// completedTradesSigId
		List<Long> completedTradesSigId = signals.getCompletedTradesSigId();
		assertEquals(2, completedTradesSigId.size());
		assertEquals(new Long(12001), completedTradesSigId.get(0));
		assertEquals(new Long(12004), completedTradesSigId.get(1));
		// completedTradesSigPerId
		List<String> completedTradesSigPerId = signals
				.getCompletedTradesSigPerId();
		assertEquals(2, completedTradesSigPerId.size());
		assertEquals("abcd", completedTradesSigPerId.get(0));
		assertEquals("efgh", completedTradesSigPerId.get(1));
	}

	private void testRecentC2Fills(List<C2RecentFill> recentC2Fills) {
		C2RecentFill recentC2Fill = recentC2Fills.get(0);
		assertEquals(333, recentC2Fill.getSignalId());
		assertEquals("CCC", recentC2Fill.getPermId());
		assertEquals(83073, recentC2Fill.getFilledAgo());
		assertEquals(36.93, recentC2Fill.getFilledPrice());
		recentC2Fill = recentC2Fills.get(1);
		assertEquals(444, recentC2Fill.getSignalId());
		assertEquals("DDD", recentC2Fill.getPermId());
		assertEquals(83072, recentC2Fill.getFilledAgo());
		assertEquals(11.11, recentC2Fill.getFilledPrice());
	}

	private void testFillInfoReceived(List<FillAcknowledgment> fillInfoReceived) {
		FillAcknowledgment fillAcknowledment = fillInfoReceived.get(0);
		assertEquals(1234, fillAcknowledment.getSigId());
		assertEquals("aaa-bbb-ccc", fillAcknowledment.getPermId());
		assertEquals(10, fillAcknowledment.getTotalQuant());
		fillAcknowledment = fillInfoReceived.get(1);
		assertEquals(222, fillAcknowledment.getSigId());
		assertEquals("bbb", fillAcknowledment.getPermId());
		assertEquals(20, fillAcknowledment.getTotalQuant());

	}

	private void testSignal(List<Signal> signals) {
		Signal signal = signals.get(0);
		assertEquals("XXX", signal.getSystemName());
		assertEquals(2272, signal.getSystemIdNum());
		assertEquals(267066, signal.getSignalid());
		assertEquals(1181568629, signal.getPostedWhen());
		assertEquals("2007-06-11 09:30:29", signal.getPostedHumanTime());
		assertEquals(ActionEnum.SSHORT, signal.getAction());
		assertEquals(33, signal.getScaledQuant());
		assertEquals(320, signal.getOriginalQuant());
		assertEquals("IBM", signal.getSymbol());
		assertEquals(AssetEnum.stock, signal.getAssetType());
		assertEquals(false, signal.isMutualFund());
		assertEquals(OrderEnum.STOP, signal.getOrderType());
		assertEquals(30.66, signal.getStop());
		assertEquals(0.0, signal.getLimit());
		assertEquals(DurationEnum.DAY, signal.getTif());
		assertEquals("", signal.getUnderlying());
		assertEquals("", signal.getRight());
		assertEquals("0", signal.getStrike());
		assertEquals("", signal.getExpir());
		assertEquals("", signal.getExchange());
		assertEquals("", signal.getMarketcode());
		assertEquals("", signal.getOcagroup());
		assertEquals("", signal.getConditionalUpon().getTextContent());
		assertEquals("", signal.getCommentary());
		Long[] matchingOpenSigsSigId = signal.getMatchingOpenSigsSigId();
		assertEquals(2, matchingOpenSigsSigId.length);
		assertEquals(new Long(1234L), matchingOpenSigsSigId[0]);
		assertEquals(new Long(5678L), matchingOpenSigsSigId[1]);
		String[] matchingOpenSigsPermId = signal.getMatchingOpenSigsPermId();
		assertEquals(2, matchingOpenSigsPermId.length);
		assertEquals("abcd-efg", matchingOpenSigsPermId[0]);
		assertEquals("zxy-ytr", matchingOpenSigsPermId[1]);
		signal = signals.get(1);
		assertEquals(ActionEnum.BTO, signal.getAction());
		signal = signals.get(3);
		assertEquals(26867, signal.getSignalid());
		assertEquals(DurationEnum.GTC, signal.getTif());
		assertEquals(OrderEnum.LIMIT, signal.getOrderType());
		assertEquals(0.0, signal.getStop());
		assertEquals(36.56, signal.getLimit());
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
