package IR;
import java.util.*;

public class ControlFlowGraph {

    public IRcommand head;
    public IRcommand tail;
    int maxLine;

    public ControlFlowGraph() {
        this.head = null;
        this.tail = null;
        this.maxLine = 0;
    }

    public void addControlNode(IRcommand cmd) {
        if (this.head == null) {
            this.head = cmd;
            this.tail = cmd;
        } else {
            this.tail.next.add(cmd);
            cmd.prev.add(this.tail);
            this.tail = cmd;
        }
        if (cmd instanceof IRcommand_MIPSrec){}
        else {
            this.maxLine++;
            cmd.updateKillGen(this.maxLine);
        }
    }
    
    public void update_CFG(IRcommand src, IRcommand trgt) {
		src.next.add(trgt);
        trgt.prev.add(src);
	}

    public void performLivenessAnalysis() {

        Set<IRcommand> worklist = new HashSet<>();
        IRcommand curr = this.tail;

        while (curr != null) {
            worklist.add(curr);
            curr = curr.prev.isEmpty() ? null : curr.prev.get(0);
        }

        while (!worklist.isEmpty()) {

            IRcommand cmd = worklist.iterator().next();
            worklist.remove(cmd);

            Set<String> new_in = new HashSet<>();
            for (IRcommand succ : cmd.next) {
                new_in.addAll(succ.out);
            }

            Set<String> new_out = new HashSet<>(new_in);
            new_out.removeAll(cmd.kill);
            new_out.addAll(cmd.gen);

            if (!new_out.equals(cmd.out) || !new_in.equals(cmd.in)) {
                cmd.out = new_out;
                cmd.in = new_in;

                // Add predecessors back to the worklist if changes occurred
                for (IRcommand pred : cmd.prev) {
                    worklist.add(pred); // No need to check, Set ensures uniqueness
                }
            }
        }

        /* ============== DEBUG PRINTS IN AND OUTS ============== */
        // curr = this.tail;
        // while (curr != null) {
        //     System.out.println("--------------------------");
        //     curr.printIR();
        //     System.out.println("IN: " + curr.in);
        //     System.out.println("OUT: " + curr.out);
        //     curr = curr.prev.isEmpty() ? null : curr.prev.get(0);
        // }
        /* ============= END PRINTS DEBUG IN AND OUT ============= */
    }


    public Set<String> performUseBeforeDefAnalysis() {
        List<IRcommand> worklist = new ArrayList<>();
        Set<String> errors = new HashSet<>();
        IRcommand curr = this.head;
        Set<String> vars = getVaris();
        boolean firstFlag = true;

        while (curr != null) {
            worklist.add(curr);
            curr = curr.next.isEmpty() ? null : curr.next.get(0);
        }

        while (!worklist.isEmpty()) {
            IRcommand cmd = worklist.get(0);
            worklist.remove(0);
            
            // compute new in set
            Set<String> new_in = new HashSet<>();
            if (isScopeEnder(cmd)) {
                IRcommand scopeStarter = getScopeStarter(cmd,false);
                if (scopeStarter != null) {
                    if (!scopeStarter.prev.isEmpty()) {
                        new_in.addAll(scopeStarter.prev.get(0).out);
                    }
                }
            } else {
                for (IRcommand currCmd : cmd.prev){
                new_in.addAll(currCmd.out);
                }
            }
            if (firstFlag) {
                new_in.addAll(vars);
                firstFlag = false;
            }

            // compute new out set
            Set<String> new_out = new HashSet<>(new_in);
            new_out.removeAll(cmd.kill);
            new_out.addAll(cmd.gen);

            if (cmd instanceof IRcommand_Load) {
                IRcommand_Load loadCmd = (IRcommand_Load) cmd;
                if (new_in.contains(loadCmd.var_name+";?")) {
                    new_out.add(loadCmd.dst.toString()+";?");
                    errors.add(loadCmd.var_name);
                }
            } 
            
            if (cmd instanceof IRcommand_Store) {
                IRcommand_Store storeCmd = (IRcommand_Store) cmd;
                if (new_in.contains(storeCmd.src.toString()+";?")) {
                    new_out.add(storeCmd.var_name+";?");
                }
            }

            if (cmd instanceof IRcommand_Allocate) {
                IRcommand_Allocate allocCmd = (IRcommand_Allocate) cmd;
                new_out.add(allocCmd.var_name+";?");
            }

            if (cmd.isBinop) {
                String addToOut = getBinopDstLabeled(cmd, new_in);
                if (addToOut != null) {
                    new_out.add(addToOut);
                }
            }

            // check if in/out sets changed
            if (!new_in.equals(cmd.in) || !new_out.equals(cmd.out)) {
                cmd.in = new_in;
                cmd.out = new_out;

                // add all successors to worklist
                for (IRcommand next : cmd.next) {
                    worklist.add(next);
                }
            }
        }

        /* ============== DEBUG PRINTS IN AND OUTS ============== */
        // curr = this.head;
        // while (curr != null) {
        //     System.out.println("--------------------------");
        //     curr.printIR();
        //     System.out.println("IN: "+curr.in);
        //     System.out.println("OUT: "+curr.out);
        //     curr = curr.next.isEmpty() ? null : curr.next.get(0);}
        /* ============= END PRINTS DEBUG IN AND OUT ============= */

        return errors;
    }

