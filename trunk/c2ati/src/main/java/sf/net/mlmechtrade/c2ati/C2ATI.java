package sf.net.mlmechtrade.c2ati;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import sf.net.mlmechtrade.c2ati.domain.ActionEnum;
import sf.net.mlmechtrade.c2ati.domain.AssetPermition;
import sf.net.mlmechtrade.c2ati.domain.AssetEnum;
import sf.net.mlmechtrade.c2ati.domain.C2Position;
import sf.net.mlmechtrade.c2ati.domain.C2RecentFill;
import sf.net.mlmechtrade.c2ati.domain.C2SystemState;
import sf.net.mlmechtrade.c2ati.domain.DurationEnum;
import sf.net.mlmechtrade.c2ati.domain.FillAcknowledgment;
import sf.net.mlmechtrade.c2ati.domain.FillConfirm;
import sf.net.mlmechtrade.c2ati.domain.LatestSignals;
import sf.net.mlmechtrade.c2ati.domain.MultFillConfirmEnum;
import sf.net.mlmechtrade.c2ati.domain.OrderEnum;
import sf.net.mlmechtrade.c2ati.domain.Signal;
import sf.net.mlmechtrade.c2ati.domain.TradingSystem;
import sf.net.mlmechtrade.c2ati.util.DOMHelper;

import com.sun.org.apache.xml.internal.dtm.ref.DTMNodeList;

public class C2ATI {
	Logger log = Logger.getLogger(C2ATI.class);

	public static final String PROTO_VERSION = "8.2";

	public static final String CLIENT = "TESTCLIENT";

	public static final String BUILD = "0.00.00";

	public static final String INIT_SERVER_HOST = "64.68.145.3";

	public static final String INIT_SERVER_PORT = "7878";

	private String MULT_FILL_CONFIRM_SEPARATOR = "|sep|";

	private String serverIPAddress;

	private String serverPort;

	private String eMail;

	private String password;

	private String host = "host";

	private String sessionId;

	private long pollInterval = 3000;

	private long serverTime = 0;

	private String postedHumanTime;

	private long clientTime = 0;

	private boolean lifeType = false;

	private XPath xPath;

	private HttpClient client;

	private boolean theFirstPool = true;

	public C2ATI(String eMail, String password, boolean liveType, String host) {
		this.eMail = eMail;
		this.password = password;
		this.lifeType = liveType;
		if (host != null) {
			this.host = host;
		}
		xPath = XPathFactory.newInstance().newXPath();
		client = new HttpClient();
		client.getHttpConnectionManager().getParams().setConnectionTimeout(
				10000);
	}

	public synchronized void login() throws IOException, ParserConfigurationException,
			SAXException, XPathExpressionException, C2ATIError {
		String requestTemplate = "http://%s:%s?cmd=login&e=%s&p=%s&protoversion=%s&client=%s&h=%s&build=%s";
		String request = String.format(requestTemplate, INIT_SERVER_HOST,
				INIT_SERVER_PORT, eMail, password, PROTO_VERSION, CLIENT, host,
				BUILD);

		log.info("LOGIN REQUEST : " + request);

		Document response = getResponse(request, false);

		// Check error
		checkError(response);

		// Parse response
		this.sessionId = xPath.evaluate("/collective2/data/session", response);

		this.serverIPAddress = xPath.evaluate("/collective2/data/redirectip",
				response);

		this.serverPort = xPath.evaluate("/collective2/data/redirectport",
				response);

		sinchronizeServerState(response);

		if (log.isDebugEnabled()) {
			log.debug("Session ID = " + this.sessionId);
			log.debug("Redirect IP = " + this.serverIPAddress);
			log.debug("Redirect Port = " + this.serverPort);
			log.debug("Poll Interval = " + this.pollInterval);
			log.debug("Server time = " + postedHumanTime);
		}
		log.info("LOGIN REQUEST : OK!");
	}

	public synchronized void logOff() throws ParserConfigurationException, SAXException,
			IOException, XPathExpressionException, C2ATIError {
		String requestTemplate = "http://%s:%s?cmd=logoff&session=%s&h=%s";
		String request = String.format(requestTemplate, this.serverIPAddress,
				this.serverPort, this.sessionId, this.host);
		processRequest(request, "logoff");
	}

