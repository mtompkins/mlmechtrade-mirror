package sf.net.mlmechtrade.c2ati.domain;

import java.util.ArrayList;
import java.util.List;


public class TradingSystem {
	private String name;

	private long systemId;

	private List<AssetPermition> permitions = new ArrayList<AssetPermition>();

	private List<Long> pendingBlock = new ArrayList<Long>();

	public String toString() {
		return "TradingSystem: name=" + name + " systemId=" + systemId
				+ " permitions=" + permitions + " pendingBlock=" + pendingBlock;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<AssetPermition> getPermitions() {
		return permitions;
	}

	public long getSystemId() {
		return systemId;
	}

	public void setSystemId(long systemId) {
		this.systemId = systemId;
	}

	public List<Long> getPendingBlock() {
		return pendingBlock;
	}
}
