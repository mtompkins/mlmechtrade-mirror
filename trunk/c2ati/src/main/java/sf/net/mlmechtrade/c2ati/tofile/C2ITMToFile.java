package sf.net.mlmechtrade.c2ati.tofile;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;

public class C2ITMToFile {

	private static final String BUY_TO_OPEN = "Buy to open";

	private static final String SELL_TO_CLOSE = "Sell to close";

	private static Logger log = Logger
			.getLogger("sf.net.mlmechtrade.c2ati.tofile.C2ITMToFile");

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		if (args.length < 2) {
			log
					.fatal("USAGE: java sf.net.mlmechtrade.c2ati.tofile.C2ITMToFile <InputFIleITMCOntent> <OutputDirectory>");
			System.exit(1);
		}
		C2ITMToFile command = new C2ITMToFile(args[0], args[1]);
		command.execute();
	}

	private String itmFile;

	private String outputDirectory;

	public C2ITMToFile(String itmFile, String outputDirectory) {
		this.itmFile = itmFile;
		this.outputDirectory = outputDirectory;
	}

	public void execute() throws IOException {
		List<String> inputLines = getInputLines();
		// BUY
		String buyFileContent = createOrderFileContent(inputLines, BUY_TO_OPEN);
		C2SignalsToFile outputer = new C2SignalsToFile(outputDirectory, null,
				true);
		outputer.toFile(buyFileContent, OutputFileTypeEnum.BUY,
				C2SignalsToFile.FILE_SUFFIX);
		// SELL
		String sellFileContent = createOrderFileContent(inputLines,
				SELL_TO_CLOSE);
		outputer.toFile(sellFileContent, OutputFileTypeEnum.SELL,
				C2SignalsToFile.FILE_SUFFIX);
	}

	public String createOrderFileContent(List<String> inputLines,
			String fragment) {
		List<String> orders = filter(inputLines, fragment);
		StringBuilder result = new StringBuilder();
		for (String buyLine : orders) {
			StringTokenizer st = new StringTokenizer(buyLine, "\t");

			// Skip until Quant
			while (!st.nextToken().equals(fragment)) {
			}

			// Parse the rest
			String quant = st.nextToken();
			String symbol = st.nextToken();
			String type = st.nextToken();
			StringTokenizer orderTypePrice = new StringTokenizer(type);
			String atSign = orderTypePrice.nextToken();
			assert (atSign.equals('@'));
			String orderType = orderTypePrice.nextToken();
			orderType = c2toTSOrderType(orderType);
			String price = "";
			if (!orderType.equals("Market")) {
				price = orderTypePrice.nextToken();
			}

			// Create output
			String outputRow = symbol + '\t' + quant + '\t' + orderType + '\t'
					+ price + '\n';
			result.append(outputRow);
		}
		return result.toString();
	}

	public String c2toTSOrderType(String orderType) {
		if (orderType.equals("limit")) {
			return "Limit";
		}
		if (orderType.equals("mkt")) {
			return "Market";
		}
		if (orderType.equals("stp")) {
			return "Stop";
		}
		return null;
	}

	/**
	 * Filter list of lines only containing specified fragmetnt
	 * 
	 * @param input
	 * @param fragment
	 * @return
	 */
	public List<String> filter(List<String> input, String fragment) {
		List<String> result = new ArrayList<String>();
		for (String inputLine : input) {
			if (inputLine.contains(fragment)) {
				result.add(inputLine);
			}
			;
		}
		return result;
	}

	/**
	 * Read inputFile and provide as list of lines
	 * 
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private List<String> getInputLines() throws FileNotFoundException,
			IOException {
		List<String> result = new ArrayList<String>(20);
		FileReader fr = new FileReader(itmFile);
		BufferedReader br = new BufferedReader(fr);
		String input = br.readLine();
		while (input != null) {
			result.add(input);
			input = br.readLine();
		}
		fr.close();
		return result;
	}
}
