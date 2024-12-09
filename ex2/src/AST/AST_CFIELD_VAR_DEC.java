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
		System.out.format("====================== cField -> varDec");

		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.var_dec = var_dec;
	}

	/************************************************/
	/* The printing message for a class field declaration AST node */
	/************************************************/
	public void PrintMe()
	{
		/*******************************/
		/* AST NODE TYPE */
		/*******************************/
		System.out.format("AST NODE cField varDec");

		/*********************************/
		/* Print to AST GRAPHIZ DOT file */
		/*********************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			String.format("cField varDec");
	}
}
