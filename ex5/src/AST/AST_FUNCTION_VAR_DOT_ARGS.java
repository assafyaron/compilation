package AST;

import SYMBOL_TABLE.*;
import TYPES.*;
import TEMP.*;
import java.util.*;
import IR.*;

public class AST_FUNCTION_VAR_DOT_ARGS extends AST_FUNCTION
{
    
    public AST_VAR var;
    public String funcName;
    public AST_EXP_ARGUMENTS funcArgs;
    public int line;
    
    /******************/
    /* CONSTRUCTOR(S) */
    /******************/
    public AST_FUNCTION_VAR_DOT_ARGS(AST_VAR var, String funcName, AST_EXP_ARGUMENTS funcArgs, int line)
    {
        /******************************/
        /* SET A UNIQUE SERIAL NUMBER */
        /******************************/
        SerialNumber = AST_Node_Serial_Number.getFresh();

        /***************************************/
        /* PRINT CORRESPONDING DERIVATION RULE */
        /***************************************/
        System.out.format("====================== function -> varDot ID( %s ) LPAREN expArgs RPAREN\n", funcName);

        /*******************************/
        /* COPY INPUT DATA MEMBERS ... */
        /*******************************/
        this.var = var;
        this.funcName = funcName;
        this.funcArgs = funcArgs;
        this.line = line;
    }

    public void PrintMe()
    {
        /*********************************/
        /* AST NODE TYPE = FUNCTION VAR DOT ARGS */
        /*********************************/
        System.out.println("AST NODE FUNCTION VAR DOT ARGS");

        /******************************************/
        /* RECURSIVELY PRINT v and name */
        /******************************************/
        if (var != null) var.PrintMe();
        System.out.println("Function name: " + funcName);

        /******************************************/
        /* RECURSIVELY PRINT eA */
        /******************************************/
        if (funcArgs != null) funcArgs.PrintMe();

        /***************************************/
        /* PRINT Node to AST GRAPHVIZ DOT file */
        /***************************************/
        AST_GRAPHVIZ.getInstance().logNode(
            SerialNumber,
            "FUNCTION VAR DOT NO ARGS\nFunction name: " + funcName);

        /****************************************/
        /* PRINT Edges to AST GRAPHVIZ DOT file */
        /****************************************/
        if (var != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, var.SerialNumber);
        if (funcArgs != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, funcArgs.SerialNumber);
    }

    public TYPE SemantMe()
    {
        // semant the variable to get his type
        TYPE varType = var.SemantMe();

        // check if the variable is TYPE_CLASS
		if(!(varType instanceof TYPE_CLASS)){
			System.out.format(">> ERROR(%d) variable is not an instance of a class\n", this.line);
				printError(this.line);
		}
        
        varType = (TYPE_CLASS)varType;
        if (SYMBOL_TABLE.getInstance().get_inside_class()){
            TYPE funcSearch = SYMBOL_TABLE.getInstance().currentClassFunctionMembers.search(funcName);
            if (funcSearch == null && ((TYPE_CLASS)varType).father != null){
                funcSearch = ((TYPE_CLASS)varType).findFunctionInInheritanceTree(funcName);
                if (funcSearch == null) {
                    System.out.format(">> ERROR(%d) %s is not a data memeber of the class or the classes it extends\n", this.line, funcName);
                    printError(this.line);
                }
            }
        }
       
        TYPE funcSearch = ((TYPE_CLASS)varType).findFunctionInInheritanceTree(funcName);
        if (funcSearch == null){
            System.out.format(">> ERROR(%d) %s is not a data memeber of the class or the classes it extends\n", this.line, funcName);
            printError(this.line);
        }

        // function has been declared, we need to check if the decleration and the call have the same arguments and types
        TYPE_LIST args = null;
        if(funcArgs != null){
            args = funcArgs.SemantMe();  // semant the arguments of the function call
        }
        
        if(!(args.checkArgumentsList(((TYPE_FUNCTION)funcSearch).params))){
            System.out.format(">> ERROR(%d) mismatch in types of arguments in function call (\n", this.line, funcName);
            printError(this.line);
        }

        // return the return type of the function
        return ((TYPE_FUNCTION)funcSearch).returnType;
    }

    public TEMP IRme()
    {
        List<TEMP> argumentList = new ArrayList<TEMP>();
        TEMP dst = TEMP_FACTORY.getInstance().getFreshTEMP();

        if (funcArgs != null){
            argumentList = this.funcArgs.IRme(argumentList);}

        String className = IR.getInstance().offsetTable.getClassName(IR.getInstance().getVarName(var));
        if (className == null) {
            System.out.println("Error: Class name not found in offset table.");
            System.exit(1);
        }


        IR.getInstance().Add_IRcommand(new IRcommand_VirtualCall(dst,var.IRme(),funcName,argumentList,IR.getInstance().currLine,className));
        return dst;
    }
}