	public synchronized LatestSignals latestSignals() throws HttpException, IOException,
			ParserConfigurationException, SAXException,
			XPathExpressionException, C2ATIError {
		// Prepare template
		String requestTemplate = "http://%s:%s?cmd=latestsigs&session=%s&h=%s";
		String request = String.format(requestTemplate, this.serverIPAddress,
				this.serverPort, this.sessionId, this.host);
		// Invoke
		log.info("LATESTSIGS REQUEST : " + request);
		Document response = getResponse(request);

		// Check error
		checkError(response);

		LatestSignals result = new LatestSignals();

		// Cancel List
		DTMNodeList canselListIds = (DTMNodeList) xPath
				.evaluate("//cancelsiglist/cancelsigid", response,
						XPathConstants.NODESET);
		for (int i = 0; i < canselListIds.getLength(); i++) {
			String canselSigId = canselListIds.item(i).getTextContent();
			result.getCanselListIds().add(Long.parseLong(canselSigId));
		}
		DTMNodeList canselListPermIds = (DTMNodeList) xPath.evaluate(
				"//cancelsiglist/cancelpermid", response,
				XPathConstants.NODESET);
		for (int i = 0; i < canselListPermIds.getLength(); i++) {
			String cancelPermId = canselListPermIds.item(i).getTextContent();
			result.getCanselListPermIds().add(cancelPermId);
		}
		if (log.isDebugEnabled()) {
			log.debug("Cansel List Signal IDs: " + result.getCanselListIds());
			log.debug("Cansel List Pemanent IDs: "
					+ result.getCanselListPermIds());
		}

		// Signals
		DTMNodeList singnals = (DTMNodeList) xPath.evaluate("//signalpacket",
				response, XPathConstants.NODESET);
		for (int i = 0; i < singnals.getLength(); i++) {
			Node node = singnals.item(i);
			addSignalToResult(result, node);
		}

		// Fill Acnowlegment
		DTMNodeList fillACK = (DTMNodeList) xPath.evaluate(
				"//fillinforeceived/fillack", response, XPathConstants.NODESET);
		for (int i = 0; i < fillACK.getLength(); i++) {
			Node node = fillACK.item(i);
			addFillACKToResult(result, node);
		}

		// Recent C2 Fills
		DTMNodeList recentC2Fills = (DTMNodeList) xPath.evaluate(
				"//recentc2fills/recent", response, XPathConstants.NODESET);
		for (int i = 0; i < recentC2Fills.getLength(); i++) {
			Node node = recentC2Fills.item(i);
			addRecentC2FillsToResult(result, node);
		}

		// Complete trades sigid
		DTMNodeList completeTradesSigIds = (DTMNodeList) xPath.evaluate(
				"//completetrades/sigid", response, XPathConstants.NODESET);
		for (int i = 0; i < completeTradesSigIds.getLength(); i++) {
			Node node = completeTradesSigIds.item(i);
			String sigIdStr = node.getTextContent();
			result.getCompletedTradesSigId().add(Long.parseLong(sigIdStr));
		}
		// Complete trades permid
		DTMNodeList completeTradesPermIds = (DTMNodeList) xPath.evaluate(
				"//completetrades/permid", response, XPathConstants.NODESET);
		for (int i = 0; i < completeTradesPermIds.getLength(); i++) {
			Node node = completeTradesPermIds.item(i);
			String permId = node.getTextContent();
			result.getCompletedTradesSigPerId().add(permId);
		}
		if (log.isDebugEnabled()) {
			log.debug("Completed trades sigids: "
					+ result.getCompletedTradesSigId());
			log.debug("Completed trades perm ids: "
					+ result.getCompletedTradesSigPerId());
		}

		// Server time and pool interval updates
		sinchronizeServerState(response);
		log.info("LATESTSIGS OK!");
		return result;
	}

	public synchronized void confirmSig(long quantity, long sigId, String permId)
			throws HttpException, IOException, ParserConfigurationException,
			SAXException, XPathExpressionException, C2ATIError {
		String requestTemplate = "http://%s:%s?cmd=confirmsig&sigid=%s&session=%s&h=%s&quant=%s";
		String request = String.format(requestTemplate, this.serverIPAddress,
				this.serverPort, sigId, this.sessionId, this.host, quantity);
		if (permId != null) {
			request += "&permid=" + permId;
		}
		processRequest(request, "confirmsig");
	}

