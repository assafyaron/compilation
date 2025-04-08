package IR;
import java.util.*;
import TEMP.*;
import MIPS.*;

public class IRcommand_Field_Set extends IRcommand {
	
	TEMP val;
    TEMP dst;
	String field_name;
	int lineNumber;
	String className;
    
	public IRcommand_Field_Set(TEMP dst,TEMP val,String field_name,int line,String className) {
		this.lineNumber = line;
		this.dst = dst;
		this.val = val;
		this.field_name = field_name;
		this.className = className;
	}

	public Set<String> varsInvolved() {
		return new HashSet<>();
	}

	public Set<String> genVars() {
		Set<String> res = new HashSet<>();
		res.add(val.toString());
		res.add(dst.toString());
		return res;
	}

	public Set<String> killVars(int maxLine) {
		return new HashSet<>();
	}

	public void replaceTempsWithColors(Map<String, Integer> colors) {
		if (colors.containsKey(dst.toString())) {
			dst = TEMP_FACTORY.getInstance().getSpecialTEMP(colors.get(dst.toString()));
		}
		if (colors.containsKey(val.toString())) {
			val = TEMP_FACTORY.getInstance().getSpecialTEMP(colors.get(val.toString()));
		}
	}

	public void printIR() {
		System.out.println("field_set "+dst.toString()+" "+field_name+" "+val.toString());
	}

	public void MIPSme() {
		int offset = MIPSGenerator.getInstance().getFieldOffset(className,field_name);
		MIPSGenerator.getInstance().fieldSet(dst,val,offset);
	}
}
