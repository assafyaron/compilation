package IR;
import java.util.*;
import TEMP.*;

public class IRcommand_New_Class extends IRcommand
{
	TEMP dst;
	public String class_name;
    int arg_cnt;
	int lineNumber;
	public String superClassName;
    
	public IRcommand_New_Class(TEMP dst, String name,int arg_cnt, int line, String superClassName) {
		this.lineNumber = line;
		this.class_name = name;
        this.arg_cnt = arg_cnt;
		this.dst = dst;
		this.superClassName = superClassName;
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
		System.out.println(dst.toString() + " := " + "new_class " + class_name);
	}

	public void MIPSme() {
		return;
	}
}