	public synchronized void cancelConfirmSigId(long sigId) throws HttpException,
			IOException, ParserConfigurationException, SAXException,
			XPathExpressionException, C2ATIError {
		String requestTemplate = "http://%s:%s?cmd=cancelconfirm&sigid=%s&session=%s&h=%s";
		String request = String.format(requestTemplate, this.serverIPAddress,
				this.serverPort, sigId, this.sessionId, this.host);
		processRequest(request, "cancelconfirm");
	}

	public synchronized void cancelConfirmPermId(String permId) throws HttpException,
			IOException, ParserConfigurationException, SAXException,
			XPathExpressionException, C2ATIError {
		String requestTemplate = "http://%s:%s?cmd=cancelconfirm&permid=%s&session=%s&h=%s";
		String request = String.format(requestTemplate, this.serverIPAddress,
				this.serverPort, permId, this.sessionId, this.host);
		processRequest(request, "cancelconfirm");
	}

	public synchronized void multFillConfirm(List<FillConfirm> fillConfirmList,
			MultFillConfirmEnum type) throws HttpException,
			XPathExpressionException, IOException,
			ParserConfigurationException, SAXException, C2ATIError {
		String request = multFillConfirmCommandString(fillConfirmList, type);
		processRequest(request, type.toString());
	}

	public synchronized String multFillConfirmCommandString(
			List<FillConfirm> fillConfirmList, MultFillConfirmEnum type) {
		// Template
		String requestTemplate = "http://%s:%s?cmd=" + type
				+ "&session=%s&h=%s";
		String request = String.format(requestTemplate, this.serverIPAddress,
				this.serverPort, this.sessionId, this.host);

		// Append fill data
		request += getFillDataString(fillConfirmList, type);

		// Live parameter
		request += "&live=" + (this.lifeType ? "1" : "0");
		return request;
	}

	public synchronized void multFillConfirmSigId(long sigId) throws HttpException,
			XPathExpressionException, IOException,
			ParserConfigurationException, SAXException, C2ATIError {
		multFillConfirm(sigId);
	}

	public synchronized void multFillConfirmPermId(String permId) throws HttpException,
			XPathExpressionException, IOException,
			ParserConfigurationException, SAXException, C2ATIError {
		multFillConfirm(permId);
	}

	public synchronized void ackComplete(long sigId) throws HttpException, IOException,
			ParserConfigurationException, SAXException,
			XPathExpressionException, C2ATIError {
		String requestTemplate = "http://%s:%s?cmd=ackcomplete&sigid=%s&session=%s&h=%s";
		String request = String.format(requestTemplate, this.serverIPAddress,
				this.serverPort, sigId, this.sessionId, this.host);

		processRequest(request, "ackcomplete");
	}

	public synchronized void ackc2Fill(String permId) throws HttpException,
			XPathExpressionException, IOException,
			ParserConfigurationException, SAXException, C2ATIError {
		ack2Fill(permId);
	}

	public synchronized void ackc2Fill(long sigId) throws HttpException,
			XPathExpressionException, IOException,
			ParserConfigurationException, SAXException, C2ATIError {
		ack2Fill(sigId);
	}

	public synchronized List<TradingSystem> getAllSignals() throws HttpException,
			IOException, ParserConfigurationException, SAXException,
			XPathExpressionException, C2ATIError {
		String requestTemplate = "http://%s:%s?cmd=getallsignals&session=%s&h=%s";
		String request = String.format(requestTemplate, this.serverIPAddress,
				this.serverPort, this.sessionId, this.host);

		if (log.isInfoEnabled()) {
			log.info("GETALLSIGNALS " + request);
		}

		// Get response
		Document response = getResponse(request, false);
		// Check error
		checkError(response);
		// Return
		String systemXPath = "/collective2/data/allPendingSignals/system";
		DTMNodeList systems = (DTMNodeList) xPath.evaluate(systemXPath,
				response, XPathConstants.NODESET);
		List<TradingSystem> result = new ArrayList<TradingSystem>();
		for (int i = 0; i < systems.getLength(); i++) {
			TradingSystem tradingSystem = new TradingSystem();
			result.add(tradingSystem);
			Node system = systems.item(i);
			// sistemid
			tradingSystem.setSystemId(getLong("systemid", system));
			// Pending Block
			DTMNodeList signalIds = (DTMNodeList) xPath.evaluate(
					"pendingblock/signalid", system, XPathConstants.NODESET);
			for (int j = 0; j < signalIds.getLength(); j++) {
				// Node node = systems.item(j);
				// /collective2/data/allPendingSignals/system[1]/pendingblock/signalid
				String xPathStr = systemXPath + "[" + (i + 1)
						+ "]/pendingblock/signalid[" + (j + 1) + "]";
				long signalId = getLong(xPathStr, response);
				tradingSystem.getPendingBlock().add(signalId);
			}
		}
		log.debug(result);
		log.info("GETALLSIGNALS OK!");
		return result;
	}

