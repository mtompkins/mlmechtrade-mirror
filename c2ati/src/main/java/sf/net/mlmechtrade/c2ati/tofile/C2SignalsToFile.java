package sf.net.mlmechtrade.c2ati.tofile;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPathExpressionException;

import org.apache.commons.httpclient.HttpException;
import org.apache.log4j.Logger;
import org.xml.sax.SAXException;

import sf.net.mlmechtrade.c2ati.C2ATI;
import sf.net.mlmechtrade.c2ati.C2ATIAPI;
import sf.net.mlmechtrade.c2ati.C2ATIError;
import sf.net.mlmechtrade.c2ati.C2ATIMockImpl;
import sf.net.mlmechtrade.c2ati.domain.ActionEnum;
import sf.net.mlmechtrade.c2ati.domain.C2RecentFill;
import sf.net.mlmechtrade.c2ati.domain.LatestSignals;
import sf.net.mlmechtrade.c2ati.domain.OrderEnum;
import sf.net.mlmechtrade.c2ati.domain.Signal;
import sf.net.mlmechtrade.c2ati.util.DOMHelper;

public class C2SignalsToFile {
	private static final String ROW_FORMAT = "%s\t%d\t%s\t%4.4f\n";

	private static Logger log = Logger
			.getLogger("sf.net.mlmechtrade.c2ati.tofile.C2SignalsToFile");

	private static final String FILE_SUFFIX = ".txt";

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat(
			"yyyyMMdd");

	private static final SimpleDateFormat timeFormat = new SimpleDateFormat(
			"yyyyMMdd_HHmmss");

	/**
	 * @param args[0]
	 *            Location
	 */
	public static void main(String[] args) {
		if (args.length <= 1) {
			log
					.fatal("USAGE: java sf.net.mlmechtrade.c2ati.tofile.C2SignalsToFile <OutputDirectory> <SystemName> [<Real Connection Y/N>]");
			System.exit(1);
		}

		// Create Output
		String outputDirectory = args[0];
		String systemName = args[1];
		boolean isReal = (args.length == 3) && args[2].equalsIgnoreCase("Y");

		C2SignalsToFile fileCreator = new C2SignalsToFile(outputDirectory,
				systemName, isReal);
		try {
			fileCreator.execute();
		} catch (Exception e) {
			String errorMessage = "System error ";
			log.fatal(errorMessage, e);
			System.exit(1);
		}
	}

	private String outputDirectory;

	private boolean isReal;

	private String systemName;

	public C2SignalsToFile(String outputDirectory, String systemName,
			boolean isReal) {
		this.outputDirectory = outputDirectory;
		this.systemName = systemName;
		this.isReal = isReal;
	}

	public void execute() throws XPathExpressionException, IOException,
			ParserConfigurationException, SAXException, C2ATIError,
			TransformerException {

		// Get Parameters
		InputStream is = getClass().getClassLoader().getResourceAsStream(
				"login.properties");
		Properties prop = new Properties();
		try {
			if (is != null) {
				prop.load(is);
				is.close();
			}
		} catch (Exception e) {
			String errorMessage = "Unable loading C2ATI file login.properties. "
					+ "File must be in your in CLASSPATH";
			log.fatal(errorMessage, e);
			System.exit(1);
		}
		String eMail = prop.getProperty("eMail", "email@company.com");
		String password = prop.getProperty("password", "password");
		String host = prop.getProperty("host", "host");
		String isLifeAccountStr = prop.getProperty("liveType", "0");

		// C2ATI API object
		C2ATIAPI c2ati;

		boolean isLifeAccount = isLifeAccountStr.equals("1");
		if (isReal) {
			c2ati = new C2ATI(eMail, password, isLifeAccount, host);
		} else {
			c2ati = new C2ATIMockImpl();
		}

		// Login
		c2ati.login();

		// Get latest signals
		int count = 0;
		while (getSignals(c2ati)) {
			if (!isReal && count++ >= 3)
				break;
		}

		// Log off
		c2ati.logOff();
	}

	private boolean getSignals(C2ATIAPI c2ati) throws IOException,
			ParserConfigurationException, SAXException,
			XPathExpressionException, C2ATIError, HttpException,
			TransformerException {
		LatestSignals latestSignls = getLatestSignals(c2ati);

		// Store Response to file
		String responseAsXML = DOMHelper.dom2XML(latestSignls.getResponse());
		toFile(responseAsXML, OutputFileTypeEnum.RESPONSE, ".xml");

		confirmRecentc2fill(latestSignls.getResentC2Fills(), c2ati);
		
		String cancelStr = outputCancel(latestSignls.getCancelListIds(), c2ati);
		toFile(cancelStr, OutputFileTypeEnum.CANCEL);

		List<Signal> signals = latestSignls.getSignals();

		// Create BUY Output
		String buyStr = outputBuy(signals, c2ati);
		toFile(buyStr, OutputFileTypeEnum.BUY);

		// Create SELL Output
		String sellStr = outputSell(signals, c2ati);
		toFile(sellStr, OutputFileTypeEnum.SELL);

		return cancelStr.length() + buyStr.length() + sellStr.length() > 0;
	}

