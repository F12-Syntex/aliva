grammar ScraperDSL;

script
    : statement* EOF
    ;

statement
    : varDecl
    | assignment
    | funcCall
    ;

varDecl
    : type ID (EQUAL expression)?
    ;

assignment
    : ID EQUAL expression
    ;

type
    : STRING_TYPE
    | DOC_TYPE
    ;

expression
    : literal
    | funcCall
    | variableRef
    ;

variableRef
    : ID
    ;

literal
    : STRING
    ;

funcCall
    : ID LPAREN (expression (COMMA expression)*)? RPAREN
    ;

STRING_TYPE  : 'string';
DOC_TYPE     : 'doc';

STRING       : '"' ( ~["\\] | '\\' . )* '"' ;
ID           : [a-zA-Z_][a-zA-Z0-9_]* ;
EQUAL        : '=';
COMMA        : ',';
LPAREN       : '(';
RPAREN       : ')';
WS           : [ \t\r\n]+ -> skip;