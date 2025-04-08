package AST;
import TYPES.*;
import TEMP.*;
import IR.*;

public abstract class AST_EXP extends AST_Node
{
	public String typeName;

	public TYPE SemantMe() {
		return null;
	}
	
	public TEMP IRme()
	{
		return null;
	}

}
