package AST;

public class AST_FUNC_DEC_ARGS extends AST_FUNC_DEC
{
    public AST_TYPE t;
    public String name;
    public AST_FUNC_DEC_ARGS_LIST args;
    public AST_STMT_LIST body;

    /******************/
    /* CONSTRUCTOR(S) */
    /******************/
    public AST_FUNC_DEC_ARGS(AST_TYPE t, String name, AST_FUNC_DEC_ARGS_LIST args, AST_STMT_LIST body)
    {
        /******************************/
        /* SET A UNIQUE SERIAL NUMBER */
        /******************************/
        SerialNumber = AST_Node_Serial_Number.getFresh();

        /***************************************/
        /* PRINT CORRESPONDING DERIVATION RULE */
        /***************************************/
        System.out.format("====================== funcDec -> func dec args\n");

        /*******************************/
        /* COPY INPUT DATA MEMBERS ... */
        /*******************************/
        this.t = t;
        this.name = name;
        this.args = args;
        this.body = body;


    }

}
