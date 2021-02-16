grammar LLAT;

//=========== Lexeme patterns and tokens start here ==============

// Put your lexical analyzer rules here - the following rule is just
// so that there is some lexer rule in the initial grammar (otherwise
// ANTLR won't make a Lexer class)


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
fragment ANYCHAR_MOD    : (.+?) ; // Requires at least ONE character, whether it's special or not. If it's an empty char, that's the parser's problem.

/* Atoms. */
ATOM: UPPER_CASE_LTR;

/* Binary operators. */
AND: '&';
OR: '|';
IMP: '->';
BICOND: '<->';
TOKEN: 'ttt';

program: .;
