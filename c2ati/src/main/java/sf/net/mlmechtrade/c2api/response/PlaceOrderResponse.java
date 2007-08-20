package sf.net.mlmechtrade.c2api.response;

import sf.net.mlmechtrade.C2Error;
import sf.net.mlmechtrade.C2Response;

/**
After you put PlaceOrderRequest, the Web site will respond either with an error message, or with data similar to the following:

<pre>
&lt;collective2>
&lt;signalid>10344682&lt;/signalid>
&lt;comments>Signal 10344682 accepted for immediate processing.&lt;/comments>
&lt;/collective2>
</pre>

<p>This pseudo-XML text is designed so that you can more easily extract the unique signal ID which is assigned to your request. Every trading signal on Collective2 is given an unique signal id.

<p>Knowing the signal id of the trade you enter is useful for more complex trades, like conditionals and OCA orders. In addition, it is required when you want to cancel an order.

 *
 */
public class PlaceOrderResponse extends C2Response {
	
	private String signalid;
	private String comments;

	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String getSignalid() {
		return signalid;
	}
	public void setSignalid(String signalid) {
		this.signalid = signalid;
	}

}
