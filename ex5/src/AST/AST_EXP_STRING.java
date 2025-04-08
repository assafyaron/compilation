package AST;
import TYPES.*;
import TEMP.*;
import IR.*;

public class AST_EXP_STRING extends AST_EXP
{
	
	public String value;
	
	/******************/
	/* CONSTRUCTOR(S) */
	/******************/
	public AST_EXP_STRING(String value)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		System.out.format("====================== exp -> STRING( %s )\n", value);
		this.value = value;
	}

	/******************************************************/
	/* The printing message for a STRING EXP AST node */
	/******************************************************/
	public void PrintMe()
	{
		/*******************************/
		/* AST NODE TYPE = AST STRING EXP */
		/*******************************/
		System.out.format("AST NODE STRING( %s )\n",value);

		/***************************************/
		/* PRINT Node to AST GRAPHVIZ DOT file */
		/***************************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			String.format("STRING\n%s",value.replace('"','\'')));
	}

	public TYPE SemantMe()
	{
		// return TYPE_STRING
		typeName = TYPE_STRING.getInstance().name;
		return TYPE_STRING.getInstance();
	}

	public TEMP IRme()
	{
		TEMP t = TEMP_FACTORY.getInstance().getFreshTEMP();
		IR.getInstance().Add_IRcommand(new IRcommandConstString(t, this.value,IR.getInstance().currLine));
		return t;
	}
}
