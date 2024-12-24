package AST;

public class AST_TYPE_ID extends AST_TYPE
{
    public String name;

    /******************/
    /* CONSTRUCTOR(S) */
    /******************/
    public AST_TYPE_ID(String name)
    {
        /******************************/
        /* SET A UNIQUE SERIAL NUMBER */
        /******************************/
        SerialNumber = AST_Node_Serial_Number.getFresh();

        /***************************************/
        /* PRINT CORRESPONDING DERIVATION RULE */
        /***************************************/
        System.out.format("====================== type -> ID( %s )\n", name);

        /*******************************/
        /* COPY INPUT DATA MEMBERS ... */
        /*******************************/
        this.name = name;
    }

    public void PrintMe()
    {
        /*********************************/
        /* AST NODE TYPE = TYPE ID */
        /*********************************/
        System.out.println("AST NODE TYPE ID");

        /******************************************/
        /* PRINT type name */
        /******************************************/
        System.out.format("TYPE NAME( %s )\n", name);

        /***************************************/
        /* PRINT Node to AST GRAPHVIZ DOT file */
        /***************************************/
        AST_GRAPHVIZ.getInstance().logNode(
            SerialNumber,
            String.format("TYPE\nID\n...->%s", name));
    }
}
