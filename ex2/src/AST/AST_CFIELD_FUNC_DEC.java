package AST;

public class AST_CFIELD_FUNC_DEC extends AST_CFIELD
{
    public AST_FUNC_DEC funcDec;

    /******************/
    /* CONSTRUCTOR(S) */
    /******************/
    public AST_CFIELD_FUNC_DEC(AST_FUNC_DEC funcDec)
    {
        /******************************/
        /* SET A UNIQUE SERIAL NUMBER */
        /******************************/
        SerialNumber = AST_Node_Serial_Number.getFresh();

        /***************************************/
        /* PRINT CORRESPONDING DERIVATION RULE */
        /***************************************/
        System.out.println("====================== cField -> funcDec");

        /*******************************/
        /* COPY INPUT DATA MEMBERS ... */
        /*******************************/
        this.funcDec = funcDec;
    }

    /***************************************************/
    /* The printing message for a class field function declaration AST node */
    /***************************************************/
    public void PrintMe()
    {
        /*********************************/
        /* AST NODE TYPE = CLASS FIELD FUNCTION DECLARATION */
        /*********************************/
        System.out.println("AST NODE CLASS FIELD FUNCTION DECLARATION");

        /******************************************/
        /* RECURSIVELY PRINT funcDec ... */
        /******************************************/
        if (funcDec != null) funcDec.PrintMe();

        /***************************************/
        /* PRINT Node to AST GRAPHVIZ DOT file */
        /***************************************/
        AST_GRAPHVIZ.getInstance().logNode(
            SerialNumber,
            "CLASS FIELD\nFUNCTION DECLARATION");

        /****************************************/
        /* PRINT Edges to AST GRAPHVIZ DOT file */
        /****************************************/
        if (funcDec != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, funcDec.SerialNumber);
    }
}