	public synchronized List<TradingSystem> requestSystemList() throws HttpException,
			IOException, ParserConfigurationException, SAXException,
			XPathExpressionException, C2ATIError {
		// Store Pool Interval
		long tmpPoolInterval = this.pollInterval;
		this.pollInterval = 120 / 2 * 1000;
		// Template
		String requestTemplate = "http://%s:%s?cmd=ackcomplete&session=%s&h=%s";
		String request = String.format(requestTemplate, this.serverIPAddress,
				this.serverPort, this.sessionId, this.host);
		if (log.isInfoEnabled()) {
			log.info("ACKCOMPLETE " + request);
		}
		// Get response
		Document response = getResponse(request);
		// Error
		checkError(response);

		// Parse document
		DTMNodeList systems = (DTMNodeList) xPath.evaluate(
				"//systemList/system", response, XPathConstants.NODESET);
		// Result
		List<TradingSystem> result = new ArrayList<TradingSystem>();
		for (int i = 0; i < systems.getLength(); i++) {
			TradingSystem tradingSystem = new TradingSystem();
			result.add(tradingSystem);
			Node system = systems.item(i);
			String name = xPath.evaluate("//name", system);
			tradingSystem.setName(name);
			tradingSystem.setSystemId(getLong("systemid", system));
			DTMNodeList permitions = (DTMNodeList) xPath.evaluate(
					"//permissions/asset", system, XPathConstants.NODESET);
			for (int j = 0; j < permitions.getLength(); j++) {
				Node permition = systems.item(j);
				AssetPermition permitionRec = new AssetPermition();
				tradingSystem.getPermitions().add(permitionRec);
				// Asset Type
				String assetTypeStr = xPath.evaluate("//assettype", permition);
				AssetEnum assetType = AssetEnum.valueOf(assetTypeStr);
				permitionRec.setAssertType(assetType);
				// Long
				String longStr = xPath.evaluate("//long", permition);
				permitionRec.setLongPermitted(longStr.equals("1"));
				// Short
				String shortStr = xPath.evaluate("//short", permition);
				permitionRec.setShortPermittd(shortStr.endsWith("1"));
			}
		}
		// Restoro Pool Interval
		this.pollInterval = tmpPoolInterval;
		log.debug(result);
		log.info("ACKCOMPLETE OK!");
		return result;
	}

