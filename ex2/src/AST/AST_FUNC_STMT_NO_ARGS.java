package AST;

public class AST_FUNC_STMT_NO_ARGS extends AST_FUNC_STMT {
    public String name;

    /******************/
    /* CONSTRUCTOR(S) */
    /******************/
    public AST_FUNC_STMT_NO_ARGS(String name) {
        /******************************/
        /* SET A UNIQUE SERIAL NUMBER */
        /******************************/
        SerialNumber = AST_Node_Serial_Number.getFresh();

        /***************************************/
        /* PRINT CORRESPONDING DERIVATION RULE */
        /***************************************/
        System.out.println("====================== callFunc -> ID LPAREN RPAREN");

        /*******************************/
        /* COPY INPUT DATA MEMBERS ... */
        /*******************************/
        this.name = name;
    }

    /***************************************************/
    /* The printing message for a function call without arguments AST node */
    /***************************************************/
    public void PrintMe() {
        /*********************************/
        /* AST NODE TYPE = FUNCTION CALL WITHOUT ARGUMENTS */
        /*********************************/
        System.out.println("AST NODE FUNCTION CALL WITHOUT ARGUMENTS");

        /******************************************/
        /* PRINT name */
        /******************************************/
        System.out.println("Function Name: " + name);

        /***************************************/
        /* PRINT Node to AST GRAPHVIZ DOT file */
        /***************************************/
        AST_GRAPHVIZ.getInstance().logNode(
                SerialNumber,
                "FUNCTION CALL WITHOUT ARGUMENTS\nName: " + name);
    }
}
