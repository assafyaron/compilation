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
		if (args != null) System.out.print("====================== funcDecArgs -> COMMA TYPE FUNC DEC ARGS LIST\n");
		if (args == null) System.out.print("====================== funcDecArgs  -> COMMA TYPE\n");

		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.t = t;
		this.name = name;
    		this.args = args;
	}

	public void PrintMe() {
        	/*********************************/
        	/* AST NODE TYPE = FUNC DEC ARGS LIST */
        	/*********************************/
        	System.out.println("AST NODE FUNC DEC ARGS LIST");

        	/******************************************/
        	/* RECURSIVELY PRINT t and args ... */
        	/******************************************/
        	if (t != null) t.PrintMe();
		System.out.format("Function name( %s )\n", name);
        	if (funcArgs != null) funcArgs.PrintMe();

        	/***************************************/
        	/* PRINT Node to AST GRAPHVIZ DOT file */
        	/***************************************/
        	AST_GRAPHVIZ.getInstance().logNode(
            		SerialNumber,
            		"FUNC DEC ARGS LIST\n");

        	/****************************************/
        	/* PRINT Edges to AST GRAPHVIZ DOT file */
        	/****************************************/
        	if (T != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, T.SerialNumber);
        	if (args != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, args.SerialNumber);
	}

}