	public synchronized List<C2SystemState> requestSystemSync() throws HttpException,
			IOException, ParserConfigurationException, SAXException,
			XPathExpressionException, C2ATIError {
		List<C2SystemState> result = new ArrayList<C2SystemState>();
		String requestTemplate = "http://%s:%s?cmd=requestsystemsync&synctype=system&systemid=all&session=%s&h=%s";
		String request = String.format(requestTemplate, this.serverIPAddress,
				this.serverPort, this.sessionId, this.host);
		// Get response
		Document response = getResponse(request, false);
		if (log.isInfoEnabled()) {
			log.info("REQUESTSYSTEMSYNC " + request);
		}
		// Check error
		checkError(response);
		// Return
		DTMNodeList systems = (DTMNodeList) xPath.evaluate(
				"//data/sync/system", response, XPathConstants.NODESET);
		for (int i = 0; i < systems.getLength(); i++) {
			C2SystemState c2SystemState = new C2SystemState();
			result.add(c2SystemState);
			Node system = systems.item(i);
			// Sysem ID
			c2SystemState.setSystemId(getLong("systemid", system));
			// System Name
			String systemName = xPath.evaluate("systemname", system);
			c2SystemState.setSystemName(systemName);
			// Time Filter Secs
			c2SystemState.setTimeFilterSecs(getLong("timefiltersecs", system));
			// Time Filter Clock
			String timeFilterClock = xPath.evaluate(
					"timefilterclock/humantime", system);
			c2SystemState.setTimeFilterClock(timeFilterClock);
			// Positions
			String xPathStr = "/collective2/data/sync/system[" + (i + 1)
					+ "]/position";
			DTMNodeList positions = (DTMNodeList) xPath.evaluate(xPathStr,
					response, XPathConstants.NODESET);
			for (int j = 0; j < positions.getLength(); j++) {
				String xPathPrefix = xPathStr + "[" + (j + 1) + "]/";
				C2Position c2Postion = new C2Position();
				c2SystemState.getPositions().add(c2Postion);
				// Node positon = positions.item(i);
				// Symbol
				String symbol = xPath
						.evaluate(xPathPrefix + "symbol", response);
				c2Postion.setSymbol(symbol);
				// AssetType
				String assetTypeStr = xPath.evaluate(xPathPrefix + "type",
						response);
				c2Postion.setAssetType(AssetEnum.valueOf(assetTypeStr));
				// mutualfund
				String mutualFundStr = xPath.evaluate(xPathPrefix
						+ "mutualfund", response);
				c2Postion.setFund(mutualFundStr.equals("1"));
				// Quant
				c2Postion.setQuant(getLong(xPathPrefix + "quant", response));
				// Underlying
				String underlying = xPath.evaluate(xPathPrefix + "underlying",
						response);
				c2Postion.setUnderlying(underlying);
				// Right
				String right = xPath.evaluate(xPathPrefix + "right", response);
				c2Postion.setRight(right);
				// Strike
				String strike = xPath
						.evaluate(xPathPrefix + "strike", response);
				c2Postion.setStrike(strike);
				// Expir
				String expir = xPath.evaluate(xPathPrefix + "expir", response);
				c2Postion.setExpir(expir);
				// Exchange
				String exchange = xPath.evaluate(xPathPrefix + "exchange",
						response);
				c2Postion.setExchange(exchange);
				// Market Code
				String marketCode = xPath.evaluate(xPathPrefix + "marketcode",
						response);
				c2Postion.setMarketCode(marketCode);
			}
		}
		log.info("REQUESTSYSTEMSYNC OK!");
		return result;

	}

	private void ack2Fill(Object id) throws IOException, HttpException,
			ParserConfigurationException, SAXException,
			XPathExpressionException, C2ATIError {
		String request = ack2FillRequestString(id);
		processRequest(request, "ackc2fill");
	}

	private String ack2FillRequestString(Object id) {
		String param = (id instanceof String) ? "permid" : "sigid";
		String requestTemplate = "http://%s:%s?cmd=ackc2fill&" + param
				+ "=%s&session=%s&h=%s";
		String response = String.format(requestTemplate, this.serverIPAddress,
				this.serverPort, id, this.sessionId, this.host);
		return response;
	}

	private void multFillConfirm(Object parameter) throws HttpException,
			XPathExpressionException, IOException,
			ParserConfigurationException, SAXException, C2ATIError {
		// Template
		String requestTemplate = "http://%s:%s?cmd=ackc2fill&";
		requestTemplate += parameter instanceof String ? "permid=" : "sigid=";
		requestTemplate += parameter.toString();
		requestTemplate += "&session=%s&h=%s";
		String request = String.format(requestTemplate, this.serverIPAddress,
				this.serverPort, this.sessionId, this.host);
		// Process request
		processRequest(request, "ackc2fill");
	}

	private String getFillDataString(List<FillConfirm> fillConfirmList,
			MultFillConfirmEnum type) {
		if (fillConfirmList.size() == 0) {
			return "";
		}
		String result = "&filldata=";
		for (int i = 0; i < fillConfirmList.size(); i++) {
			FillConfirm confirm = fillConfirmList.get(i);
			// Signal ID or Permanent ID
			result += type == MultFillConfirmEnum.mult2fillconfirm ? confirm
					.getSigId() : confirm.getPermId();
			// Separator
			result += MULT_FILL_CONFIRM_SEPARATOR;
			// Quantity & Separator
			result += confirm.getQuantity() + MULT_FILL_CONFIRM_SEPARATOR;
			// ExecutionID & Separator
			result += confirm.getBrokerageExecutionId()
					+ MULT_FILL_CONFIRM_SEPARATOR;
			// Price Of Fill
			result += confirm.getFillPrice();
			if (i != fillConfirmList.size() - 1) {
				result += MULT_FILL_CONFIRM_SEPARATOR;
			} else {
				result += '|';
			}
		}
		return result;
	}

