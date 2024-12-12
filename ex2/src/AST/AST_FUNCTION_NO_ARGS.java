package AST;

public class AST_FUNCTION_NO_ARGS extends AST_FUNCTION
{
    public String name;

    /******************/
    /* CONSTRUCTOR(S) */
    /******************/
    public AST_FUNCTION_NO_ARGS(String name)
    {
        /******************************/
        /* SET A UNIQUE SERIAL NUMBER */
        /******************************/
        SerialNumber = AST_Node_Serial_Number.getFresh();

        /***************************************/
        /* PRINT CORRESPONDING DERIVATION RULE */
        /***************************************/
        System.out.format("====================== function -> fuction no args\n");

        /*******************************/
        /* COPY INPUT DATA MEMBERS ... */
        /*******************************/
        this.name = name;
    }

}
