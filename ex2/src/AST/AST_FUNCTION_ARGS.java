

package AST;

public class AST_FUNCTION_ARGS extends AST_FUNCTION
{
    public String name;
    public AST_EXP_ARGUMENTS eA;


    /******************/
    /* CONSTRUCTOR(S) */
    /******************/
    public AST_FUNCTION_ARGS(String name, AST_EXP_ARGUMENTS eA)
    {
        /******************************/
        /* SET A UNIQUE SERIAL NUMBER */
        /******************************/
        SerialNumber = AST_Node_Serial_Number.getFresh();

        /***************************************/
        /* PRINT CORRESPONDING DERIVATION RULE */
        /***************************************/
        System.out.format("====================== FUNCTION -> FUNCTION ARGS\n");

        /*******************************/
        /* COPY INPUT DATA MEMBERS ... */
        /*******************************/
        this.name = name;
        this.eA = eA;
    }

    public void PrintMe()
    {
        /*********************************/
        /* AST NODE TYPE = FUNCTION CALL WITH ARGUMENTS */
        /*********************************/
        System.out.println("AST NODE FUNCTION ARGS");

        /******************************************/
        /* PRINT name */
        /******************************************/
        System.out.println("FUNCTION: " + name);

        /******************************************/
        /* RECURSIVELY PRINT eA */
        /******************************************/
        if (eA != null) eA.PrintMe();

        /***************************************/
        /* PRINT Node to AST GRAPHVIZ DOT file */
        /***************************************/
        AST_GRAPHVIZ.getInstance().logNode(
            SerialNumber,
            "FUNCTION ARGS\nFUNCTION: " + name);

        /****************************************/
        /* PRINT Edges to AST GRAPHVIZ DOT file */
        /****************************************/
        if (eA != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, eA.SerialNumber);
    }
}
