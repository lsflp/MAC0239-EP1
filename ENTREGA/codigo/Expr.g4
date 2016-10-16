grammar Expr; 

prog:	 stat+					#progrule
	;					
 
stat:   expr NEWLINE            # printExpr
    |   ID '=' expr NEWLINE     # assign
    |   NEWLINE                 # blank
    ;

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
NEWLINE:    '\r'? '\n';
WS:         [ \t]+ -> skip;