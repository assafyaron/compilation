package AST;

public class AST_FUNC_DEC_ARGS extends AST_FUNC_DEC
{
    public AST_TYPE t1;
    public String name1;
    public AST_TYPE t2;
    public String name2;
    public AST_FUNC_DEC_ARGS_LIST args;
    public AST_STMT_LIST body;

    /******************/
    /* CONSTRUCTOR(S) */
    /******************/
    public AST_FUNC_DEC_ARGS(AST_TYPE t1, String name1, AST_TYPE t2, String name2, AST_FUNC_DEC_ARGS_LIST args, AST_STMT_LIST body)
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
        this.t1 = t1;
        this.name1 = name1;
        this.t2 = t2;
        this.name2 = name2;
        this.args = args;
        this.body = body;


    }

}
