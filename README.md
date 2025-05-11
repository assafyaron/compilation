# Compilation Course Project
This repository contains a series of exercises that gradually build a full compiler for the L programming language, targeting MIPS32 assembly. The project is organized into five exercises, each representing a critical compilation stage.

## Project Structure

### ex1 – Lexer (Lexical Analysis)
Implements the lexical analyzer using JFlex. This stage converts raw L source code into tokens such as identifiers, literals, and keywords.

### ex2 – Parser (Syntax Analysis)
Uses CUP to build a parser that processes tokens from the lexer and verifies the syntactic structure of L programs based on a context-free grammar.

### ex3 – AST (Abstract Syntax Tree)
Generates a structured AST representing the hierarchy and semantics of the program. The AST simplifies further analysis and code generation.

### ex4 – IR (Intermediate Representation)
Transforms the AST into a language-independent IR. This IR is designed for optimizations and easier mapping to machine code.

### ex5 – Code Generation (IR to MIPS32)
This is the final and most critical stage of the compiler. It converts the IR into valid MIPS32 assembly code.

## Key Directories and Files:

**src/:** Contains all Java source code for code generation.
```src/
├── ex1/                        # Lexer implementation
│   └── Lexer.flex              # JFlex/Flex file for tokenizing L
|
├── ex2/                        # Parser implementation
│   └── Parser.cup              # CUP/Bison grammar definition
|
├── ex3/                        # AST classes
│   ├── AST_CLASS.java
│   └── ...                     # All AST node classes
|   ├── TYPE_CLASS.java         # Used for AST annotation
|   └── ...
├── ex4/                        # Intermediate Representation (IR)
│   ├── IR_CLASS.java
│   └── ...                     # All IR-level abstractions
|   ├── TEMP_CLASS.java         # Class to represent MIPS registers
|   └── ...
|
├── ex5/                        # Code Generation
|
│   ├── MIPSGenerator.java      # Emits final MIPS code and embeds handels for:
|                                    1. String operations
|                                    2. Integer overflows
|                                    3. Access violations
|
└── Main.java                   # Compiler entry point
```

**input/:** Place your input.txt file here with a valid L program.

**output/:** After running the compiler, this folder will contain output.txt – the corresponding MIPS32 assembly code, and MIPS_OUTPUT.txt which contains the output of the simulated MIPS program.

**COMPILER:** A shell script that builds and runs the entire compiler pipeline.

**Makefile:** Automates the build process.

**external_jars/:** Contains necessary JFlex and CUP jar files for lexical and syntax analysis.

**expected_output/:** Reference outputs for testing.

**bin/:** Compiled .class files are stored here.

## Language L Syntax

### Basic Types
- `int`: integer values
- `string`: string literals (enclosed in quotes "")
- `void`: used for functions without return value

### Variable Declarations
```l
int x := 5;
string s := "hello";
Tree t := nil;
```
### Class Definitions
```l
class Tree {
    Tree left := nil;
    Tree right := nil;
    int weight := 0;
}
```
### Function Declarations

```l
int sum(Tree t) { ... }
void main() { ... }
```

### Expressions
1. **Arithmetic:** +, -, *, /, all with overflow and division-by-zero checks
2. **Logical:** No built-in boolean type; equality (=), inequality (!=) inferred
3. **Nil check:** 1 - (expr = nil) behaves like if (expr != nil)

### Object-Oriented Features
```l
object.field # Object field access
new ClassName  # allocates a new object
```
1. Class field initializations inside class definition
2. No explicit constructors

### Control Flow

```l
if (condition) { ... }
while (condition) { ... }
return value;
```

### Arrays
```l
array TypeArray = Type[];
TypeArray A := new Type[size];
array[index] # Array access
```

## Running the Compiler
To compile an L program and generate MIPS32 assembly code, follow these steps:

1. Prepare Your L Program
Write your L program and save it as input.txt inside the ex5/input/ directory.
Example input.txt:

```sh
int g0 := 0;
int g := g0 + 1;
void main() {
    int x := g * 2 + 3;
    int y := 10;
    int z;
    int w;

    while (y > 0) {
        z := x + 1 + y;
        g := 1;
        y := y - 1;
    }
    PrintInt(g);
}
```

2. Build and Run the Compiler. From the ex5 directory, run:

```sh
make everything
```
This will:

  1. Build the compiler using javac and dependencies
  2. Run the entire pipeline: Lexing → Parsing → AST → IR → MIPS code generation
  3. Save the MIPS32 assembly code to output/output.txt
  4. Simulate MIPS Code

## Notes
1. All components are written in Java using JFlex and CUP.
2. The compiler supports global variables, arithmetic expressions, control flow (while), and print statements.
3. Variables must be declared before use.
4. The generated code assumes MIPS32 architecture.

## Author
This project was developed as part of the Compilation Course by Assaf Yaron, email me for any questions: assafyaron@mail.tau.ac.il.
