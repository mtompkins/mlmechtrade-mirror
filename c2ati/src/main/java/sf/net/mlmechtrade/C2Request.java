package sf.net.mlmechtrade;

public class C2Request <_Command extends Enum, _Param  extends Enum> {
	
	private _Command command;

	public C2Request(_Command command) {
		
		assert command != null;
		this.command = command;
	}
	
	public _Command getCommand() {
		return command;
	}

}
