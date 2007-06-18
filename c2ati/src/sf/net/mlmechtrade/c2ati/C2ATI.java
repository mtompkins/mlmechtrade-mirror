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

	public void login() throws IOException, ParserConfigurationException,
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

	public void logOff() throws ParserConfigurationException, SAXException,
			IOException, XPathExpressionException, C2ATIError {
		String requestTemplate = "http://%s:%s?cmd=logoff&session=%s&h=%s";
		String request = String.format(requestTemplate, this.serverIPAddress,
				this.serverPort, this.sessionId, this.host);
		processRequest(request, "logoff");
	}

	public LatestSignals latestSignals() throws HttpException, IOException,
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
			String sigId = node.getTextContent();
			result.getCompletedTradesSigId().add(sigId);
		}
		// Complete trades permid
		DTMNodeList completeTradesPermIds = (DTMNodeList) xPath.evaluate(
				"//completetrades/sigid", response, XPathConstants.NODESET);
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

	public void confirmSig(long quantity, long sigId, String permId)
			throws HttpException, IOException, ParserConfigurationException,
			SAXException, XPathExpressionException, C2ATIError {
		String requestTemplate = "http://%s:%s?cmd=confirmsig&sigid=%s&session=%s&h=%s&quant=%l";
		String request = String.format(requestTemplate, this.serverIPAddress,
				this.serverPort, sigId, this.sessionId, this.host, quantity);
		if (permId != null) {
			request += "&permid=" + permId;
		}
		processRequest(request, "confirmsig");
	}

	public void cancelConfirmSigId(long sigId) throws HttpException,
			IOException, ParserConfigurationException, SAXException,
			XPathExpressionException, C2ATIError {
		String requestTemplate = "http://%s:%s?cmd=cancelconfirm&sigid=%l&session=%s&h=%s";
		String request = String.format(requestTemplate, this.serverIPAddress,
				this.serverPort, sigId, this.sessionId, this.host);
		processRequest(request, "cancelconfirm");
	}

	public void cancelConfirmPermId(String permId) throws HttpException,
			IOException, ParserConfigurationException, SAXException,
			XPathExpressionException, C2ATIError {
		String requestTemplate = "http://%s:%s?cmd=cancelconfirm&permid=%s&session=%s&h=%s";
		String request = String.format(requestTemplate, this.serverIPAddress,
				this.serverPort, permId, this.sessionId, this.host);
		processRequest(request, "cancelconfirm");
	}

	public void multFillConfirm(List<FillConfirm> fillConfirmList,
			MultFillConfirmType type) throws HttpException,
			XPathExpressionException, IOException,
			ParserConfigurationException, SAXException, C2ATIError {

		// Template
		String requestTemplate = "http://%s:%s?cmd=" + type
				+ "&session=%s&h=%s";
		String request = String.format(requestTemplate, this.serverIPAddress,
				this.serverPort, this.sessionId, this.host);

		// Append fill data
		request += getFillDataString(fillConfirmList, type);

		// Live parameter
		request += "&live=" + (this.lifeType ? "1" : "0");

		// Process request
		processRequest(request, type.toString());
	}

	public void multFillConfirmSigId(long sigId) throws HttpException,
			XPathExpressionException, IOException,
			ParserConfigurationException, SAXException, C2ATIError {
		multFillConfirm(sigId);
	}

	public void multFillConfirmPermId(String permId) throws HttpException,
			XPathExpressionException, IOException,
			ParserConfigurationException, SAXException, C2ATIError {
		multFillConfirm(permId);
	}

	public void ackComplete(long sigId) throws HttpException, IOException,
			ParserConfigurationException, SAXException,
			XPathExpressionException, C2ATIError {
		String requestTemplate = "http://%s:%s?cmd=ackcomplete&sigid=%l&session=%s&h=%s";
		String request = String.format(requestTemplate, this.serverIPAddress,
				this.serverPort, sigId, this.sessionId, this.host);
		processRequest(request, "ackcomplete");
	}

	public List<TradingSystem> requestSystemList() throws HttpException,
			IOException, ParserConfigurationException, SAXException,
			XPathExpressionException, C2ATIError {
		// Store Pool Interval
		long tmpPoolInterval = this.pollInterval;
		this.pollInterval = 120 / 2 * 1000;
		// Template
		String requestTemplate = "http://%s:%s?cmd=ackcomplete&session=%s&h=%s";
		String request = String.format(requestTemplate, this.serverIPAddress,
				this.serverPort, this.sessionId, this.host);
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
			String systemIdStr = xPath.evaluate("//systemid", system);
			long systemId = Long.parseLong(systemIdStr);
			tradingSystem.setSystemId(systemId);
			DTMNodeList permitions = (DTMNodeList) xPath.evaluate(
					"//permissions/asset", system, XPathConstants.NODESET);
			for (int j = 0; j < permitions.getLength(); j++) {
				Node permition = systems.item(j);
				AssetPermition permitionRec = new AssetPermition();
				tradingSystem.getPermitions().add(permitionRec);
				// Asset Type
				String assetTypeStr = xPath.evaluate("//assettype", permition);
				AssetType assetType = AssetType.valueOf(assetTypeStr);
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
		return result;
	}

	public List<TradingSystem> getAllSignals() throws HttpException,
			IOException, ParserConfigurationException, SAXException,
			XPathExpressionException, C2ATIError {
		String requestTemplate = "http://%s:%s?cmd=getallsignals&session=%s&h=%s";
		String request = String.format(requestTemplate, this.serverIPAddress,
				this.serverPort, this.sessionId, this.host);
		// Get response
		Document response = getResponse(request, false);
		// Check error
		checkError(response);
		// Return
		DTMNodeList systems = (DTMNodeList) xPath.evaluate(
				"//allPendingSignals/system", response, XPathConstants.NODESET);
		List<TradingSystem> result = new ArrayList<TradingSystem>();
		for (int i = 0; i < systems.getLength(); i++) {
			TradingSystem tradingSystem = new TradingSystem();
			result.add(tradingSystem);
			Node system = systems.item(i);
			// sistemid
			String systemIdStr = xPath.evaluate("\\sistemid", system);
			long systemId = Long.parseLong(systemIdStr);
			tradingSystem.setSystemId(systemId);
			// Pending Block
			DTMNodeList signalIds = (DTMNodeList) xPath
					.evaluate("//pendingblock/signalid", response,
							XPathConstants.NODESET);
			for (int j = 0; j < signalIds.getLength(); j++) {
				String signalIdStr = systems.item(i).getNodeValue();
				long signalId = Long.parseLong(signalIdStr);
				tradingSystem.getPendingBlock().add(signalId);
			}
		}
		log.debug(result);
		return result;

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
			MultFillConfirmType type) {
		String result = "";
		for (FillConfirm confirm : fillConfirmList) {
			result += "&filldata=";
			// Signal ID or Permanent ID
			result += type == MultFillConfirmType.mult2fillconfirm ? confirm
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
		String tmp;
		tmp = xPath.evaluate("//pollinterval", response);
		this.pollInterval = Long.parseLong(tmp);

		tmp = xPath.evaluate("//servertime", response);
		this.serverTime = Long.parseLong(tmp);

		this.postedHumanTime = xPath.evaluate("//humantime", response);
	}

	private void addRecentC2FillsToResult(LatestSignals result, Node node)
			throws XPathExpressionException {
		C2RecentFill fill = new C2RecentFill();
		result.getResentC2Fills().add(fill);
		String signalIdStr = xPath.evaluate("//signalid", node);
		long signalId = Long.parseLong(signalIdStr);
		fill.setSignalId(signalId);
		String filledAgoStr = xPath.evaluate("//filledago", node);
		long filledAgo = Long.parseLong(filledAgoStr);
		fill.setFilledAgo(filledAgo);
		String filledPriceStr = xPath.evaluate("//filledprice", node);
		double filledPrice = Double.parseDouble(filledPriceStr);
		fill.setFilledPrice(filledPrice);

		// Debug
		log.debug(fill);
	}

	private void addFillACKToResult(LatestSignals result, Node node)
			throws XPathExpressionException {
		FillAcknowledgment ack = new FillAcknowledgment();
		String sigIdStr = xPath.evaluate("//sigid", node);
		long sigId = Long.parseLong(sigIdStr);
		ack.setSigId(sigId);
		String permId = xPath.evaluate("//permid", node);
		ack.setPermId(permId);
		String totalQuantStr = xPath.evaluate("//totalquant", node);
		long totalQuant = Long.parseLong(totalQuantStr);
		ack.setTotalQuant(totalQuant);
		result.getFillInfoReceived().add(ack);

		// Debug
		log.debug(ack);
	}

	private void addSignalToResult(LatestSignals result, Node node)
			throws XPathExpressionException {
		String signalIdStr = xPath.evaluate("//signalid", node);
		long signalId = Long.parseLong(signalIdStr);
		Signal signal = new Signal();
		signal.setSignalid(signalId);
		// Put object to structure
		result.getSignals().put(signalId, signal);
		// systemname
		String systemName = xPath.evaluate("//systemname", node);
		signal.setSystemName(systemName);
		// systemidnum
		String systemIdNumStr = xPath.evaluate("//systemidnum", node);
		// work around of bug (systemid empty)
		long systemIdNum = Long.parseLong(systemIdNumStr);
		signal.setSystemIdNum(systemIdNum);
		// postedwhen
		String postedWhenStr = xPath.evaluate("//postedwhen", node);
		long postedWhen = Long.parseLong(postedWhenStr);
		signal.setPostedWhen(postedWhen);
		// postedhumantime
		String postedHumanTime = xPath.evaluate("//postedHumanTime", node);
		signal.setPostedHumanTime(postedHumanTime);
		// action
		String actionStr = xPath.evaluate("//action", node);
		Action action = Action.valueOf(actionStr);
		signal.setAction(action);
		// scaledquant
		String scaledQuantStr = xPath.evaluate("//scaledquant", node);
		long scaledQuant = Long.parseLong(scaledQuantStr);
		signal.setScaledQuant(scaledQuant);
		// originalquant
		String originalQuantStr = xPath.evaluate("//originalquant", node);
		long originalQuant = Long.parseLong(originalQuantStr);
		signal.setOriginalQuant(originalQuant);
		// symbol
		String symbol = xPath.evaluate("//symbol", node);
		signal.setSymbol(symbol);
		// assettype
		String assetTypeStr = xPath.evaluate("//assettype", node);
		AssetType assetType = AssetType.valueOf(assetTypeStr);
		signal.setAssetType(assetType);
		// mutualfund
		String mutualFundStr = xPath.evaluate("//mutualfund", node);
		boolean mutualFund = mutualFundStr.equals("1");
		signal.setMutualFund(mutualFund);
		// ordertype
		String orderTypeStr = xPath.evaluate("//ordertype", node);
		OrderType orderType = OrderType.valueOf(orderTypeStr);
		signal.setOrderType(orderType);
		// stop
		String stopStr = xPath.evaluate("//stop", node);
		double stop = Double.parseDouble(stopStr);
		signal.setStop(stop);
		// limit
		String limitStr = xPath.evaluate("//limit", node);
		double limit = Double.parseDouble(limitStr);
		signal.setLimit(limit);
		// tif
		String tifStr = xPath.evaluate("//tif", node);
		Duration tif = Duration.valueOf(tifStr);
		signal.setTif(tif);
		// underlying
		String underlying = xPath.evaluate("//underlying", node);
		signal.setUnderlying(underlying);
		// right
		String right = xPath.evaluate("//right", node);
		signal.setRight(right);
		// strike
		String strike = xPath.evaluate("//strike", node);
		signal.setStrike(strike);
		// expir
		String expir = xPath.evaluate("//expir", node);
		signal.setExpir(expir);
		// exchange
		String exchange = xPath.evaluate("//exchange", node);
		signal.setExchange(exchange);
		// marketcode
		String marketcode = xPath.evaluate("//marketcode", node);
		signal.setMarketcode(marketcode);
		// ocagroup
		String ocagroup = xPath.evaluate("//ocagroup", node);
		signal.setOcagroup(ocagroup);
		// conditionalupon
		Node conditionalUpon = (Node) xPath.evaluate("//conditionalupon", node,
				XPathConstants.NODE);
		signal.setConditionalUpon(conditionalUpon);
		// commentary
		String commentary = xPath.evaluate("//commentary", node);
		signal.setCommentary(commentary);
		// matchingOpenSigs
		DTMNodeList matchingOpenSigs = (DTMNodeList) xPath.evaluate(
				"//matchingOpenSigs/match", node, XPathConstants.NODESET);
		ArrayList<String> matchingOpenSigIdArr = new ArrayList<String>();
		ArrayList<String> matchingOpenSigPermIdsArr = new ArrayList<String>();
		for (int i = 0; i < matchingOpenSigs.getLength(); i++) {
			Node matchingOpenSig = matchingOpenSigs.item(i);
			String sigId = xPath.evaluate("//sigid", matchingOpenSig);
			if (sigId.length() > 0) {
				matchingOpenSigIdArr.add(sigId);
			}
			String permId = xPath.evaluate("//permid", matchingOpenSig);
			if (permId.length() > 0) {
				matchingOpenSigPermIdsArr.add(permId);
			}
		}

		String[] matchingOpenSigsSigIds = matchingOpenSigIdArr
				.toArray(new String[matchingOpenSigIdArr.size()]);
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

	protected String readResponse(String request) throws HttpException,
			IOException {
		GetMethod get = new GetMethod(request);
		client.executeMethod(get);
		// Parse XML
		String result = get.getResponseBodyAsString();
		get.releaseConnection();
		return result;
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

	public void setEMail(String mail) {
		eMail = mail;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
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
}
