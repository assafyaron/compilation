package IR;
import java.util.*;
import TEMP.*;
import MIPS.*;

public class IRcommandConstString extends IRcommand {
	
	TEMP t;
	String str;
	int lineNumber;
	
	public IRcommandConstString(TEMP t,String str,int line) {
		this.lineNumber = line;
		this.t = t;
		this.str = str;
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
		System.out.println(t.toString() + " := " + this.str);
	}

	public void MIPSme()
	{
		if (IR.getInstance().scopeLev != 0 && !MIPSGenerator.getInstance().inClass) {
			MIPSGenerator.getInstance().loadConstStr(t,str.replaceAll("\"","")+"_str");
		}
	}
}