    public List<IRcommand_New_Class> getClasses() {
        List<IRcommand_New_Class> classes = new ArrayList<>();
        IRcommand curr = this.head;
        while (curr != null) {
            if (curr instanceof IRcommand_New_Class) {
                classes.add((IRcommand_New_Class) curr);
            }
            // Move to the next node
            curr = curr.next.isEmpty() ? null : curr.next.get(0);
        }
        return classes;
    }

    public IRcommand_New_Class getClassCmd(String className) {
        IRcommand curr = this.head;
        while (curr != null) {
            if (curr instanceof IRcommand_New_Class) {
                IRcommand_New_Class classCmd = (IRcommand_New_Class) curr;
                if (classCmd.class_name.equals(className)) {
                    return classCmd;
                }
            }
            // Move to the next node
            curr = curr.next.isEmpty() ? null : curr.next.get(0);
        }
        return null;
    }

    public List<String> buildClassMethodsList(IRcommand_New_Class cmd) {

        List<String> funcList = new ArrayList<>();
        IRcommand nxtCmd = cmd.next.get(0);
        IRcommand_Label endLblCmd = null;

        if (nxtCmd instanceof IRcommand_Label) {
            endLblCmd = (IRcommand_Label) nxtCmd;
        } else {
            System.out.println("Error: Expected IRcommand_Label after IRcommand_New_Class");
            System.exit(1);
        }
        IRcommand_Label startLblCmd = getScopeStarter(endLblCmd,true);
        IRcommand curr = startLblCmd;

        while (curr != null) {
            if (curr instanceof IRcommand_Label) {

                IRcommand_Label lblCmd = (IRcommand_Label) curr;
                if (lblCmd.label_name.equals(endLblCmd.label_name)) {
                    break;
                }
                else if (lblCmd.isFuncLbl && lblCmd.isScopeStart) {
                    funcList.add(lblCmd.label_name.split("Label_\\d+_")[1].split("\\(")[0]);
                }
            }

            // Move to the next node
            curr = curr.next.isEmpty() ? null : curr.next.get(0);
        }
        return funcList;
    }

    public Set<String> getVaris() {
        IRcommand curr = this.head;
        Set<String> vars = new HashSet<>();

        while (curr != null) {
            for (String vari : curr.gen) {
                String variable = vari.split(";")[0];
                vars.add(variable+";?");
            }

            if (curr instanceof IRcommand_Allocate) {
                IRcommand_Allocate allocateCmd = (IRcommand_Allocate) curr;
                vars.add(allocateCmd.var_name+";?");
            }
            // Move to the next node
            curr = curr.next.isEmpty() ? null : curr.next.get(0);
        }
        clearInOut();
        return vars;
    }

    public void clearInOut() {
        IRcommand curr = this.head;
        while (curr != null) {
            curr.in = new HashSet<String>();
            curr.out = new HashSet<String>();
            // Move to the next node
            curr = curr.next.isEmpty() ? null : curr.next.get(0);
        }
    }

    public String getBinopDstLabeled(IRcommand cmd, Set<String> new_in) {

        if (cmd instanceof IRcommand_Binop_Add_Integers) {
            IRcommand_Binop_Add_Integers castCmd = (IRcommand_Binop_Add_Integers) cmd;
            if (new_in.contains(castCmd.t1.toString()+";?") || (new_in.contains(castCmd.t2.toString()+";?"))) {
                return (castCmd.dst.toString()+";?");
            }
            return null;
        } else if (cmd instanceof IRcommand_Binop_Add_Strings) {
            IRcommand_Binop_Add_Strings castCmd = (IRcommand_Binop_Add_Strings) cmd;
            if (new_in.contains(castCmd.t1.toString()+";?") || (new_in.contains(castCmd.t2.toString()+";?"))) {
                return (castCmd.dst.toString()+";?");
            }
            return null;
        } else if (cmd instanceof IRcommand_Binop_Div_Integers) {
            IRcommand_Binop_Div_Integers castCmd = (IRcommand_Binop_Div_Integers) cmd;
            if (new_in.contains(castCmd.t1.toString()+";?") || (new_in.contains(castCmd.t2.toString()+";?"))) {
                return (castCmd.dst.toString()+";?");
            }
            return null;
        } else if (cmd instanceof IRcommand_Binop_EQ_Integers) {
            IRcommand_Binop_EQ_Integers castCmd = (IRcommand_Binop_EQ_Integers) cmd;
            if (new_in.contains(castCmd.t1.toString()+";?") || (new_in.contains(castCmd.t2.toString()+";?"))) {
                return (castCmd.dst.toString()+";?");
            }
            return null;
        } else if (cmd instanceof IRcommand_Binop_LT_Integers) {
            IRcommand_Binop_LT_Integers castCmd = (IRcommand_Binop_LT_Integers) cmd;
            if (new_in.contains(castCmd.t1.toString()+";?") || (new_in.contains(castCmd.t2.toString()+";?"))) {
                return (castCmd.dst.toString()+";?");
            }
            return null;
        }  else if (cmd instanceof IRcommand_Binop_Mul_Integers) {
            IRcommand_Binop_Mul_Integers castCmd = (IRcommand_Binop_Mul_Integers) cmd;
            if (new_in.contains(castCmd.t1.toString()+";?") || (new_in.contains(castCmd.t2.toString()+";?"))) {
                return (castCmd.dst.toString()+";?");
            }
            return null;
        } else if (cmd instanceof IRcommand_Binop_Sub_Integers) {
            IRcommand_Binop_Sub_Integers castCmd = (IRcommand_Binop_Sub_Integers) cmd;
            if (new_in.contains(castCmd.t1.toString()+";?") || (new_in.contains(castCmd.t2.toString()+";?"))) {
                return (castCmd.dst.toString()+";?");
            }
            return null;
        } else {
            return null;
        }

    }

