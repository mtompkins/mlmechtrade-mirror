package sf.net.mlmechtrade.c2ati;

import org.w3c.dom.Node;

public class Signal {
	private String systemName;

	private long systemIdNum;

	private long signalid;

	private long postedWhen;

	private String postedHumanTime;

	private Action action;

	private long scaledQuant;

	private long originalQuant;

	private String symbol;

	private AssetType assetType;

	private boolean mutualFund;

	private OrderType orderType;

	private double stop;

	private double limit;

	private Duration tif;

	private String underlying;

	private String right;

	private String strike;

	private String expir;

	private String exchange;

	private String marketcode;

	private String ocagroup;

	private Node conditionalUpon;

	private String commentary;

	private String[] matchingOpenSigsSigId;

	private String[] matchingOpenSigsPermId;

	public String toString() {
		String result = "\n----------------------------------"
				+ "System Name       = " + systemName;
		result += "\n" + "System ID Number  = " + systemIdNum;
		result += "\n" + "Signalid          = " + signalid;
		result += "\n" + "Posted when       = " + postedWhen;
		result += "\n" + "Posted Human Time = " + postedHumanTime;
		result += "\n" + "Action            = " + action;
		result += "\n" + "Scaled Quant      = " + scaledQuant;
		result += "\n" + "Original Quant    = " + originalQuant;
		result += "\n" + "Symbol            = " + symbol;
		result += "\n" + "Asset Type        = " + assetType;
		result += "\n" + "Mutual Fund       = " + mutualFund;
		result += "\n" + "Order Type        = " + orderType;
		result += "\n" + "Stop              = " + stop;
		result += "\n" + "Limit             = " + limit;
		result += "\n" + "Duration          = " + tif;
		result += "\n" + "Underlying        = " + underlying;
		result += "\n" + "Right             = " + right;
		result += "\n" + "Strike            = " + strike;
		result += "\n" + "Expir             = " + expir;
		result += "\n" + "Exchange          = " + exchange;
		result += "\n" + "Market Code       = " + marketcode;
		result += "\n" + "OCA Group         = " + ocagroup;
		result += "\n" + "Conditional Upon = "
				+ conditionalUpon.getTextContent();
		result += "\n" + "Commentary        = " + commentary;
		result += "\n" + "Matching Open Signal IDs = ";
		for (String openSigId : matchingOpenSigsSigId) {
			result += " " + openSigId;
		}
		result += "\n" + "Matching Open Permanent Signal IDs =";
		for (String openSigPermId : matchingOpenSigsPermId) {
			result += " " + openSigPermId;
		}
		return result + "\n";
	}

	public Action getAction() {
		return action;
	}

	public void setAction(Action action) {
		this.action = action;
	}

	public AssetType getAssetType() {
		return assetType;
	}

	public void setAssetType(AssetType assetType) {
		this.assetType = assetType;
	}

	public String getCommentary() {
		return commentary;
	}

	public void setCommentary(String commentary) {
		this.commentary = commentary;
	}

	public Node getConditionalUpon() {
		return conditionalUpon;
	}

	public void setConditionalUpon(Node conditionalUpon) {
		this.conditionalUpon = conditionalUpon;
	}

	public String getExchange() {
		return exchange;
	}

	public void setExchange(String exchange) {
		this.exchange = exchange;
	}

	public String getExpir() {
		return expir;
	}

	public void setExpir(String expir) {
		this.expir = expir;
	}

	public double getLimit() {
		return limit;
	}

	public void setLimit(double limit) {
		this.limit = limit;
	}

	public String getMarketcode() {
		return marketcode;
	}

	public void setMarketcode(String marketcode) {
		this.marketcode = marketcode;
	}

	public String[] getMatchingOpenSigsPermId() {
		return matchingOpenSigsPermId;
	}

	public void setMatchingOpenSigsPermId(String[] matchingOpenSigsPermId) {
		this.matchingOpenSigsPermId = matchingOpenSigsPermId;
	}

	public String[] getMatchingOpenSigsSigId() {
		return matchingOpenSigsSigId;
	}

	public void setMatchingOpenSigsSigId(String[] matchingOpenSigsSigId) {
		this.matchingOpenSigsSigId = matchingOpenSigsSigId;
	}

	public boolean isMutualFund() {
		return mutualFund;
	}

	public void setMutualFund(boolean mutualFund) {
		this.mutualFund = mutualFund;
	}

	public String getOcagroup() {
		return ocagroup;
	}

	public void setOcagroup(String ocagroup) {
		this.ocagroup = ocagroup;
	}

	public OrderType getOrderType() {
		return orderType;
	}

	public void setOrderType(OrderType orderType) {
		this.orderType = orderType;
	}

	public long getOriginalQuant() {
		return originalQuant;
	}

	public void setOriginalQuant(long originalQuant) {
		this.originalQuant = originalQuant;
	}

	public long getPostedWhen() {
		return postedWhen;
	}

	public void setPostedWhen(long postedWhen) {
		this.postedWhen = postedWhen;
	}

	public String getRight() {
		return right;
	}

	public void setRight(String right) {
		this.right = right;
	}

	public long getScaledQuant() {
		return scaledQuant;
	}

	public void setScaledQuant(long scaledQuant) {
		this.scaledQuant = scaledQuant;
	}

	public long getSignalid() {
		return signalid;
	}

	public void setSignalid(long signalid) {
		this.signalid = signalid;
	}

	public double getStop() {
		return stop;
	}

	public void setStop(double stop) {
		this.stop = stop;
	}

	public String getStrike() {
		return strike;
	}

	public void setStrike(String strike) {
		this.strike = strike;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public long getSystemIdNum() {
		return systemIdNum;
	}

	public void setSystemIdNum(long systemIdNum) {
		this.systemIdNum = systemIdNum;
	}

	public String getSystemName() {
		return systemName;
	}

	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}

	public Duration getTif() {
		return tif;
	}

	public void setTif(Duration tif) {
		this.tif = tif;
	}

	public String getUnderlying() {
		return underlying;
	}

	public void setUnderlying(String underlying) {
		this.underlying = underlying;
	}

	public String getPostedHumanTime() {
		return postedHumanTime;
	}

	public void setPostedHumanTime(String postedHumanTime) {
		this.postedHumanTime = postedHumanTime;
	}
}
