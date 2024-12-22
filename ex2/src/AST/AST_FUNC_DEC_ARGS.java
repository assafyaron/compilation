package AST;

public class AST_FUNC_DEC_ARGS extends AST_FUNC_DEC
{
    public AST_TYPE t;
    public String name;
    public AST_FUNC_DEC_ARGS_LIST args;
    public AST_STMT_LIST body;

    /******************/
    /* CONSTRUCTOR(S) */
    /******************/
    public AST_FUNC_DEC_ARGS(AST_TYPE t, String name, AST_FUNC_DEC_ARGS_LIST args, AST_STMT_LIST body)
    {
        /******************************/
        /* SET A UNIQUE SERIAL NUMBER */
        /******************************/
        SerialNumber = AST_Node_Serial_Number.getFresh();

        /***************************************/
        /* PRINT CORRESPONDING DERIVATION RULE */
        /***************************************/
        System.out.format("====================== FUNC DEC -> typeID LPAREN funcArgs RPAREN LBRACE stmtList RBRACE\n");

        /*******************************/
        /* COPY INPUT DATA MEMBERS ... */
        /*******************************/
        this.t = t;
        this.name = name;
        this.args = args;
        this.body = body;
    }

    public void PrintMe()
    {
        /*********************************/
        /* AST NODE TYPE = FUNC DEC ARGS */
        /*********************************/
        System.out.println("AST NODE FUNC DEC ARGS");

        /******************************************/
        /* RECURSIVELY PRINT t, args, and body ... */
        /******************************************/
        if (t != null) t.PrintMe();
        System.out.println("AST NODE FUNC DEC ARGS");
        if (args != null) args.PrintMe();
        if (body != null) body.PrintMe();

        /***************************************/
        /* PRINT Node to AST GRAPHVIZ DOT file */
        /***************************************/
        AST_GRAPHVIZ.getInstance().logNode(
            SerialNumber,
            "FUNC DEC\nARGS");

        /****************************************/
        /* PRINT Edges to AST GRAPHVIZ DOT file */
        /****************************************/
        if (t != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, t.SerialNumber);
        if (args != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, args.SerialNumber);
        if (body != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, body.SerialNumber);
    }

}
