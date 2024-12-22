package AST;

public class AST_CFIELD_VAR_DEC extends AST_CFIELD
{
	public AST_VAR_DEC var_dec;
	
	/******************/
	/* CONSTRUCTOR(S) */
	/******************/
	public AST_CFIELD_VAR_DEC(AST_VAR_DEC var_dec)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		System.out.format("====================== cField -> varDec\n");

		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.var_dec = var_dec;
	}


	 public void PrintMe()
    {
        /*********************************/
        /* AST NODE TYPE = CLASS FIELD VARIABLE DECLARATION */
        /*********************************/
        System.out.println("AST NODE CFIELD VAR DEC");

        /******************************************/
        /* RECURSIVELY PRINT var_dec ... */
        /******************************************/
        if (var_dec != null) var_dec.PrintMe();

        /***************************************/
        /* PRINT Node to AST GRAPHVIZ DOT file */
        /***************************************/
        AST_GRAPHVIZ.getInstance().logNode(
            SerialNumber,
            "CLASS FIELD\nVARIABLE DECLARATION");

        /****************************************/
        /* PRINT Edges to AST GRAPHVIZ DOT file */
        /****************************************/
        if (var_dec != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, var_dec.SerialNumber);
    }
}
