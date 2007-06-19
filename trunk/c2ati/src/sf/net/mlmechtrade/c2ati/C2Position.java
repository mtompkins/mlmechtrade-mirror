package sf.net.mlmechtrade.c2ati;

public class C2Position {
	private String symbol;

	private AssetType assetType;

	private long quant;

	private String underlying;

	private String right;

	private String strike;

	private String expir;

	private String exchange;

	private String marketCode;

	public AssetType getAssetType() {
		return assetType;
	}

	public void setAssetType(AssetType assetType) {
		this.assetType = assetType;
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

	public String getMarketCode() {
		return marketCode;
	}

	public void setMarketCode(String marketCode) {
		this.marketCode = marketCode;
	}

	public long getQuant() {
		return quant;
	}

	public void setQuant(long quant) {
		this.quant = quant;
	}

	public String getRight() {
		return right;
	}

	public void setRight(String right) {
		this.right = right;
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

	public String getUnderlying() {
		return underlying;
	}

	public void setUnderlying(String underlying) {
		this.underlying = underlying;
	}
}
