package AST;
import TYPES.*;
import TEMP.*;

public class AST_DEC_FUNC_DEC extends AST_DEC
{
    
    public AST_FUNC_DEC func_dec;

    /******************/
    /* CONSTRUCTOR(S) */
    /******************/
    public AST_DEC_FUNC_DEC(AST_FUNC_DEC func_dec)
    {
        /******************************/
        /* SET A UNIQUE SERIAL NUMBER */
        /******************************/
        SerialNumber = AST_Node_Serial_Number.getFresh();

        /***************************************/
        /* PRINT CORRESPONDING DERIVATION RULE */
        /***************************************/
        System.out.format("====================== dec -> funcDec\n");

        /*******************************/
        /* COPY INPUT DATA MEMBERS ... */
        /*******************************/
        this.func_dec = func_dec;
    }

    public void PrintMe()
    {
        /*********************************/
        /* AST NODE TYPE = FUNC DEC*/
        /*********************************/
        System.out.println("AST NODE FUNC DEC");

        /******************************************/
        /* RECURSIVELY PRINT func_dec ... */
        /******************************************/
        if (func_dec != null) func_dec.PrintMe();

        /***************************************/
        /* PRINT Node to AST GRAPHVIZ DOT file */
        /***************************************/
        AST_GRAPHVIZ.getInstance().logNode(
            SerialNumber,
            "DEC\nFUNC DEC");

        /****************************************/
        /* PRINT Edges to AST GRAPHVIZ DOT file */
        /****************************************/
        if (func_dec != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, func_dec.SerialNumber);
    }

	public TYPE SemantMe()
	{
        // semant the function decleration
		if (func_dec != null) 
        {
			return func_dec.SemantMe();
		}
		return null;
	}

    public TEMP IRme()
    {
        if (func_dec != null) 
        {
            if (func_dec instanceof AST_FUNC_DEC_ARGS) {
                AST_FUNC_DEC_ARGS func_dec_args = (AST_FUNC_DEC_ARGS) func_dec;
                return func_dec_args.IRme(false);
            } else if (func_dec instanceof AST_FUNC_DEC_NO_ARGS) {
                AST_FUNC_DEC_NO_ARGS func_dec_no_args = (AST_FUNC_DEC_NO_ARGS) func_dec;
                return func_dec_no_args.IRme(false);
            } else {
                return func_dec.IRme();
            }
        }
        return null;
    }
}
