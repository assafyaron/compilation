/***********/
/* PACKAGE */
/***********/
package MIPS;

/*******************/
/* GENERAL IMPORTS */
/*******************/
import java.io.PrintWriter;

/*******************/
/* PROJECT IMPORTS */
/*******************/
import TEMP.*;
import IR.*;
import java.util.*;

public class MIPSGenerator
{
	private int WORD_SIZE=4;
	/***********************/
	/* The file writer ... */
	/***********************/
	private PrintWriter fileWriter;
	public String currFuncName = null;
	public boolean inClass = false;
	public String currClassName = null;
	public Map<String,Map<String,Integer>> classMethodsMap = new HashMap<>();
	public Map <String,List<Field>> fieldListMap = new HashMap<>(); 

	/***********************/
	/* The file writer ... */
	/***********************/
	public void finalizeFile()
	{
		fileWriter.close();
	}
	
	public void print_int(TEMP t)
	{
		int idx=t.getSerialNumber();
		fileWriter.format("\tmove $a0,$t%d\n",idx);
		fileWriter.format("\tli $v0,1\n");
		fileWriter.format("\tsyscall\n");
		fileWriter.format("\tli $a0,32\n");
		fileWriter.format("\tli $v0,11\n");
		fileWriter.format("\tsyscall\n");
	}

	public void print_string(TEMP t)
	{
		int idx=t.getSerialNumber();
		fileWriter.format("\tmove $a0,$t%d\n",idx);
		fileWriter.format("\tli $v0,4\n");
		fileWriter.format("\tsyscall\n");
		fileWriter.format("\tli $a0,32\n");
		fileWriter.format("\tli $v0,11\n");
		fileWriter.format("\tsyscall\n");
	}

	public void allocGlobals()
	{
		fileWriter.format("\t#Globals\n");
		fileWriter.format("\tmax: .word 32767\n");
		fileWriter.format("\tmin: .word -32768\n");
		fileWriter.format("\n");
		Set<String> allStrings = IR.getInstance().controlGraph.getAllConstStrings();
		for (String str : allStrings) {
			fileWriter.format("\t%s_str: .asciiz %s\n", str.replace("\"", ""), str);
		}
		if (allStrings .size() > 0) {
			fileWriter.format("\n");
		}

		OffsetTableFrame top = IR.getInstance().offsetTable.getTopFrame();
		for (OffsetTableEntry entry : top.frame) {
			if (entry instanceof OffsetTableEntryInt)
				fileWriter.format("\tg_%s: .word %d\n",entry.varName,((OffsetTableEntryInt)entry).value);
			else if (entry instanceof OffsetTableEntryString) {
				fileWriter.format("\tg_%s: .word %s\n",entry.varName,((OffsetTableEntryString)entry).str.replace("\"", "")+"_str");
			} else {
				fileWriter.format("\tg_%s: .word 0\n",entry.varName);
			}
			fileWriter.format("\n");
		}

		List<IRcommand_New_Class> classes = IR.getInstance().controlGraph.getClasses();
		for (IRcommand_New_Class c : classes) {
			String className = c.class_name;
			computeFieldListMap(className);
			fileWriter.format("vt_%s:\n",className);
			List<String> methods = IR.getInstance().controlGraph.buildClassMethodsList(c);
			List<String> methods_temp = new ArrayList<>(methods);
			while (c.superClassName != null) {
				IRcommand_New_Class super_class_cmd = IR.getInstance().controlGraph.getClassCmd(c.superClassName);
				List<String> super_methods = IR.getInstance().controlGraph.buildClassMethodsList(super_class_cmd);
				for (int i = super_methods.size() - 1; i >= 0; i--) {
					String method = super_methods.get(i);
					if (!methods_temp.contains(method)) {
						methods_temp.add(0,method);
						methods.add(0,"class"+c.superClassName+"_"+method);
					}
				}
				c = super_class_cmd;
			}

			addVirtualTableMethods(methods,className);
			fileWriter.format("\n");
		}
		fileWriter.format(".globl main\n");
		fileWriter.format(".text\n");
		fileWriter.print("\nmain:\n");
		fileWriter.print("\tjal user_main\n");
		fileWriter.print("\tli $v0,10\n");
		fileWriter.print("\tsyscall\n");
		fileWriter.format("\n");
		fileWriter.format("###################\n");
		fileWriter.format("#### INT FLOW #####\n");
		fileWriter.format("###################\n\n");
		checkIntOverflow();
		fileWriter.format("###################\n");
		fileWriter.format("## DIVISION CHECK #\n");
		fileWriter.format("###################\n\n");
		checkDivision();
		fileWriter.format("###################\n");
		fileWriter.format("# STRING ADDITION #\n");
		fileWriter.format("###################\n\n");
		stringMemAllocation();
		concatStrings();
		fileWriter.format("###################\n");
		fileWriter.format("# STRING EQUALITY #\n");
		fileWriter.format("###################\n\n");
		stringEqCheck();
		fileWriter.format("###################\n");
		fileWriter.format("# ILLEGAL ACCESS  #\n");
		fileWriter.format("###################\n\n");
		exitAccesViolation();
		exitInvalidPtrDref();
	}

