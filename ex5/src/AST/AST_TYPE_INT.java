package AST;
import TYPES.*; 
import TEMP.*;

public class AST_TYPE_INT extends AST_TYPE
{
    /******************/
    /* CONSTRUCTOR(S) */
    /******************/
    public AST_TYPE_INT()
    {
        /******************************/
        /* SET A UNIQUE SERIAL NUMBER */
        /******************************/
        SerialNumber = AST_Node_Serial_Number.getFresh();

        /***************************************/
        /* PRINT CORRESPONDING DERIVATION RULE */
        /***************************************/
        System.out.println("====================== type -> TYPE_INT\n");

    }

    public void PrintMe()
    {
        /*********************************/
        /* AST NODE TYPE = TYPE INT */
        /*********************************/
        System.out.println("AST NODE TYPE INT");

        /***************************************/
        /* PRINT Node to AST GRAPHVIZ DOT file */
        /***************************************/
        AST_GRAPHVIZ.getInstance().logNode(
            SerialNumber,
            "TYPE\nINT");
    }

    public TYPE SemantMe()
    {
        // return TYPE_INT
    
        return TYPE_INT.getInstance();
    }

    public TEMP IRme()
    {
        // return null, no need to generate code for type
        return null;
    }
}
