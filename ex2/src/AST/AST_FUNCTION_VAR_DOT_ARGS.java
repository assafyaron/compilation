package AST;

public class AST_FUNCTION_VAR_DOT_ARGS extends AST_FUNCTION
{
    public AST_VAR v;
    public String name;
    public AST_EXP_ARGUMENTS eA;
    
    /******************/
    /* CONSTRUCTOR(S) */
    /******************/
    public AST_FUNCTION_VAR_DOT_ARGS(AST_VAR v, String name, AST_EXP_ARGUMENTS eA)
    {
        /******************************/
        /* SET A UNIQUE SERIAL NUMBER */
        /******************************/
        SerialNumber = AST_Node_Serial_Number.getFresh();

        /***************************************/
        /* PRINT CORRESPONDING DERIVATION RULE */
        /***************************************/
        System.out.format("====================== function -> varDot ID LPAREN expArgs RPAREN\n");

        /*******************************/
        /* COPY INPUT DATA MEMBERS ... */
        /*******************************/
        this.v = v;
        this.name = name;
        this.eA = eA;
    }

    public void PrintMe()
    {
        /*********************************/
        /* AST NODE TYPE = FUNCTION VAR DOT ARGS */
        /*********************************/
        System.out.println("AST NODE FUNCTION VAR DOT ARGS");

        /******************************************/
        /* RECURSIVELY PRINT v and name */
        /******************************************/
        if (v != null) v.PrintMe();
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
            "FUNCTION VAR DOT NO ARGS\nFunction name: " + name);

        /****************************************/
        /* PRINT Edges to AST GRAPHVIZ DOT file */
        /****************************************/
        if (V != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, V.SerialNumber);
        if (eA != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, eA.SerialNumber);
    }
}
