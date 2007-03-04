package net.sf.mlmechtrade.tafunc.generator;

import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.StringWriter;
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

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.tools.generic.IteratorTool;
import org.apache.velocity.tools.generic.ListTool;

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
			generateMEX(function, outDir);
		}
		System.out.println("END: generating mes files" + new Date());
	}

	private static void generateMEX(FinancialFunctionType function,
			String outDir) throws Exception {
		// Transform
		transform(function);
		String fileName = "TA_" + function.getAbbreviation() + ".c";
		System.out.println("File name: " + fileName);
		// Prepare output
		File outFile = new File(outDir, fileName);
		FileWriter fos = new FileWriter(outFile);
		// Create content
		String fileContent = getContent(function);
		// Write and close
		fos.write(fileContent);
		fos.close();
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

	private static String getContent(FinancialFunctionType function)
			throws Exception {
		// Init Velocity & template
		VelocityContext context = new VelocityContext();
		context.put("FinancialFunction", function); // function.getOptionalInputArguments().getOptionalInputArgument().iterator()
		Util util = new Util();
		context.put("util", util);
		ListTool listTool = new ListTool();
		context.put("list", listTool);
		Properties p = new Properties();
		IteratorTool iteratorTool = new IteratorTool();
		context.put("mill", iteratorTool);
		p.setProperty("resource.loader", "class");
		p
				.setProperty("class.resource.loader.class",
						"org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
		Template template = null;
		Velocity.init(p);
		template = Velocity.getTemplate("template.vm");
		StringWriter sw = new StringWriter();
		// Merge
		template.merge(context, sw);
		// Return
		return sw.toString();
	}
}
