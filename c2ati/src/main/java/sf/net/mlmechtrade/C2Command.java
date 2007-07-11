package sf.net.mlmechtrade;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import sf.net.mlmechtrade.c2ati.util.HttpHelper;

/**
 * C2 commands. It's purpose is to make valid HTTP request string to C2.
 *
 * @param <_Command> Commands' enumeration : all supported commands should be enumerated
 * @param <_Param> Parameters' enumeration : all supported parameters for all commands should be enumerated
 */
public class C2Command <_Command extends Enum,
						_Param  extends Enum> {

	/** Command type */
	private _Command command;

	/** Parameters map for http request : key=val*/
	protected Map<_Param, Object> params = new HashMap<_Param, Object>();

	public C2Command(_Command command) {
		assert command != null;
		this.command = command;
	}

	/** Overriden to make http request string.
	 * @return to make http request string, i.e. "cmd=X&param1=y&param2=zzz"
	 */
	@Override
	public String toString() {

		String ret = "cmd=" + HttpHelper.htmlEncodeCommand(command);

		for (Entry<_Param, Object> param : params.entrySet()) {
			ret += "&" + HttpHelper.htmlEncodeCommand(param.getKey(), param.getValue());
		}

		return ret;
	}

	public _Command getCommand() {
		return command;
	}

	/**
	 * @return Read/only version of parameters map
	 */
	public Map<_Param, Object> getParams() {
		return Collections.unmodifiableMap(params);
	}
	
	public C2Command setParam(_Param param, Object value) {

		if (value != null) {
			params.put(param, value);
		} else {
			params.remove(param);
		}

		return this;
	}

	protected C2ValidationResult validate() {
		
		//TODO implement validate()
		return C2ValidationResult.OK;
	}

	/** Checks one commad parameter/value pair
	 * @param param
	 * @param value
	 * @return null if OK, string with error message if failure.
	 */
	protected String checkParameter(_Param param, Object value) {
		//TODO implement checkParameter()
		return null;
	}

	/** Factory method for convienence */
	public static <_Command extends Enum, _CommandParam extends Enum> 
		C2Command<_Command, _CommandParam> create(_Command command) {
			
		return new C2Command<_Command, _CommandParam>(command);
	}
	
}
