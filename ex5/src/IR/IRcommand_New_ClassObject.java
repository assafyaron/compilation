package IR;
import java.util.*;
import TEMP.*;
import MIPS.*;

public class IRcommand_New_ClassObject extends IRcommand
{
	TEMP dst;
	String class_name;
	int lineNumber;
	boolean isGlobal;
    
	public IRcommand_New_ClassObject(TEMP dst, String name, int line, boolean isGlobal) {
		this.lineNumber = line;
		this.class_name = name;
		this.dst = dst;
		this.isGlobal = isGlobal;
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
		System.out.println(dst.toString() + " := " + "new_" + class_name);
	}

	public void MIPSme() {
		Map<String, String> extendsMap = IR.getInstance().extendMap;
		List<Field> fieldList = MIPSGenerator.getInstance().getFieldList(class_name);
		MIPSGenerator.getInstance().newClassObject(dst, class_name, fieldList);
	}
}
