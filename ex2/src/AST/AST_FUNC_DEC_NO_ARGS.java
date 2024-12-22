package AST;

public class AST_FUNC_DEC_NO_ARGS extends AST_FUNC_DEC
{
    public AST_TYPE t;
    public String name;
    public AST_STMT_LIST body;

    /******************/
    /* CONSTRUCTOR(S) */
    /******************/
    public AST_FUNC_DEC_NO_ARGS(AST_TYPE t, String name, AST_STMT_LIST body)
    {
        /******************************/
        /* SET A UNIQUE SERIAL NUMBER */
        /******************************/
        SerialNumber = AST_Node_Serial_Number.getFresh();

        /***************************************/
        /* PRINT CORRESPONDING DERIVATION RULE */
        /***************************************/
        System.out.format("====================== FUNC DEC -> FUNC DEC NO ARGS\n");

        /*******************************/
        /* COPY INPUT DATA MEMBERS ... */
        /*******************************/
        this.t = t;
        this.name = name;
        this.body = body;


    }

    public void PrintMe()
    {
        /*********************************/
        /* AST NODE TYPE = FUNCDEC NO ARGS */
        /*********************************/
        System.out.println("AST NODE FUNC DEC NO ARGS");

        /******************************************/
        /* RECURSIVELY PRINT t and body ... */
        /******************************************/
        if (t != null) t.PrintMe();
        if (body != null) body.PrintMe();

        /***************************************/
        /* PRINT Node to AST GRAPHVIZ DOT file */
        /***************************************/
        AST_GRAPHVIZ.getInstance().logNode(
            SerialNumber,
            "FUNC DEC\nNO ARGS");

        /****************************************/
        /* PRINT Edges to AST GRAPHVIZ DOT file */
        /****************************************/
        if (t != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, t.SerialNumber);
        if (body != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, body.SerialNumber);
    }
}
