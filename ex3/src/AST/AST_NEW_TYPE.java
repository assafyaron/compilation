package AST;

public class AST_NEW_TYPE extends AST_NEW_EXP
{
    public AST_TYPE t;

    /******************/
    /* CONSTRUCTOR(S) */
    /******************/
    public AST_NEW_TYPE(AST_TYPE t)
    {
        /******************************/
        /* SET A UNIQUE SERIAL NUMBER */
        /******************************/
        SerialNumber = AST_Node_Serial_Number.getFresh();

        /***************************************/
        /* PRINT CORRESPONDING DERIVATION RULE */
        /***************************************/
        System.out.println("====================== newExp-> NEW type\n");

        /*******************************/
        /* COPY INPUT DATA MEMBERS ... */
        /*******************************/
        this.t = t;
    }


    public void PrintMe()
    {
        /*********************************/
        /* AST NODE TYPE = NEW TYPE */
        /*********************************/
        System.out.println("AST NODE NEW TYPE");

        /******************************************/
        /* RECURSIVELY PRINT t ... */
        /******************************************/
        if (t != null) t.PrintMe();

        /***************************************/
        /* PRINT Node to AST GRAPHVIZ DOT file */
        /***************************************/
        AST_GRAPHVIZ.getInstance().logNode(
            SerialNumber,
            "NEW TYPE\n");

        /****************************************/
        /* PRINT Edges to AST GRAPHVIZ DOT file */
        /****************************************/
        if (t != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, t.SerialNumber);
    }
}
