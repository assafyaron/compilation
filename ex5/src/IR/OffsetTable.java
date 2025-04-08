package IR;
import java.util.*;

public class OffsetTable {

    public int scopeLevel;
    public Stack<OffsetTableFrame> offsetTable;

    public OffsetTable() {
        this.scopeLevel = 0;
        this.offsetTable = new Stack<OffsetTableFrame>();
        this.offsetTable.push(new OffsetTableFrame(scopeLevel));
    }

    public void addScope() {
        offsetTable.push(new OffsetTableFrame(scopeLevel+1));
        scopeLevel++;
    }

    public void popScope() {
        offsetTable.pop();
        scopeLevel--;
    }

    public OffsetTableFrame getTopFrame() {
        return offsetTable.peek();
    }

    public int getOffsetOfVar(String varName) {
        for (int i = offsetTable.size() - 1; i >= 0; i--) {
            OffsetTableFrame currTop = offsetTable.get(i);
            for (OffsetTableEntry entry : currTop.frame) {
                if (entry.varName.equals(varName)) {
                    return entry.offset;}
            }
        }
        return -1;
    }

    public OffsetTableEntry getEntry(String varName) {
        for (int i = offsetTable.size() - 1; i >= 0; i--) {
            OffsetTableFrame currTop = offsetTable.get(i);
            for (OffsetTableEntry entry : currTop.frame) {
                if (entry.varName.equals(varName)) {
                    return entry;}
            }
        }
        return null;
    }

    public boolean getIsArg(String varName) {
        for (int i = offsetTable.size() - 1; i >= 0; i--) {
            OffsetTableFrame currTop = offsetTable.get(i);
            for (OffsetTableEntry entry : currTop.frame) {
                if (entry.varName.equals(varName)) {
                    return entry.isArg;}
            }
        }
        return false;
    }

    public String getClassName(String varName) {
        System.out.println(varName);
        for (int i = offsetTable.size() - 1; i >= 0; i--) {
            OffsetTableFrame currTop = offsetTable.get(i);
            for (String var : currTop.varToClassMap.keySet()) {
                if (var.equals(varName)) {
                    return currTop.varToClassMap.get(var);}
            }
        }
        return null;
    }

    public boolean getIsGlobal(String varName) {
        for (int i = offsetTable.size() - 1; i >= 0; i--) {
            OffsetTableFrame currTop = offsetTable.get(i);
            for (OffsetTableEntry entry : currTop.frame) {
                if (entry.varName.equals(varName)) {
                    if (currTop.frameScopeLevel == 0) {
                        return true;
                    } else {
                        return false;
                    }
                }
            }
        }
        return false;
    }

    public void printOffsetTable() {
        System.out.println("----Printing offset table----");
        for (OffsetTableFrame currTop : offsetTable) {
            System.out.println("Scope level: " + currTop.frameScopeLevel);
            for (OffsetTableEntry entry : currTop.frame) {
                System.out.println("Var: " + entry.varName + " Offset: " + entry.offset + " isArg: " + entry.isArg);
            }
        }
        System.out.println("-----------------------");
    }

    public void setClassName(String varName, String className) {
        for (int i = offsetTable.size() - 1; i >= 0; i--) {
            OffsetTableFrame currTop = offsetTable.get(i);
            if (currTop.frameScopeLevel == scopeLevel) {
                currTop.varToClassMap.put(varName, className);
                return;
            }
        }
    }

}