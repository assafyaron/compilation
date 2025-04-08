package IR;
import java.util.*;
import TEMP.*;
import MIPS.*;

public class IRcommandConstNil extends IRcommand {
	
	int lineNumber;
    TEMP dst;
	
	public IRcommandConstNil(TEMP dst,int line) {
		this.lineNumber = line;
        this.dst = dst;
	}

	public Set<String> varsInvolved() {
		return new HashSet<>();
	}

	public Set<String> genVars() {
		return new HashSet<>();
	}

	public Set<String> killVars(int maxLine) {
		Set<String> res = new HashSet<>();
		res.add(dst.toString());
		return res;
	}

	public void replaceTempsWithColors(Map<String, Integer> colors) {
		if (colors.containsKey(dst.toString())) {
			dst = TEMP_FACTORY.getInstance().getSpecialTEMP(colors.get(dst.toString()));
		}
	}

	public void printIR() {
		System.out.println(dst.toString() + " := nil");
	}

	public void MIPSme() {
		if (IR.getInstance().scopeLev != 0 && !MIPSGenerator.getInstance().inClass) {
			MIPSGenerator.getInstance().assignConstNil(dst);
			return;
		}
	}
}
