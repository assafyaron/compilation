package AST;
import TYPES.*;
import SYMBOL_TABLE.*;
import IR.*;
import TEMP.*;

public class AST_VAR_SIMPLE extends AST_VAR
{

	public String varName;
	public int line;
	
	/******************/
	/* CONSTRUCTOR(S) */
	/******************/
	public AST_VAR_SIMPLE(String varName, int line)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();
	
		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		System.out.format("====================== var -> ID( %s )\n",varName);

		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.varName = varName;
		this.line = line;
	}


	public void PrintMe() {
		// AST NODE TYPE = AST VAR SIMPLE
		System.out.format("AST NODE VAR SIMPLE( %s )\n",varName);

		/*********************************/
		/* Print to AST GRAPHIZ DOT file */
		/*********************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			String.format("VAR\nSIMPLE\n(%s)",varName));
	}

	public TYPE SemantMe()
    {
        TYPE varFindType = null;
		//we now check to see if the variable has already been declared in the following order:
		//first we check from the inner scope
		varFindType = SYMBOL_TABLE.getInstance().findInClass(varName);
		if (varFindType != null && varFindType instanceof TYPE_CLASS){
			TYPE currClass = SYMBOL_TABLE.getInstance().get_current_class();
			if(!(currClass != null && ((TYPE_CLASS)currClass).name.equals(((TYPE_CLASS)varFindType).name))){
				varFindType = SYMBOL_TABLE.getInstance().findClassInSymbolTable(((TYPE_CLASS)varFindType).name);
			}
		}

		if (varFindType == null){
			// we want to check in inheritance tree
			TYPE currClass = SYMBOL_TABLE.getInstance().get_current_class();
			if (currClass != null && ((TYPE_CLASS)currClass).father != null){
				varFindType = ((TYPE_CLASS)currClass).findVariableInInheritanceTree(varName);
				if (varFindType != null && varFindType instanceof TYPE_CLASS){
					if(!(currClass != null && ((TYPE_CLASS)currClass).name.equals(((TYPE_CLASS)varFindType).name))){
						varFindType = SYMBOL_TABLE.getInstance().findClassInSymbolTable(((TYPE_CLASS)varFindType).name);
					}
				}
				if (varFindType == null && varFindType instanceof TYPE_CLASS){
					// only left to check if it is in global scope
					varFindType = SYMBOL_TABLE.getInstance().find(varName);
					if (varFindType != null && varFindType instanceof TYPE_CLASS){
						if(!(currClass != null && ((TYPE_CLASS)currClass).name.equals(((TYPE_CLASS)varFindType).name))){
							varFindType = SYMBOL_TABLE.getInstance().findClassInSymbolTable(((TYPE_CLASS)varFindType).name);
						}
					}
					if (varFindType == null){
						// we didnt find it
						System.out.format(">> ERROR(%d) variable %s has not been declared\n", this.line, varName);
						printError(this.line);
					}
				}
				else{
					varFindType = SYMBOL_TABLE.getInstance().find(varName);
				}
			}
			else {
				varFindType = SYMBOL_TABLE.getInstance().find(varName);
				if (varFindType != null && varFindType instanceof TYPE_CLASS){
					if(!(currClass != null && ((TYPE_CLASS)currClass).name.equals(((TYPE_CLASS)varFindType).name))){
						varFindType = SYMBOL_TABLE.getInstance().findClassInSymbolTable(((TYPE_CLASS)varFindType).name);
					}
				}
				if (varFindType == null){
					// we didnt find it
					System.out.format(">> ERROR(%d) variable %s has not been declared\n", this.line, varName);
					printError(this.line);
				}
			}
		}
			
		if(varFindType instanceof TYPE_CLASS){
			return (TYPE_CLASS)varFindType;
		}
		
		return varFindType;
	}

	public TEMP IRme()
	{
		TEMP t = TEMP_FACTORY.getInstance().getFreshTEMP();
		int offset = IR.getInstance().offsetTable.getOffsetOfVar(varName);
		boolean isArg = IR.getInstance().offsetTable.getIsArg(varName);
		boolean isGlobal = IR.getInstance().offsetTable.getIsGlobal(varName);
		
		if (offset != -1) {

			if (IR.getInstance().currentClassName != null && IR.getInstance().currentFuncName != null) {
				IR.getInstance().Add_IRcommand(new IRcommand_Load(t, varName,IR.getInstance().currLine, offset, isArg, isGlobal, true));
				return t;
			} else {
				IR.getInstance().Add_IRcommand(new IRcommand_Load(t, varName,IR.getInstance().currLine, offset, isArg, isGlobal, false));
				return t;
			}

		} else {
			System.out.format(">> ERROR(%d) variable %s has not been declared\n", this.line, varName);
			printError(this.line);

		}
		return null;
	}

	public String getVarName() {
		return varName;
	}

}