	public void load(TEMP dst,int offset)
	{
		int idxdst=dst.getSerialNumber();
		fileWriter.format("\tlw $t%d,%d($fp)\n",idxdst,offset);
	}

	public void loadField(TEMP dst,int offset)
	{
		int idxdst=dst.getSerialNumber();
		fileWriter.format("\tlw $t%d,%d($s0)\n",idxdst,offset);
	}

	public void storeField(TEMP src,int offset)
	{
		int idxsrc=src.getSerialNumber();
		fileWriter.format("\tsw $t%d,%d($s0)\n",idxsrc,offset);
	}
	
	public void loadGlobal(TEMP dst,String label)
	{
		int idxdst=dst.getSerialNumber();
		fileWriter.format("\tlw $t%d,%s\n",idxdst,label);
	}

	public void loadConstStr(TEMP dst,String label)
	{
		int idxdst=dst.getSerialNumber();
		fileWriter.format("\tla $t%d,%s\n",idxdst,label);
	}

	public void store(TEMP src,int offset)
	{
		int idxsrc=src.getSerialNumber();
		fileWriter.format("\tsw $t%d,%d($fp)\n",idxsrc,offset);		
	}

	public void storeGlobal(TEMP src,String label)
	{
		int idxsrc=src.getSerialNumber();
		fileWriter.format("\tsw $t%d,%s\n",idxsrc,label);		
	}

	public void storeFuncArgs(TEMP src,int offset)
	{
		int idxsrc=src.getSerialNumber();
		fileWriter.format("\tsw $t%d,%d($sp)\n",idxsrc,offset);		
	}

	public void li(TEMP t,int value)
	{
		int idx=t.getSerialNumber();
		fileWriter.format("\tli $t%d,%d\n",idx,value);
	}

	public void slt(TEMP dst,TEMP oprnd1,TEMP oprnd2)
	{
		int i1 =oprnd1.getSerialNumber();
		int i2 =oprnd2.getSerialNumber();
		int dstidx=dst.getSerialNumber();
		fileWriter.format("\tslt $t%d,$t%d,$t%d\n",dstidx,i1,i2);
	}

	public void seq(TEMP dst,TEMP oprnd1,TEMP oprnd2)
	{
		int i1 =oprnd1.getSerialNumber();
		int i2 =oprnd2.getSerialNumber();
		int dstidx=dst.getSerialNumber();
		fileWriter.format("\txor $t%d,$t%d,$t%d\n",dstidx,i1,i2);
		fileWriter.format("\tsltiu $t%d,$t%d,1\n",dstidx,dstidx);
	}

	public void addVirtualTableMethods(List<String> methods,String className)
	{
		classMethodsMap.put(className,new HashMap<>());
		for (int i=0;i<methods.size();i++) {
			if (methods.get(i).length() > 5) {
				if (methods.get(i).substring(0,5).equals("class")) {
					fileWriter.format("\t.word %s\n",methods.get(i));
					classMethodsMap.get(className).put(methods.get(i),i*4);
				} else {
					fileWriter.format("\t.word %s\n","class"+className+"_"+methods.get(i));
					classMethodsMap.get(className).put("class"+className+"_"+methods.get(i),i*4);
				}
			} else {
				fileWriter.format("\t.word %s\n","class"+className+"_"+methods.get(i));
				classMethodsMap.get(className).put("class"+className+"_"+methods.get(i),i*4);
			}
		}
	}

	public void printVirtualTable(String className) {
		System.out.println("-------------------------");
		System.out.println("Virtual Table for class: " + className);
		Map<String,Integer> methods = classMethodsMap.get(className);
		if (methods != null) {
			for (Map.Entry<String, Integer> entry : methods.entrySet()) {
				System.out.println(entry.getKey() + " : " + entry.getValue());
			}
		}
		System.out.println("-------------------------");
	}

	public void nestedVirtualCall(String methodName, List<TEMP> args,TEMP dst)
	{
		System.out.println("Nested virtual call to method: " + methodName);		
		methodName = currClassName+"_"+methodName;
		fileWriter.format("\tsubu $sp,$sp,4\n");
		fileWriter.format("\tsw $s0,0($sp)\n");
		fileWriter.format("\tsubu $sp,$sp,4\n");
		fileWriter.format("\tsw $s1,0($sp)\n");
		Map<String,Integer> methods = classMethodsMap.get(currClassName.substring(5));
		int idxdst=dst.getSerialNumber();
		if (args != null) {
			for (int i=0;i<args.size();i++) {
				int idxarg=args.get(args.size()-i-1).getSerialNumber();
				fileWriter.format("\tsubu $sp,$sp,4\n");
				fileWriter.format("\tsw $t%d,0($sp)\n",idxarg);
			}
		}
		fileWriter.format("\tsubu $sp,$sp,4\n");
		fileWriter.format("\tsw $s0,0($sp)\n");
		fileWriter.format("\tlw $s1,0($s0)\n");
		fileWriter.format("\tlw $s1,%d($s1)\n",methods.get(methodName));
		fileWriter.format("\tjalr $s1\n");
		if (args == null) {
			fileWriter.format("\taddu $sp,$sp,%d\n",(4));
		} else {
			fileWriter.format("\taddu $sp,$sp,%d\n",(4*(args.size()+1)));
		}
		fileWriter.format("\tmove $t%d,$v0\n",idxdst);
		fileWriter.format("\tlw $s0,0($sp)\n");
		fileWriter.format("\taddu $sp,$sp,4\n");
		fileWriter.format("\tlw $s1,0($sp)\n");
		fileWriter.format("\taddu $sp,$sp,4\n");
	}

