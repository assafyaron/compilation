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
        System.out.println("====================== funcStmt -> ID( %s ) LPAREN RPAREN SEMICOLON\n", name);

        /*******************************/
        /* COPY INPUT DATA MEMBERS ... */
        /*******************************/
        this.name = name;
    }


    public void PrintMe() {
        /*********************************/
        /* AST NODE TYPE = FUNC STMT NO ARGS */
        /*********************************/
        System.out.println("AST NODE FUNC STMT NO ARGS");

        /******************************************/
        /* PRINT name */
        /******************************************/
        System.out.println("Function name: " + name);

        /***************************************/
        /* PRINT Node to AST GRAPHVIZ DOT file */
        /***************************************/
        AST_GRAPHVIZ.getInstance().logNode(
                SerialNumber,
                "FUNC STMT NO ARGS\nFunction name: " + name);
    }
}
