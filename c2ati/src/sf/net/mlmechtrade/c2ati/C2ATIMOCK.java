package sf.net.mlmechtrade.c2ati;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.httpclient.HttpException;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class C2ATIMOCK extends C2ATI {

	public C2ATIMOCK(String eMail, String password, boolean liveType,
			String host) {
		super(eMail, password, liveType, host);
	}

	protected Document getResponse(String request) throws IOException,
			HttpException, ParserConfigurationException, SAXException {
		return null;
	}

}
