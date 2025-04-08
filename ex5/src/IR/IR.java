/***********/
/* PACKAGE */
/***********/
package IR;
import java.util.*;
import AST.*;
import TEMP.*;
import MIPS.*;

/*******************/
/* GENERAL IMPORTS */
/*******************/

/*******************/
/* PROJECT IMPORTS */
/*******************/

public class IR {

	private IRcommand head = null;
	private IRcommandList tail = null;
	public ControlFlowGraph controlGraph = new ControlFlowGraph();
	public InterferenceGraph interferenceGraph = new InterferenceGraph();
	public OffsetTable offsetTable = new OffsetTable();
	public Map<String,List<Field>> classFieldsMap = new HashMap<>();
	public Map<String,String> extendMap = new HashMap<>();
	public Set<String> weirdArrayNames = new HashSet<>();
	public String currentClassName = null;
	public String currentFuncName = null;
	public int currLine = 0;
	public int scopeLev = 0;

	/******************/
	/* Add IR command */
	/******************/
	public void Add_IRcommand(IRcommand cmd) {
		if ((head == null) && (tail == null))
		{
			this.head = cmd;
		}
		else if ((head != null) && (tail == null))
		{
			this.tail = new IRcommandList(cmd,null);
		}
		else
		{
			IRcommandList it = tail;
			while ((it != null) && (it.tail != null))
			{
				it = it.tail;
			}
			it.tail = new IRcommandList(cmd,null);
		}
		this.controlGraph.addControlNode(cmd);
		this.currLine++;

	}
	
	/**************************************/
	/* USUAL SINGLETON IMPLEMENTATION ... */
	/**************************************/
	private static IR instance = null;

	/*****************************/
	/* PREVENT INSTANTIATION ... */
	/*****************************/
	protected IR() {}

	/******************************/
	/* GET SINGLETON INSTANCE ... */
	/******************************/
	public static IR getInstance()
	{
		if (instance == null)
		{
			/*******************************/
			/* [0] The instance itself ... */
			/*******************************/
			instance = new IR();
		}
		return instance;
	}

	public void MIPSme()
	{
		if (head != null) head.MIPSme();
		if (tail != null) tail.MIPSme();
	}

	public void setColors(Map<String, Integer> colors) {
		IRcommand curr = this.head;
        while (curr != null) {
			if (curr instanceof IRcommand_VirtualCall){
				IRcommand_VirtualCall call = (IRcommand_VirtualCall) curr;
				if (call.args != null) {
					for (TEMP arg :call.args) {
						System.out.println("arg: " + arg.toString());
					}
				}
			}


			curr.replaceTempsWithColors(colors);
            curr = curr.next.isEmpty() ? null : curr.next.get(0);
        }
    }

	public String getVarName(AST_VAR var) {
        if (var instanceof AST_VAR_SIMPLE) {
            return ((AST_VAR_SIMPLE) var).varName;
        } else if (var instanceof AST_VAR_FIELD) {
            return ((AST_VAR_FIELD) var).getVarName();
		} else if (var instanceof AST_VAR_SUBSCRIPT) {
			return ((AST_VAR_SUBSCRIPT) var).getVarName();
        } else {
            System.out.println("Error: Unsupported variable type for getVarName()");
			System.exit(1);
            return null;
    	}
	}
}
