
package AST;

public class AST_EXP_FUNCTION extends AST_EXP
{
    public AST_FUNCTION f;

    /******************/
    /* CONSTRUCTOR(S) */
    /******************/
    public AST_EXP_FUNCTION(AST_FUNCTION f)
    {
        /******************************/
        /* SET A UNIQUE SERIAL NUMBER */
        /******************************/
        SerialNumber = AST_Node_Serial_Number.getFresh();

        /***************************************/
        /* PRINT CORRESPONDING DERIVATION RULE */
        /***************************************/
        System.out.format("====================== exp -> fuction\n");

        /*******************************/
        /* COPY INPUT DATA MEMBERS ... */
        /*******************************/
        this.f = f;

    }


    public void PrintMe()
    {
        /*********************************/
        /* AST NODE TYPE = EXP FUNCTION CALL */
        /*********************************/
        System.out.println("AST NODE exp FUNCTION CALL");

        /******************************************/
        /* RECURSIVELY PRINT callFunc ... */
        /******************************************/
        if (f != null) f.PrintMe();

        /***************************************/
        /* PRINT Node to AST GRAPHVIZ DOT file */
        /***************************************/
        AST_GRAPHVIZ.getInstance().logNode(
            SerialNumber,
            "exp\nFUNCTION CALL");

        /****************************************/
        /* PRINT Edges to AST GRAPHVIZ DOT file */
        /****************************************/
        if (f != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, f.SerialNumber);
    }
}
