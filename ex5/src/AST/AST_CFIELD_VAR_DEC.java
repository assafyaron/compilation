package AST;
import TYPES.*;
import TEMP.*;
import IR.*;

public class AST_CFIELD_VAR_DEC extends AST_CFIELD
{
	
	public AST_VAR_DEC varDec;
	
	/******************/
	/* CONSTRUCTOR(S) */
	/******************/
	public AST_CFIELD_VAR_DEC(AST_VAR_DEC varDec)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		System.out.format("====================== cField -> varDec\n");

		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.varDec = varDec;
	}


	 public void PrintMe()
    {
        /*********************************/
        /* AST NODE TYPE = CLASS FIELD VARIABLE DECLARATION */
        /*********************************/
        System.out.println("AST NODE CFIELD VAR DEC");

        /******************************************/
        /* RECURSIVELY PRINT var_dec ... */
        /******************************************/
        if (varDec != null) varDec.PrintMe();

        /***************************************/
        /* PRINT Node to AST GRAPHVIZ DOT file */
        /***************************************/
        AST_GRAPHVIZ.getInstance().logNode(
            SerialNumber,
            "CLASS FIELD\nVARIABLE DECLARATION");

        /****************************************/
        /* PRINT Edges to AST GRAPHVIZ DOT file */
        /****************************************/
        if (varDec != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, varDec.SerialNumber);
    }

    public TYPE SemantMe()
	{
        // semant the variable declaration
		if(varDec != null) 
		{
			return varDec.SemantMe();
		}

		return null;
	}

	public TEMP IRme() {

		if (varDec != null)
		{

			TEMP dst = varDec.IRme();

			if (varDec instanceof AST_VAR_DEC_ARGS) {
				AST_VAR_DEC_ARGS varDecArgs = (AST_VAR_DEC_ARGS) varDec;
				String name = varDecArgs.varName;
				TYPE type = varDecArgs.t.SemantMe();
				String className = IR.getInstance().currentClassName;
				boolean isString = (type instanceof TYPE_STRING);
				boolean isInt = (type instanceof TYPE_INT);
				String stringValue = null;
				int intValue = 0;
				if (varDecArgs.exp != null) {
					if (varDecArgs.exp instanceof AST_EXP_INT) {
						AST_EXP_INT expInt = (AST_EXP_INT) varDecArgs.exp;
						intValue = expInt.i;
					} else if (varDecArgs.exp instanceof AST_EXP_STRING) {
						AST_EXP_STRING expString = (AST_EXP_STRING) varDecArgs.exp;
						stringValue = expString.value;}
				}
				IR.getInstance().classFieldsMap.get(className).add(new Field(name,isString,isInt,intValue,stringValue,type));

			} else if (varDec instanceof AST_VAR_DEC_NO_ARGS) {
				AST_VAR_DEC_NO_ARGS varDecNoArgs = (AST_VAR_DEC_NO_ARGS) varDec;
				String name = varDecNoArgs.varName;
				TYPE type = varDecNoArgs.t.SemantMe();
				String className = IR.getInstance().currentClassName;
				boolean isString = (type instanceof TYPE_STRING);
				boolean isInt = (type instanceof TYPE_INT);
				IR.getInstance().classFieldsMap.get(className).add(new Field(name,isString,isInt,0,"",type));
			}

			return dst;
		}
		return null;
	}
}
