package IR;
import java.util.*;
import TEMP.*;
import MIPS.*;

public class IRcommand_VirtualCall extends IRcommand {
	
    String methodName;
    List<TEMP> args;
    TEMP dst;
	TEMP object;
	int lineNumber;
	String className;

    public IRcommand_VirtualCall(TEMP dst,TEMP object,String methodName,List<TEMP> args,int line,String className) {
		this.className = className;
        this.methodName = methodName;
        this.args = args;
		this.lineNumber = line;
		this.object = object;
		this.dst = dst;
    }

	public Set<String> varsInvolved() {
		return new HashSet<>();
	}

	public Set<String> genVars() {
		Set<String> res = new HashSet<>();
		if (args != null) {
			for (TEMP arg : args) {
				res.add(arg.toString());
			}
		}
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

		if (args != null) {
			for (int i=0; i < args.size(); i++) {
				if (colors.containsKey(args.get(i).toString())) {
					args.set(i, TEMP_FACTORY.getInstance().getSpecialTEMP(colors.get(args.get(i).toString())));
				}
			}
		}
	}

	public void printIR(){
		if (args == null) {
			System.out.println(dst.toString()+":= "+object.toString()+" virtual_call "+methodName+"()");
		} else {
			System.out.print(dst.toString()+":= "+object.toString()+" virtual_call "+methodName+"(");
			for (TEMP arg : args) {
				System.out.print(arg.toString());
				if (args.indexOf(arg) != args.size() - 1) {
					System.out.print(",");
				}
			}
			System.out.println(")");
		}
	}

	public void MIPSme() {
		Map<String,Integer> virtualTable = MIPSGenerator.getInstance().classMethodsMap.get(className);
		String methodNameForFunc = null;
		for (String key : virtualTable.keySet()) {
			if (key.endsWith(methodName)) {
				methodNameForFunc = key;
				break;
			}
		}
		MIPSGenerator.getInstance().printVirtualTable(className);
		MIPSGenerator.getInstance().virtualCall(dst, object,methodNameForFunc,virtualTable,args);
	}
}
