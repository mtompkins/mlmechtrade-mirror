package sf.net.mlmechtrade.c2api;

/**
 * All supported parameters for C2 Signal Entry API commands
 *
 */
public enum C2SignalEntryCommandParamEnum {

	/**
		<p><b>Parameter:</b> systemid
		<p><b>Value or Example:</b> 123456
		<p><b>What it Means:</b> The system ID.
		<p><b>Comments:</b> 
		<p><b>Can be used with commands:</b> signal
	*/
	systemid,
	
	/**
	<p><b>Parameter:</b> pw
	<p><b>Value or Example:</b> loginPassword
	<p><b>Comments:</b> case sensitive
	<p><b>Can be used with commands: signal</b> 
	 */
	pw,
	
	/**
	<p><b>Parameter:</b> action
	<p><b>Value or Example:</b> 
		<li>BTO Buy To Open open a long position 
		<li>SSHORT Sell Short used for stocks 
		<li>STO Sell To Open used for non-stocks 
		<li>BTC Buy To Close close a short position 
		<li>STC Sell To Close close a long position 
	<p><b>What it Means:</b> 
	<p><b>Comments:</b> 
	<p><b>Can be used with commands: signal</b> 
	 */
	action,
	
	/**
	<p><b>Parameter:</b> quant
	<p><b>Value or Example:</b> 100
	<p><b>What it Means:</b> <p>Number of shares or contracts
	<p>For reverse command: new opening quant (optional; use only if you want your final position to be a quantity different than your prior quantity; if not specified, you will go from long to short, or short to long, using the same quantity of position before the reversal)
	<p><b>Comments:</b> 
	<p><b>Can be used with commands: signal, reverse</b> 
	 */
	quant,
	
	/**
	<p><b>Parameter:</b> instrument
	<p><b>Value or Example:</b> 
		<li>stock <p><i>Note that ETFs like QQQ and DIA are traded on a stock exchange and thus are considered stocks.</i>
		<li>option     
		<li>future     
		<li>forex 
	<p><b>Comments:</b>
	<p><b>Can be used with commands: signal</b> 
	 */
	instrument,
	
	/**
	<p><b>Parameter:</b> symbol
	<p><b>Value or Example:</b> IBM
	<p><b>What it Means:</b> 
	<p><b>Comments:</b> You are responsible for looking up your own symbols. If you trade Forex, you know that Collective2 figures out Forex symbols automatically when you manually enter trades on the Web site. However, you'll need to enter already-valid symbols when using the Web service interface. 
	<p><b>Can be used with commands: signal, reverse</b> 
	 */
	symbol,
	
	/**
	<p><b>Parameter:</b> limit
	<p><b>Value or Example:</b> 35.06
	<p><b>What it Means:</b> 
	<p><b>Comments:</b> Only use if this is a limit order.
	<p><b>Can be used with commands: signal</b> 
	 */
	limit,
	
	/**
	<p><b>Parameter:</b> stop
	<p><b>Value or Example:</b> 20.10
	<p><b>What it Means:</b> 
	<p><b>Comments:</b> Only use if this is a stop order. For market orders, do not use either stop or limit parameters.
	<p><b>Can be used with commands: signal</b> 
	 */
	stop,
	
	/**
	<p><b>Parameter:</b> duration
	<p><b>Value or Example:</b> 
		<li>DAY - Day Order  
		<li>GTC - Good Til Cancel 
	<p><b>Comments:</b> 
	<p><b>Can be used with commands: signal, reverse</b> 
	 */
	duration,
	
	
	/**
	<p><b>Parameter:</b> signalid
	<p><b>Value or Example:</b> 100
	<p><b>What it Means:</b> 
	<p><b>Comments:</b> 
	<p>To specify your own signalid, simply submit your signal id numbers along with the order.
	<p>Thereafter you can use your own signal ids to cancel orders and build conditionals.
	<p>If you specify a signalid, it must be a positive integer that is greater than zero and less than or equal to 4294967295.
	<p><b>Can be used with commands:</b> signal
	 */
	signalid,
	
	
	/**
	<p><b>Parameter:</b> conditionalupon
	<p><b>Value or Example:</b> 100
	<p><b>What it Means:</b> To place a conditional order
	<p><b>Comments:</b> 
	<p><b>Can be used with commands:</b> signal
	 */
	conditionalupon,
	
	/**
	<p><b>Parameter:</b> triggerprice
	<p><b>Value or Example:</b> 100
	<p><b>What it Means:</b> price
	<p><b>Comments:</b> 
	<p><b>Can be used with commands:</b> reverse (optional)
	 */
	triggerprice,
	
	/**
	<p><b>Parameter:</b> ocaid
	<p><b>Value or Example:</b> 12345
	<p><b>What it Means:</b> Specifies a group of trades act as a One-Cancels-Another (OCA) group
	<p><b>Comments:</b> You can get OCA Group ID from server using command requestocaid
	<p><b>Can be used with commands:</b> signal (optional)
	 */
	ocaid,

	/**
	<p><b>Parameter:</b> stoploss
	<p><b>Value or Example:</b> 900.50
	<p><b>What it Means:</b> stop loss
	<p><b>Comments:</b> If you enter both a stoploss and a profittarget, the two exit orders will automatically become a One-Cancels-Another (OCA) group. That way, if you exit a trade at the profit target (for example), your stoploss order won't be left lying aroun.
	<p><b>Can be used with commands:</b> signal (optional)
	 */
	stoploss,

	/**
	<p><b>Parameter:</b> profittarget
	<p><b>Value or Example:</b> 1200
	<p><b>What it Means:</b> profit target
	<p><b>Comments:</b> If you enter both a stoploss and a profittarget, the two exit orders will automatically become a One-Cancels-Another (OCA) group. That way, if you exit a trade at the profit target (for example), your stoploss order won't be left lying around.
	<p>Example:
		<pre>http://www.collective2 .com/cgi-perl/signal. mpl?cmd=signal&
		systemid=1234&pw=abcd&instrument=future&action=BTO&quant=5&
		symbol=@ESU6&limit=1000&duration=GTC&stoploss=900.50&profittarget=1200</pre>

	<p><b>Can be used with commands:</b> signal (optional)
	 */
	profittarget,


	
	
	
	
	
}




//TMPTMP
/* *
<p><b>Parameter:</b> 
<p><b>Value or Example:</b> 
<p><b>What it Means:</b> 
<p><b>Comments:</b> 
<p><b>Can be used with commands:</b> 
 */
