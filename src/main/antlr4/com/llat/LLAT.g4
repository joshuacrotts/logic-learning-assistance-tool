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
COMMA      : ',';
SEMICOLON  : ';';

/* Binary and unary operators for propositional logic. */
AND     : '&' | '∧'  ;
OR      : '|' | '∨' | '+' | '||'  ;
IMP     : '->' | '→' | '⇒' | '⊃'  ;
BICOND  : '<->' | '⇔' | '≡' | '↔' ;
NEG     : '~' | '¬' | '!'  ;
IDENTITY: '=';

/* Atoms. */
ATOM: UPPER_CASE_LTR;

/* Constants. */
CONSTANT: [a-t];

/* Variables. */
VARIABLE: [u-z];

/* Quantifiers. */
EXISTENTIAL: '∃';
UNIVERSAL: '∀';

/* Conclusion Indicator Symbols. */
THEREFORE: '⊢' | '∴' | '=>';

//=========== Parser rules. ==============

program: (predProof EOF) | (propProof EOF) | (predicateWff) | (propositionalWff);

/* Propositional Logic Rules. */
atom: ATOM;

/* Starting rule. */
propositionalWff: propWff EOF;

propWff: atom
    | propNegRule
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
universal: (OPEN_PAREN UNIVERSAL variable CLOSE_PAREN) | (OPEN_PAREN variable CLOSE_PAREN);
existential: OPEN_PAREN EXISTENTIAL variable CLOSE_PAREN;
predicate: atom(constant|variable)+;

/* Starting rule. */
predicateWff: predWff EOF;

predWff: predicate
    | predNegRule
    | predQuantifier
    | predAndRule
    | predOrRule
    | predImpRule
    | predBicondRule
    | predIdentityRule;

predQuantifier: NEG? (existential | universal) predWff;
predNegRule: NEG predWff;
predAndRule: OPEN_PAREN predWff AND predWff CLOSE_PAREN;
predOrRule : OPEN_PAREN predWff OR predWff CLOSE_PAREN;
predImpRule: OPEN_PAREN predWff IMP predWff CLOSE_PAREN;
predBicondRule: OPEN_PAREN predWff BICOND predWff CLOSE_PAREN;
predIdentityRule: (constant|variable) IDENTITY (constant|variable);

/* Proof rules. */
/* Proof for predicate logic. */
predPremise: ((predWff (COMMA|SEMICOLON)) | predWff);
predConclusion: predWff;
predProof: predPremise+ THEREFORE predConclusion;

/* Proof for propositional logic. */
propPremise: ((propWff (COMMA|SEMICOLON)) | propWff);
propConclusion: propWff;
propProof: propPremise+ THEREFORE propConclusion;