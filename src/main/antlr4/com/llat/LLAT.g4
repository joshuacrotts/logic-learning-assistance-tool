grammar LLAT;

//=========== Lexeme patterns and tokens. ==============

/* Miscellaneous and skippable lexemes. */
WHITESPACE              : [ \r\n\t]+                               -> skip ;
COMMENT                 : '//' (~ [\r\n])* NEWLINE                 -> skip ; // Match any text that has // preceding.
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

/* Word literals for logic (if they want to type it out). */
fragment AND_LIT: 'AND' | 'and';
fragment OR_LIT: 'OR' | 'or';
fragment IMP_LIT: 'IMPLIES' | 'implies';
fragment BICOND_LIT: 'IFF' | 'iff';
fragment NEG_LIT: 'NOT' | 'not';
fragment XOR_LIT: 'XOR' | 'xor';
fragment IDENTITY_LIT: 'EQUIVALENT' | 'equivalent';

/* Other symbols. */
OPEN_PAREN : '(';
CLOSE_PAREN: ')';
COMMA      : ',';
SEMICOLON  : ';';

/* Binary and unary operators for propositional logic. */
AND     : '&' | '∧' | '^' | '·' | AND_LIT;
OR      : '|' | '∨' | '+' | '||' | OR_LIT;
IMP     : '->' | '→' | '⇒' | '⊃' | '>' | IMP_LIT;
BICOND  : '<->' | '⇔' | '≡' | '↔' | '<>' | BICOND_LIT ;
NEG     : '˜' | '\u007e' | '\uff5e' | '\u223c' | '¬' | '!' | NEG_LIT ;
XOR     : '⊕' | '⊻' | '≢' | '⩒' | '↮' | XOR_LIT ;
IDENTITY: '=' | IDENTITY_LIT;

/* Atoms. */
ATOM: UPPER_CASE_LTR;

/* Constants. */
CONSTANT: [a-t];

/* Variables. */
VARIABLE: [u-z];

/* Quantifiers. */
EXISTENTIAL: '∃' ;
UNIVERSAL: '∀' ;

/* Conclusion Indicator Symbols. */
THEREFORE: '⊢' | '∴' | '=>';

/* Semantic entailment (models). */
SEMANTIC_ENTAILMENT: '⊧' | '⊨' ;

//=========== Parser rules. ==============

program: (predProof EOF)
       | (propProof EOF)
       | (predSemanticEntailment EOF)
       | (propSemanticEntailment EOF)
       | (propositionalWff COMMA propositionalWff EOF)
       | (predicateWff COMMA predicateWff EOF)
       | (propositionalWff EOF)
       | (predicateWff EOF);

/* Propositional Logic Rules. */
atom: ATOM;

/* Starting rule. */
propositionalWff: propWff;

propWff: atom
    | propNegRule
    | propAndRule
    | propOrRule
    | propImpRule
    | propBicondRule
    | propExclusiveOrRule;

propNegRule: NEG propWff;
propAndRule: OPEN_PAREN propWff AND propWff CLOSE_PAREN;
propOrRule : OPEN_PAREN propWff OR propWff CLOSE_PAREN;
propImpRule: OPEN_PAREN propWff IMP propWff CLOSE_PAREN;
propBicondRule: OPEN_PAREN propWff BICOND propWff CLOSE_PAREN;
propExclusiveOrRule: OPEN_PAREN propWff XOR propWff CLOSE_PAREN;

/* Predicate Logic Rules. */
constant: CONSTANT;
variable: VARIABLE;
universal: (OPEN_PAREN UNIVERSAL variable CLOSE_PAREN) | (OPEN_PAREN variable CLOSE_PAREN);
existential: OPEN_PAREN EXISTENTIAL variable CLOSE_PAREN;
predicate: atom(constant|variable)+;

/* Starting rule. */
predicateWff: predWff;

predWff: predicate
    | predNegRule
    | predQuantifier
    | predAndRule
    | predOrRule
    | predImpRule
    | predBicondRule
    | predExclusiveOrRule
    | predIdentityRule;

predQuantifier: NEG? (existential | universal) predWff;
predNegRule: NEG predWff;
predAndRule: OPEN_PAREN predWff AND predWff CLOSE_PAREN;
predOrRule : OPEN_PAREN predWff OR predWff CLOSE_PAREN;
predImpRule: OPEN_PAREN predWff IMP predWff CLOSE_PAREN;
predBicondRule: OPEN_PAREN predWff BICOND predWff CLOSE_PAREN;
predExclusiveOrRule: OPEN_PAREN predWff XOR predWff CLOSE_PAREN;
predIdentityRule: (constant|variable) IDENTITY (constant|variable);

/* Proof rules. */
/* Proof for predicate logic. */
predPremise: ((predicateWff (COMMA|SEMICOLON)) | predicateWff);
predConclusion: predicateWff;
predProof: predPremise+ THEREFORE predConclusion;

/* Proof for propositional logic. */
propPremise: ((propositionalWff (COMMA|SEMICOLON)) | propositionalWff);
propConclusion: propositionalWff;
propProof: propPremise+ THEREFORE propConclusion;

/* Semantic entailment for predicate logic. */
predSemanticEntailment: predPremise+ SEMANTIC_ENTAILMENT predicateWff;

/* Semantic entailment for propositional logic. */
propSemanticEntailment: propPremise+ SEMANTIC_ENTAILMENT propositionalWff;