	public void virtualCall(TEMP dst,TEMP object,String methodName,Map<String,Integer> methods,List<TEMP> args)
	{
		System.out.println("Virtual call to method: " + methodName);		
		int idxobj=object.getSerialNumber();
		int idxdst=dst.getSerialNumber();
		if (args != null) {
			for (int i=0;i<args.size();i++) {
				int idxarg=args.get(args.size()-i-1).getSerialNumber();
				fileWriter.format("\tsubu $sp,$sp,4\n");
				fileWriter.format("\tsw $t%d,0($sp)\n",idxarg);
			}
		}
		fileWriter.format("\tlw $s0,0($t%d)\n",idxobj);
		fileWriter.format("\tlw $s1,%d($s0)\n",methods.get(methodName));
		fileWriter.format("\tsubu $sp,$sp,4\n");
		fileWriter.format("\tsw $t%d,0($sp)\n",idxobj);
		fileWriter.format("\tlw $s0,0($t%d)\n",idxobj);
		fileWriter.format("\tlw $s1,%d($s0)\n",methods.get(methodName));
		fileWriter.format("\tjalr $s1\n");
		if (args == null) {
			fileWriter.format("\taddu $sp,$sp,%d\n",(4));
		} else {
			fileWriter.format("\taddu $sp,$sp,%d\n",(4*(args.size()+1)));
		}
		fileWriter.format("\tmove $t%d,$v0\n",idxdst);
	}

	public void newClassObject(TEMP dst,String className,List<Field> fieldList)
	{
		fileWriter.format("\tsubu $sp,$sp,4\n");
		fileWriter.format("\tsw $s0,0($sp)\n");
		int idxdst=dst.getSerialNumber();
		fileWriter.format("\tli $v0,9\n");
		if (fieldList != null) {
			fileWriter.format("\tli $a0,%d\n",fieldList.size()*WORD_SIZE+4);
		} else {
			fileWriter.format("\tli $a0,%d\n",4);
		}
		fileWriter.format("\tsyscall\n");
		fileWriter.format("\tmove $t%d,$v0\n",idxdst);
		fileWriter.format("\tla $s0,vt_%s\n",className);
		fileWriter.format("\tsw $s0,0($t%d)\n",idxdst);
		if (fieldList != null) {
			int i = 4;
			for (Field field : fieldList) {
				if (field.isString) {
					if (field.stringValue == "") {
						fileWriter.format("\tli $s0,0\n");
					} else {
						String str = field.stringValue.replace("\"", "")+"_str";
						fileWriter.format("\tla $s0,%s\n",str);
					}
					fileWriter.format("\tsw $s0,%d($t%d)\n",i,idxdst);
				} else if (field.isInt) {
					int val = field.intValue;
					fileWriter.format("\tli $s0,%d\n",val);
					fileWriter.format("\tsw $s0,%d($t%d)\n",i,idxdst);
				} else {
					fileWriter.format("\tli $s0,0\n");
					fileWriter.format("\tsw $s0,%d($t%d)\n",i,idxdst);
				}
				i += WORD_SIZE;
			}
		}
		fileWriter.format("\tlw $s0,0($sp)\n");
		fileWriter.format("\taddu $sp,$sp,4\n");
	}

	public void fieldAccess(TEMP dst,TEMP object,int offset)
	{
		int idxdst=dst.getSerialNumber();
		int idxobj=object.getSerialNumber();
		fileWriter.format("\tbeqz $t%d,exit_invalid_ptr_dref\n",idxobj);
		fileWriter.format("\tlw $t%d,%d($t%d)\n",idxdst,offset,idxobj);
	}

	public void fieldSet(TEMP object,TEMP value,int offset)
	{
		int idxobj=object.getSerialNumber();
		int idxval=value.getSerialNumber();
		fileWriter.format("\tbeqz $t%d,exit_invalid_ptr_dref\n",idxobj);
		fileWriter.format("\tsw $t%d,%d($t%d)\n",idxval,offset,idxobj);
	}

