package AST;

public class AST_FUNC_STMT_ARGS extends AST_FUNC_STMT
{
    public String name;
    public AST_EXP_ARGUMENTS eA;

    /******************/
    /* CONSTRUCTOR(S) */
    /******************/
    public AST_FUNC_STMT_ARGS(String name, AST_EXP_ARGUMENTS eA)
    {
        /******************************/
        /* SET A UNIQUE SERIAL NUMBER */
        /******************************/
        SerialNumber = AST_Node_Serial_Number.getFresh();

        /***************************************/
        /* PRINT CORRESPONDING DERIVATION RULE */
        /***************************************/
        System.out.println("====================== FUNC STMT -> var DOT ID LPAREN RPAREN SEMICOLON\n");

        /*******************************/
        /* COPY INPUT DATA MEMBERS ... */
        /*******************************/
        this.name = name;
        this.eA = eA;
    }

    public void PrintMe()
    {
        /*********************************/
        /* AST NODE TYPE = FUNC STMT ARGS */
        /*********************************/
        System.out.println("AST NODE FUNC STMT ARGS");

        /******************************************/
        /* PRINT name */
        /******************************************/
        System.out.println("Function name: " + name);

        /******************************************/
        /* RECURSIVELY PRINT eA */
        /******************************************/
        if (eA != null) eA.PrintMe();

        /***************************************/
        /* PRINT Node to AST GRAPHVIZ DOT file */
        /***************************************/
        AST_GRAPHVIZ.getInstance().logNode(
            SerialNumber,
            "FUNC STMT ARGS\nFunction name: " + name);

        /****************************************/
        /* PRINT Edges to AST GRAPHVIZ DOT file */
        /****************************************/
        if (eA != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, eA.SerialNumber);
    }
}
