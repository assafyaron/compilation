   
import java.io.*;                 //Provides classes for input/output operations.
import java.lang.reflect.Field;;    // Allows access to fields at runtime.
import java.io.PrintWriter;       // Allows to write formatted text to a .txt file.
import java_cup.runtime.Symbol;  // Represents tokens in a parser. Each token has attributes such as type and value.
   
public class Main
{
	static public void main(String argv[])
	{
		Lexer l;  //Represents the lexer object responsible for tokenizing the input.
		Symbol s; //Represents a token returned by the lexer.
		FileReader file_reader = null;  //Reads the input file character by character.
		PrintWriter file_writer = null;  // Writes output tokens to the specified file.
		String inputFilename = argv[0];  // Name of the input .txt file.
		String outputFilename = argv[1];  // Name of file to write to.
		boolean isItComment = false;  // Boolean to mark if the reader is inside a comment.
		String fieldName;  // The field name to be used.
		
		try
		{
			/********************************/
			/* [1] Initialize a file reader */
			/********************************/
			file_reader = new FileReader(inputFilename);  // Opens the input file for reading. The FileReader object allows the lexer to process its content.

			/********************************/
			/* [2] Initialize a file writer */
			/********************************/
			file_writer = new PrintWriter(outputFilename);  // Opens the output file for writing. 
			
			/******************************/
			/* [3] Initialize a new lexer */     
			/******************************/
			l = new Lexer(file_reader);    // Creates a lexer object using the input file reader. The lexer processes the input file to generate tokens.


			/***********************/
			/* [4] Read next token */
			/***********************/
			s = l.next_token();     //Fetches the first token from the input file. Each token is represented as a Symbol object.


			/********************************/
			/* [5] Main reading tokens loop */
			/********************************/
			
			while (s.sym != TokenNames.EOF)    //The while loop continues until the lexer returns an EOF (end of file) token (TokenNames.EOF).
			{
				/************************/
				/* [6] Print to console */    // Prints the line number, token position, and token value to the console.
				/************************/
				Class<TokenNames> tokenNamesClass = TokenNames.class;

				Field[] fields = tokenNamesClass.getDeclaredFields();  // Save all the fields declared in tokenNamesClass.

				// Check for comment tokens
				if (s.sym == TokenNames.START_COMMENT) isItComment = true;
				if (s.sym == TokenNames.END_COMMENT) isItComment = false;
								
				// Check for error tokens
				if (fields[s.sym].getName().equals("ERROR")) {
					throw new Exception("Illegal characters");
				}
								
				fieldName = fields[s.sym].getName();
				
				// Skip comment tokens for writing to the file
				if (!(fieldName.equals("START_COMMENT") || fieldName.equals("END_COMMENT"))) {
					file_writer.print(fields[s.sym].getName());
									
					// Print token value if it exists
					if (s.value != null) {
						// Validate and handle integer values
						if (fields[s.sym].getName().equals("INT")) {
							if (((String)s.value).charAt(0) == '0' && ((String)s.value).length() > 1) {
								throw new Exception("Leading zeros are not allowed");
							}
							int val = Integer.valueOf((String)s.value);
							if (0 > val || val > 32767) {
								throw new Exception("The integer is too big");
							}
						}
						file_writer.print("(");
						if (s.value instanceof String && fields[s.sym].getName().equals("STRING")) {
							file_writer.print(s.value);
						} else {
							file_writer.print(s.value);
						}
						file_writer.print(")");
					}
									
					// Print token position
					file_writer.print("[");
					file_writer.print(l.getLine());
					file_writer.print(",");
					file_writer.print(l.getTokenStartPosition());
					file_writer.print("]");
					file_writer.print("\n");
				}
												
				/***********************/
				/* [8] Read next token */
				/***********************/
				s = l.next_token();
			}


			if (isItComment == true) throw new Exception("Comment was not closed");

			
			/******************************/
			/* [9] Close lexer input file */
			/******************************/
			l.yyclose();  // Closes the lexer and the input file it was reading.

			/**************************/
			/* [10] Close output file */
			/**************************/
			file_writer.close();   // Ensures all written data is flushed and the output file is properly closed.
    	}
			     
		catch (Exception e)            // The try-catch block ensures that any exceptions (e.g., file not found,
		{                              // syntax errors in the input) are caught and printed using e.printStackTrace().
			e.printStackTrace();
            if (file_writer != null) {
                try {
                    file_writer = new PrintWriter(new FileWriter(outputFilename));
                    file_writer.print("ERROR");  // Write to the output file only ERROR.
                    file_writer.close();
                } catch (Exception e1) {
                    System.out.println("ERROR");  // Print ERROR out to terminal.
                    e1.printStackTrace();
                }
            } else {
                System.out.println("ERROR: Unable to initialize file writer.");
            }
        } finally {
            try {
                if (file_reader != null) file_reader.close();
                if (file_writer != null) file_writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
	}
}
