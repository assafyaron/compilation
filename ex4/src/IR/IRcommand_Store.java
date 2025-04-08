
package IR;

import java.util.*;
import TEMP.*;

public class IRcommand_Store extends IRcommand {

	String var_name;
	TEMP src;
	int lineNumber;
	String varLabeled;
	
	public IRcommand_Store(String var_name,TEMP src,int line) {
		this.src      = src;
		this.var_name = var_name;
		this.lineNumber = line;
		this.varLabeled = var_name+";"+line;
	}

	public Set<String> genVars() {
		return new HashSet<>(Arrays.asList(varLabeled));
	}

	public Set<String> killVars(int maxLine) {
		List<String> arr = new ArrayList<>();
		for (int j=0;j<maxLine;j++) {
			if (j != this.lineNumber) {
				arr.add(var_name+";"+j);
			}
		}
		arr.add(var_name+";?");
		return new HashSet<>(arr);
	}

	public void printIR() {
		System.out.println(String.format("store(%s := %s)", var_name, src.toString()));
	}


}
