package sf.net.mlmechtrade.c2ati;

public class AssetPermition {
	private AssetType assertType;

	private boolean longPermitted;

	private boolean shortPermittd;

	public AssetType getAssertType() {
		return assertType;
	}

	public void setAssertType(AssetType assertType) {
		this.assertType = assertType;
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
