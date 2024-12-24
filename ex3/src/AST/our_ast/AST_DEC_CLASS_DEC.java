package AST;

public class AST_DEC_CLASS_DEC extends AST_DEC
{
    public AST_CLASS_DEC class_dec;

    /******************/
    /* CONSTRUCTOR(S) */
    /******************/
    public AST_DEC_CLASS_DEC(AST_CLASS_DEC class_dec)
    {
        /******************************/
        /* SET A UNIQUE SERIAL NUMBER */
        /******************************/
        SerialNumber = AST_Node_Serial_Number.getFresh();

        /***************************************/
        /* PRINT CORRESPONDING DERIVATION RULE */
        /***************************************/
        System.out.format("====================== dec -> classDec\n");

        /*******************************/
        /* COPY INPUT DATA MEMBERS ... */
        /*******************************/
        this.class_dec = class_dec;

    }

    public void PrintMe()
    {
        /*********************************/
        /* AST NODE TYPE = CLASS DECLARATION */
        /*********************************/
        System.out.println("AST NODE CLASS DEC");

        /******************************************/
        /* RECURSIVELY PRINT class_dec ... */
        /******************************************/
        if (class_dec != null) class_dec.PrintMe();

        /***************************************/
        /* PRINT Node to AST GRAPHVIZ DOT file */
        /***************************************/
        AST_GRAPHVIZ.getInstance().logNode(
            SerialNumber,
            "DEC\nCLASS DEC");

        /****************************************/
        /* PRINT Edges to AST GRAPHVIZ DOT file */
        /****************************************/
        if (class_dec != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, class_dec.SerialNumber);
    }

}
