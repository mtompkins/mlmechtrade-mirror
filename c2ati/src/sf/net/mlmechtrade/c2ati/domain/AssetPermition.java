package sf.net.mlmechtrade.c2ati.domain;


public class AssetPermition {
	private AssetEnum assetType;

	private boolean longPermitted;

	private boolean shortPermittd;

	public String toString() {
		return "AssetPermition: assetType=" + assetType + " longPermitted="
				+ longPermitted + " shortPermittd=" + shortPermittd;
	}

	public AssetEnum getAssertType() {
		return assetType;
	}

	public void setAssertType(AssetEnum assetType) {
		this.assetType = assetType;
	}

	public boolean isLongPermitted() {
		return longPermitted;
	}

	public void setLongPermitted(boolean longPermitted) {
		this.longPermitted = longPermitted;
	}

	public boolean isShortPermittd() {
		return shortPermittd;
	}

	public void setShortPermittd(boolean shortPermittd) {
		this.shortPermittd = shortPermittd;
	}
}
