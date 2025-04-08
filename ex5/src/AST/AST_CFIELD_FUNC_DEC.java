package AST;
import TYPES.*;
import TEMP.*;
import IR.*;

public class AST_CFIELD_FUNC_DEC extends AST_CFIELD
{
    public AST_FUNC_DEC func_dec;
    public boolean inClass = false;

    /******************/
    /* CONSTRUCTOR(S) */
    /******************/
    public AST_CFIELD_FUNC_DEC(AST_FUNC_DEC func_dec)
    {
        /******************************/
        /* SET A UNIQUE SERIAL NUMBER */
        /******************************/
        SerialNumber = AST_Node_Serial_Number.getFresh();

        /***************************************/
        /* PRINT CORRESPONDING DERIVATION RULE */
        /***************************************/
        System.out.println("====================== cField -> funcDec");

        /*******************************/
        /* COPY INPUT DATA MEMBERS ... */
        /*******************************/
        this.func_dec = func_dec;
    }

    /***************************************************/
    /* The printing message for a class field function declaration AST node */
    /***************************************************/
    public void PrintMe()
    {
        /*********************************/
        /* AST NODE TYPE = CLASS FIELD FUNCTION DECLARATION */
        /*********************************/
        System.out.println("AST NODE CLASS FIELD FUNCTION DECLARATION");

        /******************************************/
        /* RECURSIVELY PRINT func_dec ... */
        /******************************************/
        if (func_dec != null) func_dec.PrintMe();

        /***************************************/
        /* PRINT Node to AST GRAPHVIZ DOT file */
        /***************************************/
        AST_GRAPHVIZ.getInstance().logNode(
            SerialNumber,
            "CLASS FIELD\nFUNCTION DECLARATION");

        /****************************************/
        /* PRINT Edges to AST GRAPHVIZ DOT file */
        /****************************************/
        if (func_dec != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, func_dec.SerialNumber);
    }

    public TYPE SemantMe()
    {
        if(func_dec != null)
        {
            return func_dec.SemantMe();
        }
        return null;
    }

    public TEMP IRme()
    {
        if(func_dec != null)
        {
            this.inClass = true;
            if (func_dec instanceof AST_FUNC_DEC_ARGS) {
                AST_FUNC_DEC_ARGS func_dec_args = (AST_FUNC_DEC_ARGS) func_dec;
                func_dec_args.IRme(true);
            } else if (func_dec instanceof AST_FUNC_DEC_NO_ARGS) {
                AST_FUNC_DEC_NO_ARGS func_dec_no_args = (AST_FUNC_DEC_NO_ARGS) func_dec;
                func_dec_no_args.IRme(true);
            } else {
                return func_dec.IRme(); 
            }
        }
        return null;
    }
}
