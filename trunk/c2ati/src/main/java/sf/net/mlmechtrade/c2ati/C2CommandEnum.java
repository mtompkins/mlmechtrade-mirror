package sf.net.mlmechtrade.c2ati;

/**
 * A list of all possible C2 commands.
 * 
 * @since C2 version 8.2
 *
 */
public enum C2CommandEnum {

	login,
	logoff,
	latestsigs,
	confirmsig,
	cancelconfirm,
	ackcomplete,
	getallsignals,
	requestsystemsync,
	ackc2fill,
	mult2fillconfirm,
}
