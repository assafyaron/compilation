package AST;
import TYPES.*;
import TEMP.TEMP;
import IR.*;
import TEMP.*;

public class AST_EXP_FUNCTION extends AST_EXP
{

    public AST_FUNCTION funcExp;

    public AST_EXP_FUNCTION(AST_FUNCTION funcExp) {
        /* SET A UNIQUE SERIAL NUMBER */
        SerialNumber = AST_Node_Serial_Number.getFresh();

        /* PRINT CORRESPONDING DERIVATION RULE */
        System.out.format("====================== exp -> fuction\n");

        /* COPY INPUT DATA MEMBERS ... */
        this.funcExp = funcExp;
    }

    public void PrintMe() {
        /* AST NODE TYPE = EXP FUNCTION */
        System.out.println("AST NODE EXP FUNCTION");

        /* RECURSIVELY PRINT f ... */
        if (funcExp != null) funcExp.PrintMe();

        /* PRINT Node to AST GRAPHVIZ DOT file */
        AST_GRAPHVIZ.getInstance().logNode(
            SerialNumber,
            "EXP\nFUNCTION");

        /* PRINT Edges to AST GRAPHVIZ DOT file */
        if (funcExp != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, funcExp.SerialNumber);
    }

    public TYPE SemantMe() {
        // semant the function call
		if(funcExp != null) {
            TYPE t = funcExp.SemantMe();
            typeName = t.name;
            return t;
        }
        return null;
	}

    public TEMP IRme() 
    {
        if (funcExp != null)
        {
            return funcExp.IRme();
        }
        return null;
    }
}
