package AST;
import TYPES.*;
import TEMP.*;
import IR.*;

public abstract class AST_VAR extends AST_Node
{
    public String myClassName; // for printing purposes

    public abstract String getVarName();

    public TYPE SemantMe() {
        return null;
    }

    public TEMP IRme()
    {
        return null;
    }
}
