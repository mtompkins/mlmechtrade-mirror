package sf.net.mlmechtrade.c2ati.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringBufferInputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class DOMHelper {
	@SuppressWarnings("deprecation")
	public static Document parse(String xmlString)
			throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilder parser = DocumentBuilderFactory.newInstance()
				.newDocumentBuilder();

		InputStream stream = new StringBufferInputStream(xmlString);
		return parser.parse(stream);
	}

	public static Document parse(InputStream is)
			throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilder parser = DocumentBuilderFactory.newInstance()
				.newDocumentBuilder();
		return parser.parse(is);
	}
}
