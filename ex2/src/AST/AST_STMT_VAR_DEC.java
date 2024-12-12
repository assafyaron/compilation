package AST;

public class AST_STMT_VAR_DEC extends AST_STMT
{
    public AST_VAR_DEC var_dec;

    /******************/
    /* CONSTRUCTOR(S) */
    /******************/
    public AST_STMT_VAR_DEC(AST_VAR_DEC var_dec)
    {
        /******************************/
        /* SET A UNIQUE SERIAL NUMBER */
        /******************************/
        SerialNumber = AST_Node_Serial_Number.getFresh();

        /***************************************/
        /* PRINT CORRESPONDING DERIVATION RULE */
        /***************************************/
        System.out.println("====================== stmt -> varDec");

        /*******************************/
        /* COPY INPUT DATA MEMBERS ... */
        /*******************************/
        this.var_dec = var_dec;
    }

    /***************************************************/
    /* The printing message for a variable declaration statement AST node */
    /***************************************************/
    public void PrintMe()
    {
        /*********************************/
        /* AST NODE TYPE = STATEMENT VARIABLE DECLARATION */
        /*********************************/
        System.out.println("AST NODE STATEMENT VARIABLE DECLARATION");

        /******************************************/
        /* RECURSIVELY PRINT varDec ... */
        /******************************************/
        if (var_dec != null) var_dec.PrintMe();

        /***************************************/
        /* PRINT Node to AST GRAPHVIZ DOT file */
        /***************************************/
        AST_GRAPHVIZ.getInstance().logNode(
            SerialNumber,
            "STATEMENT\nVARIABLE DECLARATION");

        /****************************************/
        /* PRINT Edges to AST GRAPHVIZ DOT file */
        /****************************************/
        if (var_dec != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, var_dec.SerialNumber);
    }
}