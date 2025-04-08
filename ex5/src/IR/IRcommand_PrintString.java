package IR;
import java.util.*;
import TEMP.*;
import MIPS.*;

public class IRcommand_PrintString extends IRcommand {

	List<TEMP> t;
	int lineNumber;
	
	public IRcommand_PrintString(List<TEMP> t,int line) {
		this.t = t;
		this.lineNumber = line;
	}

	public Set<String> varsInvolved() {
		return new HashSet<>();
	}

	public Set<String> genVars() {
		Set<String> res = new HashSet<>();
		for (TEMP arg : t) {
			res.add(arg.toString());
		}
		return res;
	}

	public Set<String> killVars(int maxLine) {
		return new HashSet<>();
	}

	public void replaceTempsWithColors(Map<String, Integer> colors) {
		if (colors.containsKey(t.get(0).toString())) {
			t.set(0, TEMP_FACTORY.getInstance().getSpecialTEMP(colors.get(t.get(0).toString())));
		}
	}

	public void printIR() {
		if (t == null) {
			System.out.println("PrintString(null)");
			return;
		} else {
			System.out.println("PrintString(" + t.get(0).toString() + ")");
		}
	}

	public void MIPSme() 
	{
		MIPSGenerator.getInstance().print_string(t.get(0));
	}

}
