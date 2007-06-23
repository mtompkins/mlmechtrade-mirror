package sf.net.mlmechtrade.c2ati.domain;

import java.util.LinkedList;
import java.util.List;

public class C2SystemState {
	private long systemId;

	private String systemName;

	private long timeFilterSecs;

	private String timeFilterClock;

	List<C2Position> positions = new LinkedList<C2Position>();

	public String toString() {
		return "C2SystemState: systemId" + systemId + " systemName="
				+ systemName + " timeFilterSecs=" + timeFilterSecs
				+ " timeFilterClock=" + timeFilterClock;
	}

	public long getSystemId() {
		return systemId;
	}

	public void setSystemId(long systemId) {
		this.systemId = systemId;
	}

	public String getSystemName() {
		return systemName;
	}

	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}

	public String getTimeFilterClock() {
		return timeFilterClock;
	}

	public void setTimeFilterClock(String timeFilterClock) {
		this.timeFilterClock = timeFilterClock;
	}

	public long getTimeFilterSecs() {
		return timeFilterSecs;
	}

	public void setTimeFilterSecs(long timeFilterSecs) {
		this.timeFilterSecs = timeFilterSecs;
	}

	public List<C2Position> getPositions() {
		return positions;
	}
}