	public void printClassMethodsMap() {
		System.out.println("Class Methods Map:");
		System.out.println("-------------------------");
		for (Map.Entry<String, Map<String, Integer>> entry : classMethodsMap.entrySet()) {
			String className = entry.getKey();
			Map<String, Integer> methods = entry.getValue();
			System.out.println("Class: " + className);
			for (Map.Entry<String, Integer> methodEntry : methods.entrySet()) {
				System.out.println("Method: " + methodEntry.getKey() + ", Offset: " + methodEntry.getValue());
			}
		}
		System.out.println("-------------------------");
	}

	public void add(TEMP dst,TEMP oprnd1,TEMP oprnd2)
	{
		int i1 =oprnd1.getSerialNumber();
		int i2 =oprnd2.getSerialNumber();
		int dstidx=dst.getSerialNumber();
		fileWriter.format("\tadd $t%d,$t%d,$t%d\n",dstidx,i1,i2);
		fileWriter.format("\tmove $a0,$t%d\n",dstidx);
		fileWriter.format("\tjal int_flow_check\n");
		fileWriter.format("\tmove $t%d,$a0\n",dstidx);
	}

	public void addStrings(TEMP dst,TEMP oprnd1,TEMP oprnd2)
	{
		int i1 =oprnd1.getSerialNumber();
		int i2 =oprnd2.getSerialNumber();
		int dstidx=dst.getSerialNumber();
		fileWriter.format("\tmove $a1,$t%d\n",i1);
		fileWriter.format("\tmove $a2,$t%d\n",i2);
		fileWriter.format("\tjal string_mem_alloc\n");
		fileWriter.format("\tmove $t%d,$a0\n",dstidx);
		fileWriter.format("\tjal concat_strs\n",dstidx);
	}

	public void eqStrings(TEMP dst,TEMP oprnd1,TEMP oprnd2)
	{
		int i1 =oprnd1.getSerialNumber();
		int i2 =oprnd2.getSerialNumber();
		int dstidx=dst.getSerialNumber();
		fileWriter.format("\tmove $a0,$t%d\n",i1);
		fileWriter.format("\tmove $a1,$t%d\n",i2);
		fileWriter.format("\tjal string_eq_check\n");
		fileWriter.format("\tmove $t%d,$v0\n",dstidx);
	}

	public void sub(TEMP dst,TEMP oprnd1,TEMP oprnd2)
	{
		int i1 =oprnd1.getSerialNumber();
		int i2 =oprnd2.getSerialNumber();
		int dstidx=dst.getSerialNumber();
		fileWriter.format("\tsub $t%d,$t%d,$t%d\n",dstidx,i1,i2);
		fileWriter.format("\tmove $a0,$t%d\n",dstidx);
		fileWriter.format("\tjal int_flow_check\n");
		fileWriter.format("\tmove $t%d,$a0\n",dstidx);
	}

	public void mul(TEMP dst,TEMP oprnd1,TEMP oprnd2)
	{
		int i1 =oprnd1.getSerialNumber();
		int i2 =oprnd2.getSerialNumber();
		int dstidx=dst.getSerialNumber();
		fileWriter.format("\tmul $t%d,$t%d,$t%d\n",dstidx,i1,i2);
		fileWriter.format("\tmove $a0,$t%d\n",dstidx);
		fileWriter.format("\tjal int_flow_check\n");
		fileWriter.format("\tmove $t%d,$a0\n",dstidx);
	}

	public void div(TEMP dst,TEMP oprnd1,TEMP oprnd2)
	{
		int i1 =oprnd1.getSerialNumber();
		int i2 =oprnd2.getSerialNumber();
		int dstidx=dst.getSerialNumber();
		fileWriter.format("\tmove $a0,$t%d\n",i1);
		fileWriter.format("\tmove $a1,$t%d\n",i2);
		fileWriter.format("\tjal check_division\n");
		fileWriter.format("\tmove $t%d,$a0\n",dstidx);
		fileWriter.format("\tmove $a0,$t%d\n",dstidx);
		fileWriter.format("\tjal int_flow_check\n");
		fileWriter.format("\tmove $t%d,$a0\n",dstidx);
	}

	public void label(String inlabel)
	{
		if (inlabel.equals("main"))
		{
			fileWriter.format("%s:\n","user_main");
		}
		else
		{
			fileWriter.format("%s:\n",inlabel);
		}
	}	

	public void jump(String inlabel)
	{
		fileWriter.format("\tj %s\n",inlabel);
	}

	public void jal(String funcName)
	{
		fileWriter.format("\tjal %s\n",funcName.replace("\"", ""));
	}

	public void blt(TEMP oprnd1,TEMP oprnd2,String label)
	{
		int i1 =oprnd1.getSerialNumber();
		int i2 =oprnd2.getSerialNumber();
		
		fileWriter.format("\tblt $t%d,$t%d,%s\n",i1,i2,label);				
	}

	public void bge(TEMP oprnd1,TEMP oprnd2,String label)
	{
		int i1 =oprnd1.getSerialNumber();
		int i2 =oprnd2.getSerialNumber();
		fileWriter.format("\tbge $t%d,$t%d,%s\n",i1,i2,label);				
	}

