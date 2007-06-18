package sf.net.mlmechtrade.c2ati;

public class AssetPermition {
	private AssetType assetType;

	private boolean longPermitted;

	private boolean shortPermittd;

	public String toString() {
		return "AssetPermition: assetType=" + assetType + " longPermitted="
				+ longPermitted + " shortPermittd=" + shortPermittd;
	}

	public AssetType getAssertType() {
		return assetType;
	}

	public void setAssertType(AssetType assetType) {
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
