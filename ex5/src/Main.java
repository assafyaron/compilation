   
import java.io.*;
import java.util.*;
import java_cup.runtime.Symbol;
import AST.*;
import IR.*;
import MIPS.*;

public class Main
{
	static public void main(String argv[])
	{

		Lexer l;
		Parser p;
		Symbol s;
		AST_PROGRAM_REC AST;
		FileReader file_reader;
		PrintWriter file_writer;
		String inputFilename = argv[0];
		String outputFilename = argv[1];
		Set<String> errors; 
		List<String> sortedErrors;
		

		try
		{
			/********************************/
			/* [1] Initialize a file reader */
			/********************************/
			file_reader = new FileReader(inputFilename);

			/********************************/
			/* [2] Initialize a file writer */
			/********************************/
			file_writer = new PrintWriter(outputFilename);
			AST_Node.fileWriter = file_writer;
			
			/******************************/
			/* [3] Initialize a new lexer */
			/******************************/
			l = new Lexer(file_reader);
			
			/*******************************/
			/* [4] Initialize a new parser */
			/*******************************/
			p = new Parser(l, file_writer);

			/***********************************/
			/* [5] 3 ... 2 ... 1 ... Parse !!! */
			/***********************************/
			AST = (AST_PROGRAM_REC) p.parse().value;
			
			/*************************/
			/* [6] Print the AST ... */
			/*************************/
			AST.PrintMe();

			/**************************/
			/* [7] Semant the AST ... */
			/**************************/
			AST.SemantMe();

			/**************************/
			/* [8] IR the AST ... */
			/**************************/
			IR.getInstance().Add_IRcommand(new IRcommand_MIPSrec());
			AST.IRme();

			/***************************************************************************************/
			/* PRINT IR CMDS                                                                       */
			/* Comment out next line to print the IR Commands (true means next cmds also printed)  */
			// System.out.println("========IR CMDS BEFORE REG-ALLOC========\n");
			IR.getInstance().controlGraph.printControlGraph(false);                             
			// /***************************************************************************************/

			IR.getInstance().controlGraph.performLivenessAnalysis();
			IR.getInstance().interferenceGraph.buildGraph();
			System.out.println("\n========InterferenceGraph && Coloring========");
			IR.getInstance().interferenceGraph.printGraph();
			System.out.println("\n========Colors========");
			Map<String,Integer> colors = IR.getInstance().interferenceGraph.colorGraph();
			System.out.println("\n========DONE========\n");
			IR.getInstance().setColors(colors);
			System.out.println("========IR CMDS AFTER REG-ALLOC========\n");
			IR.getInstance().controlGraph.printControlGraph(false);

			/*************************/
			/* [9] MIPS the IR ...   */
			/*************************/
			IR.getInstance().MIPSme();

			/*************************************/
			/* [10] Finalize AST GRAPHIZ DOT file */
			/*************************************/
			AST_GRAPHVIZ.getInstance().finalizeFile();
			
			/***************************/
			/* [11] Finalize MIPS file */
			/***************************/
			MIPSGenerator.getInstance().finalizeFile();

			/**************************/
			/* [12] Close output file */
			/**************************/
			file_writer.close();		
		}
				
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}