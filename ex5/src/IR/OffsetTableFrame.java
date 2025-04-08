package IR;
import java.util.*;
import TEMP.*;

public class OffsetTableFrame {

    public Stack<OffsetTableEntry> frame;
    public int offset = 4;
    public int frameScopeLevel;
    public Map<String,String> varToClassMap;

    public OffsetTableFrame(int frameScopeLevel) {
        this.frame = new Stack<OffsetTableEntry>();
        this.frameScopeLevel = frameScopeLevel;
        this.varToClassMap = new HashMap<>();
    }

    // push undefined value with known offset
    public void pushEntry(OffsetTableEntry entry) {
        frame.push(entry);
    }

    // push int value with known offset
    public void pushEntry(OffsetTableEntryInt entry) {
        frame.push(entry);
    }

    // push string value with known offset
    public void pushEntry(OffsetTableEntryString entry) {
        frame.push(entry);
    }

    // push undefined value with unknown offset
    public int pushEntry(String varname, boolean isArg) {
        boolean isInScope = checkIfVarInScope(varname);
        if (!isInScope) {
            frame.push(new OffsetTableEntry(varname, -offset, isArg));
            offset += 4;
        }
        return -(offset-4);
    }

    // push string value with unknown offset
    public int pushEntry(String name, String str, boolean isArg) {
        boolean isInScope = checkIfVarInScope(name);
        if (!isInScope) {
            frame.push(new OffsetTableEntryString(name, -offset, str, isArg));
            offset += 4;
        }
        return -(offset-4);
    }

    // push int value with unknown offset
    public int pushEntry(String name, int value, boolean isArg) {
        boolean isInScope = checkIfVarInScope(name);
        if (!isInScope) {
            frame.push(new OffsetTableEntryInt(name, -offset, value, isArg));
            offset += 4;
        }
        return -(offset-4);
    }

    public void printVarToClassMap() {
        System.out.println("----------------------------------------");
        System.out.println("Var to Class Map:");
        for (Map.Entry<String, String> entry : varToClassMap.entrySet()) {
            System.out.println("Var: " + entry.getKey() + ", Class: " + entry.getValue());
        }
        System.out.println("----------------------------------------");
    }

    public boolean checkIfVarInScope(String varName) {
        for (OffsetTableEntry entry : frame) {
            if (entry.varName.equals(varName)) {
                return true;
            }
        }
        return false;
    }
}