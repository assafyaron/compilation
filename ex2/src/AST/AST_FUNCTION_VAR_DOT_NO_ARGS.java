package AST;

public class AST_FUNCTION_VAR_DOT_NO_ARGS extends AST_FUNCTION
{
    public AST_VAR v;
    public String name;
    
    /******************/
    /* CONSTRUCTOR(S) */
    /******************/
    public AST_FUNCTION_VAR_DOT_NO_ARGS(AST_VAR v, String name)
    {
        /******************************/
        /* SET A UNIQUE SERIAL NUMBER */
        /******************************/
        SerialNumber = AST_Node_Serial_Number.getFresh();

        /***************************************/
        /* PRINT CORRESPONDING DERIVATION RULE */
        /***************************************/
        System.out.format("====================== function -> varDot ID( %s ) LPAREN RPAREN\n", name);

        /*******************************/
        /* COPY INPUT DATA MEMBERS ... */
        /*******************************/
        this.v = v;
        this.name = name;
    }

    public void PrintMe()
    {
        /*********************************/
        /* AST NODE TYPE = FUNCTION VAR DOT NO ARGS */
        /*********************************/
        System.out.println("AST NODE FUNCTION VAR DOT NO ARGS");

        /******************************************/
        /* RECURSIVELY PRINT v and name ... */
        /******************************************/
        if (v != null) v.PrintMe();
        System.out.println("FUNCTION: " + name);

        /***************************************/
        /* PRINT Node to AST GRAPHVIZ DOT file */
        /***************************************/
        AST_GRAPHVIZ.getInstance().logNode(
            SerialNumber,
            "FUNCTION VAR DOT NO ARGS\nFUNCTION: " + name);

        /****************************************/
        /* PRINT Edges to AST GRAPHVIZ DOT file */
        /****************************************/
        if (v != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, v.SerialNumber);
    }
}
