package AST;

public class AST_FUNC_STMT_VAR_DOT_NO_ARGS extends AST_FUNC_STMT
{
    public AST_VAR var;
    public String name;

    /******************/
    /* CONSTRUCTOR(S) */
    /******************/
    public AST_FUNC_STMT_VAR_DOT_NO_ARGS(AST_VAR var, String name)
    {
        /******************************/
        /* SET A UNIQUE SERIAL NUMBER */
        /******************************/
        SerialNumber = AST_Node_Serial_Number.getFresh();

        /***************************************/
        /* PRINT CORRESPONDING DERIVATION RULE */
        /***************************************/
        System.out.format("====================== funcStmt-> var DOT ID LPAREN RPAREN SEMICOLON\n");

        /*******************************/
        /* COPY INPUT DATA MEMBERS ... */
        /*******************************/
        this.var = var;
        this.name = name;
    }


    public void PrintMe()
    {
        /*********************************/
        /* AST NODE TYPE = FUNC STMT VAR DOT NO ARGS */
        /*********************************/
        System.out.println("AST NODE FUNC STMT VAR DOT NO ARGS");

        /******************************************/
        /* RECURSIVELY PRINT var and name ... */
        /******************************************/
        if (var != null) var.PrintMe();
        System.out.println("Function name: " + name);

        /***************************************/
        /* PRINT Node to AST GRAPHVIZ DOT file */
        /***************************************/
        AST_GRAPHVIZ.getInstance().logNode(
            SerialNumber,
            "FUNC STMT VAR DOT NO ARGS\nFunction name: " + name);

        /****************************************/
        /* PRINT Edges to AST GRAPHVIZ DOT file */
        /****************************************/
        if (var != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, var.SerialNumber);
    }
}
