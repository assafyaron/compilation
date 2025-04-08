package IR;
import java.util.*;
import TYPES.*;
import TEMP.*;

public class Field {

    public String name;
    public TYPE type;
    public boolean isString;
    public boolean isInt;
    public int intValue;
    public String stringValue;

    public Field(String name, boolean isString, boolean isInt, int intValue, String stringValue, TYPE type) {
        this.name = name;
        this.type = type;
        this.isString = isString;
        this.isInt = isInt;
        this.intValue = intValue;
        this.stringValue = stringValue;
    }
}
