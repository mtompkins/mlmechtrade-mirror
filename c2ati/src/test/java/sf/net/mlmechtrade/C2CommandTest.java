package sf.net.mlmechtrade;

import junit.framework.TestCase;
import sf.net.mlmechtrade.C2Command;

public class C2CommandTest extends TestCase {
	
	private static enum Command {
		go,
		stop
	}

	private static enum Param {
		speed,
		here,
		there
	}
	
	private static class MyCommand extends C2Command<Command, Param> {
		public MyCommand(Command command) {
			super(command);
		}
	}
	
	public void testGoSpeed() {
		
		MyCommand cmd = new MyCommand(Command.go);
		cmd.setParam(Param.speed, 10);
		assertEquals("cmd=go&speed=10", cmd.toString());
	}

}
