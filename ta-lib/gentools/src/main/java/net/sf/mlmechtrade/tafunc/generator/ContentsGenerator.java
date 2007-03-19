/*
 * $Id$
 */

package net.sf.mlmechtrade.tafunc.generator;

import net.sf.mlmechtrade.tafunc.api.FinancialFunctions;

import org.apache.velocity.VelocityContext;

/**
 * Velocity-based generator that takes all financial functions as an input.
 *
 */
public class ContentsGenerator extends VelocityGenerator {

	private FinancialFunctions functions;
	
	public ContentsGenerator(FinancialFunctions functions, String templateFilename) {
		super(templateFilename);
		this.functions = functions;
	}

	@Override
	protected VelocityContext createContext() {
		VelocityContext context = super.createContext();
		context.put("FinancialFunctions", functions);
		return context;
	}
}
