package AST;

public class AST_VAR_DEC_NEW_EXP_ARGS extends AST_VAR_DEC
{
    public AST_TYPE type;
    public String name;
    public AST_NEW_EXP newExp;

    /******************/
    /* CONSTRUCTOR(S) */
    /******************/
    public AST_VAR_DEC_NEW_EXP_ARGS(AST_TYPE type, String name, AST_NEW_EXP newExp)
    {
        /******************************/
        /* SET A UNIQUE SERIAL NUMBER */
        /******************************/
        SerialNumber = AST_Node_Serial_Number.getFresh();

        /***************************************/
        /* PRINT CORRESPONDING DERIVATION RULE */
        /***************************************/
        System.out.println("====================== varDec -> type ID ASSIGN newExp SEMICOLON\n");

        /*******************************/
        /* COPY INPUT DATA MEMBERS ... */
        /*******************************/
        this.type = type;
        this.name = name;
        this.newExp = newExp;
    }

    /***************************************************/
    /* The printing message for a variable declaration with type, ID, and new expression AST node */
    /***************************************************/
    public void PrintMe()
    {
        /*********************************/
        /* AST NODE TYPE = VARDEC TYPE AND ID NEW EXP */
        /*********************************/
        System.out.println("AST NODE VARDEC TYPE AND ID NEW EXP");

        /******************************************/
        /* RECURSIVELY PRINT typAndId and nex ... */
        /******************************************/
        if (type != null) type.PrintMe();
        if (newExp != null) newExp.PrintMe();
        /***************************************/
        /* PRINT Node to AST GRAPHVIZ DOT file */
        /***************************************/
        AST_GRAPHVIZ.getInstance().logNode(
            SerialNumber,
            "VARDEC\nTYPE\nAND\nID\nNEW EXP");

        /****************************************/
        /* PRINT Edges to AST GRAPHVIZ DOT file */
        /****************************************/
        if (type != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, type.SerialNumber);
        if (newExp != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, newExp.SerialNumber);
    }
}
