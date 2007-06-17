package sf.net.mlmechtrade.c2ati;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import sf.net.mlmechtrade.c2ati.Signal;

public class LatestSignals {
	List<Long> canselListIds = new ArrayList<Long>();
	
	List<String> canselListPermIds = new ArrayList<String>();

	Map<Long, Signal> signals = new TreeMap<Long, Signal>();

	String ocaChanges;

	List<FillAcknowledgment> fillInfoReceived = new ArrayList<FillAcknowledgment>();

	List<C2RecentFill> resentC2Fills = new ArrayList<C2RecentFill>();

	List<String> completedTradesSigId = new ArrayList<String>();

	List<String> completedTradesSigPerId = new ArrayList<String>();

	public List<Long> getCanselListIds() {
		return canselListIds;
	}

	public Map<Long, Signal> getSignals() {
		return signals;
	}

	public String getOcaChanges() {
		return ocaChanges;
	}

	public void setOcaChanges(String ocaChanges) {
		this.ocaChanges = ocaChanges;
	}

	public List<FillAcknowledgment> getFillInfoReceived() {
		return fillInfoReceived;
	}

	public List<C2RecentFill> getResentC2Fills() {
		return resentC2Fills;
	}

	public List<String> getCompletedTradesSigId() {
		return completedTradesSigId;
	}

	public List<String> getCompletedTradesSigPerId() {
		return completedTradesSigPerId;
	}

	public List<String> getCanselListPermIds() {
		return canselListPermIds;
	}
}
