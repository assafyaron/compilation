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
        System.out.println("====================== newExp -> NEW type [exp]\n");

        /*******************************/
        /* COPY INPUT DATA MEMBERS ... */
        /*******************************/
        this.t = t;
        this.e = e;
    }

    /***************************************************/
    /* The printing message for a new type with expression in brackets AST node */
    /***************************************************/
    public void PrintMe()
    {
        /*********************************/
        /* AST NODE TYPE = NEW TYPE WITH EXPRESSION IN BRACKETS */
        /*********************************/
        System.out.println("AST NODE NEW TYPE WITH EXPRESSION IN BRACKETS");

        /******************************************/
        /* RECURSIVELY PRINT type and exp ... */
        /******************************************/
        if (type != null) type.PrintMe();
        if (exp != null) exp.PrintMe();

        /***************************************/
        /* PRINT Node to AST GRAPHVIZ DOT file */
        /***************************************/
        AST_GRAPHVIZ.getInstance().logNode(
            SerialNumber,
            "NEW TYPE\nWITH EXPRESSION IN BRACKETS");

        /****************************************/
        /* PRINT Edges to AST GRAPHVIZ DOT file */
        /****************************************/
        if (type != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, type.SerialNumber);
        if (exp != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, exp.SerialNumber);
    }
}
