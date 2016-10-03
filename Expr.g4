grammar Expr; 

prog:	expr*;

expr:	'(' expr ('.O.'|'.A.'|'.I.') expr ')'
    |	'(' ('.N.') expr ')'
    |   ID
    ;

OR : '.O.';
AND: '.A.';
IMP: '.I.';
ID:       [a-z];  
WHITESPACE : ( '\t' | ' ' | '\r' | '\n'| '\u000C' )+ -> skip ;