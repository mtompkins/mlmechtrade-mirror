package sf.net.mlmechtrade.c2ati.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.xml.sax.SAXException;

import sf.net.mlmechtrade.c2ati.C2ATI;
import sf.net.mlmechtrade.c2ati.C2ATIError;
import sf.net.mlmechtrade.c2ati.domain.FillConfirm;
import sf.net.mlmechtrade.c2ati.domain.MultFillConfirmEnum;
import junit.framework.TestCase;

public class c2ATIACKTest extends TestCase {
	C2ATI fixture;

	public void setUp() throws IOException {
		fixture = new C2ATIMockImpl();
	}

	public void testConfirmSig() throws XPathExpressionException, IOException,
			ParserConfigurationException, SAXException, C2ATIError {
		fixture.login();
		fixture.confirmSig(1, 1111, "abc");
		fixture.cancelConfirmSigId(1234);
		fixture.logOff();
	}

	public void testMult2FillConfirm() throws XPathExpressionException,
			IOException, ParserConfigurationException, SAXException, C2ATIError {
		fixture.login();
		List<FillConfirm> fillConfirmList = new ArrayList<FillConfirm>();
		FillConfirm fillConfirm = new FillConfirm();
		fillConfirmList.add(fillConfirm);
		fillConfirm.setBrokerageExecutionId("aaaa");
		fillConfirm.setFillPrice(12.05);
		fillConfirm.setPermId("122");
		fillConfirm.setQuantity(20);
		fillConfirm.setSigId(4444444);
		FillConfirm fillConfirm2 = new FillConfirm();
		fillConfirmList.add(fillConfirm2);
		fillConfirm2.setBrokerageExecutionId("bbb");
		fillConfirm2.setFillPrice(23.16);
		fillConfirm2.setPermId("443");
		fillConfirm2.setQuantity(35);
		fillConfirm2.setSigId(555555);
		String expected = "http://64.68.145.33:7878?cmd=mult2fillconfirm&session=267127353762716967&h=host&filldata=4444444|sep|20|sep|aaaa|sep|12.05|sep|555555|sep|35|sep|bbb|sep|23.16|&live=0";
		String actual = fixture.multFillConfirmCommandString(fillConfirmList,
				MultFillConfirmEnum.mult2fillconfirm);
		assertEquals(expected, actual);
		fixture.multFillConfirm(fillConfirmList,
				MultFillConfirmEnum.mult2fillconfirm);
		expected = "http://64.68.145.33:7878?cmd=mult3fillconfirm&session=267127353762716967&h=host&filldata=122|sep|20|sep|aaaa|sep|12.05|sep|443|sep|35|sep|bbb|sep|23.16|&live=0";
		actual = fixture.multFillConfirmCommandString(fillConfirmList,
				MultFillConfirmEnum.mult3fillconfirm);
		assertEquals(expected, actual);
		fixture.multFillConfirm(fillConfirmList,
				MultFillConfirmEnum.mult3fillconfirm);
		fixture.multFillConfirm(fillConfirmList,
				MultFillConfirmEnum.multfillconfirmcumu);
		expected = "http://64.68.145.33:7878?cmd=multfillconfirmcumu&session=267127353762716967&h=host&filldata=122|sep|20|sep|aaaa|sep|12.05|sep|443|sep|35|sep|bbb|sep|23.16|&live=0";
		actual = fixture.multFillConfirmCommandString(fillConfirmList,
				MultFillConfirmEnum.multfillconfirmcumu);
		assertEquals(expected, actual);
		fixture.multFillConfirm(fillConfirmList,
				MultFillConfirmEnum.multfillconfirmcumu);
		fixture.logOff();
	}
	//public void testRecentc2fill() throws XPathExpressionException,
	// IOException, ParserConfigurationException, SAXException, C2ATIError {
//fixture.login();
//fixture.
//fixture.logOff();
//}
	public void testCompleteTrades() throws XPathExpressionException,
			IOException, ParserConfigurationException, SAXException, C2ATIError {
		fixture.login();
		fixture.ackComplete(5555);
		fixture.logOff();
	}
}