package AST;

public class AST_VAR_DEC_NO_ARGS extends AST_VAR_DEC
{
    public AST_TYPE t;
    public String name;

    /******************/
    /* CONSTRUCTOR(S) */
    /******************/
    public AST_VAR_DEC_NO_ARGS(AST_TYPE t, String name)
    {
        /******************************/
        /* SET A UNIQUE SERIAL NUMBER */
        /******************************/
        SerialNumber = AST_Node_Serial_Number.getFresh();

        /***************************************/
        /* PRINT CORRESPONDING DERIVATION RULE */
        /***************************************/
        System.out.println("====================== varDec -> type ID SEMICOLON\n");

        /*******************************/
        /* COPY INPUT DATA MEMBERS ... */
        /*******************************/
        this.t = t;
        this.name = name;
    }

    public void PrintMe()
    {
        /*********************************/
        /* AST NODE TYPE = VAR DEC NO ARGS */
        /*********************************/
        System.out.println("AST NODE VAR DEC NO ARGS");

        /******************************************/
        /* RECURSIVELY PRINT typAndId ... */
        /******************************************/
        if (t != null) t.PrintMe();
        System.out.println("ID Name: " + name);

        /***************************************/
        /* PRINT Node to AST GRAPHVIZ DOT file */
        /***************************************/
        AST_GRAPHVIZ.getInstance().logNode(
            SerialNumber,
            "VAR DEC\nNO ARGS");

        /****************************************/
        /* PRINT Edges to AST GRAPHVIZ DOT file */
        /****************************************/
        if (t != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, t.SerialNumber);
    }

}
