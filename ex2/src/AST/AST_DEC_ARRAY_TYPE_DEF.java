package AST;

public class AST_DEC_ARRAY_TYPE_DEF extends AST_DEC
{
    public AST_ARRAY_TYPE_DEF array_type_def;

    /******************/
    /* CONSTRUCTOR(S) */
    /******************/
    public AST_DEC_ARRAY_TYPE_DEF(AST_ARRAY_TYPE_DEF array_type_def)
    {
        /******************************/
        /* SET A UNIQUE SERIAL NUMBER */
        /******************************/
        SerialNumber = AST_Node_Serial_Number.getFresh();

        /***************************************/
        /* PRINT CORRESPONDING DERIVATION RULE */
        /***************************************/
        System.out.format("====================== dec -> arrayTypeDef\n");

        /*******************************/
        /* COPY INPUT DATA MEMBERS ... */
        /*******************************/
        this.array_type_def = array_type_def;

    }

}
