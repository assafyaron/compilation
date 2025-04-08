package IR;
import java.util.*;
import TEMP.*;
import MIPS.*;

public class IRcommand_Field_Access extends IRcommand {
	
    TEMP dst;
    TEMP object;
	String field_name;
	int lineNumber;
	String className;
    
	public IRcommand_Field_Access(TEMP dst,TEMP object,String field_name,int line,String className) {
		this.lineNumber = line;
        this.object = object;
		this.dst = dst;
		this.field_name = field_name;
		this.className = className;
	}

	public Set<String> varsInvolved() {
		return new HashSet<>();
	}

	public Set<String> genVars() {
		Set<String> res = new HashSet<>();
		res.add(object.toString());
		return res;
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
		if (colors.containsKey(object.toString())) {
			object = TEMP_FACTORY.getInstance().getSpecialTEMP(colors.get(object.toString()));
		}
	}

	public void printIR() {
		System.out.println(dst.toString()+" := "+"field_access "+object.toString()+" "+field_name);
	}

	public void MIPSme() {
		int offset = MIPSGenerator.getInstance().getFieldOffset(className,field_name);
		System.out.println("Class name: "+className+" Field name: "+field_name+" Field offset: " + offset);
		MIPSGenerator.getInstance().fieldAccess(dst,object,offset);
	}
}
