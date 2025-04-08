package IR;
import java.util.*;
import TEMP.*;

public class OffsetTableEntryString extends OffsetTableEntry {

    public String str;

    public OffsetTableEntryString(String varName,int offset,String str,boolean isArg) {
        super(varName,offset,isArg);
        this.str = str;
    }

}