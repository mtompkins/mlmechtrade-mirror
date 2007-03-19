/*
 * $Id$
 */
package net.sf.mlmechtrade.tafunc.generator;

import java.io.InputStream;
import java.util.Date;
import java.util.Iterator;
import java.util.Properties;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import net.sf.mlmechtrade.tafunc.api.FinancialFunctionType;
import net.sf.mlmechtrade.tafunc.api.FinancialFunctions;
import net.sf.mlmechtrade.tafunc.api.FinancialFunctionType.OptionalInputArguments;
import net.sf.mlmechtrade.tafunc.api.FinancialFunctionType.OutputArguments;
import net.sf.mlmechtrade.tafunc.api.FinancialFunctionType.RequiredInputArguments;


public class GenerateMexAPI {
	private static final Properties cTypeMap = new Properties();
	static {
		cTypeMap.setProperty("Integer", "int	");
		cTypeMap.setProperty("Double", "double	");
		cTypeMap.setProperty("Double Array", "double*	");
		cTypeMap.setProperty("Integer Array", "int*	");
		cTypeMap.setProperty("High", "const double*	");
		cTypeMap.setProperty("Low", "const double*	");
		cTypeMap.setProperty("Close", "const double*	");
		cTypeMap.setProperty("Open", "const double*	");
		cTypeMap.setProperty("Volume", "const double*	");
		cTypeMap.setProperty("MA Type", "double	");
	}

	private static final Properties cMexTypeMap = new Properties();
	static {
		cMexTypeMap.setProperty("Integer", "int	");
		cMexTypeMap.setProperty("Double", "double	");
		cMexTypeMap.setProperty("Double Array", "double *");
		cMexTypeMap.setProperty("High", "double *");
		cMexTypeMap.setProperty("Low", "double *");
		cMexTypeMap.setProperty("Close", "double *");
		cMexTypeMap.setProperty("Open", "double *");
		cMexTypeMap.setProperty("Volume", "double *");
		cMexTypeMap.setProperty("MA Type", "double	");
	}

	public static void main(String[] argv) throws Exception {
		System.out.println("START: generating mex files" + new Date());
		// Validate args
		if (argv.length != 1) {
			System.err.println("Usage GenerateMexAPI outputDir");
			System.exit(0);
		}
		String outDir = argv[0];

		// Init JAXB context
		JAXBContext jaxbContext = JAXBContext
				.newInstance("net.sf.mlmechtrade.tafunc.api");
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
		// Load API configuration
		String fileName = "ta_func_api.xml";
		InputStream is = ClassLoader.getSystemResourceAsStream(fileName);
		// Unmarchal
		FinancialFunctions root = (FinancialFunctions) unmarshaller
				.unmarshal(is);
		// Generate
		for (FinancialFunctionType function : root.getFinancialFunction()) {
			// Transform
			transform(function);
			generateMEX(function, outDir);
			generateM(function, outDir);
		}
		
		generateContents(root, outDir);

		System.out.println("END: generating mexx files" + new Date());
	}

	/**
	 * Generates MEXX function into C file. 
	 * @param function Function to generate C file for
	 * @param outDir Output directory for generated file
	 * @throws Exception When **it happens.
	 */
	private static void generateMEX(FinancialFunctionType function,
			String outDir) throws Exception {
		String fileName = "TA_" + function.getAbbreviation() + ".c";
		new FinancialFunctionGenerator(function, "template-c.vm").generate(outDir, fileName);
	}

	/**
	 * Generates MEXX function help into M file. 
	 * @param function Function to generate M file for
	 * @param outDir Output directory for generated file
	 * @throws Exception When **it happens.
	 */
	private static void generateM(FinancialFunctionType function,
			String outDir) throws Exception {

		String fileName = "TA_" + function.getAbbreviation() + ".m";
		new FinancialFunctionGenerator(function, "template-m.vm").generate(outDir, fileName);
	}

	/**
	 * Generates Contents.m (package help) 
	 * @param functions All functions
	 * @param outDir Output directory for generated file
	 * @throws Exception When **it happens.
	 */
	private static void generateContents(FinancialFunctions functions,
			String outDir) throws Exception {

		new ContentsGenerator(functions, "template-contents-m.vm").generate(outDir, "Contents.m");
	}

	private static void transform(FinancialFunctionType function) {
		// Transform InArgs
		RequiredInputArguments inArgs = function.getRequiredInputArguments();
		if (inArgs != null) {
			for (FinancialFunctionType.RequiredInputArguments.RequiredInputArgument inArg : inArgs
					.getRequiredInputArgument()) {
				inArg.setCType(cTypeMap.getProperty(inArg.getType().value()));
				inArg.setCMexType(cMexTypeMap.getProperty(inArg.getType()
						.value()));
				inArg.setCamelCaseName(Util.camelCase(inArg.getName()).replaceAll("-", ""));
			}
		}
		// Transform OptInArgs
		OptionalInputArguments optInArgs = function.getOptionalInputArguments();
		if (optInArgs != null) {
			for (FinancialFunctionType.OptionalInputArguments.OptionalInputArgument optInArg : optInArgs
					.getOptionalInputArgument()) {
				optInArg.setCType(cTypeMap.getProperty(optInArg.getType()
						.value()));
				optInArg.setCMexType(cMexTypeMap.getProperty(optInArg.getType()
						.value()));
				System.out.println(optInArg.getType().value());
				optInArg.setCamelCaseName(Util.concat(optInArg.getName().replaceAll("-", "")));
			}
		}
		// Transform outArgs
		OutputArguments outArgs = function.getOutputArguments();
		if (outArgs != null) {
			FinancialFunctionType.OutputArguments.OutputArgument outArg = null;
			Iterator<FinancialFunctionType.OutputArguments.OutputArgument> it;
			for (it = outArgs.getOutputArgument().iterator(); it.hasNext();) {
				outArg = it.next();
				outArg.setCType(cTypeMap.getProperty(outArg.getType().value()));
				outArg.setCamelCaseName(Util.camelCase(outArg.getName().replaceAll("-", "")));
				outArg.setNotLast(it.hasNext());
			}
		}

	}
}