	public void bne(TEMP oprnd1,TEMP oprnd2,String label)
	{
		int i1 =oprnd1.getSerialNumber();
		int i2 =oprnd2.getSerialNumber();
		fileWriter.format("\tbne $t%d,$t%d,%s\n",i1,i2,label);				
	}

	public void beq(TEMP oprnd1,TEMP oprnd2,String label)
	{
		int i1 =oprnd1.getSerialNumber();
		int i2 =oprnd2.getSerialNumber();
		fileWriter.format("\tbeq $t%d,$t%d,%s\n",i1,i2,label);				
	}

	public void beqz(TEMP oprnd1,String label)
	{
		int i1 =oprnd1.getSerialNumber();
		fileWriter.format("\tbeq $t%d,0,%s\n",i1,label);				
	}

	public void subu(String dst, int imm)
	{
		fileWriter.format("\tsubu $%s,$%s,%d\n",dst.replace("\"", ""),dst.replace("\"", ""),imm);
	}

	public void addu(String dst, int imm)
	{
		fileWriter.format("\taddu $%s,$%s,%d\n",dst.replace("\"", ""),dst.replace("\"", ""),imm);
	}

	public void moveRet(TEMP src,boolean backwards)
	{
		boolean fucnRet = (this.currFuncName != null);
		if (src != null) {
			int srcidx = src.getSerialNumber();
			if (backwards) {
				fileWriter.format("\tmove $t%d,$v0\n",srcidx);
			} else {
				fileWriter.format("\tmove $v0,$t%d\n",srcidx);
			}
		}
		if (fucnRet && !backwards) {
			fileWriter.format("\tj %s\n",this.currFuncName+"_epilogue");
		}
	}

	public void assignConstNil(TEMP dst)
	{
		int idxdst=dst.getSerialNumber();
		fileWriter.format("\tli $t%d,0\n",idxdst);
	}

	public void newArray(TEMP dst,TEMP length, boolean isGlobal)
	{
		int idxdst=dst.getSerialNumber();
		int idxlen=length.getSerialNumber();
		fileWriter.format("\tli $v0,9\n");
		fileWriter.format("\tmove $a0,$t%d\n",idxlen);
		fileWriter.format("\tadd $a0,$a0,1\n");
		fileWriter.format("\tmul $a0,$a0,4\n");
		fileWriter.format("\tsyscall\n");
		fileWriter.format("\tmove $t%d,$v0\n",idxdst);
		fileWriter.format("\tsw $t%d,0($t%d)\n",idxlen,idxdst);
	}

	public void arrayAccess(TEMP dst,TEMP arr,TEMP offset)
	{
		int idxdst=dst.getSerialNumber();
		int idxarr=arr.getSerialNumber();
		int idxoff=offset.getSerialNumber();
		fileWriter.format("\tsubu $sp,$sp,4\n");
		fileWriter.format("\tsw $s0, 0($sp)\n");
		fileWriter.format("\tbltz $t%d,exit_access_violation\n",idxoff);
		fileWriter.format("\tbeqz $t%d,exit_invalid_ptr_dref\n",idxarr);
		fileWriter.format("\tlw $s0,0($t%d)\n",idxarr);
		fileWriter.format("\tbge $t%d,$s0,exit_access_violation\n",idxoff);
		fileWriter.format("\tmove $s0,$t%d\n",idxoff);
		fileWriter.format("\tadd $s0,$s0,1\n");
		fileWriter.format("\tmul $s0,$s0,4\n");
		fileWriter.format("\taddu $s0,$t%d,$s0\n",idxarr);
		fileWriter.format("\tlw $t%d,0($s0)\n",idxdst);
		fileWriter.format("\tlw $s0, 0($sp)\n");
		fileWriter.format("\taddu $sp,$sp,4\n");
	}

	public void arraySet(TEMP arr,TEMP offset,TEMP value)
	{
		int idxarr=arr.getSerialNumber();
		int idxoff=offset.getSerialNumber();
		int idxval=value.getSerialNumber();
		fileWriter.format("\tsubu $sp,$sp,4\n");
		fileWriter.format("\tsw $s0, 0($sp)\n");
		fileWriter.format("\tbltz $t%d,exit_access_violation\n",idxoff);
		fileWriter.format("\tlw $s0,0($t%d)\n",idxarr);
		fileWriter.format("\tbge $t%d,$s0,exit_access_violation\n",idxoff);
		fileWriter.format("\tmove $s0,$t%d\n",idxoff);
		fileWriter.format("\tadd $s0,$s0,1\n");
		fileWriter.format("\tmul $s0,$s0,4\n");
		fileWriter.format("\taddu $s0,$t%d,$s0\n",idxarr);
		fileWriter.format("\tsw $t%d,0($s0)\n",idxval);
		fileWriter.format("\tlw $s0, 0($sp)\n");
		fileWriter.format("\taddu $sp,$sp,4\n");
	}

