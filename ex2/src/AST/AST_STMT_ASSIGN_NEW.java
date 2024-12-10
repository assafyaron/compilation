package AST;

public class AST_STMT_ASSIGN_NEW extends AST_STMT
{
    public AST_VAR var;
    public AST_NEW_EXP newExp;

    /******************/
    /* CONSTRUCTOR(S) */
    /******************/
    public AST_STMT_ASSIGN_NEW(AST_VAR var, AST_NEW_EXP newExp)
    {
        /******************************/
        /* SET A UNIQUE SERIAL NUMBER */
        /******************************/
        SerialNumber = AST_Node_Serial_Number.getFresh();

        /***************************************/
        /* PRINT CORRESPONDING DERIVATION RULE */
        /***************************************/
        System.out.println("====================== stmt -> var ASSIGN newExp SEMICOLON\n");

        /*******************************/
        /* COPY INPUT DATA MEMBERS ... */
        /*******************************/
        this.var = var;
        this.newExp = newExp;
    }

    /***************************************************/
    /* The printing message for an assignment statement with new expression AST node */
    /***************************************************/
    public void PrintMe()
    {
        /*********************************/
        /* AST NODE TYPE = STATEMENT ASSIGNMENT NEW EXPRESSION */
        /*********************************/
        System.out.println("AST NODE STATEMENT ASSIGNMENT NEW EXPRESSION");

        /******************************************/
        /* RECURSIVELY PRINT var and newExp ... */
        /******************************************/
        if (var != null) var.PrintMe();
        if (newExp != null) newExp.PrintMe();

        /***************************************/
        /* PRINT Node to AST GRAPHVIZ DOT file */
        /***************************************/
        AST_GRAPHVIZ.getInstance().logNode(
            SerialNumber,
            "STATEMENT\nASSIGNMENT NEW EXPRESSION");

        /****************************************/
        /* PRINT Edges to AST GRAPHVIZ DOT file */
        /****************************************/
        if (var != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, var.SerialNumber);
        if (newExp != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, newExp.SerialNumber);
    }
}
