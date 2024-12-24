package AST;

public class AST_DEC_VAR_DEC extends AST_DEC
{
    public AST_VAR_DEC var_dec;

    /******************/
    /* CONSTRUCTOR(S) */
    /******************/
    public AST_DEC_VAR_DEC(AST_VAR_DEC var_dec)
    {
        /******************************/
        /* SET A UNIQUE SERIAL NUMBER */
        /******************************/
        SerialNumber = AST_Node_Serial_Number.getFresh();

        /***************************************/
        /* PRINT CORRESPONDING DERIVATION RULE */
        /***************************************/
        System.out.format("====================== dec -> varDec\n");

        /*******************************/
        /* COPY INPUT DATA MEMBERS ... */
        /*******************************/
        this.var_dec = var_dec;

    }

    public void PrintMe()
    {
        /*********************************/
        /* AST NODE TYPE = DEC VAR DEC */
        /*********************************/
        System.out.println("AST NODE DEC VAR DEC");

        /******************************************/
        /* RECURSIVELY PRINT var_dec ... */
        /******************************************/
        if (var_dec != null) var_dec.PrintMe();

        /***************************************/
        /* PRINT Node to AST GRAPHVIZ DOT file */
        /***************************************/
        AST_GRAPHVIZ.getInstance().logNode(
            SerialNumber,
            "DEC\nVAR DEC");

        /****************************************/
        /* PRINT Edges to AST GRAPHVIZ DOT file */
        /****************************************/
        if (var_dec != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, var_dec.SerialNumber);
    }

}
