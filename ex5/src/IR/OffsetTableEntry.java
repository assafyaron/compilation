package IR;
import java.util.*;
import TEMP.*;

public class OffsetTableEntry {

    public int offset;
    public String varName;
    public boolean isArg = false;

    public OffsetTableEntry(String varName,int offset,boolean isArg) {
        this.offset = offset;
        this.varName = varName;
        this.isArg = isArg;
    }

}