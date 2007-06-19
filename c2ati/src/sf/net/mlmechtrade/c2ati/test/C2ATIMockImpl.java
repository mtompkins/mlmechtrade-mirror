package sf.net.mlmechtrade.c2ati.test;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.httpclient.HttpException;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import sf.net.mlmechtrade.c2ati.C2ATI;

public class C2ATIMockImpl extends C2ATI {
	public C2ATIMockImpl() {
		super("C2 eMail", "C2 passwor", false, "host");
	}

	protected Document getResponse(String request) throws IOException,
			HttpException, ParserConfigurationException, SAXException {
		return null;
	}

	protected Document getResponse(String request, boolean isPoolCommand)
			throws IOException, HttpException, ParserConfigurationException,
			SAXException {
		return null;
	}
	
}
