package AST;
import TYPES.*;
import TEMP.*;
import IR.*;

public class AST_VAR_SUBSCRIPT extends AST_VAR
{

	public AST_VAR arrayName;
	public AST_EXP idxValue;
	public int line;
	public boolean isAccess = false;
	
	/******************/
	/* CONSTRUCTOR(S) */
	/******************/
	public AST_VAR_SUBSCRIPT(AST_VAR arrayName,AST_EXP idxValue, int line)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		System.out.print("====================== var -> var LBRACK exp RBRACK\n");

		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.arrayName = arrayName;
		this.idxValue = idxValue;
		this.line = line;
	}

	/*****************************************************/
	/* The printing message for a subscript var AST node */
	/*****************************************************/
	public void PrintMe()
	{
		/*************************************/
		/* AST NODE TYPE = AST VAR SUBSCRIPT */
		/*************************************/
		System.out.print("AST NODE VAR SUBSCRIPT\n");

		/****************************************/
		/* RECURSIVELY PRINT VAR + SUBSRIPT ... */
		/****************************************/
		if (arrayName != null) arrayName.PrintMe();
		if (idxValue != null) idxValue.PrintMe();
		
		/***************************************/
		/* PRINT Node to AST GRAPHVIZ DOT file */
		/***************************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			"VAR\nSUBSCRIPT\n...[...]");
		
		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		if (arrayName != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,arrayName.SerialNumber);
		if (idxValue != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,idxValue.SerialNumber);
	}

	public TYPE SemantMe()
	{
		// semant recursively
		TYPE expType = idxValue.SemantMe();
		TYPE varType = arrayName.SemantMe();

		// check if variable is an instance of array
		if(!(varType instanceof TYPE_ARRAY))
		{
			System.out.format(">> ERROR(%d) (%s) is not subscriptable\n",line, varType.name);
			printError(line);
		}

		// check if subscript is a non-negative integer
		if(!(expType instanceof TYPE_INT))
        {
            System.out.format(">> ERROR(%d) subscript a non-negative integer\n",line);
            printError(line);
        }
        else
        {
            if((idxValue instanceof AST_EXP_INT) && ((AST_EXP_INT)idxValue).i < 0)
            {
                System.out.format(">> ERROR(%d) subscript a non-negative integer\n",line);
                printError(line);
			}
        }

		// return the array elements type
		myClassName = ((TYPE_ARRAY)varType).type.name;
		return ((TYPE_ARRAY)varType).type;
	}

	public TEMP IRme() 
	{
		TEMP dst = TEMP_FACTORY.getInstance().getFreshTEMP();
		TEMP arrTemp = arrayName.IRme();
		TEMP idxValTemp = idxValue.IRme();
		IR.getInstance().Add_IRcommand(new IRcommand_Array_Access(dst, arrTemp, idxValTemp,IR.getInstance().currLine));
		return dst;
	}

	public String getVarName() {
		if (arrayName instanceof AST_VAR_SIMPLE) {
			return ((AST_VAR_SIMPLE) arrayName).varName;
		} else if (arrayName instanceof AST_VAR_SUBSCRIPT) {
			return ((AST_VAR_SUBSCRIPT) arrayName).getVarName();
		} else if (arrayName instanceof AST_VAR_FIELD) {
			return ((AST_VAR_FIELD) arrayName).getVarName();
		}
		return null;
	}
}
