grammar ScraperDSL;

script
    : statement* EOF
    ;

statement
    : varDecl
    | assignment
    | ifStatement
    | whileStatement
    | forStatement
    | 'break' ';'?
    | 'continue' ';'?
    | funcCall ';'?
    ;

varDecl
    : (STRING_TYPE | NUMBER_TYPE | BOOLEAN_TYPE | LIST_TYPE | MAP_TYPE) ID ('=' expression)? ';'?
    ;

assignment
    : ID '=' expression ';'?
    | ID '[' expression ']' '=' expression ';'?
    ;

ifStatement
    : 'if' '(' expression ')' block ('else' block)?
    ;

whileStatement
    : 'while' '(' expression ')' block
    ;

forStatement
    : 'for' '(' ID 'in' expression ')' block
    ;

block
    : '{' statement* '}'
    ;

// Expression with ternary support
expression
    : logicalOrExpr ('?' expression ':' expression)?
    ;

logicalOrExpr
    : logicalAndExpr ('||' logicalAndExpr)*
    ;

logicalAndExpr
    : equalityExpr ('&&' equalityExpr)*
    ;

equalityExpr
    : comparisonExpr (op=('==' | '!=') comparisonExpr)*
    ;

comparisonExpr
    : additiveExpr (op=('<' | '<=' | '>' | '>=') additiveExpr)*
    ;

additiveExpr
    : multiplicativeExpr (op=('+' | '-') multiplicativeExpr)*
    ;

multiplicativeExpr
    : unaryExpr (op=('*' | '/' | '%') unaryExpr)*
    ;

unaryExpr
    : op=('!' | '-') unaryExpr
    | primary
    ;

// Primary now supports post-indexing on any primary result
primary
    : literal
    | listLiteral
    | mapLiteral
    | variableRef
    | funcCall
    | '(' expression ')'
    | primary '[' expression ']'
    ;

literal
    : STRING
    | NUMBER
    | BOOLEAN
    | NULL
    ;

listLiteral
    : '[' (expression (',' expression)*)? ']'
    ;

mapLiteral
    : '{' (mapEntry (',' mapEntry)*)? '}'
    ;

mapEntry
    : STRING ':' expression
    ;

variableRef
    : ID
    | ID '[' expression ']'
    ;

funcCall
    : ID '(' (expression (',' expression)*)? ')'
    ;

// Types
STRING_TYPE : 'string';
NUMBER_TYPE : 'number';
BOOLEAN_TYPE : 'boolean';
LIST_TYPE   : 'list';
MAP_TYPE    : 'map';

// Literals
BOOLEAN : 'true' | 'false';
NULL    : 'null';
NUMBER  : '-'? [0-9]+ ('.' [0-9]+)?;
STRING  : '"' (~["\\] | '\\' .)* '"' | '\'' (~['\\] | '\\' .)* '\'';

// Identifiers
ID : [a-zA-Z_][a-zA-Z0-9_]*;

// Whitespace & Comments
WS : [ \t\r\n]+ -> skip;
COMMENT : '//' ~[\r\n]* -> skip;