package sf.net.mlmechtrade.c2ati;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;

public class C2ATIError extends Exception {
	private static final long serialVersionUID = -3904409967423414808L;

	public C2ATIError(Document errorResponse) throws XPathExpressionException {
		XPath xpath = XPathFactory.newInstance().newXPath();
		String infoURL;
		infoURL = xpath.evaluate("/collective2/error/url", errorResponse);
		String errorLiteral;
		errorLiteral = xpath.evaluate("/collective2/error/code", errorResponse);
		storeState(errorLiteral, infoURL);
	}

	private void storeState(String errorLiteral, String infoURL) {
		this.errorCode = ErrorCode.errorCodeFromLiteral(errorLiteral);
		this.url = infoURL;
	}

	public String toString() {
		String rez = errorCode.getStrCode();
		if (url != null && url.length() > 0) {
			rez += " For more information " + url;
		}
		return rez;
	}

	public enum ErrorCode {
		SESSION_TIMED_OUT("SESSION TIMED OUT"), MISSINGORINCORRECTPARAMS(
				"MISSINGORINCORRECTPARAMS"), INCORRECTEMAILPW(
				"INCORRECTEMAILPW"), OLDCLIENT("OLDCLIENT"), NOSUBSCRIPTIONS(
				"NOSUBSCRIPTIONS"), NEEDAGMT("NEEDAGMT");

		private String strCode;

		ErrorCode(String strCode) {
			this.strCode = strCode;
		}

		public static ErrorCode errorCodeFromLiteral(String strCode) {
			if (strCode.equals(ErrorCode.SESSION_TIMED_OUT.getStrCode())) {
				return ErrorCode.SESSION_TIMED_OUT;
			}
			return valueOf(strCode);
		}

		public String getStrCode() {
			return strCode;
		}
	}

	private ErrorCode errorCode;

	private String url;

	public ErrorCode getErrorCode() {
		return errorCode;
	}

	public String getUrl() {
		return url;
	}

}
