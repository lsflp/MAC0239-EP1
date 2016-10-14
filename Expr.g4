grammar Expr; 

tableaux:	prog EOF;

prog:	expr*;																												

expr:	op='.N.' expr								# OpNot
    |	expr op=('.O.'|'.A.'|'.I.') expr    		# Op2Atom
    |	'(' expr ')'								# Paren
    |   ID											# Atom
    ;

OR : '.O.';
AND: '.A.';
IMP: '.I.';
NOT: '.N.';
ID:       [a-z];  
WHITESPACE : ( '\t' | ' ' | '\r' | '\n'| '\u000C' )+ -> skip ;