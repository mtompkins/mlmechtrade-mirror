package sf.net.mlmechtrade.c2ati;

import java.io.IOException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.apache.commons.httpclient.HttpException;
import org.xml.sax.SAXException;

import sf.net.mlmechtrade.c2ati.C2ATIAPI;
import sf.net.mlmechtrade.c2ati.C2ATIError;
import sf.net.mlmechtrade.c2ati.domain.AssetEnum;
import sf.net.mlmechtrade.c2ati.domain.C2Position;
import sf.net.mlmechtrade.c2ati.domain.C2SystemState;
import junit.framework.TestCase;

public class C2ATISyncTest extends TestCase {
	C2ATIAPI fixture;

	public void setUp() throws IOException {
		fixture = new C2ATIMockImpl();
	}

	public void testRequestSystemSync() throws HttpException,
			XPathExpressionException, IOException,
			ParserConfigurationException, SAXException, C2ATIError {
		List<C2SystemState> systemSync = fixture.requestSystemSync();
		assertNotNull(systemSync);
		assertEquals(2, systemSync.size());
		// 1
		C2SystemState systemState = systemSync.get(0);
		assertEquals(158523, systemState.getSystemId());
		assertEquals("YYY", systemState.getSystemName());
		assertEquals(1181841762, systemState.getTimeFilterSecs());
		assertEquals("2007-06-14 13:22:42", systemState.getTimeFilterClock());
		List<C2Position> positions = systemState.getPositions();
		assertEquals(2, positions.size());
		C2Position position = positions.get(0);
		assertEquals("CCCC", position.getSymbol());
		assertEquals(AssetEnum.stock, position.getAssetType());
		assertEquals(false, position.isFund());
		assertEquals(132, position.getQuant());
		assertEquals("", position.getUnderlying());
		assertEquals("", position.getRight());
		assertEquals("0", position.getStrike());
		assertEquals("", position.getExpir());
		assertEquals("", position.getExchange());
		assertEquals("1", position.getMarketCode());
		position = positions.get(1);
		assertEquals("YHOO", position.getSymbol());
		// 2
		systemState = systemSync.get(1);
		assertEquals("GGGG", systemState.getSystemName());
		positions =  systemState.getPositions();
		assertEquals(1, positions.size());
		position = positions.get(0);
		assertEquals(true, position.isFund());
	}
}
