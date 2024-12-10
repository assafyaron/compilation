package AST;

public class AST_VAR_DEC_NO_ARGS extends AST_VAR_DEC
{
    public AST_TYPE type;
    public String name;

    /******************/
    /* CONSTRUCTOR(S) */
    /******************/
    public AST_VAR_DEC_NO_ARGS(AST_TYPE type, String name)
    {
        /******************************/
        /* SET A UNIQUE SERIAL NUMBER */
        /******************************/
        SerialNumber = AST_Node_Serial_Number.getFresh();

        /***************************************/
        /* PRINT CORRESPONDING DERIVATION RULE */
        /***************************************/
        System.out.println("====================== varDec -> type ID SEMICOLON");

        /*******************************/
        /* COPY INPUT DATA MEMBERS ... */
        /*******************************/
        this.type = type;
        this.name = name;
    }

    /***************************************************/
    /* The printing message for a variable declaration with type and ID AST node */
    /***************************************************/
    public void PrintMe()
    {
        /*********************************/
        /* AST NODE TYPE = VARDEC TYPE AND ID */
        /*********************************/
        System.out.println("AST NODE VARDEC TYPE AND ID");

        /******************************************/
        /* RECURSIVELY PRINT typAndId ... */
        /******************************************/
        if (type != null) type.PrintMe();
        if (name != null) name.PrintMe();

        /***************************************/
        /* PRINT Node to AST GRAPHVIZ DOT file */
        /***************************************/
        AST_GRAPHVIZ.getInstance().logNode(
            SerialNumber,
            "VARDEC\nTYPE\nAND\nID");

        /****************************************/
        /* PRINT Edges to AST GRAPHVIZ DOT file */
        /****************************************/
        if (type != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, type.SerialNumber);
        if (name != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, name.SerialNumber);
    }
}
