package AST;

public class AST_EXP_STRING extends AST_EXP
{
	public String s;
	
	/******************/
	/* CONSTRUCTOR(S) */
	/******************/
	public AST_EXP_STRING(String s)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		System.out.format("====================== exp -> STRING( %s )\n", s);

		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.s = s;
	}

	/************************************************/
	/* The printing message for an EXP STRING AST node */
	/************************************************/
	public void PrintMe()
	{

		/*********************************/
        	/* AST NODE TYPE = EXPRESSION STRING */
        	/*********************************/
        	System.out.println("AST NODE EXP STRING");
		
		/*******************************/
		/* AST NODE TYPE = AST STRING EXP */
		/*******************************/
		System.out.format("STRING VALUE( %s )\n",s);

		/*********************************/
		/* Print to AST GRAPHIZ DOT file */
		/*********************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			String.format("EXP\nSTRING\n(%s)",s));
	}
}
