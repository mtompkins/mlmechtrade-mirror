package sf.net.mlmechtrade;

import java.util.HashMap;
import java.util.Map;

import sf.net.mlmechtrade.metadata.Parameters;

/**
 * A specialization of C2Command where validation is performed using command annotations
 * (from sf.net.mlmechtrade.metadata)
 *
 */
public class C2AnnotatedCommand
			<_Command extends Enum, _Param extends Enum>
		extends C2Command<_Command, _Param> {

	/** Metadata for all classes that inherits from C2AnnotatedCommand */
	protected static final Map<Class, Map> METADATA = new HashMap<Class, Map>();

	/** Metadata for this particular command class (cached in METADATA) */
	protected final Parameters<_Param> metadata;

	public C2AnnotatedCommand(_Command command) {
		super(command);
		metadata = getMetadata();
	}

	protected Parameters<_Param> getMetadata() {

		//Map<_Command, Parameters> metadataForAllCommands = initializeMetadata(command);
		//assert metadataForAllCommands != null;

		Parameters<_Param> metadataForThisCommand = initializeMetadata();
		assert metadataForThisCommand != null;

		return metadataForThisCommand;
	}

	/*@SuppressWarnings("unchecked")
	protected Parameters<_Param> getCommandMetadata() {

		//Map<_Command, Parameters> classMetadata = getCommandClassMetadata();
		//return myMetadata;
		return null;
	}*/

	@SuppressWarnings("unchecked")
	protected Parameters<_Param> initializeMetadata() {
		
		Map<_Command, Parameters> metadataForAllCommands = initializeMetadata(getCommand().getClass());
		if (metadataForAllCommands != null) {
			return metadataForAllCommands.get(getCommand());
		}

		synchronized (METADATA) {

			metadataForAllCommands = initializeMetadata(getCommand().getClass());
			if (metadataForAllCommands != null) {
				return metadataForAllCommands.get(getCommand());
			}
			
			Parameters<_Param> result = metadataForAllCommands.get(getCommand());
			assert result != null : "Couldn't get metadata for command " + getCommand() + ", class=" + getCommand().getClass().getName();
			return result;
		}
	}
	
	@SuppressWarnings("unchecked")
	private Map<_Command, Parameters> initializeMetadata(Class<? extends Enum> commandsEnum) {
		
		Map<_Command, Parameters> result = new HashMap<_Command, Parameters>();

		for (Enum command : commandsEnum.getEnumConstants()) {
			
			Parameters parameters = new Parameters<_Param>();
			// TODO fill command metadata
			// TODO think, how to make parameters' metadata read only
			result.put((_Command)command, parameters);
			
		}

		return result;
	}

	@Override
	protected String checkParameter(_Param param, Object value) {
		// TODO implement checkParameter against metadata
		return super.checkParameter(param, value);
	}

	@SuppressWarnings("unchecked")
	protected Map<_Command, Parameters> getCommandClassMetadata() {

		Map<_Command, Parameters> classMetadata = METADATA.get(getCommand().getClass());
		return classMetadata;
	}

	@SuppressWarnings("unchecked")
	protected void setCommandClassMetadata(Map<_Command, Parameters> metadata) {

		Map<Class, Map> previous = METADATA.put(getCommand().getClass(), metadata);
		assert previous == null : "setCommandClassMetadata was called more than once on class " + getCommand().getClass();
	}

}
