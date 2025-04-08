package IR;
import java.util.*;
import TEMP.*;

public class IRcommand_PrintInt extends IRcommand {

	TEMP t;
	int lineNumber;
	
	public IRcommand_PrintInt(TEMP t,int line) {
		this.t = t;
		this.lineNumber = line;
	}

	public Set<String> genVars() {
		return new HashSet<>();
	}

	public Set<String> killVars(int maxLine) {
		return new HashSet<>();
	}

	public void printIR() {
		System.out.println("call PrintInt(" + t.toString() + ")");
	}

}
