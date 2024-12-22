package AST;

public class AST_STMT_RETURN_EXP extends AST_STMT
{
    public AST_EXP e;

    /******************/
    /* CONSTRUCTOR(S) */
    /******************/
    public AST_STMT_RETURN_EXP(AST_EXP e)
    {
        /******************************/
        /* SET A UNIQUE SERIAL NUMBER */
        /******************************/
        SerialNumber = AST_Node_Serial_Number.getFresh();

        /***************************************/
        /* PRINT CORRESPONDING DERIVATION RULE */
        /***************************************/
        System.out.println("====================== stmt -> RETURN exp SEMICOLON\n");

        /*******************************/
        /* COPY INPUT DATA MEMBERS ... */
        /*******************************/
        this.e = e;
    }

    /***************************************************/
    /* The printing message for a return statement with expression AST node */
    /***************************************************/
    public void PrintMe()
    {
        /*********************************/
        /* AST NODE TYPE = STMT RETURN EXP */
        /*********************************/
        System.out.println("AST NODE STMT RETURN EXP");

        /******************************************/
        /* RECURSIVELY PRINT exp ... */
        /******************************************/
        if (e != null) e.PrintMe();

        /***************************************/
        /* PRINT Node to AST GRAPHVIZ DOT file */
        /***************************************/
        AST_GRAPHVIZ.getInstance().logNode(
            SerialNumber,
            "STMT\nRETURN EXP");

        /****************************************/
        /* PRINT Edges to AST GRAPHVIZ DOT file */
        /****************************************/
        if (e != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, e.SerialNumber);
    }
}
