/*
 * $Id$
 */
package net.sf.mlmechtrade.tafunc.generator;

import net.sf.mlmechtrade.tafunc.api.FinancialFunctionType;

import org.apache.velocity.VelocityContext;

/**
 * Velocity-based generator that takes a single financial function as an input.
 *
 */
public class FinancialFunctionGenerator extends VelocityGenerator {

	private FinancialFunctionType function;
	
	public FinancialFunctionGenerator(FinancialFunctionType function, String templateFilename) {
		super(templateFilename);
		this.function = function;
	}

	@Override
	protected VelocityContext createContext() {
		VelocityContext context = super.createContext();
		context.put("FinancialFunction", function); // function.getOptionalInputArguments().getOptionalInputArgument().iterator()
		return context;
	}
}