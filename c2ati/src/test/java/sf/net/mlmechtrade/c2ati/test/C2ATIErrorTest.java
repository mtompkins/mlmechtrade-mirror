package sf.net.mlmechtrade.c2ati.test;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import junit.framework.TestCase;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import sf.net.mlmechtrade.c2ati.C2ATIError;
import sf.net.mlmechtrade.c2ati.util.DOMHelper;

public class C2ATIErrorTest extends TestCase {

	public void setUp() throws ParserConfigurationException, SAXException,
			IOException {

	}

	// @SuppressWarnings("deprecation")
	public void testC2ATIError() throws ParserConfigurationException,
			SAXException, IOException, XPathExpressionException {
		Document document = docINCORRECTEMAILPW();
		C2ATIError err = new C2ATIError(document);
		assertNotNull(err);
		assertEquals(C2ATIError.ErrorCode.INCORRECTEMAILPW, err.getErrorCode());
		assertEquals("", err.getUrl());
		assertEquals("INCORRECTEMAILPW", err.toString());
	}

	@SuppressWarnings("deprecation")
	private Document docINCORRECTEMAILPW() throws ParserConfigurationException,
			SAXException, IOException {
		String doc = "<collective2>" + "<status>error</status>"
				+ "<error><code>INCORRECTEMAILPW</code></error><data>"
				+ "</data></collective2>";
		return DOMHelper.parse(doc);
	}

}
