package AST;

import TYPES.*;

public class AST_STMT_VAR_DEC extends AST_STMT
{
	/****************/
	/* DATA MEMBERS */
	/****************/
	public AST_VAR_DEC var;
	
	/******************/
	/* CONSTRUCTOR(S) */
	/******************/
	public AST_STMT_VAR_DEC(AST_VAR_DEC var)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		this.var = var;
	}
	
	public TYPE SemantMe()
	{
		return var.SemantMe();
	}
	
	public void PrintMe()
	{
		var.PrintMe();

		/***************************************/
		/* PRINT Node to AST GRAPHVIZ DOT file */
		/***************************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			String.format("STMT\nVAR\nDEC"));
		
		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,var.SerialNumber);		
	}
}