	public void addPrologue(String label_name,int varnum, boolean isClassMethod)
	{
		label(label_name);
		fileWriter.format("\t#Prologue\n");
		fileWriter.format("\tsubu $sp,$sp,4\n");
		fileWriter.format("\tsw $ra,0($sp)\n");
		fileWriter.format("\tsubu $sp,$sp,4\n");
		fileWriter.format("\tsw $fp,0($sp)\n");
		fileWriter.format("\tmove $fp,$sp\n");
		// Caller saves $t0-$t9
		fileWriter.format("\t#Caller saves $t0-$t9\n");
		for (int i=0;i<10;i++) {
			fileWriter.format("\tsubu $sp,$sp,4\n");
			fileWriter.format("\tsw $t%d,0($sp)\n",i);
		}
		fileWriter.format("\t#Freeing space for local vars\n");
		fileWriter.format("\tsub $sp,$sp,%d\n",WORD_SIZE*varnum);
		fileWriter.format("\n");
		if (isClassMethod) {
			fileWriter.format("\tlw $s0,8($fp)\n");
		}
	}

	public void addEpilogue(String epilogue_label)
	{
		if (epilogue_label.equals("main"))
		{
			fileWriter.format("%s:\n","user_main_epilogue");
		} else {
			fileWriter.format("\n%s:\n",epilogue_label);
		}
		fileWriter.format("\t#Epilogue\n");
		fileWriter.format("\tmove $sp,$fp\n");
		// Caller restores $t0-$t9
		fileWriter.format("\t#Caller restores $t0-$t9\n");
		for (int i=0;i<10;i++) {
			fileWriter.format("\tlw $t%d,%d($sp)\n",i,(-4*(i+1)));
		}
		fileWriter.format("\n");
		fileWriter.format("\tlw $fp,0($sp)\n");
		fileWriter.format("\tlw $ra,4($sp)\n");
		fileWriter.format("\taddu $sp,$sp,8\n");
		fileWriter.format("\tjr $ra\n\n");
	}

	public void checkIntOverflow() {
		fileWriter.format("#Int flow check\n");
		fileWriter.format("int_flow_check:\n");
		fileWriter.format("\tsub $sp,$sp,4\n");
		fileWriter.format("\tsw $s0,0($sp)\n");
		fileWriter.format("\tlw $s0,min\n");
		fileWriter.format("\tlw $s1,max\n");
		fileWriter.format("\tbge $a0,$s0,check_int_overflow\n");
		fileWriter.format("\tmove $a0, $s0\n\n");
		fileWriter.format("\tcheck_int_overflow:\n");
		fileWriter.format("\t\tble $a0,$s1,ret_int_flow_check\n");
		fileWriter.format("\t\tmove $a0,$s1\n\n");
		fileWriter.format("\tret_int_flow_check:\n");
		fileWriter.format("\t\tlw $s0,0($sp)\n");
		fileWriter.format("\t\taddu $sp,$sp,4\n");
		fileWriter.format("\t\tjr $ra\n\n");
	}

	public void checkDivision() {
		fileWriter.format("#Division checks\n");
		fileWriter.format("check_division:\n");
		fileWriter.format("\tbeq $a1,$zero,exit_illegal_division\n");
		fileWriter.format("\tdiv $a0,$a1\n");
		fileWriter.format("\tmflo $a0\n");
		fileWriter.format("\tmfhi $a1\n\n");
		fileWriter.format("\t#If Remainder == 0, result is good\n");
		fileWriter.format("\tbeq $a1,$zero,end_div\n\n");
		fileWriter.format("\t#Else, check if result < 0\n");
		fileWriter.format("\tblt $a0,$zero,adjust_floor_div\n");
		fileWriter.format("\tj end_div\n\n");
		fileWriter.format("\t#Result needs to be floored\n");
		fileWriter.format("\tadjust_floor_div:\n");
		fileWriter.format("\t\tsub $a0,$a0,1\n");
		fileWriter.format("\tend_div:\n");
		fileWriter.format("\t\tjr $ra\n\n");
		fileWriter.format("\texit_illegal_division:\n");
		fileWriter.format("\t\tli $v0,4\n");
		fileWriter.format("\t\tla $a0,string_illegal_div_by_0\n");
		fileWriter.format("\t\tsyscall\n");
		fileWriter.format("\t\tli $v0,10\n");
		fileWriter.format("\t\tsyscall\n\n");
	}

