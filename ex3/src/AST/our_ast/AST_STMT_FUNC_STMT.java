package AST;

public class AST_STMT_FUNC_STMT extends AST_STMT
{
    public AST_FUNC_STMT f;

    /******************/
    /* CONSTRUCTOR(S) */
    /******************/
    public AST_STMT_FUNC_STMT(AST_FUNC_STMT f)
    {
        /******************************/
        /* SET A UNIQUE SERIAL NUMBER */
        /******************************/
        SerialNumber = AST_Node_Serial_Number.getFresh();

        /***************************************/
        /* PRINT CORRESPONDING DERIVATION RULE */
        /***************************************/
        System.out.println("====================== stmt-> funcStmt");

        /*******************************/
        /* COPY INPUT DATA MEMBERS ... */
        /*******************************/
        this.f = f;
    }

    public void PrintMe()
    {
        /*********************************/
        /* AST NODE TYPE = STMT FUNC STMT */
        /*********************************/
        System.out.println("AST NODE STMT FUNC STMT");

        /******************************************/
        /* RECURSIVELY PRINT  ... */
        /******************************************/
        if (f != null) f.PrintMe();

        /***************************************/
        /* PRINT Node to AST GRAPHVIZ DOT file */
        /***************************************/
        AST_GRAPHVIZ.getInstance().logNode(
            SerialNumber,
            "STMT\nFUNC STMT");

        /****************************************/
        /* PRINT Edges to AST GRAPHVIZ DOT file */
        /****************************************/
        if (f != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, f.SerialNumber);
    }
}
