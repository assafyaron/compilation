
package IR;

import java.util.*;
import TEMP.*;
import MIPS.*;

public class IRcommand_Store extends IRcommand {

	String var_name;
	TEMP src;
	int lineNumber;
	String varLabeled;
	int offset;
	boolean isArg;
	boolean isGlobal;
	boolean isField;
	
	public IRcommand_Store(String var_name,TEMP src,int line,int offset,boolean isArg,boolean isGlobal, boolean isField) {
		this.isField = isField;
		this.src      = src;
		this.var_name = var_name;
		this.lineNumber = line;
		this.varLabeled = var_name+";"+line;
		this.offset = offset;
		this.isArg = isArg;
		this.isGlobal = isGlobal;
	}

	public Set<String> varsInvolved() {
		Set<String> res = new HashSet<>();
		res.add(var_name);
		return res;
	}

	public Set<String> genVars() {
		Set<String> res = new HashSet<>();
		res.add(src.toString());
		return res;
	}

	public Set<String> killVars(int maxLine) {
		return new HashSet<>();
	}

	public void replaceTempsWithColors(Map<String, Integer> colors) {
		if (colors.containsKey(src.toString())) {
			src = TEMP_FACTORY.getInstance().getSpecialTEMP(colors.get(src.toString()));
		}
	}

	public void printIR() {
		if (isGlobal) {
			System.out.println(String.format("store(%s := %s)[sw %s,%s]", src.toString(), var_name, src.toString(), "g_"+var_name));
		} else if (!isArg) {
			System.out.println(String.format("store(%s := %s)[%s,($fp)]", src.toString(), var_name, offset-40));
		} else {
			System.out.println(String.format("store(%s := %s)[%s,($fp)]", src.toString(), var_name, offset));
		}
	}

	public void MIPSme()
	{

		if (isField) {

			String class_name = MIPSGenerator.getInstance().currClassName.substring(5);
			List<Field> fieldList = MIPSGenerator.getInstance().getFieldList(class_name);
			Field field = MIPSGenerator.getInstance().getField(fieldList,var_name);
			if (field == null) {
				if (isGlobal) {
					MIPSGenerator.getInstance().storeGlobal(src,"g_"+var_name);
				} else if (!isArg) {
					MIPSGenerator.getInstance().store(src,offset-40);
				} else {
					MIPSGenerator.getInstance().store(src,offset+4);
				}
			} else {
				offset = fieldList.indexOf(field) * 4 + 4;
				MIPSGenerator.getInstance().storeField(src,offset);
			}

		}
		else if (!MIPSGenerator.getInstance().inClass) {
			if (IR.getInstance().scopeLev != 0) {
				if (isGlobal) {
					MIPSGenerator.getInstance().storeGlobal(src,"g_"+var_name);
				} else if (!isArg) {
					MIPSGenerator.getInstance().store(src,offset-40);
				} else {
					MIPSGenerator.getInstance().store(src,offset);
				}
			} else {
				if (isGlobal){
					MIPSGenerator.getInstance().storeGlobal(src,"g_"+var_name);
				}
			}
		}
	}
}