	public void stringMemAllocation()
	{
		fileWriter.format("#Allocate memory for new string\n");
		fileWriter.format("string_mem_alloc:\n");
		fileWriter.format("\taddi $sp,$sp,-20\n");
		fileWriter.format("\tsw $ra,16($sp)\n");
		fileWriter.format("\tsw $s0,12($sp)\n");
		fileWriter.format("\tsw $s1,8($sp)\n");
		fileWriter.format("\tsw $s2,4($sp)\n");
		fileWriter.format("\tsw $s3,0($sp)\n");
		fileWriter.format("\tmove $s0,$a1\n");
		fileWriter.format("\tmove $s1,$a2\n");
		fileWriter.format("\tli $s2,0\n\n");
		fileWriter.format("\tstr1_mem_alloc:\n");
		fileWriter.format("\t\tlb $s3,0($s0)\n");
		fileWriter.format("\t\tbeq $s3,$zero, str2_mem_alloc\n");
		fileWriter.format("\t\taddiu $s2,$s2, 1\n");
		fileWriter.format("\t\taddiu $s0,$s0, 1\n");
		fileWriter.format("\t\tj str1_mem_alloc\n\n");
		fileWriter.format("\tstr2_mem_alloc:\n");
		fileWriter.format("\t\tlb $s3,0($s1)\n");
		fileWriter.format("\t\tbeq $s3,$zero, ret_str_mem_alloc\n");
		fileWriter.format("\t\taddiu $s2,$s2,1\n");
		fileWriter.format("\t\taddiu $s1,$s1,1\n");
		fileWriter.format("\t\tj str2_mem_alloc\n\n");
		fileWriter.format("\tret_str_mem_alloc:\n");
		fileWriter.format("\t\taddiu $s2,$s2,1\n");
		fileWriter.format("\t\tmove $a0,$s2\n");
		fileWriter.format("\t\tli $v0,9\n");
		fileWriter.format("\t\tsyscall\n");
		fileWriter.format("\t\tmove $a0,$v0\n");
		fileWriter.format("\t\tlw $ra,16($sp)\n");
		fileWriter.format("\t\tlw $s0,12($sp)\n");
		fileWriter.format("\t\tlw $s1,8($sp)\n");
		fileWriter.format("\t\tlw $s2,4($sp)\n");
		fileWriter.format("\t\tlw $s3,0($sp)\n");
		fileWriter.format("\t\taddi $sp,$sp,20\n");
		fileWriter.format("\t\tjr $ra\n\n");
	}

	public void concatStrings()
	{
		fileWriter.format("#Concat strings\n");
		fileWriter.format("concat_strs:\n");
		fileWriter.format("\tsubu $sp,$sp,20\n");
		fileWriter.format("\tsw $ra,16($sp)\n");
		fileWriter.format("\tsw $s0,12($sp)\n");
		fileWriter.format("\tsw $s1,8($sp)\n");
		fileWriter.format("\tsw $s2,4($sp)\n");
		fileWriter.format("\tsw $s3,0($sp)\n");
		fileWriter.format("\tmove $s0,$a1\n");
		fileWriter.format("\tmove $s1,$a2\n");
		fileWriter.format("\tmove $s3,$a0\n\n");
		fileWriter.format("\tcopy_str1:\n");
		fileWriter.format("\t\tlb $s2,0($s0)\n");
		fileWriter.format("\t\tbeq $s2,$zero,copy_str2\n");
		fileWriter.format("\t\tsb $s2,0($s3)\n");
		fileWriter.format("\t\taddiu $s0,$s0,1\n");
		fileWriter.format("\t\taddiu $s3,$s3,1\n");
		fileWriter.format("\t\tj copy_str1\n\n");
		fileWriter.format("\tcopy_str2:\n");
		fileWriter.format("\t\tlb $s2,0($s1)\n");
		fileWriter.format("\t\tbeq $s2,$zero,ret_concat_strs\n");
		fileWriter.format("\t\tsb $s2,0($s3)\n");
		fileWriter.format("\t\taddiu $s1,$s1,1\n");
		fileWriter.format("\t\taddiu $s3,$s3,1\n");
		fileWriter.format("\t\tj copy_str2\n\n");
		fileWriter.format("\tret_concat_strs:\n");
		fileWriter.format("\t\tsb $zero,0($s3)\n");
		fileWriter.format("\t\tlw $ra,16($sp)\n");
		fileWriter.format("\t\tlw $s0,12($sp)\n");
		fileWriter.format("\t\tlw $s1,8($sp)\n");
		fileWriter.format("\t\tlw $s2,4($sp)\n");
		fileWriter.format("\t\tlw $s3,0($sp)\n");
		fileWriter.format("\t\taddu $sp,$sp,20\n");
		fileWriter.format("\t\tjr $ra\n\n");
	}

