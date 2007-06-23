package sf.net.mlmechtrade.c2ati.domain;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class LatestSignals {
	List<Long> canselListIds = new ArrayList<Long>();

	List<String> canselListPermIds = new ArrayList<String>();

	List<Signal> signals = new LinkedList<Signal>();

	List<FillAcknowledgment> fillInfoReceived = new ArrayList<FillAcknowledgment>();

	List<C2RecentFill> resentC2Fills = new ArrayList<C2RecentFill>();

	List<Long> completedTradesSigId = new ArrayList<Long>();

	List<String> completedTradesSigPerId = new ArrayList<String>();

	public String toString() {
		return "LatestSignals: canselListIds=" + canselListIds
				+ " canselListPermIds" + canselListPermIds + " signals="
				+ signals + " fillInfoReceived="
				+ fillInfoReceived + " resentC2Fills=" + resentC2Fills
				+ " completedTradesSigId=" + completedTradesSigId
				+ " completedTradesSigPerId=" + completedTradesSigPerId;
	}

	public List<Long> getCanselListIds() {
		return canselListIds;
	}

	public List<FillAcknowledgment> getFillInfoReceived() {
		return fillInfoReceived;
	}

	public List<C2RecentFill> getResentC2Fills() {
		return resentC2Fills;
	}

	public List<Long> getCompletedTradesSigId() {
		return completedTradesSigId;
	}

	public List<String> getCompletedTradesSigPerId() {
		return completedTradesSigPerId;
	}

	public List<String> getCanselListPermIds() {
		return canselListPermIds;
	}

	public List<Signal> getSignals() {
		return signals;
	}
}
