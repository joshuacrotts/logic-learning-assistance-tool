grammar LLAT;

//=========== Lexeme patterns and tokens. ==============

/* Miscellaneous and skippable lexemes. */
WHITESPACE              : [ \r\n\t]+                               -> skip ;
COMMENT                 : '//'(.)*?NEWLINE                         -> skip ; // Match any text that has // preceding.
fragment DIGIT          : [0-9]                                            ;
fragment LOWER_CASE_LTR : [a-z]                                            ;
fragment UPPER_CASE_LTR : [A-Z]                                            ;
fragment ANY_CASE_LTR   : [a-zA-Z]                                         ;
fragment UNDERSCORE     : '_'                                              ;
fragment SINGLE_QUOTE   : '\''                                             ;
fragment DOUBLE_QUOTE   : '"'                                              ;
fragment ANYCHAR        : .                                                ;
fragment NEWLINE        : '\n'                                             ;
fragment CARRIAGE_RET   : '\r'                                             ;
fragment TAB            : '\t'                                             ;
fragment NULL_CHAR      : '\\0'                                            ;
fragment ESCAPED_CHAR   : ('\\' .)                                         ;

/* Other symbols. */
OPEN_PAREN : '(';
CLOSE_PAREN: ')';



/* Binary operators. */
AND     : '&'   ;
OR      : '|'   ;
IMP     : '->'  ;
BICOND  : '<->' ;
NEG     : '~'   ;

/* Atoms. */
ATOM: UPPER_CASE_LTR;

//=========== Parser rules. ==============

atom: ATOM;

wff: atom
    | negRule
    | OPEN_PAREN wff CLOSE_PAREN
    | andRule
    | orRule
    | impRule
    | bicondRule;

negRule: NEG wff;
andRule: OPEN_PAREN wff AND wff CLOSE_PAREN;
orRule : OPEN_PAREN wff OR wff CLOSE_PAREN;
impRule: OPEN_PAREN wff IMP wff CLOSE_PAREN;
bicondRule: OPEN_PAREN wff BICOND wff CLOSE_PAREN;