	public void stringEqCheck() {
		fileWriter.format("#String equality check\n");
		fileWriter.format("string_eq_check:\n");
		fileWriter.format("\taddi $sp,$sp,-24\n");
		fileWriter.format("\tsw $ra,20($sp)\n");
		fileWriter.format("\tsw $s0,16($sp)\n");
		fileWriter.format("\tsw $s1,12($sp)\n");
		fileWriter.format("\tsw $s2,8($sp)\n");
		fileWriter.format("\tsw $s3,4($sp)\n");
		fileWriter.format("\tsw $s4,0($sp)\n");
		fileWriter.format("\tmove $s0,$zero\n");
		fileWriter.format("\tmove $s1,$a0\n");
		fileWriter.format("\tmove $s2,$a1\n\n");
		fileWriter.format("\teq_by_char:\n");
		fileWriter.format("\t\tlb $s3,0($s1)\n");
		fileWriter.format("\t\tlb $s4,0($s2)\n");
		fileWriter.format("\t\tbne $s3,$s4,ret_strs_eq_check\n");
		fileWriter.format("\t\tbeq $s3,$zero,ret_strs_eq\n");
		fileWriter.format("\t\taddu $s1,$s1,1\n");
		fileWriter.format("\t\taddu $s2,$s2,1\n");
		fileWriter.format("\t\tj eq_by_char\n\n");
		fileWriter.format("\tret_strs_eq:\n");
		fileWriter.format("\t\tli $s0,1\n\n");
		fileWriter.format("\tret_strs_eq_check:\n");
		fileWriter.format("\t\tmove $v0,$s0\n");
		fileWriter.format("\t\tlw $ra,20($sp)\n");
		fileWriter.format("\t\tlw $s0,16($sp)\n");
		fileWriter.format("\t\tlw $s1,12($sp)\n");
		fileWriter.format("\t\tlw $s2,8($sp)\n");
		fileWriter.format("\t\tlw $s3,4($sp)\n");
		fileWriter.format("\t\tlw $s4,0($sp)\n");
		fileWriter.format("\t\taddi $sp,$sp,24\n");
		fileWriter.format("\t\tjr $ra\n\n");
	}

	public void exitAccesViolation() {
		fileWriter.format("exit_access_violation:\n");
		fileWriter.format("\tli $v0,4\n");
		fileWriter.format("\tla $a0,string_access_violation\n");
		fileWriter.format("\tsyscall\n");
		fileWriter.format("\tli $v0,10\n");
		fileWriter.format("\tsyscall\n\n");
	}

	public void exitInvalidPtrDref() {
		fileWriter.format("exit_invalid_ptr_dref:\n");
		fileWriter.format("\tli $v0,4\n");
		fileWriter.format("\tla $a0,string_invalid_ptr_dref\n");
		fileWriter.format("\tsyscall\n");
		fileWriter.format("\tli $v0,10\n");
		fileWriter.format("\tsyscall\n\n");
	}

	public void computeFieldListMap(String class_name) {
		Map<String, String> extendsMap = IR.getInstance().extendMap;
		List<Field> fieldList = IR.getInstance().classFieldsMap.get(class_name);
		String temp = class_name;
		while (extendsMap.get(temp) != null) {
			temp = extendsMap.get(temp);
			List<Field> superFieldList = IR.getInstance().classFieldsMap.get(temp);
			if (superFieldList != null) {
				fieldList.addAll(0,superFieldList);
			}
		}
		fieldListMap.put(class_name, fieldList);
	}

	public List<Field> getFieldList(String class_name) {
		List<Field> fieldList = fieldListMap.get(class_name);
		return fieldListMap.get(class_name);
	}

	public Field getField(List<Field> fieldList, String var_name) {
		for (Field field : fieldList) {
			if (field.name.equals(var_name)) {
				return field;
			}
		}
		return null;
	}

	public int getFieldOffset(String class_name, String field_name) {
		List<Field> fieldList = MIPSGenerator.getInstance().getFieldList(class_name);
		for (Field field : fieldList) {
			if (field.name.equals(field_name)) {
				return fieldList.indexOf(field) * WORD_SIZE + 4;
			}
		}
		return -1;
	}

	public void printFieldList(List<Field> fieldList, String class_name) {
		System.out.println("--------------------------------------");
		System.out.println("Field list for class: " + class_name);
		for (Field field : fieldList) {
			System.out.println(field.name);
		}
		System.out.println("--------------------------------------");
	}

	
	/**************************************/
	/* USUAL SINGLETON IMPLEMENTATION ... */
	/**************************************/
	private static MIPSGenerator instance = null;

	/*****************************/
	/* PREVENT INSTANTIATION ... */
	/*****************************/
	protected MIPSGenerator() {}

	/******************************/
	/* GET SINGLETON INSTANCE ... */
	/******************************/
	public static MIPSGenerator getInstance()
	{
		if (instance == null)
		{
			/*******************************/
			/* [0] The instance itself ... */
			/*******************************/
			instance = new MIPSGenerator();

			try
			{
				/*********************************************************************************/
				/* [1] Open the MIPS text file and write data section with error message strings */
				/*********************************************************************************/
				String dirname="./output/";
				String filename=String.format("MIPS.txt");

				/***************************************/
				/* [2] Open MIPS text file for writing */
				/***************************************/
				instance.fileWriter = new PrintWriter(dirname+filename);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}

			/*****************************************************/
			/* [3] Print data section with error message strings */
			/*****************************************************/
			instance.fileWriter.print(".data\n");
			instance.fileWriter.print("\tstring_access_violation: .asciiz \"Access Violation\"\n");
			instance.fileWriter.print("\tstring_illegal_div_by_0: .asciiz \"Illegal Division By Zero\"\n");
			instance.fileWriter.print("\tstring_invalid_ptr_dref: .asciiz \"Invalid Pointer Dereference\"\n");
		}
		return instance;
	}
}