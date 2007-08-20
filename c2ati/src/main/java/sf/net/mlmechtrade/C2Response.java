package sf.net.mlmechtrade;

import sf.net.mlmechtrade.annotations.Response;

@Response
public abstract class C2Response {

	/** @return null if all OK. */
	public C2Error validate() {
		
		// TODO: validate using annotations
		return null;
	}
}
