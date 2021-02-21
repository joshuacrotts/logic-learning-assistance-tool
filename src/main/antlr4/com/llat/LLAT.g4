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

/* Binary and unary operators for propositional logic. */
AND     : '&' | '∧'  ;
OR      : '|' | '∨' | '+' | '||'  ;
IMP     : '->' | '→' | '⇒' | '⊃'  ;
BICOND  : '<->' | '⇔' | '≡' | '↔' ;
NEG     : '~' | '¬' | '!'  ;

/* Atoms. */
ATOM: UPPER_CASE_LTR;

/* Constants. */
CONSTANT: [a-t];

/* Variables. */
VARIABLE: [u-z];

/* Quantifiers. */
EXISTENTIAL: '∃';
UNIVERSAL: '∀';

//=========== Parser rules. ==============

program: predWff | propWff;

/* Propositional Logic Rules. */
atom: ATOM;

propWff: atom
    | propNegRule
    | OPEN_PAREN propWff CLOSE_PAREN
    | propAndRule
    | propOrRule
    | propImpRule
    | propBicondRule;

propNegRule: NEG propWff;
propAndRule: OPEN_PAREN propWff AND propWff CLOSE_PAREN;
propOrRule : OPEN_PAREN propWff OR propWff CLOSE_PAREN;
propImpRule: OPEN_PAREN propWff IMP propWff CLOSE_PAREN;
propBicondRule: OPEN_PAREN propWff BICOND propWff CLOSE_PAREN;

/* Predicate Logic Rules. */
constant: CONSTANT;
variable: VARIABLE;
universal: ('(' UNIVERSAL variable ')') | ('(' variable ')');
existential: '(' (EXISTENTIAL variable) ')';

predicate: ATOM(constant|variable)+;

predWff:
      constant
    | variable
    | predicate
    | quantifier* predNegRule
    | quantifier* OPEN_PAREN predWff CLOSE_PAREN
    | quantifier* predAndRule
    | quantifier* predOrRule
    | quantifier* predImpRule
    | quantifier* predBicondRule;

quantifier: NEG? (existential | universal);
predNegRule: NEG predWff;
predAndRule: OPEN_PAREN predWff AND predWff CLOSE_PAREN;
predOrRule : OPEN_PAREN predWff OR predWff CLOSE_PAREN;
predImpRule: OPEN_PAREN predWff IMP predWff CLOSE_PAREN;
predBicondRule: OPEN_PAREN predWff BICOND predWff CLOSE_PAREN;