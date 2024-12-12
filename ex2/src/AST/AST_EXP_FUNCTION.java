
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

    /***************************************************/
    /* The printing message for a class declaration with inheritance AST node */
    /***************************************************/
    public void PrintMe()
    {
        /*********************************/
        /* AST NODE TYPE = CLASS DEC INHERITANCE */
        /*********************************/
        System.out.println("AST NODE CLASS DEC INHERITANCE");

        /******************************************/
        /* PRINT class name1, name2, and clist rec... */
        /******************************************/
        System.out.format("Function\n", f);
        if (f != null) f.PrintMe();

        /***************************************/
        /* PRINT Node to AST GRAPHVIZ DOT file */
        /***************************************/
        AST_GRAPHVIZ.getInstance().logNode(
            SerialNumber,
            String.format("CLASS DEC\n EXTENDS\n...->%s", f));

        /****************************************/
        /* PRINT Edges to AST GRAPHVIZ DOT file */
        /****************************************/
        if (f != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, f.SerialNumber);
    }
}
