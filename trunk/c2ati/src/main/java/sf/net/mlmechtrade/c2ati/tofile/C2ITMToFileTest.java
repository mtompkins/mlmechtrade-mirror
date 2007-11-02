package sf.net.mlmechtrade.c2ati.tofile;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

public class C2ITMToFileTest extends TestCase {
	private C2ITMToFile fixture;

	public void setUp() {
		fixture = new C2ITMToFile("", "C:\\TEMP\\");
	}

	public void testFilter() {
		List<String> testInput = new ArrayList<String>();
		testInput.add("_ABC_");
		testInput.add("_AAA_");
		List result = fixture.filter(testInput, "AB");
		assertEquals(1, result.size());
		assertEquals("_ABC_", result.get(0));
	}

	public void testC2toTSOrderType() {
		assertEquals("Limit", fixture.c2toTSOrderType("limit"));
		assertEquals("Market", fixture.c2toTSOrderType("mkt"));
		assertEquals("Stop", fixture.c2toTSOrderType("stp"));
		assertNull(fixture.c2toTSOrderType(""));
	}

	public void testCreateOrderFileContent() {
		String testInput = "\t\t\tBuy to open\t945\tYHOO\t@ limit 25.80\tDAY\t";
		List<String> inputLines = new ArrayList<String>();
		inputLines.add(testInput);
		assertEquals("YHOO\t945\tLimit\t25.80\n", fixture.createOrderFileContent(
				inputLines, "Buy to open"));
	}
}
