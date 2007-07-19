package sf.net.mlmechtrade;

public abstract class C2Request <_Command extends Enum, _Param  extends Enum> {
	
	private _Command command;

	public C2Request(_Command command) {
		
		assert command != null;
		this.command = command;
	}
	
	public _Command getCommand() {
		return command;
	}

	/** @return null if all OK. */
	public abstract C2Error validate();
}
