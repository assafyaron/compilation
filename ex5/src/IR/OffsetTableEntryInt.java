package IR;
import java.util.*;
import TEMP.*;

public class OffsetTableEntryInt extends OffsetTableEntry {

    public int value;

    public OffsetTableEntryInt(String varName,int offset,int value,boolean isArg) {
        super(varName,offset,isArg);
        this.value = value;
    }

}