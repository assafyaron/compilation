package AST;

public class AST_EXP_ARGUMENTS extends AST_Node
{
	/****************/
	/* DATA MEMBERS */
	/****************/
	public AST_EXP e;
	public AST_EXP_ARGUMENTS eA;

	/******************/
	/* CONSTRUCTOR(S) */
	/******************/
	public AST_EXP_ARGUMENTS(AST_EXP e, AST_EXP_ARGUMENTS eA)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		if (eA != null) System.out.print("====================== expArguments -> exp expArguments\n");
		if (eA == null) System.out.print("====================== expArguments -> exp\n");

		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.e = e;
		this.eA = eA;
	}

	/******************************************************/
	/* The printing message for an expression arguments AST node */
	/******************************************************/
	public void PrintMe()
	{
		/**************************************/
		/* AST NODE TYPE = AST EXP ARGS */
		/**************************************/
		System.out.print("AST NODE EXP ARGS\n");

		/*************************************/
		/* RECURSIVELY PRINT HEAD + TAIL ... */
		/*************************************/
		if (e != null) e.PrintMe();
		if (eA != null) eA.PrintMe();

		/**********************************/
		/* PRINT to AST GRAPHVIZ DOT file */
		/**********************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			"EXP\ARGS\n");
		
		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		if (e != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,e.SerialNumber);
		if (eA != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,eA.SerialNumber);
	}
}
