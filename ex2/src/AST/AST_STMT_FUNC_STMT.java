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
        System.out.println("====================== stmt -> callFunc SEMICOLON");

        /*******************************/
        /* COPY INPUT DATA MEMBERS ... */
        /*******************************/
        this.f = f;
    }

    /***************************************************/
    /* The printing message for a function call statement AST node */
    /***************************************************/
    public void PrintMe()
    {
        /*********************************/
        /* AST NODE TYPE = FUNCTION CALL STATEMENT */
        /*********************************/
        System.out.println("AST NODE FUNCTION CALL STATEMENT");

        /******************************************/
        /* RECURSIVELY PRINT callFunc ... */
        /******************************************/
        if (f != null) f.PrintMe();

        /***************************************/
        /* PRINT Node to AST GRAPHVIZ DOT file */
        /***************************************/
        AST_GRAPHVIZ.getInstance().logNode(
            SerialNumber,
            "FUNCTION CALL STATEMENT");

        /****************************************/
        /* PRINT Edges to AST GRAPHVIZ DOT file */
        /****************************************/
        if (f != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, f.SerialNumber);
    }
}