package IR;
import java.util.*;
import TEMP.*;
import MIPS.*;

public class IRcommand_Return extends IRcommand {

    TEMP return_value;
    int lineNumber;
    
    public IRcommand_Return(TEMP return_value,int line) {
        this.return_value = return_value;
        this.lineNumber = line;
    }

    public Set<String> varsInvolved() {
		return new HashSet<>();
	}

    public Set<String> genVars() {
		Set<String> res = new HashSet<>();
        if (return_value != null) {
            res.add(return_value.toString());
        }
		return res;
	}

	public Set<String> killVars(int maxLine) {
		return new HashSet<>();
	}

    public void replaceTempsWithColors(Map<String, Integer> colors) {
        if (return_value == null) {
            return;
        }
		if (colors.containsKey(return_value.toString())) {
			return_value = TEMP_FACTORY.getInstance().getSpecialTEMP(colors.get(return_value.toString()));
		}
	}

    public void printIR() {
        if (return_value == null) {
            System.out.println("return");
            return;
        } else {
            System.out.println("return " + return_value.toString());
        }
    }

    public void MIPSme() {

        if (IR.getInstance().scopeLev != 0) {
            MIPSGenerator.getInstance().moveRet(return_value,false);
        }
	}
}
