package AST;

public class AST_FUNCTION_NO_ARGS extends AST_FUNCTION
{
    public String name;

    /******************/
    /* CONSTRUCTOR(S) */
    /******************/
    public AST_FUNCTION_NO_ARGS(String name)
    {
        /******************************/
        /* SET A UNIQUE SERIAL NUMBER */
        /******************************/
        SerialNumber = AST_Node_Serial_Number.getFresh();

        /***************************************/
        /* PRINT CORRESPONDING DERIVATION RULE */
        /***************************************/
        System.out.format("====================== FUNCTION -> FUNCTION NO ARGS\n");

        /*******************************/
        /* COPY INPUT DATA MEMBERS ... */
        /*******************************/
        this.name = name;
    }

    public void PrintMe() {
        /*********************************/
        /* AST NODE TYPE = FUNCTION NO ARGS */
        /*********************************/
        System.out.println("AST NODE FUNCTION NO ARGS");

        /******************************************/
        /* PRINT name */
        /******************************************/
        System.out.println("FUNCTION: " + name);

        /***************************************/
        /* PRINT Node to AST GRAPHVIZ DOT file */
        /***************************************/
        AST_GRAPHVIZ.getInstance().logNode(
                SerialNumber,
                "FUNCTION NO ARGS\nFUNCTION: " + name);
    }
}