	private void processRequest(String request, String command)
			throws IOException, HttpException, ParserConfigurationException,
			SAXException, XPathExpressionException, C2ATIError {
		log.info(command.toUpperCase() + " REQUEST : " + request);
		// Read response
		Document response = getResponse(request, false);
		// Check error
		checkError(response);
		log.info(command.toUpperCase() + " REQUEST : OK!");
	}

	private void sinchronizeServerState(Document response)
			throws XPathExpressionException {
		this.pollInterval = getLong("//data/pollinterval", response);
		this.serverTime = getLong("//data/servertime", response);
		this.postedHumanTime = xPath.evaluate("//data/humantime", response);
	}

	private void addRecentC2FillsToResult(LatestSignals result, Node node)
			throws XPathExpressionException {
		C2RecentFill fill = new C2RecentFill();
		result.getResentC2Fills().add(fill);
		fill.setSignalId(getLong("signalid", node));
		String permId = xPath.evaluate("permid", node);
		fill.setPermId(permId);
		fill.setSignalId(getLong("signalid", node));
		fill.setFilledAgo(getLong("filledago", node));
		fill.setFilledPrice(getDouble("filledprice", node));
		// Debug
		log.debug(fill);
	}

	private void addFillACKToResult(LatestSignals result, Node node)
			throws XPathExpressionException {
		FillAcknowledgment ack = new FillAcknowledgment();
		ack.setSigId(getLong("sigid", node));
		String permId = xPath.evaluate("permid", node);
		ack.setPermId(permId);
		ack.setTotalQuant(getLong("totalquant", node));
		result.getFillInfoReceived().add(ack);

		// Debug
		log.debug(ack);
	}

	private void addSignalToResult(LatestSignals result, Node node)
			throws XPathExpressionException {
		Signal signal = new Signal();
		// Put object to structure
		result.getSignals().add(signal);
		// systemname
		String systemName = xPath.evaluate("systemname", node);
		signal.setSystemName(systemName);
		// systemidnum
		signal.setSystemIdNum(getLong("systemidnum", node));
		// signalid
		signal.setSignalid(getLong("signalid", node));
		// postedwhen
		signal.setPostedWhen(getLong("postedwhen", node));
		// postedhumantime
		String postedHumanTime = xPath.evaluate("postedhumantime", node);
		signal.setPostedHumanTime(postedHumanTime);
		// action
		String actionStr = xPath.evaluate("action", node);
		ActionEnum action = ActionEnum.valueOf(actionStr);
		signal.setAction(action);
		// scaledquant
		signal.setScaledQuant(getLong("scaledquant", node));
		// originalquant
		signal.setOriginalQuant(getLong("originalquant", node));
		// symbol
		String symbol = xPath.evaluate("symbol", node);
		signal.setSymbol(symbol);
		// assettype
		String assetTypeStr = xPath.evaluate("assettype", node);
		AssetEnum assetType = AssetEnum.valueOf(assetTypeStr);
		signal.setAssetType(assetType);
		// mutualfund
		String mutualFundStr = xPath.evaluate("mutualfund", node);
		boolean mutualFund = mutualFundStr.equals("1");
		signal.setMutualFund(mutualFund);
		// ordertype
		String orderTypeStr = xPath.evaluate("ordertype", node);
		OrderEnum orderType = OrderEnum.valueOf(orderTypeStr);
		signal.setOrderType(orderType);
		// stop
		signal.setStop(getDouble("stop", node));
		// limit
		signal.setLimit(getDouble("limit", node));
		// tif
		String tifStr = xPath.evaluate("tif", node);
		DurationEnum tif = DurationEnum.valueOf(tifStr);
		signal.setTif(tif);
		// underlying
		String underlying = xPath.evaluate("underlying", node);
		signal.setUnderlying(underlying);
		// right
		String right = xPath.evaluate("right", node);
		signal.setRight(right);
		// strike
		String strike = xPath.evaluate("strike", node);
		signal.setStrike(strike);
		// expir
		String expir = xPath.evaluate("expir", node);
		signal.setExpir(expir);
		// exchange
		String exchange = xPath.evaluate("exchange", node);
		signal.setExchange(exchange);
		// marketcode
		String marketcode = xPath.evaluate("marketcode", node);
		signal.setMarketcode(marketcode);
		// ocagroup
		String ocagroup = xPath.evaluate("ocagroup", node);
		signal.setOcagroup(ocagroup);
		// conditionalupon
		Node conditionalUpon = (Node) xPath.evaluate("conditionalupon", node,
				XPathConstants.NODE);
		signal.setConditionalUpon(conditionalUpon);
		// commentary
		String commentary = xPath.evaluate("commentary", node);
		signal.setCommentary(commentary);
		// matchingOpenSigs
		DTMNodeList matchingOpenSigs = (DTMNodeList) xPath.evaluate(
				"//matchingOpenSigs/match", node, XPathConstants.NODESET);
		ArrayList<Long> matchingOpenSigIdArr = new ArrayList<Long>();
		ArrayList<String> matchingOpenSigPermIdsArr = new ArrayList<String>();
		for (int i = 0; i < matchingOpenSigs.getLength(); i++) {
			Node matchingOpenSig = matchingOpenSigs.item(i);
			matchingOpenSigIdArr.add(getLong("sigid", matchingOpenSig));
			String permId = xPath.evaluate("permid", matchingOpenSig);
			if (permId.length() > 0) {
				matchingOpenSigPermIdsArr.add(permId);
			}
		}

		Long[] matchingOpenSigsSigIds = matchingOpenSigIdArr
				.toArray(new Long[matchingOpenSigIdArr.size()]);
		signal.setMatchingOpenSigsSigId(matchingOpenSigsSigIds);

		String[] MatchingOpenSigsPermIds = matchingOpenSigPermIdsArr
				.toArray(new String[matchingOpenSigPermIdsArr.size()]);
		signal.setMatchingOpenSigsPermId(MatchingOpenSigsPermIds);

		// Debug
		log.debug(signal);
	}

