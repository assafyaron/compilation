package IR;
import java.util.*;
import TEMP.*;
import MIPS.*;

public class IRcommand_Array_Access extends IRcommand {
	
	TEMP arr;
	TEMP offset;
    TEMP dst;
	int lineNumber;
    
	public IRcommand_Array_Access(TEMP dst,TEMP array_pointer,TEMP offset, int line) {
		this.lineNumber = line;
		this.dst = dst;
		this.arr = array_pointer;
        this.offset = offset;
	}

	public Set<String> genVars() {
        Set<String> res = new HashSet<>();
		res.add(arr.toString());
		res.add(offset.toString());
		return res;
    }

	public Set<String> killVars(int maxLine) {
		Set<String> res = new HashSet<>();
		res.add(dst.toString());
		return res;
	}

	public void replaceTempsWithColors(Map<String, Integer> colors) {
		if (colors.containsKey(arr.toString())) {
			arr = TEMP_FACTORY.getInstance().getSpecialTEMP(colors.get(arr.toString()));
		}
		if (colors.containsKey(offset.toString())) {
			offset = TEMP_FACTORY.getInstance().getSpecialTEMP(colors.get(offset.toString()));
		}
		if (colors.containsKey(dst.toString())) {
			dst = TEMP_FACTORY.getInstance().getSpecialTEMP(colors.get(dst.toString()));
		}
	}

	public Set<String> varsInvolved() {
		return new HashSet<>();
	}

	public void printIR() {
		System.out.println(dst.toString() + " := " + "array_access " + arr.toString() + "," + offset.toString());
	}

	public void MIPSme()
	{
		MIPSGenerator.getInstance().arrayAccess(dst,arr,offset);
	}
}
