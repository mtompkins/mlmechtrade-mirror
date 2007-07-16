package sf.net.mlmechtrade.c2api;

import sf.net.mlmechtrade.C2Command;

/**
 * 
 * C2 Signal Entry API command
 * 
 *
 */
public class C2SignalEntryCommand extends C2Command<C2SignalEntryCommandEnum, C2SignalEntryCommandParamEnum> {

	protected C2SignalEntryCommand(C2SignalEntryCommandEnum command) {
		super(command);
	}

}
