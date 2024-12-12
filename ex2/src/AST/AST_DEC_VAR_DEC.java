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

}