	protected Document getResponse(String request) throws IOException,
			HttpException, ParserConfigurationException, SAXException {
		return getResponse(request, true);
	}

	protected Document getResponse(String request, boolean isPoolCommand)
			throws IOException, HttpException, ParserConfigurationException,
			SAXException {
		// Check Pool Time
		if (isPoolCommand) {
			checkPoolTime();
		}

		// HTTP GET
		GetMethod get = new GetMethod(request);
		client.executeMethod(get);

		// Parse XML
		Document response = DOMHelper.parse(get.getResponseBodyAsStream());
		get.releaseConnection();
		return response;
	}

	private void checkError(Document response) throws XPathExpressionException,
			C2ATIError {
		String status = xPath.evaluate("/collective2/status", response);
		if (status.equals("error")) {
			log.error(response);
			throw new C2ATIError(response);
		}
	}

	private void checkPoolTime() {
		if (theFirstPool) {
			theFirstPool = false;
			return;
		}
		long tmpTime = this.clientTime;
		this.clientTime = System.currentTimeMillis();
		long delta = tmpTime - this.clientTime - this.pollInterval;
		if (delta <= 0) {
			return;
		}
		try {
			Thread.sleep(delta);
		} catch (InterruptedException e) {
			log.fatal(e);
		}
		this.clientTime = System.currentTimeMillis();
	}

	public long getServerTime() {
		return serverTime;
	}

	public String getEMail() {
		return eMail;
	}

	public synchronized void setEMail(String mail) {
		eMail = mail;
	}

	public synchronized void setPassword(String password) {
		this.password = password;
	}

	public String getHost() {
		return host;
	}

	public synchronized void setHost(String host) {
		this.host = host;
	}

	public String getSessionId() {
		return sessionId;
	}

	public long getPollInterval() {
		return pollInterval;
	}

	public long getClientTime() {
		return clientTime;
	}

	public String getServerIPAddress() {
		return serverIPAddress;
	}

	public String getServerlPort() {
		return serverPort;
	}

	public String getPostedHumanTime() {
		return postedHumanTime;
	}

	private long getLong(String field, Node node)
			throws XPathExpressionException {
		String tmpStr = xPath.evaluate(field, node);
		return Long.parseLong(tmpStr);
	}

	private double getDouble(String path, Node node)
			throws XPathExpressionException {
		String tmpStr = xPath.evaluate(path, node);
		return Double.parseDouble(tmpStr);
	}
}
