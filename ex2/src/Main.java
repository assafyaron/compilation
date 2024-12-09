   
import java.io.*;
import java.io.PrintWriter;
import java_cup.runtime.Symbol;
import AST.*;

public class Main
{
	static public void main(String argv[])
	{
		Lexer l;
		Parser p;
		Symbol s;
		AST_STMT_LIST AST;
		FileReader file_reader;
		PrintWriter file_writer;
		String inputFilename = argv[0];
		String outputFilename = argv[1];
		
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
			
			/******************************/
			/* [3] Initialize a new lexer */
			/******************************/
			l = new Lexer(file_reader);
			
			/*******************************/
			/* [4] Initialize a new parser */
			/*******************************/
			p = new Parser(l);

			/***********************************/
			/* [5] 3 ... 2 ... 1 ... Parse !!! */
			/***********************************/
			AST = (AST_STMT_LIST) p.parse().value;
			file_writer.write("OK\n");
			
			/*************************/
			/* [6] Print the AST ... */
			/*************************/
			AST.PrintMe();
			
			/*************************/
			/* [7] Close output file */
			/*************************/
			file_writer.close();
			
			/*************************************/
			/* [8] Finalize AST GRAPHIZ DOT file */
			/*************************************/
			AST_GRAPHVIZ.getInstance().finalizeFile();
    		}
			     
		catch (SyntaxException e) {
            		if (file_writer != null) {
                		try {
					file_writer = new PrintWriter(new FileWriter(outputFilename));
					file_writer.print("ERROR(");
					file_writer.print(e.getMessage());
					file_writer.print(")");
					file_writer.close();
                		} catch (IOException ioException) {
                    			System.out.println("ERROR: Unable to write error message to file.");
                    			ioException.printStackTrace();
                		}
            		}
            		e.printStackTrace();
        	}
		catch (LexicalError e) {
            		if (file_writer != null) {
                		file_writer.print("ERROR");
                		file_writer.close();
            		}
            		e.printStackTrace();
        	} 
	        catch (IOException e) {
            		System.out.println("ERROR: File not found or cannot be read/written.");
            		e.printStackTrace();
        	}
        	catch (Exception e) {
	    		if (file_writer != null) {
                		try {
                    			file_writer = new PrintWriter(new FileWriter(outputFilename));
                    			file_writer.print("ERROR(");
                    			file_writer.print(e.getMessage() != null ? e.getMessage() : "An unknown error occurred.");
                    			file_writer.print(")");
                    			file_writer.close();
                		} catch (IOException ioException) {
                    			System.out.println("ERROR: Unable to write error message to file.");
                    			ioException.printStackTrace();
                		}
            		}
            		e.printStackTrace();
        	}
        	finally {
            		try {
                		if (file_reader != null) file_reader.close();
                		if (file_writer != null) file_writer.close();
            		} catch (IOException e) {
                		e.printStackTrace();
            		}
        	}
    	}
}

public class SyntaxException extends Exception {
    public SyntaxError(String message) {
        super(message);
    }
}

public class LexicalException extends Exception {
    public LexicalError(String message) {
        super(message);
    }
}
