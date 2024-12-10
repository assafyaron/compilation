package AST;

public class AST_ARRAY_TYPE_DEF_SIMPLE extends AST_ARRAY_TYPE_DEF
{
	public String name;
  	public AST_TYPE t;
	
	/******************/
	/* CONSTRUCTOR(S) */
	/******************/
	public AST_ARRAY_TYPE_DEF_SIMPLE(String name, AST_TYPE t)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		System.out.format("====================== arrayTypedef -> ARRAY ID( %s ) EQ type LBRACK RBRACK SEMICOLON\n", name);

		/*******************************/
		/* COPY INPUT DATA MEMBERS ... */
		/*******************************/
		this.name = name;
    		this.t = t;
	}

	/************************************************/
	/* The printing message for an ARRAY TYPEDEF AST node */
	/************************************************/
	public void PrintMe()
    {
        /*********************************/
        /* AST NODE TYPE = ARRAY TYPEDEF */
        /*********************************/
        System.out.println("AST NODE ARRAY TYPEDEF");

        /******************************************/
        /* PRINT name and type */
        /******************************************/
        System.out.format("ARRAY NAME( %s )\n", name);
        System.out.print("TYPE:\n");
        if (typ != null) typ.PrintMe();

        /***************************************/
        /* PRINT Node to AST GRAPHVIZ DOT file */
        /***************************************/
        AST_GRAPHVIZ.getInstance().logNode(
            SerialNumber,
            String.format("ARRAY\nTYPEDEF\n...->%s", name));

        /****************************************/
        /* PRINT Edges to AST GRAPHVIZ DOT file */
        /****************************************/
        if (typ != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, typ.SerialNumber);
    }
}
