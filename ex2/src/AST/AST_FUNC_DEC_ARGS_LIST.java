package AST;

public class AST_FUNC_DEC_ARGS_LIST extends AST_Node
{
	/****************/
	/* DATA MEMBERS */
	/****************/
	public AST_TYPE t;
  	public String name;
	public AST_FUNC_DEC_ARGS_LIST args;

	/******************/
	/* CONSTRUCTOR(S) */
	/******************/
	public AST_FUNC_DEC_ARGS_LIST(AST_TYPE t, String name, AST_FUNC_DEC_ARGS_LIST args)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		if (args != null) System.out.print("====================== funcDecArgs -> comma type funcDecArgs\n");
		if (args == null) System.out.print("====================== funcDecArgs -> comma type\n");

		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.t = t;
		this.name = name;
    	this.args = args;
	}

}
