package AST;

public class AST_FUNC_STMT_VAR_DOT_ARGS extends AST_FUNC_STMT
{
    public AST_VAR var;
    public String name;
    public AST_EXP_ARGUMENTS eA;

    /******************/
    /* CONSTRUCTOR(S) */
    /******************/
    public AST_FUNC_STMT_VAR_DOT_ARGS(AST_VAR var, String name, AST_EXP_ARGUMENTS eA)
    {
        /******************************/
        /* SET A UNIQUE SERIAL NUMBER */
        /******************************/
        SerialNumber = AST_Node_Serial_Number.getFresh();

        /***************************************/
        /* PRINT CORRESPONDING DERIVATION RULE */
        /***************************************/
        System.out.println("====================== FUNC STMT-> FUNC STMT VAR DOT ARGS\n");

        /*******************************/
        /* COPY INPUT DATA MEMBERS ... */
        /*******************************/
        this.var = var;
        this.name = name;
        this.eA = eA;
    }


    public void PrintMe()
    {
        /*********************************/
        /* AST NODE TYPE = FUNC STMT VAR DOT ARGS */
        /*********************************/
        System.out.println("AST NODE FUNC STMT VAR DOT ARGS");

        /******************************************/
        /* RECURSIVELY PRINT var and name */
        /******************************************/
        if (var != null) var.PrintMe();
        System.out.println("FUNCTION: " + name);

        /******************************************/
        /* RECURSIVELY PRINT expArgs */
        /******************************************/
        if (eA != null) eA.PrintMe();

        /***************************************/
        /* PRINT Node to AST GRAPHVIZ DOT file */
        /***************************************/
        AST_GRAPHVIZ.getInstance().logNode(
            SerialNumber,
            "FUNC STMT VAR DOT ARGS\nFUNCTION: " + name);

        /****************************************/
        /* PRINT Edges to AST GRAPHVIZ DOT file */
        /****************************************/
        if (var != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, var.SerialNumber);
        if (eA != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, eA.SerialNumber);
    }
}
