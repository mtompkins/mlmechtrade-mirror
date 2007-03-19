/*
 * $Id$
 */
package net.sf.mlmechtrade.tafunc.generator;

import java.io.File;
import java.io.FileWriter;
import java.io.StringWriter;
import java.util.Properties;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.tools.generic.IteratorTool;
import org.apache.velocity.tools.generic.ListTool;

/**
 * Velocity-based file generator. 
 *
 */
public class VelocityGenerator {

	protected String templateFilename;
	private static Exception staticInitFailedException;
	
	public VelocityGenerator(String templateFilename) {
		this.templateFilename = templateFilename;
	}

	/**
	 * Creates and initializes Velocity context
	 */
	protected VelocityContext createContext() {
		
		VelocityContext context = new VelocityContext();
		Util util = new Util();
		context.put("util", util);
		ListTool listTool = new ListTool();
		context.put("list", listTool);
		IteratorTool iteratorTool = new IteratorTool();
		context.put("mill", iteratorTool);
		return context;
	}

	public void generate(String outputDirectoryName,
			String outputFileName) throws Exception {

		rethrowStaticInitError();
		
		System.out.println("File name: " + outputFileName);
		// Prepare output
		File outFile = new File(outputDirectoryName, outputFileName);
		FileWriter fos = new FileWriter(outFile);
		// Create content
		String fileContent = getContent();
		// Write and close
		fos.write(fileContent);
		fos.close();
	}

	
	private String getContent() throws Exception {
		return getContent(createContext());
	}

	private String getContent(VelocityContext context) throws Exception {
		StringWriter sw = new StringWriter();
		Template template = Velocity.getTemplate(getTemplateFilename());
		// Merge
		template.merge(context, sw);
		// Return
		return sw.toString();
	}

	/**
	 * Rethrows static{} initializer exception, if any
	 * @throws Exception
	 */
	protected void rethrowStaticInitError() throws Exception {
		if (staticInitFailedException != null) {
			throw staticInitFailedException;
		}
	}
	
	static {
		// Init Velocity 
		Properties p = new Properties();
		p.setProperty("resource.loader", "class");
		p.setProperty("class.resource.loader.class",
						"org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
		try {
			Velocity.init(p);
		} catch (Exception e) {
			staticInitFailedException = e;
		}
	}

	public String getTemplateFilename() {
		return templateFilename;
	}

}
