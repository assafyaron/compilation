package IR;
import java.util.*;
import TEMP.*;
import MIPS.*;

public class IRcommandConstInt extends IRcommand {
	
	TEMP t;
	int value;
	int lineNumber;
	String tLabeled;
	
	public IRcommandConstInt(TEMP t,int value,int line) {
		this.lineNumber = line;
		this.t = t;
		this.value = value;
		this.tLabeled = t.toString()+";"+line;
	}

	public Set<String> varsInvolved() {
		return new HashSet<>();
	}

	public Set<String> genVars() {
		return new HashSet<>();
	}

	public Set<String> killVars(int maxLine) {
		Set<String> res = new HashSet<>();
		res.add(t.toString());
		return res;
	}

	public void replaceTempsWithColors(Map<String, Integer> colors) {
		if (colors.containsKey(t.toString())) {
			t = TEMP_FACTORY.getInstance().getSpecialTEMP(colors.get(t.toString()));
		}
	}

	public void printIR() {
		System.out.println(t.toString() + " := " + String.format("%d", value));
	}

	public void MIPSme()
	{
		if (IR.getInstance().scopeLev != 0 && !MIPSGenerator.getInstance().inClass) {
			MIPSGenerator.getInstance().li(t,value);
		}
	}
}
