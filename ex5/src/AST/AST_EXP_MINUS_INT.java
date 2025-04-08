package AST;
import TYPES.*;
import TEMP.*;
import IR.*;

public class AST_EXP_MINUS_INT extends AST_EXP
{

	public int i;
	
	/******************/
	/* CONSTRUCTOR(S) */
	/******************/
	public AST_EXP_MINUS_INT(int i)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		System.out.format("====================== exp -> MINUS INT( %d )\n", i);

		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.i = i;
	}

	/************************************************/
	/* The printing message for an EXP MINUS INT AST node */
	/************************************************/
	public void PrintMe()
	{
		/*******************************/
		/* AST NODE TYPE = EXP MINUS INT */
		/*******************************/

		System.out.print("AST NODE EXP MINUS INT\n");

        	/******************************************/
        	/* PRINT i ... */
        	/******************************************/
		 System.out.format("INT VALUE( %d )\n", i);

		/*********************************/
		/* Print to AST GRAPHIZ DOT file */
		/*********************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
            String.format("EXP\nMINUS INT\n(%d)",i));
	}

	public TYPE SemantMe()
	{
		// return TYPE_INT
		typeName = TYPE_INT.getInstance().name;
		return TYPE_INT.getInstance();
	}

	public TEMP IRme() 
	{
		TEMP t = TEMP_FACTORY.getInstance().getFreshTEMP();
		IR.getInstance().Add_IRcommand(new IRcommandConstInt(t, -(this.i),IR.getInstance().currLine));
		return t;
	}
}
