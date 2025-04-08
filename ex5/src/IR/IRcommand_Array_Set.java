package IR;
import java.util.*;
import TEMP.*;
import MIPS.*;

public class IRcommand_Array_Set extends IRcommand {
	
	TEMP arr;
	TEMP offset;
    TEMP value;
	int lineNumber;
    
	public IRcommand_Array_Set(TEMP array_pointer,TEMP offset, int line, TEMP value) {
		this.lineNumber = line;
		this.arr = array_pointer;
        this.offset = offset;
        this.value = value;
	}

	public Set<String> varsInvolved() {
		return new HashSet<>();
	}

	public Set<String> genVars() {
        Set<String> res = new HashSet<>();
		res.add(offset.toString());
		if (value != null) {
			res.add(value.toString());
		}
		res.add(arr.toString());
		return res;
    }

	public Set<String> killVars(int maxLine) {
		return new HashSet<>();
	}

	public void replaceTempsWithColors(Map<String, Integer> colors) {
		if (colors.containsKey(arr.toString())) {
			arr.serial = colors.get(arr.toString());
		}
		if (colors.containsKey(offset.toString())) {
			offset.serial = colors.get(offset.toString());
		}
		if (colors.containsKey(value.toString())) {
			value.serial = colors.get(value.toString());
		}
	}

	public void printIR() {
		System.out.println("array_set "+arr.toString()+","+offset.toString()+","+value.toString());
	}

	public void MIPSme()
	{
		MIPSGenerator.getInstance().arraySet(arr,offset,value);
	}
}
