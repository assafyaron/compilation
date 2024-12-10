package AST;

public class AST_FUNC_STMT_VAR_DOT_ARGS extends AST_FUNC_STMT
{
    public AST_VAR var;
    public String name;
    public AST_EXP_ARGUMENTS expArgs;

    /******************/
    /* CONSTRUCTOR(S) */
    /******************/
    public AST_FUNC_STMT_VAR_DOT_ARGS(AST_VAR var, String name, AST_EXP_ARGUMENTS expArgs)
    {
        /******************************/
        /* SET A UNIQUE SERIAL NUMBER */
        /******************************/
        SerialNumber = AST_Node_Serial_Number.getFresh();

        /***************************************/
        /* PRINT CORRESPONDING DERIVATION RULE */
        /***************************************/
        System.out.println("====================== callFunc -> varDot ID LPAREN expArgs RPAREN\n");

        /*******************************/
        /* COPY INPUT DATA MEMBERS ... */
        /*******************************/
        this.var = var;
        this.name = name;
        this.expArgs = expArgs;
    }

    /***************************************************/
    /* The printing message for a function call with variable and dot and arguments AST node */
    /***************************************************/
    public void PrintMe()
    {
        /*********************************/
        /* AST NODE TYPE = FUNCTION CALL WITH VAR AND DOT AND ARGUMENTS */
        /*********************************/
        System.out.println("AST NODE FUNCTION CALL WITH VAR AND DOT AND ARGUMENTS");

        /******************************************/
        /* RECURSIVELY PRINT var and name */
        /******************************************/
        if (var != null) var.PrintMe();
        System.out.println("Function Name: " + name);

        /******************************************/
        /* RECURSIVELY PRINT expArgs */
        /******************************************/
        if (expArgs != null) expArgs.PrintMe();

        /***************************************/
        /* PRINT Node to AST GRAPHVIZ DOT file */
        /***************************************/
        AST_GRAPHVIZ.getInstance().logNode(
            SerialNumber,
            "FUNCTION CALL WITH VAR AND DOT AND ARGUMENTS\nFunction Name: " + name);

        /****************************************/
        /* PRINT Edges to AST GRAPHVIZ DOT file */
        /****************************************/
        if (varDot != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, var.SerialNumber);
        if (expArgs != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, expArgs.SerialNumber);
    }
}