	private void confirmRecentc2fill(List<C2RecentFill> resentC2Fills, C2ATIAPI c2ati) throws HttpException, XPathExpressionException, IOException, ParserConfigurationException, SAXException, C2ATIError {
		for (C2RecentFill fill : resentC2Fills) {
			c2ati.ackc2Fill(fill.getSignalId());
		}
		
	}

	private void toFile(String str, OutputFileTypeEnum fileType, String suffix)
			throws IOException {
		// Prepare Files
		if (str.length() > 0) {
			Date currDate = new Date();

			// Time stamp
			String currDateStr = dateFormat.format(currDate);
			if (OutputFileTypeEnum.BUY == fileType
					|| OutputFileTypeEnum.SELL == fileType) {
				currDateStr = dateFormat.format(currDate);
			} else if (OutputFileTypeEnum.CANCEL == fileType
					|| OutputFileTypeEnum.RESPONSE == fileType) {
				currDateStr = timeFormat.format(currDate);
			}

			String fileName = currDateStr + '-' + fileType.toString() + suffix;
			File outputFile = new File(outputDirectory, fileName);
			FileWriter fw = new FileWriter(outputFile, true);
			fw.write(str);
			fw.close();
		}
	}

	private void toFile(String str, OutputFileTypeEnum fileType)
			throws IOException {
		toFile(str, fileType, FILE_SUFFIX);
	}

	public String outputCancel(List<Long> cancelSignals, C2ATIAPI c2ati)
			throws HttpException, XPathExpressionException, IOException,
			ParserConfigurationException, SAXException, C2ATIError {
		StringBuilder sb = new StringBuilder();
		for (Long sigId : cancelSignals) {
			sb.append(sigId).append("\n");
			c2ati.cancelConfirmSigId(sigId);
		}
		return sb.toString();
	}

	private LatestSignals getLatestSignals(C2ATIAPI c2ati) throws IOException,
			ParserConfigurationException, SAXException,
			XPathExpressionException, C2ATIError, HttpException {
		// Filter Out signals
		LatestSignals response = c2ati.latestSignals();
		List<Signal> signals = response.getSignals();
		// Filter Out only latest signals
		signals = currentSytemSignals(signals);
		response.setSignals(signals);
		return response;

	}

	public String outputSell(List<Signal> signals, C2ATIAPI c2ati)
			throws HttpException, XPathExpressionException, IOException,
			ParserConfigurationException, SAXException, C2ATIError {
		return output(signals, OutputFileTypeEnum.SELL, c2ati);
	}

	public String outputBuy(List<Signal> signals, C2ATIAPI c2ati)
			throws HttpException, XPathExpressionException, IOException,
			ParserConfigurationException, SAXException, C2ATIError {
		return output(signals, OutputFileTypeEnum.BUY, c2ati);
	}

	private String output(List<Signal> signals, OutputFileTypeEnum fileType,
			C2ATIAPI c2ati) throws HttpException, XPathExpressionException,
			IOException, ParserConfigurationException, SAXException, C2ATIError {
		StringBuilder sb = new StringBuilder();
		// Formatter formatter = new Formatter();

		for (Signal signal : signals) {
			ActionEnum action = signal.getAction();
			OrderEnum orderType = signal.getOrderType();
			double limit = signal.getLimit();
			double stop = signal.getStop();
			String symbol = signal.getSymbol();
			int quantity = signal.getScaledQuant();
			String resulLine = "";
			if (((action == ActionEnum.BTO || action == ActionEnum.BTC) && fileType == OutputFileTypeEnum.BUY)
					|| ((action == ActionEnum.STC
							|| action == ActionEnum.SSHORT || action == ActionEnum.STO) && fileType == OutputFileTypeEnum.SELL)) {
				if (orderType == OrderEnum.LIMIT) {
					resulLine = String.format(ROW_FORMAT, symbol, quantity,
							"Limit", limit);
				} else if (orderType == OrderEnum.MARKET) {
					resulLine = String.format(ROW_FORMAT, symbol, quantity,
							"Market", limit);
				} else if (orderType == OrderEnum.STOP) {
					resulLine = String.format(ROW_FORMAT, symbol, quantity,
							"Stop", stop);
				}
				c2ati.confirmSig(quantity, signal.getSignalid(), null);
			}
			sb.append(resulLine);
		}
		return sb.toString();
	}

	public List<Signal> currentSytemSignals(List<Signal> signals) {
		List<Signal> result = new ArrayList<Signal>();
		for (Signal signal : signals) {
			if (signal.getSystemName().equalsIgnoreCase(systemName)) {
				result.add(signal);
			}
		}
		return result;
	}

}
