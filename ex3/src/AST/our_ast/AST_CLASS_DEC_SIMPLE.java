package AST;

public class AST_CLASS_DEC_SIMPLE extends AST_CLASS_DEC
{
    public String name;
    public AST_CFIELD_REC cFR;

    /******************/
    /* CONSTRUCTOR(S) */
    /******************/
    public AST_CLASS_DEC_SIMPLE(String name, AST_CFIELD_REC cFR)
    {
        /******************************/
        /* SET A UNIQUE SERIAL NUMBER */
        /******************************/
        SerialNumber = AST_Node_Serial_Number.getFresh();

        /***************************************/
        /* PRINT CORRESPONDING DERIVATION RULE */
        /***************************************/
        System.out.format("====================== classDec -> CLASS ID( %s ) LBRACE cFieldRec RBRACE\n", name);

        /*******************************/
        /* COPY INPUT DATA MEMBERS ... */
        /*******************************/
        this.name = name;
        this.cFR = cFR;
    }

    public void PrintMe()
    {
        /*********************************/
        /* AST NODE TYPE = CLASS DEC SIMPLE */
        /*********************************/
        System.out.println("AST NODE CLASS DEC SIMPLE");

        /******************************************/
        /* PRINT class name and cFR ... */
        /******************************************/
        System.out.format("CLASS NAME( %s )\n", name);
        if (cFR != null) cFR.PrintMe();

        /***************************************/
        /* PRINT Node to AST GRAPHVIZ DOT file */
        /***************************************/
        AST_GRAPHVIZ.getInstance().logNode(
            SerialNumber,
            String.format("CLASS DEC\nSIMPLE\n...->%s", name));

        /****************************************/
        /* PRINT Edges to AST GRAPHVIZ DOT file */
        /****************************************/
        if (cFR != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, cFR.SerialNumber);
    }
}
