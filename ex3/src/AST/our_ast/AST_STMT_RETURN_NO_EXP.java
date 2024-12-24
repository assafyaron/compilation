package AST;

public class AST_STMT_RETURN_NO_EXP extends AST_STMT
{

    /******************/
    /* CONSTRUCTOR(S) */
    /******************/
    public AST_STMT_RETURN_NO_EXP()
    {
        /******************************/
        /* SET A UNIQUE SERIAL NUMBER */
        /******************************/
        SerialNumber = AST_Node_Serial_Number.getFresh();

        /***************************************/
        /* PRINT CORRESPONDING DERIVATION RULE */
        /***************************************/
        System.out.println("====================== stmt -> RETURN SEMICOLON\n");

    }

    public void PrintMe()
    {
        /*********************************/
        /* AST NODE TYPE = STMT RETURN NO EXP */
        /*********************************/
        System.out.println("AST NODE STMT RETURN NO EXP");

        /***************************************/
        /* PRINT Node to AST GRAPHVIZ DOT file */
        /***************************************/
        AST_GRAPHVIZ.getInstance().logNode(
            SerialNumber,
            "STMT\nRETURN NO EXP");

        /****************************************/
        /* PRINT Edges to AST GRAPHVIZ DOT file */
        /****************************************/
    }
}
