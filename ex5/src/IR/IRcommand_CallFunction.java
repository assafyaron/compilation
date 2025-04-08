package IR;
import java.util.*;
import TEMP.*;
import MIPS.*;

public class IRcommand_CallFunction extends IRcommand {
	
    String funcName;
    List<TEMP> args;
	int lineNumber;
	TEMP dst;

    public IRcommand_CallFunction(TEMP dst,String funcName,List<TEMP> args,int line) {
        this.funcName = funcName;
        this.args = args;
		this.lineNumber = line;
		this.dst = dst;
    }

	public Set<String> varsInvolved() {
		return new HashSet<>();
	}

	public Set<String> genVars() {
		Set<String> res = new HashSet<>();
		if (args == null) {
			return res;
		}
		for (TEMP arg : args) {
			res.add(arg.toString());
		}
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
		if (args == null) {
			return;
		}
		for (int i = 0; i < args.size(); i++) {
			if (colors.containsKey(args.get(i).toString())) {
				args.set(i, TEMP_FACTORY.getInstance().getSpecialTEMP(colors.get(args.get(i).toString())));
			}
		}
	}

	public void printIR(){
		if (args == null) {
			System.out.println(dst.toString()+" := call function: " + funcName + "()");
		} else {
			System.out.print(dst.toString()+" := call function: " + funcName + "(");
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
		int i = 0;
		if (MIPSGenerator.getInstance().currClassName != null){
			MIPSGenerator.getInstance().nestedVirtualCall(funcName, args, dst);
			return;
		}
		if (args != null) {
			for (int j = args.size() - 1; j >= 0; j--) {
				TEMP arg = args.get(j);
				MIPSGenerator.getInstance().subu("sp", 4);
				MIPSGenerator.getInstance().storeFuncArgs(arg, 0);
				i++;
			}

		}
		MIPSGenerator.getInstance().jal(funcName);
		MIPSGenerator.getInstance().addu("sp",4*i);
		MIPSGenerator.getInstance().moveRet(dst,true);
	}
}
