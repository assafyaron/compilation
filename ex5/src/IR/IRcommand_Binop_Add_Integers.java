package IR;
import java.util.*;
import TEMP.*;
import MIPS.*;

public class IRcommand_Binop_Add_Integers extends IRcommand {
	
	public TEMP t1;
	public TEMP t2;
	public TEMP dst;
	int lineNumber;
	String dstLabeled;
	
	public IRcommand_Binop_Add_Integers(TEMP dst,TEMP t1,TEMP t2,int line){
		this.isBinop = true;
		this.dst = dst;
		this.t1 = t1;
		this.t2 = t2;
		this.lineNumber = line;
		this.dstLabeled = dst.toString()+";"+line;
	}

	public Set<String> varsInvolved() {
		return new HashSet<>();
	}

	public Set<String> genVars() {
		Set<String> res = new HashSet<>();
		res.add(t1.toString());
		res.add(t2.toString());
		return res;
	}

	public Set<String> killVars(int maxLine) {
		Set<String> res = new HashSet<>();
		res.add(dst.toString());
		return res;
	}

	public void replaceTempsWithColors(Map<String, Integer> colors) {
		if (colors.containsKey(t1.toString())) {
			t1 = TEMP_FACTORY.getInstance().getSpecialTEMP(colors.get(t1.toString()));
		}
		if (colors.containsKey(t2.toString())) {
			t2 = TEMP_FACTORY.getInstance().getSpecialTEMP(colors.get(t2.toString()));
		}
		if (colors.containsKey(dst.toString())) {
			dst = TEMP_FACTORY.getInstance().getSpecialTEMP(colors.get(dst.toString()));
		}
	}
	
	public void printIR(){
		if (t1 == null && t2 != null) {
			System.out.println(dst.toString() + " := " + t2.toString());
			return;
		} else if (t2 == null && t1 != null) {
			System.out.println(dst.toString() + " := " + t1.toString());
			return;
		} else if (t1 == null && t2 == null) {
			System.out.println(dst.toString());
			return;
		} else {
			System.out.println(dst.toString() + " := " + t1.toString() + " + " + t2.toString());
		}
	}

	public void MIPSme()
	{
		MIPSGenerator.getInstance().add(dst,t1,t2);
	}
}