    public void printControlGraph(boolean withNextCmds) {
        IRcommand curr = this.head;
        int j = 0;
		while (curr != null) {
            if (withNextCmds) {
                System.out.println("------------------------");
            }
            System.out.print(j+": ");
    		curr.printIR();
			// Ensure curr.next is not null and contains elements
			if (curr.next != null && !curr.next.isEmpty()) {
                if (withNextCmds) {
                    System.out.println("Connected to -> : ");
                    for (int i=0;i<curr.next.size();i++) {
					    curr.next.get(i).printIR();
				    }
                }
                // Move to the next node
                curr = curr.next.isEmpty() ? null : curr.next.get(0);
                j++;
			} else {
				break; // Exit the loop if there are no more elements
			}
        }
        System.out.println("=======END OF PRINTING=======");
    }

    public boolean isScopeEnder(IRcommand cmd) {
        if (cmd instanceof IRcommand_Label) {
            IRcommand_Label lblCmd = (IRcommand_Label) cmd;
            if (!lblCmd.isScopeStart) {
                return true;
            }
        }
        return false;
    }

    public IRcommand_Label getScopeStarter(IRcommand cmd, boolean classFlag) {
        IRcommand_Label castCmd = (IRcommand_Label) cmd;
        IRcommand curr = castCmd;
        int labelNum = Integer.parseInt(castCmd.label_name.split("_")[1]);
        String labelStrPlusOne;
        if (classFlag) {
            labelNum = Integer.parseInt(castCmd.label_name.split("_")[1]) - 2;
        }
        labelStrPlusOne = ("Label_"+(labelNum+1)+"_"+castCmd.label_name.split("_\\d+_")[1]);

        while (curr != null) {
            if (curr instanceof IRcommand_Label) {
                IRcommand_Label currLbl = (IRcommand_Label) curr;
                if (labelStrPlusOne.split("_end")[0].equals(currLbl.label_name.split("_start")[0])) {
                    return currLbl;
                }
            }
            curr = curr.prev.isEmpty() ? null : curr.prev.get(0);
        }
        return null;
    }

    public Set<String> getAllConstStrings() {
        IRcommand curr = this.head;
        Set<String> constStrings = new HashSet<>();
        while (curr != null) {

            if (curr instanceof IRcommandConstString) {
                IRcommandConstString constStrCmd = (IRcommandConstString) curr;
                constStrings.add(constStrCmd.str);
            }

            // Move to the next node
            curr = curr.next.isEmpty() ? null : curr.next.get(0);
        }

        return constStrings;
    }

    public Set<String> getVarnum(IRcommand_Label cmd) {
        IRcommand curr = cmd;
        Set<String> vars = new HashSet<>();
        String labelStrPlusOne = ("Label"+"_"+(Integer.parseInt(cmd.label_name.split("_")[1])+1));
        while (curr != null) {

            if (curr instanceof IRcommand_Label) {
                IRcommand_Label lblCmd = (IRcommand_Label) curr;
                String parts[] = lblCmd.label_name.split("_");
                String labelStrOrig = parts[0]+"_"+parts[1];
                if (labelStrOrig.equals(labelStrPlusOne)) {
                    for (String var : vars) {
                    }
                    return vars;}}

            for (String var : curr.varsInvolved()) {
                vars.add(var);
            }

            // Move to the next node
            curr = curr.next.isEmpty() ? null : curr.next.get(0);
        }

        return vars;
    }

    public int getNumOfArgs(String funcName) {
        IRcommand curr = this.head;
        System.out.println("FuncName: "+funcName);
        while (curr != null) {
            if (curr instanceof IRcommand_Label) {
                IRcommand_Label lblCmd = (IRcommand_Label) curr;
                System.out.println("LabelName: "+lblCmd.label_name);
                if (lblCmd.label_name.equals(funcName)) {
                    return lblCmd.argList.size();
                }
            }
            // Move to the next node
            curr = curr.next.isEmpty() ? null : curr.next.get(0);
        }
        return -1;
    }
}