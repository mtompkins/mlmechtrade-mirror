package sf.net.mlmechtrade;

import java.util.ArrayList;
import java.util.List;

public class C2ValidationResult {
	
	public static final C2ValidationResult OK = new C2ValidationResult();
	
	private List<String> errorList = new ArrayList<String>();

	public boolean hasErrors() {
		return errorList.isEmpty();
	}

	public List<String> getErrorList() {
		return errorList;
	}

}
