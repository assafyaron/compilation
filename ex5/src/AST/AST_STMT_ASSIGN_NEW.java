package AST;
import TYPES.*;
import TEMP.*;
import IR.*;
import SYMBOL_TABLE.*;

public class AST_STMT_ASSIGN_NEW extends AST_STMT
{

    public AST_VAR var;
    public AST_NEW_EXP newExp;
    public int line;

    /******************/
    /* CONSTRUCTOR(S) */
    /******************/
    public AST_STMT_ASSIGN_NEW(AST_VAR var, AST_NEW_EXP newExp, int line) {
        /******************************/
        /* SET A UNIQUE SERIAL NUMBER */
        /******************************/
        SerialNumber = AST_Node_Serial_Number.getFresh();

        /***************************************/
        /* PRINT CORRESPONDING DERIVATION RULE */
        /***************************************/
        System.out.println("====================== stmt -> var ASSIGN newExp SEMICOLON\n");

        /*******************************/
        /* COPY INPUT DATA MEMBERS ... */
        /*******************************/
        this.var = var;
        this.newExp = newExp;
        this.line = line;
    }

    /***************************************************/
    /* The printing message for an assignment statement with new expression AST node */
    /***************************************************/
    public void PrintMe()
    {
        /*********************************/
        /* AST NODE TYPE = STMT ASSIGN NEW */
        /*********************************/
        System.out.println("AST NODE STMT ASSIGN NEW");

        /******************************************/
        /* RECURSIVELY PRINT v and new_exp ... */
        /******************************************/
        if (var != null) var.PrintMe();
        if (newExp != null) newExp.PrintMe();

        /***************************************/
        /* PRINT Node to AST GRAPHVIZ DOT file */
        /***************************************/
        AST_GRAPHVIZ.getInstance().logNode(
            SerialNumber,
            "STMT\nASSIGN NEW");

        /****************************************/
        /* PRINT Edges to AST GRAPHVIZ DOT file */
        /****************************************/
        if (var != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, var.SerialNumber);
        if (newExp != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, newExp.SerialNumber);
    }

    public TYPE SemantMe()
    {
        // semant the variavble and the NEW expression
        TYPE varType = var.SemantMe();
        TYPE newType = newExp.SemantMe();

        TYPE elemType = null;

        if (varType instanceof TYPE_ARRAY){
            elemType = ((TYPE_ARRAY)varType).type;
        }
        else {
            elemType = varType;
        }

        // check if variable types are equal
        if(!elemType.equals(newType)){
            // check if the type of the variable is TYPE_CLASS
            if(!(varType instanceof TYPE_CLASS)){
                System.out.format(">> ERROR(%d) type missmatch, cannot assign %s to %s\n",line, newType.name, varType.name);
                printError(line);
            }

            // variable type is a class but not equal to newExp type, so we check for inheritance
            if(!((TYPE_CLASS)newType).checkIfInherit((TYPE_CLASS)varType)){
                System.out.format(">> ERROR(%d) type missmatch, cannot assign %s to %s\n",line, newType.name, varType.name);
                printError(line);
                }
            }

        // check if we assign a non array type to an array
        if(varType instanceof TYPE_ARRAY && !(newExp instanceof AST_NEW_TYPE_EXP_IN_BRACKS)){
            System.out.format(">> ERROR(%d) type missmatch, cannot assign non array to array\n",line);
            printError(line);
        }

        // check if we assign assign array type to a non array type
        if(!(varType instanceof TYPE_ARRAY) && !(newExp instanceof AST_NEW_TYPE)){
            System.out.format(">> ERROR(%d) type missmatch, cannot assign array to non array\n",line);
            printError(line);
        }

        return null;
    }

    public TEMP IRme()
    {        
        TEMP src = null;

		if (newExp != null) {

            if (var instanceof AST_VAR_FIELD) {
                AST_VAR_FIELD varField = (AST_VAR_FIELD) var;
                TEMP varTemp = varField.var.IRme();
                String varName = varField.getVarName();
                String className = IR.getInstance().offsetTable.getClassName(varName);
                if (className == null) {
                    System.out.format(">> ERROR [%d] Class name not found for field assignment, var %s\n", line, varName);
                    System.exit(1);
                }
                if (newExp instanceof AST_NEW_TYPE) {
                    AST_NEW_TYPE newType = (AST_NEW_TYPE) newExp;
                    src = newType.IRme(varName);
                } else {
                    src = newExp.IRme();
                }
                IR.getInstance().Add_IRcommand(new IRcommand_Field_Set(varTemp,src,varField.variableDataMemberName,IR.getInstance().currLine,className));

            } else if (var instanceof AST_VAR_SUBSCRIPT) {
                AST_VAR_SUBSCRIPT varSubscript = (AST_VAR_SUBSCRIPT) var;
                TEMP idxValTemp = varSubscript.idxValue.IRme();
                TEMP arrTemp = varSubscript.arrayName.IRme();
                if (newExp instanceof AST_NEW_TYPE) {
                    AST_NEW_TYPE newType = (AST_NEW_TYPE) newExp;
                    src = newType.IRme(varSubscript.getVarName());
                } else {
                    src = newExp.IRme();
                }
                IR.getInstance().Add_IRcommand(new IRcommand_Array_Set(arrTemp,idxValTemp,IR.getInstance().currLine,src));
            }

            else if (var instanceof AST_VAR_SIMPLE) {
                
                AST_VAR_SIMPLE varSimple = (AST_VAR_SIMPLE) var;
                String varName = varSimple.varName;
                TEMP dst = varSimple.IRme();
                int offset = IR.getInstance().offsetTable.getOffsetOfVar(varName);        
                boolean isGlobal = IR.getInstance().offsetTable.getIsGlobal(varSimple.varName);

                if (newExp instanceof AST_NEW_TYPE_EXP_IN_BRACKS) {
                    AST_NEW_TYPE_EXP_IN_BRACKS newExpBracks = (AST_NEW_TYPE_EXP_IN_BRACKS) newExp;
                    IR.getInstance().Add_IRcommand(new IRcommand_New_Array(dst,newExpBracks.arraySize.IRme(),IR.getInstance().currLine,isGlobal));
                }

                else if (newExp instanceof AST_NEW_TYPE) {
                    AST_NEW_TYPE newExpType = (AST_NEW_TYPE) newExp;
                    newExpType.IRme(varName);
                }

                else {
                    System.out.println("Error: trying to assign a new expression which isnt class or array");
                    System.exit(0);
                }
                
                if (IR.getInstance().currentClassName != null && IR.getInstance().currentFuncName != null) {
                    IR.getInstance().Add_IRcommand(new IRcommand_Store(varName, dst, IR.getInstance().currLine, offset, false, isGlobal,true));
                } else {
                    IR.getInstance().Add_IRcommand(new IRcommand_Store(varName, dst, IR.getInstance().currLine, offset, false, isGlobal,false));
                }
            
            } else {

                src = newExp.IRme();
            }
        }
        
        return src;
    }
}
