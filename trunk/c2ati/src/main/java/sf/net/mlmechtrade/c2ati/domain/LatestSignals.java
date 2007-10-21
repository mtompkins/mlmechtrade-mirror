package sf.net.mlmechtrade.c2ati.domain;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.w3c.dom.Document;

public class LatestSignals {
	Document response;
	
	List<Long> canselListIds = new ArrayList<Long>();

	List<String> cancelListPermIds = new ArrayList<String>();

	List<Signal> signals = new LinkedList<Signal>();

	List<FillAcknowledgment> fillInfoReceived = new ArrayList<FillAcknowledgment>();

	List<C2RecentFill> resentC2Fills = new ArrayList<C2RecentFill>();

	List<Long> completedTradesSigId = new ArrayList<Long>();

	List<String> completedTradesSigPerId = new ArrayList<String>();

	public String toString() {
		return "LatestSignals: canselListIds=" + canselListIds
				+ " cancelListPermIds" + cancelListPermIds + " signals="
				+ signals + " fillInfoReceived="
				+ fillInfoReceived + " resentC2Fills=" + resentC2Fills
				+ " completedTradesSigId=" + completedTradesSigId
				+ " completedTradesSigPerId=" + completedTradesSigPerId;
	}

	public List<Long> getCancelListIds() {
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

	public List<String> getCancelListPermIds() {
		return cancelListPermIds;
	}

	public List<Signal> getSignals() {
		return signals;
	}

	public Document getResponse() {
		return response;
	}

	public void setResponse(Document response) {
		this.response = response;
	}

	public void setSignals(List<Signal> signals) {
		this.signals = signals;
	}
}
