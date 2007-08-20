package sf.net.mlmechtrade.comm;

import java.io.InputStream;

import sf.net.mlmechtrade.C2Error;
import sf.net.mlmechtrade.C2Request;
import sf.net.mlmechtrade.C2Response;
import sf.net.mlmechtrade.C2ResponseParser;

/**
 * Communication session with C2 server
 *
 */
public class C2Session {
	
	/** Asynchronous C2 server response handler. Used with invokeAsync() method. */
	public static interface AsyncRequestHandler {
	
		C2Response responseReceived(Object commandIdentificator, C2Response response);
	}

	/** Simple synchronous C2 server call. */
	public C2Response invoke(C2Request request, C2ResponseParser parser) throws C2Error {
		
		validate(request);
		
		final String httpCommandParams0 = request.toString();
		final String httpCommandParams = addSessionHttpParameters(httpCommandParams0);
		
		final InputStream serverResponse = sendToServer(httpCommandParams);
		return parser.parse(serverResponse);
	}
	
	/** Asynchronous C2 server call. */
	public void invokeAsync(C2Request request, C2ResponseParser parser,
						Object commandIdentificator, AsyncRequestHandler handler)
					throws C2Error {
		
		validate(request);
		
//		final String httpCommandParams0 = request.toString();
//		final String httpCommandParams = addSessionHttpParameters(httpCommandParams0);

		// TODO - create & run thread, return
		// 
	}

	protected InputStream sendToServer(String httpCommandParams) throws C2Error {

		//TODO implement sendToServer
		throw new C2Error("NOT IMPLEMENTED");
	}

	/**
	 * Override it to decorate http command arguments with sessions IDs, user name, pwd, etc.
	 * @param httpCommandParams0 plain command http GET request arguments
	 * @return full GET request arguments
	 */
	protected String addSessionHttpParameters(String httpCommandParams0) {
		// default implementation
		return httpCommandParams0;
	}

	protected void validate(C2Request request) throws C2Error {

		C2Error error = request.validate();
		if (error != null) {
			throw error;
		}
	}

}
