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

}
