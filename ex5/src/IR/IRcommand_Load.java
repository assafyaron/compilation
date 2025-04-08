
package IR;
import java.util.*;
import TEMP.*;
import MIPS.*;

public class IRcommand_Load extends IRcommand {

	TEMP dst;
	String var_name;
	int offset;
	int lineNumber;
	String dstLabeled;
	boolean isArg;
	boolean isGlobal;
	boolean isField;
	
	public IRcommand_Load(TEMP dst,String var_name,int line,int offset,boolean isArg, boolean isGlobal, boolean isField) {
		this.dst      = dst;
		this.var_name = var_name;
		this.lineNumber = line;
		this.dstLabeled = dst.toString()+";"+line;
		this.offset = offset;
		this.isArg = isArg;
		this.isGlobal = isGlobal;
		this.isField = isField;
	}

	public Set<String> varsInvolved() {
		Set<String> res = new HashSet<>();
		res.add(var_name);
		return res;
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
		if (isGlobal) {
			System.out.println(String.format("%s := load(%s)[lw %s,%s]", dst.toString(), var_name, dst.toString(), "g_"+var_name));
		} else if (!isArg) {
			System.out.println(String.format("%s := load(%s)[%s,($fp)]", dst.toString(), var_name, offset-40));
		} else {
			System.out.println(String.format("%s := load(%s)[%s,($fp)]", dst.toString(), var_name, offset));
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
					MIPSGenerator.getInstance().loadGlobal(dst,"g_"+var_name);
				} else if (!isArg) {
					MIPSGenerator.getInstance().load(dst,offset-40);
				} else {
					MIPSGenerator.getInstance().load(dst,offset+4);
				}
			} else {
				offset = fieldList.indexOf(field) * 4 + 4;
				MIPSGenerator.getInstance().loadField(dst,offset);
			}
		}
		else if (!MIPSGenerator.getInstance().inClass) {
			if (IR.getInstance().scopeLev != 0) {
				if (isGlobal) {
					MIPSGenerator.getInstance().loadGlobal(dst,"g_"+var_name);
				} else if (!isArg) {
					MIPSGenerator.getInstance().load(dst,offset-40);
				} else {
					MIPSGenerator.getInstance().load(dst,offset);
				}
			} else {
				if (isGlobal){
					MIPSGenerator.getInstance().loadGlobal(dst,"g_"+var_name);
				}
			}
		}
	}
	
}
