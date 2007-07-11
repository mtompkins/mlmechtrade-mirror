package sf.net.mlmechtrade.metadata;

public class Parameter<_Param extends Enum> {
	
	private _Param param;
	private boolean required;

	public Parameter(_Param param) {
		this.param = param;
	}

	public _Param getParam() {
		return param;
	}

	public void setParam(_Param param) {
		this.param = param;
	}

	public boolean isRequired() {
		return required;
	}

	public void setRequired(boolean required) {
		this.required = required;
	}

}
