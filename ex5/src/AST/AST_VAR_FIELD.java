package AST;
import TYPES.*;
import SYMBOL_TABLE.*;
import TEMP.*;
import IR.*;

public class AST_VAR_FIELD extends AST_VAR
{

	public AST_VAR var;
	public String variableDataMemberName;
	public int line;
	
	/******************/
	/* CONSTRUCTOR(S) */
	/******************/
	public AST_VAR_FIELD(AST_VAR var, String variableDataMemberName, int line)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		System.out.format("====================== var -> var DOT ID( %s )\n",variableDataMemberName);

		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.var = var;
		this.variableDataMemberName = variableDataMemberName;
		this.line = line;
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
		if (var != null) var.PrintMe();
		System.out.format("FIELD NAME( %s )\n",variableDataMemberName);

		/***************************************/
		/* PRINT Node to AST GRAPHVIZ DOT file */
		/***************************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			String.format("VAR\nFIELD\n...->%s",variableDataMemberName));
		
		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		if (var != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,var.SerialNumber);
	}

	public TYPE SemantMe()
	{
		// semant the variable
		TYPE type = var.SemantMe();
		myClassName = type.name;
		
		// check if the variable is TYPE_CLASS
		if(!(type instanceof TYPE_CLASS)){
			System.out.format(">> ERROR(%d) variable is not an instance of a class\n", this.line);
			printError(this.line);
		}

		// check if the variable is indeed a data member of the class or the classes it extends, if we are in the class currently
		type = (TYPE_CLASS)type;

		TYPE varSearch = null;
		if(SYMBOL_TABLE.getInstance().get_current_class() != null && SYMBOL_TABLE.getInstance().get_current_class().name.equals(type.name)){
			varSearch = SYMBOL_TABLE.getInstance().currentClassVariableMembers.search(variableDataMemberName);
			if(varSearch == null){
				varSearch = ((TYPE_CLASS)type).findVariableInInheritanceTree(variableDataMemberName);
				if(varSearch == null){
					System.out.format(">> ERROR(%d) %s is not a data memeber of the class or the classes it extends\n", this.line, variableDataMemberName);
					printError(this.line);
				}
			}
		}
		
		//else, we just look at the class and its inheritance tree
		else{
			varSearch = ((TYPE_CLASS)type).findVariableInInheritanceTree(variableDataMemberName);
			if(varSearch == null){
				System.out.format(">> ERROR(%d) %s is not a data memeber of the class or the classes it extends\n", this.line, variableDataMemberName);
				printError(this.line);
			}
		}
	
		// return the variable data member type we found (if we found it)
		return varSearch;
	}

	public TEMP IRme()
	{
		TEMP varTemp = var.IRme();
		TEMP dst = TEMP_FACTORY.getInstance().getFreshTEMP();
		String currClass = IR.getInstance().offsetTable.getClassName(getVarName());
		if (currClass == null) {
			System.out.format(">> HERE 1 ERROR(%d) variable %s has not been declared\n", this.line, getVarName());
			System.exit(1);
		}
		IR.getInstance().Add_IRcommand(new IRcommand_Field_Access(dst, varTemp, variableDataMemberName, IR.getInstance().currLine, currClass));
		return dst;
	}

	public String getVarName() {
		if (var instanceof AST_VAR_SIMPLE) {
			AST_VAR_SIMPLE varSimple = (AST_VAR_SIMPLE) var;
			return varSimple.varName;
		} else if (var instanceof AST_VAR_FIELD) {
			AST_VAR_FIELD varField = (AST_VAR_FIELD) var;
			return varField.getVarName();
		} else if (var instanceof AST_VAR_SUBSCRIPT) {
			AST_VAR_SUBSCRIPT varSubscript = (AST_VAR_SUBSCRIPT) var;
			return varSubscript.getVarName();
		} else {
			System.out.format(">> ERROR(%d) variable name not found\n", this.line);
			System.exit(1);
		}
		return null;
	}
}
