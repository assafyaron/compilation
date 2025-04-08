package IR;
import java.util.*;
import TEMP.*;
import MIPS.*;

public class IRcommand_Label extends IRcommand {

	String label_name;
	int lineNumber;
	boolean isScopeStart;
	boolean isFuncLbl;
	boolean isClasslbl;
	boolean isClassMethod;
	List<String> argList;
	
	public IRcommand_Label(String label_name,int line,boolean isScopeStart,boolean isFuncLbl,boolean isClasslbl,List<String> argList,boolean isClassMethod)
	{
		this.label_name = label_name;
		this.lineNumber = line;
		this.isScopeStart = isScopeStart;
		this.isFuncLbl = isFuncLbl;
		this.isClasslbl = isClasslbl;
		this.argList = argList;
		this.isClassMethod = isClassMethod;
	}

	public Set<String> varsInvolved() {
		return new HashSet<>();
	}

	public Set<String> genVars() {
		return new HashSet<>();
	}

	public Set<String> killVars(int maxLine) {
		return new HashSet<>();
	}

	public void replaceTempsWithColors(Map<String, Integer> colors) {
		return;
	}

	public void printIR() {
		System.out.println("===" + label_name + "===");
		if (!isScopeStart) {
			System.out.println();
		}
	}

	public void MIPSme()
	{
		String mipsLabel = label_name.split("Label_\\d+_")[1].split("\\(")[0];
		if (isScopeStart) {
			IR.getInstance().scopeLev++;
			if (isFuncLbl) {
				Set<String> vars = IR.getInstance().controlGraph.getVarnum(this);
				if (isClassMethod) {
					mipsLabel = MIPSGenerator.getInstance().currClassName + "_" + mipsLabel;
					List<Field> fieldList = MIPSGenerator.getInstance().getFieldList(MIPSGenerator.getInstance().currClassName.substring(5));
					for (Field field : fieldList) {
						if (vars.contains(field.name)) {
							vars.remove(field.name);
						}
					}
				}
				if (argList != null) {
					for (String arg : argList) {
						if (vars.contains(arg)) {
							vars.remove(arg);
						}
					}
				}
				
				int varnum = vars.size();
				MIPSGenerator.getInstance().currFuncName = mipsLabel;
				MIPSGenerator.getInstance().addPrologue(mipsLabel,varnum,isClassMethod);
				MIPSGenerator.getInstance().inClass = false;
			} else {
				if (isClasslbl) {
					MIPSGenerator.getInstance().inClass = true;
					MIPSGenerator.getInstance().currClassName = mipsLabel.split("_")[0];
				} else {
					MIPSGenerator.getInstance().label(label_name);
				}
			}
		} else {
			IR.getInstance().scopeLev--;
			if (isFuncLbl) {
				MIPSGenerator.getInstance().currFuncName = null;
				if (isClassMethod) {
					MIPSGenerator.getInstance().inClass = true;
					mipsLabel = MIPSGenerator.getInstance().currClassName + "_" + mipsLabel;
				}
				MIPSGenerator.getInstance().addEpilogue(mipsLabel.substring(0,mipsLabel.length()-4)+"_epilogue");
			} else {
				if (isClasslbl) {
					MIPSGenerator.getInstance().inClass = false;
					MIPSGenerator.getInstance().currClassName = null;
				} else {
					MIPSGenerator.getInstance().label(label_name);
				}
			}
		}
	}
}
