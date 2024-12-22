package AST;

public class AST_VAR_DEC_ARGS extends AST_VAR_DEC
{
	public AST_TYPE t;
	public String name;
	public AST_EXP e;
  	
	
	/******************/
	/* CONSTRUCTOR(S) */
	/******************/
	public AST_VAR_DEC_ARGS(AST_TYPE t, String name, AST_EXP e)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		System.out.print("====================== varDec -> type ID ASSIGN exp SEMICOLON\n");

		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.t = t;
		this.name = name;
		this.e = e;
	}

	public void PrintMe()
    {
        /*********************************/
        /* AST NODE TYPE = VAR DEC ARGS */
        /*********************************/
        System.out.println("AST NODE VAR DEC ARGS");

        /******************************************/
        /* RECURSIVELY PRINT t and e ... */
        /******************************************/
        if (t != null) t.PrintMe();
	System.out.println("ID Name: " + name);
        if (e != null) e.PrintMe();

        /***************************************/
        /* PRINT Node to AST GRAPHVIZ DOT file */
        /***************************************/
        AST_GRAPHVIZ.getInstance().logNode(
            SerialNumber,
            "VAR DEC\nARGS");

        /****************************************/
        /* PRINT Edges to AST GRAPHVIZ DOT file */
        /****************************************/
        if (t != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, t.SerialNumber);
        if (e != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, e.SerialNumber);
    }

}
