package AST;

public class AST_VAR_DEC_NEW_EXP_ARGS extends AST_VAR_DEC
{
    public AST_TYPE t;
    public String name;
    public AST_NEW_EXP new_exp;

    /******************/
    /* CONSTRUCTOR(S) */
    /******************/
    public AST_VAR_DEC_NEW_EXP_ARGS(AST_TYPE t, String name, AST_NEW_EXP new_exp)
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
        this.t = t;
        this.name = name;
        this.new_exp = new_exp;
    }

    public void PrintMe()
    {
        /*********************************/
        /* AST NODE TYPE = VAR DEC NEW EXP */
        /*********************************/
        System.out.println("AST NODE VAR DEC NEW EXP");

        /******************************************/
        /* RECURSIVELY PRINT t and new_exp ... */
        /******************************************/
        if (t != null) t.PrintMe();
        System.out.println("ID Name: " + name);
        if (new_exp != null) new_exp.PrintMe();
        /***************************************/
        /* PRINT Node to AST GRAPHVIZ DOT file */
        /***************************************/
        AST_GRAPHVIZ.getInstance().logNode(
            SerialNumber,
            "VAR DEC\nNEW EXP");

        /****************************************/
        /* PRINT Edges to AST GRAPHVIZ DOT file */
        /****************************************/
        if (t != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, t.SerialNumber);
        if (new_exp != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, new_exp.SerialNumber);
    }
}
