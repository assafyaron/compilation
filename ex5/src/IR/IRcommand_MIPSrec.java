package IR;
import java.util.*;
import TEMP.*;
import MIPS.*;

public class IRcommand_MIPSrec extends IRcommand {
	
	public IRcommand_MIPSrec() {}

	public Set<String> genVars() {return null;}

	public Set<String> killVars(int maxLine) {return null;}

	public void replaceTempsWithColors(Map<String, Integer> colors) {}

	public void printIR() {
        System.out.println("MIPSrec");
    }

	public Set<String> varsInvolved() {return null;}

	public void MIPSme()
	{
		MIPSGenerator.getInstance().allocGlobals();
	}
}
