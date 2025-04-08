package AST;
import TYPES.*;
import TEMP.*;
import IR.*;
import SYMBOL_TABLE.*;


public class AST_STMT_ASSIGN extends AST_STMT
{

	public AST_VAR var;
	public AST_EXP exp;
	public int line;

	/*******************/
	/*  CONSTRUCTOR(S) */
	/*******************/
	public AST_STMT_ASSIGN(AST_VAR var,AST_EXP exp, int line)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		System.out.print("====================== stmt-> var ASSIGN exp SEMICOLON\n");

		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.var = var;
		this.exp = exp;
		this.line = line;
	}

	public void PrintMe()
	{
		/********************************************/
		/* AST NODE TYPE = AST STMT ASSIGN */
		/********************************************/
		System.out.print("AST NODE STMT ASSIGN\n");

		/***********************************/
		/* RECURSIVELY PRINT VAR + EXP ... */
		/***********************************/
		if (var != null) var.PrintMe();
		if (exp != null) exp.PrintMe();

		/***************************************/
		/* PRINT Node to AST GRAPHVIZ DOT file */
		/***************************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			"STMT\nASSIGN\n");
		
		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,var.SerialNumber);
		AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,exp.SerialNumber);
	}

	public TYPE SemantMe()
	{
		// semant the variavble and the NEW expression
		TYPE varType = var.SemantMe();
		TYPE expType = exp.SemantMe();

		if (varType instanceof TYPE_CLASS){
			if (expType instanceof TYPE_CLASS){
				if(!(((TYPE_CLASS)expType).checkIfInherit((TYPE_CLASS)varType))){
					System.out.format(">> ERROR [%d] Type mismatch in assignment: %s cannot be assigned to %s\n", line, expType.name, varType.name);
					printError(line);
				}
			}
			else{
				if (!(expType instanceof TYPE_NIL)){
					System.out.format(">> ERROR [%d] Type mismatch in assignment: %s cannot be assigned to %s\n", line, expType.name, varType.name);
					printError(line);
				}
			}
		}
		else if (varType instanceof TYPE_ARRAY){
			if (expType instanceof TYPE_ARRAY){
				if(!((((TYPE_ARRAY)expType).name).equals(((TYPE_ARRAY)varType).name))){
					System.out.format(">> ERROR [%d] Type mismatch in assignment: %s cannot be assigned to %s\n", line, expType.name, varType.name);
					printError(line);
				}
			}
			else{
				if (!(expType instanceof TYPE_NIL)){
					System.out.format(">> ERROR [%d] Type mismatch in assignment: %s cannot be assigned to %s\n", line, expType.name, varType.name);
					printError(line);
				}
			}
		}
		else if (!(varType.equals(expType))){
			System.out.format(">> ERROR [%d] Type mismatch in assignment: %s cannot be assigned to %s\n", line, expType.name, varType.name);
			printError(line);	
		}

		return null;
	}

	public TEMP IRme() 
	{	
		TEMP src = null;

		if (var instanceof AST_VAR_SIMPLE) {

			AST_VAR_SIMPLE varSimple = (AST_VAR_SIMPLE) var;
			String varName = varSimple.varName;
			int offset = IR.getInstance().offsetTable.getOffsetOfVar(varName);
			boolean isGlobal = IR.getInstance().offsetTable.getIsGlobal(varName);
			boolean isArg = IR.getInstance().offsetTable.getIsArg(varName);
			if (IR.getInstance().currentClassName != null && IR.getInstance().currentFuncName != null) {
				IR.getInstance().Add_IRcommand(new IRcommand_Store(varName,exp.IRme(),IR.getInstance().currLine, offset, isArg, isGlobal,true));
			} else {
				IR.getInstance().Add_IRcommand(new IRcommand_Store(varName,exp.IRme(),IR.getInstance().currLine, offset, isArg, isGlobal,false));
			}

		} else if (var instanceof AST_VAR_SUBSCRIPT) {
			AST_VAR_SUBSCRIPT varSubscript = (AST_VAR_SUBSCRIPT) var;
			TEMP idxValTemp = varSubscript.idxValue.IRme();
			TEMP arrTemp = varSubscript.arrayName.IRme();
			IR.getInstance().Add_IRcommand(new IRcommand_Array_Set(arrTemp,idxValTemp,IR.getInstance().currLine,exp.IRme()));
			
		} else if (var instanceof AST_VAR_FIELD) {
			AST_VAR_FIELD varField = (AST_VAR_FIELD) var;
			src = exp.IRme();
			TEMP dst = varField.var.IRme();
			String varName = varField.getVarName();
			String className = IR.getInstance().offsetTable.getClassName(varName);
			if (className == null) {
				System.out.format(">> ERROR [%d] Class name not found for field assignment, var %s\n", line, varName);
				System.exit(1);
			}
			IR.getInstance().Add_IRcommand(new IRcommand_Field_Set(dst,src,varField.variableDataMemberName,IR.getInstance().currLine,className));
		
		} else {

			src = exp.IRme();
		}

		if (exp instanceof AST_EXP_VAR) {
			AST_EXP_VAR expVar = (AST_EXP_VAR) exp;
			String className = IR.getInstance().offsetTable.getClassName(expVar.var.getVarName());
			if (className != null) {
				String nameOfVar = IR.getInstance().getVarName(var);
				IR.getInstance().offsetTable.getTopFrame().varToClassMap.put(nameOfVar,className);
				System.out.println("Adding to varToClassMap: " + nameOfVar + " -> " + className);
			}
		}

		return src;
	}
}
