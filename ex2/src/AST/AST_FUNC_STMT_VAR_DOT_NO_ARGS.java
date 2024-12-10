package AST;

public class AST_FUNC_STMT_VAR_DOT_NO_ARGS extends AST_FUNC_STMT
{
    public AST_VAR var;
    public String name;

    /******************/
    /* CONSTRUCTOR(S) */
    /******************/
    public AST_FUNC_STMT_VAR_DOT_NO_ARGS(AST_VAR var, String name)
    {
        /******************************/
        /* SET A UNIQUE SERIAL NUMBER */
        /******************************/
        SerialNumber = AST_Node_Serial_Number.getFresh();

        /***************************************/
        /* PRINT CORRESPONDING DERIVATION RULE */
        /***************************************/
        System.out.format("====================== callFunc -> var Dot ID LPAREN RPAREN\n");

        /*******************************/
        /* COPY INPUT DATA MEMBERS ... */
        /*******************************/
        this.var = var;
        this.name = name;
    }

    /***************************************************/
    /* The printing message for a function call with variable and dot without arguments AST node */
    /***************************************************/
    public void PrintMe()
    {
        /*********************************/
        /* AST NODE TYPE = FUNCTION CALL WITH VAR AND DOT WITHOUT ARGUMENTS */
        /*********************************/
        System.out.println("AST NODE FUNCTION CALL WITH VAR AND DOT WITHOUT ARGUMENTS");

        /******************************************/
        /* RECURSIVELY PRINT var and name ... */
        /******************************************/
        if (var != null) var.PrintMe();
        System.out.println("Function Name: " + name);

        /***************************************/
        /* PRINT Node to AST GRAPHVIZ DOT file */
        /***************************************/
        AST_GRAPHVIZ.getInstance().logNode(
            SerialNumber,
            "FUNCTION CALL WITH VAR AND DOT WITHOUT ARGUMENTS\nFunction Name: " + name);

        /****************************************/
        /* PRINT Edges to AST GRAPHVIZ DOT file */
        /****************************************/
        if (var != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, var.SerialNumber);
    }
}
