package sf.net.mlmechtrade.c2ati;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import sf.net.mlmechtrade.c2ati.util.HttpHelper;

/**
 * 
 * Collective2C command, maps to http request to be sent to C2
 *
 */
public class C2ATICommand {
	
	private C2ATICommandEnum command;

	/**
	 * Parameters map for http request : key=val
	 */
	protected Map<String, String> params = new HashMap<String, String>();

	public C2ATICommand(C2ATICommandEnum command) {
		
		assert command != null;

		this.command = command;
	}

	@Override
	public String toString() {

		String ret = "cmd=" + HttpHelper.htmlEncode(command.name());

		for (Entry<String, String> param : params.entrySet()) {
			ret += "&" + HttpHelper.htmlEncode(param.getKey()) + '=' + HttpHelper.htmlEncode(param.getValue());
		}

		return ret;
	}

	public C2ATICommandEnum getCommand() {
		return command;
	}

	/**
	 * @return Read/only version of parameters map
	 */
	public Map<String, String> getParams() {
		return Collections.unmodifiableMap(params);
	}
	
	public C2ATICommand setParam(String param, String value) {

		if (value != null) {
			checkParameter(param, value);
			params.put(param, value);
		} else {
			params.remove(param);
		}

		return this;
	}

	protected void checkParameter(String param, String value) {
	}
	
	/** Factory method for convienence */
	public static C2ATICommand create(C2ATICommandEnum command) {
		return new C2ATICommand(command);
	}
	
}
