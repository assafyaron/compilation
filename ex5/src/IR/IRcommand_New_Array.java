package IR;
import java.util.*;
import TEMP.*;
import MIPS.*;

public class IRcommand_New_Array extends IRcommand {
	
	TEMP dst;
	TEMP length;
	int lineNumber;
    boolean isGlobal;
	
	public IRcommand_New_Array(TEMP dst,TEMP length,int line, boolean isGlobal) {
		this.lineNumber = line;
		this.dst = dst;
		this.length = length;
        this.isGlobal = isGlobal;
	}

	public Set<String> varsInvolved() {
		return new HashSet<>();
	}

	public Set<String> genVars() {
		Set<String> res = new HashSet<>();
		res.add(length.toString());
		res.add(dst.toString());
		return res;
	}

	public Set<String> killVars(int maxLine) {
		return new HashSet<>();
		// res.add(dst.toString());
		// return res;
	}

	public void replaceTempsWithColors(Map<String, Integer> colors) {
		if (colors.containsKey(length.toString())) {
			length = TEMP_FACTORY.getInstance().getSpecialTEMP(colors.get(length.toString()));
		}
		if (colors.containsKey(dst.toString())) {
			dst = TEMP_FACTORY.getInstance().getSpecialTEMP(colors.get(dst.toString()));
		}
	}

	public void printIR() {
		System.out.println(dst.toString() + " := " + "new_array " + length.toString());
	}

	public void MIPSme()
	{
		MIPSGenerator.getInstance().newArray(dst, length, isGlobal);
	}
}
