package AST;

public class AST_FUNCTION_VAR_DOT_ARGS extends AST_FUNCTION
{
    public AST_VAR v;
    public String name;
    public AST_EXP_ARGUMENTS eA;
    
    /******************/
    /* CONSTRUCTOR(S) */
    /******************/
    public AST_FUNCTION_VAR_DOT_ARGS(AST_VAR v, String name, AST_EXP_ARGUMENTS eA)
    {
        /******************************/
        /* SET A UNIQUE SERIAL NUMBER */
        /******************************/
        SerialNumber = AST_Node_Serial_Number.getFresh();

        /***************************************/
        /* PRINT CORRESPONDING DERIVATION RULE */
        /***************************************/
        System.out.format("====================== function -> fuction var dot args\n");

        /*******************************/
        /* COPY INPUT DATA MEMBERS ... */
        /*******************************/
        this.v = v;
        this.name = name;
        this.eA = eA;
    }
}
