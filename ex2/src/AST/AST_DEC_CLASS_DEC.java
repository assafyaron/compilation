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
        System.out.format("====================== dec -> classDec");

        /*******************************/
        /* COPY INPUT DATA MEMBERS ... */
        /*******************************/
        this.class_dec = class_dec;

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
        System.out.format("CLASS 1 NAME( %s )\n", name1);
        System.out.format("CLASS 2 NAME( %s )\n", name2);
        if (cFR != null) cFR.PrintMe();

        /***************************************/
        /* PRINT Node to AST GRAPHVIZ DOT file */
        /***************************************/
        AST_GRAPHVIZ.getInstance().logNode(
            SerialNumber,
            String.format("CLASS DEC\n EXTENDS\n...->%s", name1));

        /****************************************/
        /* PRINT Edges to AST GRAPHVIZ DOT file */
        /****************************************/
        if (cFR != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, cFR.SerialNumber);
    }
}
