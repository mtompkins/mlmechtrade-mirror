package sf.net.mlmechtrade;

import java.io.InputStream;

public interface C2ResponseParser {

	C2Response parse(InputStream is);
}
