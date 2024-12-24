package AST;

public class AST_VAR_FIELD extends AST_VAR
{
	public AST_VAR v;
	public String name;
	
	/******************/
	/* CONSTRUCTOR(S) */
	/******************/
	public AST_VAR_FIELD(AST_VAR v, String name)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		System.out.format("====================== var -> var DOT ID( %s )\n",name);

		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.v = v;
		this.name = name;
	}

	public void PrintMe()
	{
		/*********************************/
		/* AST NODE TYPE = AST VAR FIELD */
		/*********************************/
		System.out.print("AST NODE VAR FIELD\n");

		/**********************************************/
		/* RECURSIVELY PRINT v, then name ... */
		/**********************************************/
		if (v != null) v.PrintMe();
		System.out.format("FIELD NAME( %s )\n",name);

		/***************************************/
		/* PRINT Node to AST GRAPHVIZ DOT file */
		/***************************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			String.format("VAR\nFIELD\n...->%s",name));
		
		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		if (v != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,v.SerialNumber);
	}
}
