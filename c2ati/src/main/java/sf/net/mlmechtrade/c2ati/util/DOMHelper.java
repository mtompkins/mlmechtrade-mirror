package sf.net.mlmechtrade.c2ati.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class DOMHelper {
	@SuppressWarnings("deprecation")
	public static Document parse(String xmlString)
			throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilder parser = DocumentBuilderFactory.newInstance()
				.newDocumentBuilder();

		InputStream stream = new java.io.StringBufferInputStream(xmlString);
		return parser.parse(stream);
	}

	public static Document parse(InputStream is)
			throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilder parser = DocumentBuilderFactory.newInstance()
				.newDocumentBuilder();
		return parser.parse(is);
	}

	public static String dom2XML(Document doc) throws TransformerException {
		DOMSource domSource = new DOMSource(doc);

		// Create a string writer
		StringWriter stringWriter = new StringWriter();

		// Create the result stream for the transform
		StreamResult result = new StreamResult(stringWriter);

		// Create a Transformer to serialize the document
		TransformerFactory tFactory = TransformerFactory.newInstance();

		Transformer transformer = tFactory.newTransformer();
		transformer.setOutputProperty("indent", "yes");

		// Transform the document to the result stream
		transformer.transform(domSource, result);

		return stringWriter.toString();
	}
}
