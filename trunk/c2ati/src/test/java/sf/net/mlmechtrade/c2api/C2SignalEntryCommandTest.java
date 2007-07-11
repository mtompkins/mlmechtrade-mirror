package sf.net.mlmechtrade.c2api;

import static sf.net.mlmechtrade.c2api.C2SignalEntryCommandEnum.signal;
import static sf.net.mlmechtrade.c2api.C2SignalEntryCommandParamEnum.systemid;
import junit.framework.TestCase;

public class C2SignalEntryCommandTest extends TestCase {
	
	//TODO write tests in C2SignalEntryCommandTest

	public void testSignal() {
		
		C2SignalEntryCommand cmd = new C2SignalEntryCommand(signal);
		cmd.setParam(systemid, 100);

		String request = cmd.toString();
		assertTrue(request  != null && request.length() > 0);
	}

}
