package sf.net.mlmechtrade.c2ati;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.httpclient.HttpException;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import sf.net.mlmechtrade.c2ati.C2ATI;
import sf.net.mlmechtrade.c2ati.util.DOMHelper;

public class C2ATIMockImpl extends C2ATI {
	public C2ATIMockImpl() {
		super("C2 eMail", "C2 passwor", false, "host");
	}

	protected Document getResponse(String request) throws IOException,
			HttpException, ParserConfigurationException, SAXException {
		return getResponse(request, true);
	}

	protected Document getResponse(String request, boolean isPoolCommand)
			throws IOException, HttpException, ParserConfigurationException,
			SAXException {
		String command = TestUtil.getCmd(request);
		InputStream is = getClass().getClassLoader().getResourceAsStream(
				"test/" + command + ".xml");
		return DOMHelper.parse(is);
	}
}
