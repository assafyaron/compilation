package AST;

public class AST_PROGRAM_REC extends AST_PROGRAM {

    /****************/
	/* DATA MEMBERS */
	/****************/
    public AST_DEC dec;
    public AST_PROGRAM prog;

    /******************/
    /* CONSTRUCTOR(S) */
    /******************/
    public AST_PROGRAM_REC(AST_DEC dec, AST_PROGRAM prog)
    {
        /******************************/
        /* SET A UNIQUE SERIAL NUMBER */
        /******************************/
        SerialNumber = AST_Node_Serial_Number.getFresh();

        /***************************************/
        /* PRINT CORRESPONDING DERIVATION RULE */
        /***************************************/
        if(prog == null) System.out.println("====================== program -> dec\n");
        else System.out.println("====================== program -> dec program\n");

        /*******************************/
        /* COPY INPUT DATA NENBERS ... */
        /*******************************/
        this.dec = dec;
        this.prog = prog;
    }
}
