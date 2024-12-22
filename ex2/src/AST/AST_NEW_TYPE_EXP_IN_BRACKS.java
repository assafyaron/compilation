package AST;

public class AST_NEW_TYPE_EXP_IN_BRACKS extends AST_NEW_EXP
{
    public AST_TYPE t;
    public AST_EXP e;

    /******************/
    /* CONSTRUCTOR(S) */
    /******************/
    public AST_NEW_TYPE_EXP_IN_BRACKS(AST_TYPE t, AST_EXP e)
    {
        /******************************/
        /* SET A UNIQUE SERIAL NUMBER */
        /******************************/
        SerialNumber = AST_Node_Serial_Number.getFresh();

        /***************************************/
        /* PRINT CORRESPONDING DERIVATION RULE */
        /***************************************/
        System.out.println("====================== NEW EXP -> NEW TYPE EXP IN BRACKS\n");

        /*******************************/
        /* COPY INPUT DATA MEMBERS ... */
        /*******************************/
        this.t = t;
        this.e = e;
    }

    public void PrintMe()
    {
        /*********************************/
        /* AST NODE TYPE = NEW TYPE EXP IN BRACKS */
        /*********************************/
        System.out.println("AST NODE NEW TYPE EXP IN BRACKS");

        /******************************************/
        /* RECURSIVELY PRINT t and e ... */
        /******************************************/
        if (t != null) t.PrintMe();
        if (e != null) e.PrintMe();

        /***************************************/
        /* PRINT Node to AST GRAPHVIZ DOT file */
        /***************************************/
        AST_GRAPHVIZ.getInstance().logNode(
            SerialNumber,
            "NEW TYPE\nEXP IN BRACKS");

        /****************************************/
        /* PRINT Edges to AST GRAPHVIZ DOT file */
        /****************************************/
        if (t != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, t.SerialNumber);
        if (e != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, e.SerialNumber);
    }
}
