package sf.net.mlmechtrade.c2api;

/**
 * All supported C2 Signal Entry API commands
 * 
 */
public enum C2SignalEntryCommandEnum {

	/**
	 * <h1>Placing an order </h1>
	 * 
	 * 
	 * <p>
	 * Buy 1200 shares of IBM at the market:
	 * <p>
	 * Your program calls the following URL (all on one line):
	 * 
	 * <pre>
	 * http://www.collective2.com/cgi-perl/signal.mpl?cmd=signal&amp;
	 * 	 systemid=1234&amp;pw=abcd&amp;instrument=stock&amp;action=BTO&amp;quant=1200&amp;
	 * 	 symbol=IBM&amp;duration=DAY
	 * </pre>
	 * 
	 * 
	 * <p>
	 * Buy 5 contracts of the E-Mini S&P at limit 1120 or better, good til
	 * cancel:
	 * 
	 * <pre>
	 * http://www.collective2.com/cgi-perl/signal.mpl?cmd=signal&amp;
	 * 	 systemid=1234&amp;pw=abcd&amp;instrument=future&amp;action=BTO&amp;quant=5&amp;
	 * 	 symbol=@ESM4&amp;duration=GTC
	 * </pre>
	 * 
	 */
	signal,
	
	/**
	 * Reversal Orders
	 */
	reverse,

	/** To request an OCA id number */
	requestocaid,

	/**
	You can specify that a group of trades act as a One-Cancels-Another (OCA) group. The definition of an OCA group is this: as soon as the first order within the group is filled, the remaining orders within the group are cancelled.
	To make an oca group, you need to specify the ocagroupid with each signal you enter. The ocagroupid is an unique number. You can either specify your own ocagroupid, or can request one from the C2 server.	 */
	ocaid,
	
	/**
	 * <h1>Canceling Orders</h1>
	 * 
	 */
	cancel,
	
	/**
	 * <h1>Cancel all pending orders</h1>
	 * 
	 */
	cancelallpending,

	/**
	 * <h1>Flushing signals</h1>
	 * 
	 */
	flushpendingsignals,

	/**
	 * <h1>Closing all positions</h1>
	 * 
	 */
	closeallpositions,


	/**
	 * <h1>Requesting Buying Power</h1>
	 * 
	 */
	getbuypower,

	/**
	 * <h1>Requesting signal status</h1>
	 * 
	 */
	signalstatus,

	/**
	 * <h1>Requesting position status</h1>
	 * 
	 */
	positionstatus,

	/**
	 * <h1>Requesting all systems</h1>
	 * 
	 */
	allsystems,
	
	
	/**
	 * <h1>Get equity info: getsystemhypothetical</h1>
	 * 
	 */
	getsystemhypothetical,
}
