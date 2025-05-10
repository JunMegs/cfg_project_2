grammar Arithmetic;

// Parser Rules
expr
    : expr '+' term    # Addition
    | expr '-' term    # Subtraction
    | term             # TermOnly
    ;

term
    : term '*' factor  # Multiplication
    | term '/' factor  # Division
    | factor           # FactorOnly
    ;

factor
    : '(' expr ')'     # Parentheses
    | ID '(' expr ')'  # Parentheses
    | ID               # Variable
    | NUM              # Number
    ;

// Lexer Rules
ID  : [a-zA-Z] ;         // Single lowercase and uppercase letter
NUM : [0-9]+ ;         // Integer number
WS  : [ \t\r\n]+ -> skip ; // Skip whitespace