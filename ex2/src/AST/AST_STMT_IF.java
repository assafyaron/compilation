package AST;

public class AST_STMT_IF extends AST_STMT
{
    public AST_EXP cond;
    public AST_STMT_LIST body;

    /******************/
    /* CONSTRUCTOR(S) */
    /******************/
    public AST_STMT_IF(AST_EXP cond, AST_STMT_LIST body)
    {
        /******************************/
        /* SET A UNIQUE SERIAL NUMBER */
        /******************************/
        SerialNumber = AST_Node_Serial_Number.getFresh();

        /***************************************/
        /* PRINT CORRESPONDING DERIVATION RULE */
        /***************************************/
        System.out.println("====================== stmt -> IF LPAREN exp RPAREN LBRACE stmtList RBRACE");

        /*******************************/
        /* COPY INPUT DATA MEMBERS ... */
        /*******************************/
        this.cond = cond;
        this.body = body;
    }

    /***************************************************/
    /* The printing message for an if statement AST node */
    /***************************************************/
    public void PrintMe()
    {
        /*********************************/
        /* AST NODE TYPE = IF STATEMENT */
        /*********************************/
        System.out.println("AST NODE IF STATEMENT");

        /******************************************/
        /* RECURSIVELY PRINT cond and body ... */
        /******************************************/
        if (cond != null) cond.PrintMe();
        if (body != null) body.PrintMe();

        /***************************************/
        /* PRINT Node to AST GRAPHVIZ DOT file */
        /***************************************/
        AST_GRAPHVIZ.getInstance().logNode(
            SerialNumber,
            "IF STATEMENT");

        /****************************************/
        /* PRINT Edges to AST GRAPHVIZ DOT file */
        /****************************************/
        if (cond != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, cond.SerialNumber);
        if (body != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, body.SerialNumber);
    }
}
